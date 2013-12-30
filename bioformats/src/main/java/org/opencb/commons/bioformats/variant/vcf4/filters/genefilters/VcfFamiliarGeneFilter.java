package org.opencb.commons.bioformats.variant.vcf4.filters.genefilters;

import org.broadinstitute.variant.vcf.VCFStandardHeaderLines;
import org.opencb.commons.bioformats.feature.AllelesCode;
import org.opencb.commons.bioformats.feature.Genotype;
import org.opencb.commons.bioformats.variant.vcf4.VcfRecord;

import java.util.*;

/**
 * Created by parce on 12/30/13.
 */
public class VcfFamiliarGeneFilter extends VcfGeneFilter {

    private Set<String> affectedIndividuals;
    private Set<String> unaffectedIndividuals;

    private Map<VcfRecord,Set<Integer>> sharedAllelesMap;

    public VcfFamiliarGeneFilter(Set<String> affectedIndividuals, Set<String> unaffectedIndividuals) {
        this.affectedIndividuals = affectedIndividuals;
        this.unaffectedIndividuals = unaffectedIndividuals;
    }


    public List<VcfRecord> apply(List<VcfRecord> variantsToBeFiltered) {
        // TODO: cambiar nombre variable y metodo
        // TODO: list o set, y tipo de list, o null
        List<VcfRecord> variantsWhoPassedBothsFilters = new ArrayList<VcfRecord>();

        List<VcfRecord> variantsWhoPassedTheFirstFilter = singleVariantFilter(variantsToBeFiltered);
        if (variantsWhoPassedTheFirstFilter.size() > 1) {
            variantsWhoPassedBothsFilters.addAll(multiVariantFilter(variantsWhoPassedTheFirstFilter));
        }

        return variantsWhoPassedBothsFilters;
    }

    private List<VcfRecord> singleVariantFilter(List<VcfRecord> variantsToBeFiltered) {
        // TODO: ¿array o linked? -> creo que aqui array
        List<VcfRecord> passedVariants = new ArrayList<VcfRecord>();
        this.sharedAllelesMap = new HashMap<VcfRecord, Set<Integer>>();
        //int[] alleles = new int[2];
        for (VcfRecord variant : variantsToBeFiltered) {
            // obtain the shared alternative alleles from the affected samples
            Set<Integer> alternativeAlleles = this.affectedSamplesSharedAlternativeAllelesInVariant(variant);
            if (alternativeAlleles.size() > 0) {
                // check that at least one shared alleles is not homozigous in any unaffected sample
                if (!this.sharedAllelesAreHomozigousInUnaffectedSamples(variant, alternativeAlleles)) {
                    passedVariants.add(variant);
                    this.sharedAllelesMap.put(variant, alternativeAlleles);
                }
            }
        }
        return passedVariants;
    }


    private Set<VcfRecord> multiVariantFilter(List<VcfRecord> variantsToBeFiltered) {
        Set<VcfRecord> res = new HashSet<VcfRecord>();
        List<VcfRecord[]> pairs = variantsPairs(variantsToBeFiltered);
        for (VcfRecord[] pair : pairs) {
            if (!res.contains(pair[0]) || !res.contains(pair[1])) {
                boolean validPair = true;
                for (String unaffectedSample : this.unaffectedIndividuals) {
                    // TODO: cambiar nombre metodo
                    if (sampleHasAllele(unaffectedSample, pair[0], sharedAllelesMap.get(pair[0])) &&
                            sampleHasAllele(unaffectedSample, pair[1], sharedAllelesMap.get(pair[1])))
                    {
                        validPair = false;
                        break;
                    }
                }
                if (validPair) {
                    res.add(pair[0]);
                    res.add(pair[1]);
                }
            }
        }
        // TODO: realmente no es necesario liberar el map, pero, ¿conviene?
        sharedAllelesMap.clear();
        return res;
    }

    // TODO: quizas este metodo pueda moverse a una clase mas general, por ejemplo, VcfRecord
    private boolean sampleHasAllele(String sample, VcfRecord variant, Set<Integer> alleles) {
        boolean hasAllele = false;
        for (int allele : alleles) {
            Genotype sampleGenotype = variant.getSampleGenotype(sample);
            if (sampleGenotype.getCode() != AllelesCode.ALL_ALLELES_MISSING &&
                    (sampleGenotype.getAllele1() == allele || sampleGenotype.getAllele2() == allele))
            {
                // TODO: basta con que el sample tenga alguno de los alelos??
                hasAllele = true;
                break;
            }
        }
        return hasAllele;
    }

