package org.opencb.commons.bioformats.variant.utils;

import org.opencb.commons.bioformats.feature.AllelesCode;
import org.opencb.commons.bioformats.feature.Genotype;
import org.opencb.commons.bioformats.variant.Variant;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by parce on 3/24/14.
 */
public class VariantUtils {

    public static boolean alternativeAllelesAreHomozygousInSamples(Variant variant, Set<Integer> altAlleles, Set<String> samples) {
        boolean allAllelesHomozygousInSomeSample = false;
        // check the samples
        for (String sample : samples) {
            Genotype sampleGenotype = new Genotype(variant.getSampleData(sample, "GT"));
            if ((sampleGenotype.getCode() != AllelesCode.ALL_ALLELES_MISSING) &&
                    (!sampleGenotype.isAllele1Ref()) &&
                    (sampleGenotype.getAllele1() == sampleGenotype.getAllele2())) {
                // sample is homozygous for one allele, check if this allele is in the alleles set
                for (Iterator<Integer> altAllelesIterator = altAlleles.iterator(); altAllelesIterator.hasNext();) {
                    int sharedAllele = altAllelesIterator.next();
                    if (sampleGenotype.getAllele1() == sharedAllele) {
                        altAllelesIterator.remove();
                    }
                }
                // if the shared alternative alleles set is empty, every allele is homozygous at least in one of the samples
                if (altAlleles.size() == 0) {
                    allAllelesHomozygousInSomeSample = true;
                    break;
                }
            }
        }
        return allAllelesHomozygousInSomeSample;
    }

    public static Set<Integer> samplesShareAlternativeAllelesInVariant(Set<String> samples, Variant variant) {
        Set<Integer> sharedAlternativeAlleles = new HashSet<>();
        boolean firstSample = true;
        for (String sample : samples) {
            Genotype sampleGenotype = new Genotype(variant.getSampleData(sample, "GT"));
            if (sampleGenotype.getCode() != AllelesCode.ALL_ALLELES_MISSING) {
                //allAffectedAreMissing = false;
                if (firstSample) {
                    if (sampleGenotype.isAllele1Ref() && sampleGenotype.isAllele2Ref()) {
                        // if an affected sample hasn't at least one alternative allele,
                        // there are no shared alternative alleles
                        sharedAlternativeAlleles.clear();
                        break;
                    }
                    firstSample = false;
                    // save the alternative alleles
                    if (!sampleGenotype.isAllele1Ref()) {
                        sharedAlternativeAlleles.add(sampleGenotype.getAllele1());
                    }
                    if (!sampleGenotype.isAllele2Ref()) {
                        sharedAlternativeAlleles.add(sampleGenotype.getAllele2());
                    }
                } else {
                    // remove from the alternative alleles set, all the alleles that are not present in the affected sample
                    for (Iterator<Integer> altAllIterator = sharedAlternativeAlleles.iterator(); altAllIterator.hasNext();) {
                        int sharedAllele = altAllIterator.next();
                        if (sampleGenotype.getAllele1() != sharedAllele && sampleGenotype.getAllele2() != sharedAllele) {
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

    /**
     * check if some of the samples has the alleles combination received by parameters
     * @param firstVariant - first variant
     * @param secondVariant - second variant
     * @param alleleFirstVariant - allele to be looked for in the first variant
     * @param alleleSecondVariant - allele to be looked for in the second variant
     * @return true if any unaffected sample has allele 1 in the first variant and allele 2 in the second variant
     */
    public static boolean allelesCombinationInAnySample(Variant firstVariant,
                                                         Variant secondVariant,
                                                         Integer alleleFirstVariant,
                                                         Integer alleleSecondVariant,
                                                         Set<String> samples)
    {
        boolean allelesCombinationInSample = false;
        for (String sample: samples) {
            if (firstVariant.sampleHasAllele(sample, alleleFirstVariant) &&
                    secondVariant.sampleHasAllele(sample, alleleSecondVariant))
            {
                allelesCombinationInSample = true;
                break;
            }
        }
        return allelesCombinationInSample;
    }
}
