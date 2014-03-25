package org.opencb.commons.bioformats.variant.vcf4.io.readers;

import org.opencb.commons.bioformats.variant.Variant;
import org.opencb.commons.bioformats.variant.annotators.VariantAnnotator;
import org.opencb.commons.bioformats.variant.annotators.VariantGeneNameAnnotator;

import java.util.*;

/**
 * @author Alejandro Aleman Ramos <aaleman@cipf.es>
 */
public class VariantVcfByGeneReader extends VariantVcfReader implements VariantReader {

    private Map<String, List<Variant>> variantsByGene;
    private VariantAnnotator geneNameAnnot;
    private List<List<Variant>> buffer;
    private boolean annot;

    public VariantVcfByGeneReader(String filename) {
        this(filename, false);
    }

    public VariantVcfByGeneReader(String filename, boolean annot) {
        super(filename);
        this.variantsByGene = new LinkedHashMap<>();
        this.geneNameAnnot = new VariantGeneNameAnnotator();
        this.buffer = new LinkedList<>();
        this.annot = annot;

    }

    @Override
    public Variant read() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Variant> read(int batchSize) {

        List<Variant> listRecords = new ArrayList<>();
        List<Variant> list;
        boolean finishedGene = false;

        if (!buffer.isEmpty()) {
            return buffer.remove(0);
        }

        Variant variant = super.read();
        if (variant != null) {

            if (annot) {
                geneNameAnnot.annot(variant);
            }
            while (variant != null && !finishedGene) {
                if (variant.containsAttribute("GeneNames")) {
                    String[] genes = variant.getAttribute("GeneNames").split(",");
                    for (String gene : genes) {
                        if (variantsByGene.containsKey(gene)) {
                            list = variantsByGene.get(gene);
                        } else {
                            list = new ArrayList<>();
                            variantsByGene.put(gene, list);
                        }
                        list.add(variant);
                    }

                    finishedGene = this.checkFinishedGenes(genes);
                }

                if (!finishedGene) {
                    variant = super.read();
                    if (annot) {
                        geneNameAnnot.annot(variant);
                    }
                }
            }
        } else {
            this.checkFinishedGenes(new String[]{});
        }

        listRecords = (buffer.size() > 0) ? buffer.remove(0) : listRecords;

        return listRecords;
    }

    private boolean checkFinishedGenes(String[] genes) {

        List<String> aux = Arrays.asList(genes);
        List<String> removableGenes = new LinkedList<>();
        boolean finishedGenes = false;

        for (Map.Entry<String, List<Variant>> entry : this.variantsByGene.entrySet()) {
            if (!aux.contains(entry.getKey())) {
                buffer.add(entry.getValue());
                removableGenes.add(entry.getKey());

            }
        }
        for (String gene : removableGenes) {
            variantsByGene.remove(gene);
            finishedGenes = true;
        }

        return finishedGenes;
    }
}