    private List<VcfRecord[]> variantsPairs(List<VcfRecord> variantList) {
        // TODO: hacer este metodo mas elegante
        List<VcfRecord[]> pairs = new ArrayList<VcfRecord[]>();
        for (int i=0; i<variantList.size()-1; i ++) {
            for (int j=i+1; j<variantList.size(); j++) {
                VcfRecord[] pair = new VcfRecord[2];
                pair[0] = variantList.get(i);
                pair[1] = variantList.get(j);
                pairs.add(pair);
            }
        }
        return pairs;
    }

    private Set<Integer> affectedSamplesSharedAlternativeAllelesInVariant(VcfRecord variant) {
        Set<Integer> sharedAlternativeAlleles = new HashSet<Integer>();
        boolean firstAffectedSample = true;
        for (String affectedSample : this.affectedIndividuals) {
            Genotype affectedSampleGenotype = variant.getSampleGenotype(affectedSample);
            if (affectedSampleGenotype.getCode() != AllelesCode.ALL_ALLELES_MISSING) {
                //allAffectedAreMissing = false;
                if (firstAffectedSample) {
                    if (affectedSampleGenotype.isAllele1Ref() && affectedSampleGenotype.isAllele2Ref()) {
                        // if an affected sample hasn't at least one alternative allele,
                        // there are no shared alternative alleles
                        sharedAlternativeAlleles.clear();
                        break;
                    }
                    firstAffectedSample = false;
                    // save the alternative alleles
                    if (!affectedSampleGenotype.isAllele1Ref()) {
                        sharedAlternativeAlleles.add(affectedSampleGenotype.getAllele1());
                    }
                    if (!affectedSampleGenotype.isAllele2Ref()) {
                        sharedAlternativeAlleles.add(affectedSampleGenotype.getAllele2());
                    }
                } else {
                    // remove from the alternative alleles set, all the alleles that are not present in the affected sample
                    for (Iterator<Integer> altAllIterator = sharedAlternativeAlleles.iterator(); altAllIterator.hasNext();) {
                        Integer sharedAllele = altAllIterator.next();
                        if (affectedSampleGenotype.getAllele1() != sharedAllele && affectedSampleGenotype.getAllele2() != sharedAllele) {
                            altAllIterator.remove();
                        }
                    }
                    if (sharedAlternativeAlleles.size() == 0) {
                        // there is no common alternative alleles between all the affected samples
                        break;
                    }
                }
            }
        }
        return sharedAlternativeAlleles;
    }

    private boolean sharedAllelesAreHomozigousInUnaffectedSamples(VcfRecord variant, Set<Integer> sharedAlternativeAlleles) {
        boolean allSharedAllelesHomozigousInUnaffected = false;
        // check the unaffected samples
        for (String unaffectedSample : this.unaffectedIndividuals) {
            Genotype unaffectedSampleGenotype = variant.getSampleGenotype(unaffectedSample);
            if ((unaffectedSampleGenotype.getCode() != AllelesCode.ALL_ALLELES_MISSING) &&
                    (!unaffectedSampleGenotype.isAllele1Ref()) &&
                    (unaffectedSampleGenotype.getAllele1() == unaffectedSampleGenotype.getAllele2())) {
                // check if unaffected sample is homozigous for one of the alternative alleles
                for (Iterator<Integer> altAllIterator = sharedAlternativeAlleles.iterator(); altAllIterator.hasNext();) {
                    Integer sharedAllele = altAllIterator.next();
                    if (unaffectedSampleGenotype.getAllele1() == sharedAllele) {
                        altAllIterator.remove();
                    }
                }
                // if the shared alternative alleles set is empty, every shared alleled is homozigous
                // at least in one of the unaffected samples
                if (sharedAlternativeAlleles.size() == 0) {
                    allSharedAllelesHomozigousInUnaffected = true;
                    break;
                }
            }
        }
        return allSharedAllelesHomozigousInUnaffected;
    }
}
