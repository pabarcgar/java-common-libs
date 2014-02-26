package org.opencb.commons.bioformats.variant.effect;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.multipart.FormDataMultiPart;
import org.opencb.commons.bioformats.variant.Variant;
import org.opencb.commons.bioformats.variant.utils.effect.VariantEffect;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Alejandro Aleman Ramos <aaleman@cipf.es>
 */
public class EffectCalculator {

    public static List<VariantEffect> getEffects(List<Variant> batch) {
        ObjectMapper mapper = new ObjectMapper();
        List<VariantEffect> batchEffect = new ArrayList<>(batch.size());

        if (batch.isEmpty()) {
            return batchEffect;
        }

        StringBuilder chunkVcfRecords = new StringBuilder();
        Client client = Client.create();
        WebResource webResource = client.resource("http://ws.bioinfo.cipf.es/cellbase/rest/latest/hsa/genomic/variant/");

        javax.ws.rs.client.Client clientNew = ClientBuilder.newClient();
//        WebTarget webTarget = clientNew.target("http://ws-beta.bioinfo.cipf.es/cellbase/rest/v3/hsapiens/feature/transcript/");

        for (Variant record : batch) {
            chunkVcfRecords.append(record.getChromosome()).append(":");
            chunkVcfRecords.append(record.getPosition()).append(":");
            chunkVcfRecords.append(record.getReference()).append(":");
            chunkVcfRecords.append(record.getAlternate()).append(",");
        }

        FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
        formDataMultiPart.field("variants", chunkVcfRecords.substring(0, chunkVcfRecords.length() - 1));

//        Response response = webTarget.path("consequence_type").queryParam("of", "json").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(formDataMultiPart.toString(), MediaType.MULTIPART_FORM_DATA_TYPE));
        String response = webResource.path("consequence_type").queryParam("of", "json").type(MediaType.MULTIPART_FORM_DATA).post(String.class, formDataMultiPart);

        // TODO aaleman: Check the new Web Service

        try {
            batchEffect = mapper.readValue(response.toString(), mapper.getTypeFactory().constructCollectionType(List.class, VariantEffect.class));
        } catch (IOException e) {
            System.err.println(chunkVcfRecords.toString());
            e.printStackTrace();
        }

        return batchEffect;
    }

    public static List<VariantEffect> getEffectsWithPolyPhenAndSift(List<Variant> batch) {
        ObjectMapper mapper = new ObjectMapper();
        List<VariantEffect> batchEffect = new ArrayList<>(batch.size());

        if (batch.size() == 0) {
            return batchEffect;
        }

        StringBuilder chunkVcfRecords = new StringBuilder();
        Client client = Client.create();
        WebResource webResource = client.resource("http://ws.bioinfo.cipf.es/cellbase/rest/latest/hsa/genomic/variant/");

        javax.ws.rs.client.Client clientNew = ClientBuilder.newClient();
        WebTarget webTarget = clientNew.target("http://ws-beta.bioinfo.cipf.es/cellbase/rest/v3/hsapiens/feature/transcript/");

        for (Variant record : batch) {
            chunkVcfRecords.append(record.getChromosome()).append(":");
            chunkVcfRecords.append(record.getPosition()).append(":");
            chunkVcfRecords.append(record.getReference()).append(":");
            chunkVcfRecords.append(record.getAlternate()).append(",");
        }

        FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
        formDataMultiPart.field("variants", chunkVcfRecords.substring(0, chunkVcfRecords.length() - 1));

//        Response response = webTarget.path("consequence_type").queryParam("of", "json").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(formDataMultiPart.toString(), MediaType.MULTIPART_FORM_DATA_TYPE));
        String response = webResource.path("consequence_type").queryParam("of", "json").type(MediaType.MULTIPART_FORM_DATA).post(String.class, formDataMultiPart);

        // TODO aaleman: Check the new Web Service

        try {
            batchEffect = mapper.readValue(response.toString(), mapper.getTypeFactory().constructCollectionType(List.class, VariantEffect.class));
        } catch (IOException e) {
            System.err.println(chunkVcfRecords.toString());
            e.printStackTrace();
        }


        javax.ws.rs.core.Response newResponse;
        double ss, ps;
        int se, pe;

        for (VariantEffect effect : batchEffect) {
            if (effect.getAaPosition() != -1 && effect.getTranscriptId() != "" && effect.getAminoacidChange().length() == 3) {

                String change = effect.getAminoacidChange().split("/")[1];

                newResponse = webTarget.path(effect.getTranscriptId()).path("function_prediction").queryParam("aaPosition", effect.getAaPosition()).queryParam("aaChange", change).
                        request(MediaType.APPLICATION_JSON_TYPE).get();

                ObjectMapper mapperNew = new ObjectMapper();
                JsonNode actualObj;

                String resp = null;
                try {
                    resp = newResponse.readEntity(String.class);
                    actualObj = mapperNew.readTree(resp);
                    Iterator<JsonNode> it = actualObj.get("response").iterator();

                    while (it.hasNext()) {
                        JsonNode polyphen = it.next();
                        if (polyphen.get("numResults").asInt() > 0) {
                            Iterator<JsonNode> itResults = polyphen.get("result").iterator();
                            while (itResults.hasNext()) {
                                JsonNode aa = itResults.next();

                                if (aa.has("aaPositions") && aa.get("aaPositions").has("" + effect.getAaPosition()) && aa.get("aaPositions").get("" + effect.getAaPosition()).has("" + change)) {

                                    JsonNode val = aa.get("aaPositions").get("" + effect.getAaPosition()).get("" + change);

                                    if (val.has("ss") && val.has("ps") && val.has("se") && val.has("pe")) {
                                        if (!val.get("ss").isNull()) {
                                            ss = val.get("ss").asDouble();
                                            effect.setSiftScore(ss);
                                        }

                                        if (!val.get("ps").isNull()) {
                                            ps = val.get("ps").asDouble();
                                            effect.setPolyphenScore(ps);
                                        }

                                        if (!val.get("se").isNull()) {
                                            se = val.get("se").asInt();
                                            effect.setSiftEffect(se);
                                        }
                                        if (!val.get("pe").isNull()) {
                                            pe = val.get("pe").asInt();
                                            effect.setPolyphenEffect(pe);
                                        }
                                    }
                                }
                            }
                        }
                    }

                } catch (JsonParseException e) {
                    System.err.println(resp);
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

        return batchEffect;
    }

    public static List<List<VariantEffect>> getEffectPerVariant(List<Variant> batch) {
        List<List<VariantEffect>> list = new ArrayList<>(batch.size());
        List<VariantEffect> auxEffect;
        List<VariantEffect> effects = getEffects(batch);
        VariantEffect effect;

        for (Variant record : batch) {
            auxEffect = new ArrayList<>(20);
            for (int i = 0; i < effects.size(); i++) {
                effect = effects.get(i);
                if (record.getChromosome().equals(effect.getChromosome())
                        && record.getPosition() == effect.getPosition()
                        && record.getReference().equals(effect.getReferenceAllele())
                        && record.getAlternate().equals(effect.getAlternativeAllele())) {
                    auxEffect.add(effect);
                }
            }
            list.add(auxEffect);
        }
        return list;
    }
}
