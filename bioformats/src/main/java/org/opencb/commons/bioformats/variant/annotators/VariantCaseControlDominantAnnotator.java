package org.opencb.commons.bioformats.variant.annotators;

import org.opencb.commons.bioformats.feature.Genotype;
import org.opencb.commons.bioformats.pedigree.Condition;
import org.opencb.commons.bioformats.pedigree.Individual;
import org.opencb.commons.bioformats.pedigree.Pedigree;
import org.opencb.commons.bioformats.variant.Variant;

import java.util.*;

/**
 * Created by lcruz on 31/03/14.
 */
public class VariantCaseControlDominantAnnotator implements VariantAnnotator {
    Set<String> setAffected = new HashSet<String>();
    Set<String> setControls = new HashSet<String>();

    public static final String DCC_SCORE_TAG = "DCC_Score";

    public VariantCaseControlDominantAnnotator (Pedigree pedigree){
        setAffected.addAll(pedigree.getIndividuals(Condition.AFFECTED));
        setControls.addAll(pedigree.getIndividuals(Condition.UNAFFECTED));
    }

    @Override
    public void annot(List<Variant> batch) {
        if(batch == null || batch.isEmpty()){
            throw new UnsupportedOperationException("The list of variants is empty");
        } else {
            Map<String, Boolean> mapBadSampleNames = new HashMap<String, Boolean>();
            Boolean sampleNamesFilled = false;
            Integer numMutations = new Integer(0);

            // Iterate the list of variants to check if this variant is mutated
            for(Variant variant: batch){
                Map<Integer, String> mapAlleleMutationValues = new HashMap<Integer, String>();
                Integer numAffected = setControls.size();

                // Iterate the affected and put the mutation number in a map
                for (String sampleName : setAffected) {
                    Genotype genotype;
                    genotype = new Genotype(variant.getSampleData(sampleName, "GT"));
                    if (genotype.getAllele1() != null && genotype.getAllele1().compareTo(0) > 0) {
                        mapAlleleMutationValues.put(genotype.getAllele1(), sampleName);
                    }
                    if (genotype.getAllele2() != null && genotype.getAllele2().compareTo(0) > 0) {
                        mapAlleleMutationValues.put(genotype.getAllele2(), sampleName);
                    }

                    if(!sampleNamesFilled){
                        mapBadSampleNames.put(sampleName, false);
                    }
                }

                // Set the var to true, to don't let fill the mapBadSamplesNames again
                sampleNamesFilled = true;

                // If there is no alleles with a mutation, score = 0
                if(mapAlleleMutationValues.isEmpty()){
                    variant.addAttribute(DCC_SCORE_TAG, "0");
                } else {
                    // Try if all the allele mutations are in the control of this variant
                    Iterator<String> it = setControls.iterator();
                    String sampleName;

                    while(it.hasNext() && !mapAlleleMutationValues.isEmpty()){
                        sampleName = it.next();

                        Genotype genotype = new Genotype(variant.getSampleData(sampleName, "GT"));
                        if(genotype.getAllele1() != null && mapAlleleMutationValues.containsKey(genotype.getAllele1())){
                            mapAlleleMutationValues.remove(genotype.getAllele1());
                        }
                        if(genotype.getAllele2() != null && mapAlleleMutationValues.containsKey(genotype.getAllele2())){
                            mapAlleleMutationValues.remove(genotype.getAllele2());
                        }
                    }

                    if(mapAlleleMutationValues.isEmpty()){
                        variant.addAttribute(DCC_SCORE_TAG, "0");
                    } else {
                        for (Map.Entry<Integer, String> entry: mapAlleleMutationValues.entrySet()) {
                            mapBadSampleNames.put(entry.getValue().toString(), true);
                        }
                    }
                }
            }

            for (Map.Entry<String, Boolean> entry: mapBadSampleNames.entrySet()) {
                if(Boolean.valueOf(entry.getValue().toString())){
                    numMutations++;
                }
            }

            // Write the score annotation for the variants that haven't been annotated yet
            for(Variant variant: batch){
                if (variant.getAttribute(DCC_SCORE_TAG) == null || !variant.getAttribute(DCC_SCORE_TAG).equalsIgnoreCase("0")){
                    variant.addAttribute(DCC_SCORE_TAG, numMutations+"/"+setAffected.size());
                }
            }
        }
    }

    @Override
    public void annot(Variant elem) {
        throw new UnsupportedOperationException("This annotator doesn't work on individual variants");
    }
}
