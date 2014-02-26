package org.opencb.commons.bioformats.variant.filters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.opencb.commons.bioformats.feature.Region;
import org.opencb.commons.bioformats.variant.Variant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Alejandro Aleman Ramos <aaleman@cipf.es>
 */
public class VariantGeneFilter extends VariantFilter {
    private List<Region> regionList;
    private List<String> geneList;


    public VariantGeneFilter(String genes) {
        this.regionList = new ArrayList<>(10);
        this.geneList = Splitter.on(",").splitToList(genes);
        populateRegionList();
    }

    public VariantGeneFilter(String genes, int priority) {
        super(priority);
        this.regionList = new ArrayList<>(10);
        this.geneList = Splitter.on(",").splitToList(genes);
        populateRegionList();
    }


    public VariantGeneFilter(File file) {
        this.regionList = new ArrayList<>(10);
        this.geneList = parseFile(file);
        populateRegionList();

    }

    public VariantGeneFilter(File file, int priority) {
        super(priority);
        this.geneList = parseFile(file);
        populateRegionList();
    }

    private List<String> parseFile(File file) {
        List<String> genes = new ArrayList<>(10);
        BufferedReader br;
        String line;
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                if (!line.equals("")) {
                    genes.add(line);

                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return genes;
    }

    private void populateRegionList() {

//        Client wsRestClient = ClientBuilder.newClient();
//        WebTarget webResource = wsRestClient.target("http://ws.bioinfo.cipf.es/cellbase/rest/latest/hsa/feature/gene/");

        Client wsRestClient = Client.create();
        WebResource webResource = wsRestClient.resource("http://ws.bioinfo.cipf.es/cellbase/rest/latest/hsa/feature/gene/");

        ObjectMapper mapper = new ObjectMapper();

        Iterator<String> it = geneList.iterator();
        while (it.hasNext()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 100 && it.hasNext(); i++) {
                sb.append(it.next()).append(",");
            }
            String response = webResource.path(sb.toString()).path("info").queryParam("of", "json").get(String.class);

            JsonNode actualObj;
            try {
                actualObj = mapper.readTree(response.toString());
                Iterator<JsonNode> itAux = actualObj.iterator();
                Iterator<JsonNode> aux;

                while (itAux.hasNext()) {
                    JsonNode node = itAux.next();
                    if (node.isArray()) {

                        aux = node.iterator();
                        while (aux.hasNext()) {
                            JsonNode auxNode = aux.next();

                            String chr = auxNode.get("chromosome").asText();
                            long start = auxNode.get("start").asLong();
                            long end = auxNode.get("end").asLong();

                            Region r = new Region(chr, start, end);
                            regionList.add(r);

                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("regionList = " + regionList);
    }

    @Override
    public boolean apply(Variant variant) {

        for (Region r : regionList) {
            if (r.contains(variant.getChromosome(), variant.getPosition())) {
                return true;
            }
        }

        return false;
    }
}
