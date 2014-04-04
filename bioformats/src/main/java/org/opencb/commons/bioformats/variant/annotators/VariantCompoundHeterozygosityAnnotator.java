package org.opencb.commons.bioformats.variant.annotators;

import org.opencb.commons.bioformats.pedigree.Condition;
import org.opencb.commons.bioformats.pedigree.Pedigree;
import org.opencb.commons.bioformats.variant.Variant;
import org.opencb.commons.bioformats.variant.utils.VariantUtils;

import java.util.*;

/**
 * Created by parce on 12/30/13.
 */
public class VariantCompoundHeterozygosityAnnotator implements VariantAnnotator {

    private Set<String> affectedIndividuals;
    private Set<String> unaffectedIndividuals;

    //private Map<Variant,Set<Integer>> sharedAllelesMap;

    public static final String COMPOUND_HETEROZYGOUS_TAG = "CH";

    public VariantCompoundHeterozygosityAnnotator(Pedigree pedigree) {
        super();
        this.affectedIndividuals = pedigree.getIndividuals(Condition.AFFECTED);
        this.unaffectedIndividuals = pedigree.getIndividuals(Condition.UNAFFECTED);
    }

    @Override
    public void annot(List<Variant> batch) {
        Map<Variant,Set<Integer>> sharedAllelesMap = new HashMap<>();
        List<Variant> variantsWhoPassedTheFirstFilter = affectedSamplesSharedAllelesHomozygousInUnaffectedSamplesFilter(batch, sharedAllelesMap);
        if (variantsWhoPassedTheFirstFilter.size() > 1) {
            compoundHeterozygosityAnnot(variantsWhoPassedTheFirstFilter, sharedAllelesMap);
        }
    }

    @Override
    public void annot(Variant elem) {
        throw new UnsupportedOperationException("Variant Compound Heterozygosity annotator cannot be applied to individual variants");
    }

    /**
     * Filter all the variants that dont'share alternative alleles between the affected samples, o are homozigous in
     * some unaffected sample for all those (affected samples)shared alleles
     * @param variantsToBeFiltered Unfiltered variant list
     * @return Filtered variant list, containing all variants that are not
     */
    private List<Variant> affectedSamplesSharedAllelesHomozygousInUnaffectedSamplesFilter(List<Variant> variantsToBeFiltered, Map<Variant,Set<Integer>> sharedAllelesMap) {
        List<Variant> passedVariants = new ArrayList<>();
        //int[] alleles = new int[2];
        for (Variant variant : variantsToBeFiltered) {
            // obtain the shared alternative alleles from the affected samples
            Set<Integer> alternativeAlleles = VariantUtils.samplesShareAlternativeAllelesInVariant(this.affectedIndividuals, variant);
            if (alternativeAlleles.size() > 0) {
                // check that at least one shared alleles is not homozygous in any unaffected sample
                if (!VariantUtils.alternativeAllelesAreHomozygousInSamples(variant, alternativeAlleles, this.unaffectedIndividuals)) {
                    passedVariants.add(variant);
                    sharedAllelesMap.put(variant, alternativeAlleles);
                }
            }
        }
        return passedVariants;
    }


    private void compoundHeterozygosityAnnot(List<Variant> variantsToBeFiltered, Map<Variant, Set<Integer>> sharedAllelesMap) {
        Set<Variant> passedVariants = new HashSet<>();
        List<Variant[]> pairs = variantsPairs(variantsToBeFiltered);
        // check every possible pair to see if they pass the filter
        for (Variant[] pair : pairs) {
            Variant firstVariant = pair[0];
            Variant secondVariant = pair[1];
                // combinaciones de alelos entre
                Set<Integer> allelesFirstVariant = sharedAllelesMap.get(firstVariant);
                Set<Integer> allelesSecondVariant = sharedAllelesMap.get(secondVariant);
                boolean validPairOfVariants = false;
                Iterator<Integer> itAllelesFirstVariant = allelesFirstVariant.iterator();
                while (itAllelesFirstVariant.hasNext() && !validPairOfVariants) {
                    Integer alleleFirstVariant = itAllelesFirstVariant.next();
                    Iterator<Integer> itAllelesSecondVariant = allelesSecondVariant.iterator();
                    while (itAllelesSecondVariant.hasNext() && !validPairOfVariants)  {
                        Integer alleleSecondVariant = itAllelesSecondVariant.next();
                        boolean allelesCombinationInUnaffectedSample = VariantUtils.allelesCombinationInAnySample(firstVariant,
                                secondVariant,
                                alleleFirstVariant,
                                alleleSecondVariant,
                                this.unaffectedIndividuals);
                        // if we found a genotype that is
                        if (!allelesCombinationInUnaffectedSample) {
                            validPairOfVariants = true;
                        }
                    }
                }
                if (validPairOfVariants) {
                    // annotate each variant with chr_pos_ref_alt from its pair
                    pair[0].addAttribute(COMPOUND_HETEROZYGOUS_TAG, this.getCHTagValue(pair[0], pair[1]));
                    pair[1].addAttribute(COMPOUND_HETEROZYGOUS_TAG, this.getCHTagValue(pair[1], pair[0]));
                }
            }
    }

    private String getCHTagValue(Variant variant, Variant mate) {
        // previous tag value
        String previousTagValue = variant.getAttribute(COMPOUND_HETEROZYGOUS_TAG);
        StringBuilder tagValue = new StringBuilder(previousTagValue == null?"":previousTagValue + ":");
        tagValue.append(mate.getChromosome()).append('_').append(mate.getPosition()).append('_').append(mate.getReference()).append('_').append(mate.getAlternate());
        return tagValue.toString();
    }

    private List<Variant[]> variantsPairs(List<Variant> variantList) {
        // TODO: hacer este metodo mas elegante
        List<Variant[]> pairs = new ArrayList<>();
        for (int i=0; i<variantList.size()-1; i ++) {
            for (int j=i+1; j<variantList.size(); j++) {
                Variant[] pair = new Variant[2];
                pair[0] = variantList.get(i);
                pair[1] = variantList.get(j);
                pairs.add(pair);
            }
        }
        return pairs;
    }
}
