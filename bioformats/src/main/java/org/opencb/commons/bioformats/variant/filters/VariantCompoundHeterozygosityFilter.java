package org.opencb.commons.bioformats.variant.filters;

import org.opencb.commons.bioformats.feature.AllelesCode;
import org.opencb.commons.bioformats.feature.Genotype;
import org.opencb.commons.bioformats.pedigree.Condition;
import org.opencb.commons.bioformats.pedigree.Pedigree;
import org.opencb.commons.bioformats.variant.Variant;
import org.opencb.commons.bioformats.variant.utils.VariantUtils;

import java.util.*;

/**
 * Created by parce on 12/30/13.
 */
public class VariantCompoundHeterozygosityFilter extends VariantGeneLevelFilter {

    private Set<String> affectedIndividuals;
    private Set<String> unaffectedIndividuals;

    //private Map<Variant,Set<Integer>> sharedAllelesMap;

    public VariantCompoundHeterozygosityFilter(Pedigree pedigree) {
        super();
        this.readPedigree(pedigree);
    }

//    // TODO: parece que este constructor no se usa
//    public VariantCompoundHeterozygosityFilter(Set<String> affectedIndividuals, Set<String> unaffectedIndividuals) {
//        this();
//        this.affectedIndividuals = affectedIndividuals;
//        this.unaffectedIndividuals = unaffectedIndividuals;
//    }

    public List<Variant> apply(List<Variant> variantsToBeFiltered) {
        // TODO: cambiar nombre variable y metodo
        // TODO: list o set, y tipo de list, o null
        List<Variant> variantsWhoPassedBothFilters = new ArrayList<>();
        Map<Variant,Set<Integer>> sharedAllelesMap = new HashMap<>();
        List<Variant> variantsWhoPassedTheFirstFilter = affectedSamplesSharedAllelesHomozygousInUnaffectedSamplesFilter(variantsToBeFiltered, sharedAllelesMap);
        if (variantsWhoPassedTheFirstFilter.size() > 1) {
            variantsWhoPassedBothFilters.addAll(compoundHeterozygosityFilter(variantsWhoPassedTheFirstFilter, sharedAllelesMap));
        }

        return variantsWhoPassedBothFilters;
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


    private Set<Variant> compoundHeterozygosityFilter(List<Variant> variantsToBeFiltered, Map<Variant, Set<Integer>> sharedAllelesMap) {
        Set<Variant> passedVariants = new HashSet<>();
        List<Variant[]> pairs = variantsPairs(variantsToBeFiltered);
        // check every possible pair to see if they pass the filter
        for (Variant[] pair : pairs) {
            Variant firstVariant = pair[0];
            Variant secondVariant = pair[1];
            // check if both variants from the pair has already passed the filter
            if (!passedVariants.contains(firstVariant) || !passedVariants.contains(secondVariant)) {
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
                    passedVariants.add(pair[0]);
                    passedVariants.add(pair[1]);
                }
            }
        }
        return passedVariants;
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

    // TODO: ¿metodos para devolver estos conjuntos en la clase Pedigree?
    public void readPedigree(Pedigree pedigree) {
        this.affectedIndividuals = new HashSet<>();
        this.unaffectedIndividuals = new HashSet<>();
        for (String individualId : pedigree.getIndividuals().keySet()) {
            if (pedigree.getIndividual(individualId).getCondition() == Condition.AFFECTED) {
                this.affectedIndividuals.add(individualId);
            } else if (pedigree.getIndividual(individualId).getCondition() == Condition.UNAFFECTED) {
                this.unaffectedIndividuals.add(individualId);
            }
        }
    }


}
