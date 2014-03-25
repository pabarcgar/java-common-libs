package org.opencb.commons.bioformats.variant.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.opencb.commons.bioformats.variant.Variant;
import org.opencb.commons.test.GenericTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by parce on 3/24/14.
 */
public class VariantUtilsTest extends GenericTest {

    final String sample1 = "s1";
    final String sample2 = "s2";
    final String sample3 = "s3";
    final String sample4 = "s4";

    private Set<String> samples1to3;
    private Set<String> samples3and4;

    private Map<String, String> genotype00;
    private Map<String, String> genotype01;
    private Map<String, String> genotype02;
    private Map<String, String> genotype11;
    private Map<String, String> genotype12;
    private Map<String, String> genotype22;

    private Variant variantA;
    private Variant variantB;
    private Variant variantC;
    private Variant variantD;
    private Variant variantE;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        // genotypes
        genotype00 = new HashMap<>();
        genotype01 = new HashMap<>();
        genotype02 = new HashMap<>();
        genotype11 = new HashMap<>();
        genotype12 = new HashMap<>();
        genotype22 = new HashMap<>();
        genotype00.put(Variant.GENOTYPE_TAG, "0/0");
        genotype01.put(Variant.GENOTYPE_TAG, "0/1");
        genotype02.put(Variant.GENOTYPE_TAG, "0/2");
        genotype11.put(Variant.GENOTYPE_TAG, "1/1");
        genotype12.put(Variant.GENOTYPE_TAG, "1/2");
        genotype22.put(Variant.GENOTYPE_TAG, "2/2");
        // samples definition
        samples1to3 = new HashSet<>();
        samples1to3.add(sample1);
        samples1to3.add(sample2);
        samples1to3.add(sample3);
        samples3and4 = new HashSet<>();
        samples3and4.add(sample3);
        samples3and4.add(sample4);
        // samples genotypes in variants
        // sharing one allele Variant genotypes
//        sharingOneAlleleVariant.getSampleData(sample1, Variant.GENOTYPE_TAG); result = "1/2";(VARIANTA)
//        sharingOneAlleleVariant.getSampleData(sample2, Variant.GENOTYPE_TAG); result = "1/1";
//        sharingOneAlleleVariant.getSampleData(sample3, Variant.GENOTYPE_TAG); result = "0/1";
//        sharingOneAlleleVariant.getSampleData(sample4, Variant.GENOTYPE_TAG); result = "0/2";
//        // sharing one allele variant B genotypes
//        sharingOneAlleleVariantB.getSampleData(sample1, Variant.GENOTYPE_TAG); result = "1/2";(VARIANTB)
//        sharingOneAlleleVariantB.getSampleData(sample2, Variant.GENOTYPE_TAG); result = "1/1";
//        sharingOneAlleleVariantB.getSampleData(sample3, Variant.GENOTYPE_TAG); result = "0/2";
//        sharingOneAlleleVariantB.getSampleData(sample4, Variant.GENOTYPE_TAG); result = "0/1";
        // not shared alleles in affected samples variant
//        notSharedAllelesVariant.getSampleData(sample1, Variant.GENOTYPE_TAG); result = "0/2";(VARIANTC)
//        notSharedAllelesVariant.getSampleData(sample2, Variant.GENOTYPE_TAG); result = "0/1";
//        notSharedAllelesVariant.getSampleData(sample3, Variant.GENOTYPE_TAG); result = "0/1";
//        notSharedAllelesVariant.getSampleData(sample4, Variant.GENOTYPE_TAG); result = "0/0";
        // all affected share two alleles Variant genotypes
//        sharingTwoAllelesVariant.getSampleData(sample1, Variant.GENOTYPE_TAG); result = "1/2";(VARIANTD)
//        sharingTwoAllelesVariant.getSampleData(sample2, Variant.GENOTYPE_TAG); result = "1/2";
//        sharingTwoAllelesVariant.getSampleData(sample3, Variant.GENOTYPE_TAG); result = "1/2";
//        sharingTwoAllelesVariant.getSampleData(sample4, Variant.GENOTYPE_TAG); result = "1/2";
//        // no alternative alleles in one affected sample variant
//        noAlternativeAffectedSampleVariant.getSampleData(sample1, Variant.GENOTYPE_TAG); result = "0/1";(VARIANTE)
//        noAlternativeAffectedSampleVariant.getSampleData(sample2, Variant.GENOTYPE_TAG); result = "0/0";
//        noAlternativeAffectedSampleVariant.getSampleData(sample3, Variant.GENOTYPE_TAG); result = "0/1";
//        noAlternativeAffectedSampleVariant.getSampleData(sample4, Variant.GENOTYPE_TAG); result = "0/0";
//        allAllelesRefVariant = noAlternativeAffectedSampleVariant;
        variantA = new Variant(null, 0, null, null);
        variantB = new Variant(null, 0, null, null);
        variantC = new Variant(null, 0, null, null);
        variantD = new Variant(null, 0, null, null);
        variantE = new Variant(null, 0, null, null);
        variantA.addSampleData(sample1, genotype12);
        variantA.addSampleData(sample2, genotype11);
        variantA.addSampleData(sample3, genotype01);
        variantA.addSampleData(sample4, genotype02);
        variantB.addSampleData(sample1, genotype12);
        variantB.addSampleData(sample2, genotype11);
        variantB.addSampleData(sample3, genotype02);
        variantB.addSampleData(sample4, genotype01);
        variantC.addSampleData(sample1, genotype02);
        variantC.addSampleData(sample2, genotype01);
        variantC.addSampleData(sample3, genotype01);
        variantC.addSampleData(sample4, genotype00);
        variantD.addSampleData(sample1, genotype12);
        variantD.addSampleData(sample2, genotype12);
        variantD.addSampleData(sample3, genotype12);
        variantD.addSampleData(sample4, genotype12);
        variantE.addSampleData(sample1, genotype01);
        variantE.addSampleData(sample2, genotype00);
        variantE.addSampleData(sample3, genotype01);
        variantE.addSampleData(sample4, genotype00);
    }
    @Test
    public void allelesCombinationInAnySample() throws Exception {
        // samples, variants and genotypes



        // test different combinations
        boolean hasCombination12 = VariantUtils.allelesCombinationInAnySample(variantA,
                                                                                variantC,
                                                                                1,
                                                                                2,
                                                                                samples3and4);
        assertFalse("None of the samples has alleles combination 1-2", hasCombination12);
        boolean hasCombination11 = VariantUtils.allelesCombinationInAnySample(variantA,
                variantC,
                1,
                1,
                samples3and4);
        assertTrue("Sample 3 has alleles combination 1-1", hasCombination11);
        boolean hasCombination21 = VariantUtils.allelesCombinationInAnySample(variantA,
                variantC,
                2,
                1,
                samples3and4);
        assertFalse("None of the samples has alleles combination 2-1", hasCombination21);
        hasCombination21 = VariantUtils.allelesCombinationInAnySample(variantA,
                variantB,
                2,
                1,
                samples3and4);
        hasCombination11 = VariantUtils.allelesCombinationInAnySample(variantA,
                variantB,
                1,
                1,
                samples3and4);
        assertTrue(hasCombination12 = VariantUtils.allelesCombinationInAnySample(variantA,
                variantB,
                1,
                2,
                samples3and4));
        assertTrue("Sample 3 has combination 1-2, Sample 4 has combination 2-1, and none of them have combination 1-1",
                    hasCombination21 && hasCombination12 && !hasCombination11);
    }


    @Test
    public void alternativeAllelesAreHomozygousInSamples() throws Exception {

        // variant definitions
        Variant variantA = new Variant(null, 0, null, null);
        Variant variantB = new Variant(null, 0, null, null);
        Variant variantC = new Variant(null, 0, null, null);
        variantA.addSampleData(sample1, genotype01);
        variantA.addSampleData(sample2, genotype11);
        variantA.addSampleData(sample3, genotype11);
        variantB.addSampleData(sample1, genotype11);
        variantB.addSampleData(sample2, genotype22);
        variantB.addSampleData(sample3, genotype01);
        variantC.addSampleData(sample1, genotype02);
        variantC.addSampleData(sample2, genotype01);
        variantC.addSampleData(sample3, genotype01);

        // invoke tested method
        Set<Integer> oneAlleleSet = new HashSet<>();
        oneAlleleSet.add(1);
        Set<Integer> twoAllelesSet = new HashSet<>();
        twoAllelesSet.add(1);
        twoAllelesSet.add(2);
        boolean oneHomozygousSampleVariantShareAllele1 = VariantUtils.alternativeAllelesAreHomozygousInSamples(variantA,
                                                                                                                oneAlleleSet,
                                                                                                                samples1to3);
        boolean oneHomozygousSampleVariantShareAlleles1And2 = VariantUtils.alternativeAllelesAreHomozygousInSamples(variantA,
                twoAllelesSet,
                samples1to3);
        boolean twoHomozygousSamplesVariantShareAlleles1And2 = VariantUtils.alternativeAllelesAreHomozygousInSamples(variantB,
                twoAllelesSet,
                samples1to3);
        boolean noHomozygousSamplesVariantShareAllele1 =  VariantUtils.alternativeAllelesAreHomozygousInSamples(variantC,
                oneAlleleSet,
                samples1to3);

        // assert results
        assertTrue("''Variant A' should have homozygous allele 1 at least in one sample", oneHomozygousSampleVariantShareAllele1);
        assertFalse("'Variant A' shouldn't have homozygous alleles 1 and 2 at least in one sample each", oneHomozygousSampleVariantShareAlleles1And2);
        assertTrue("'Variant B' should have homozygous alleles 1 and 2 at least in one sample each", twoHomozygousSamplesVariantShareAlleles1And2);
        assertFalse("'Variant C' shouldn't have homozygous allele 1 in any sample", noHomozygousSamplesVariantShareAllele1);
    }

    @Test
    public void samplesShareAlternativeAllelesInVariant() throws Exception
    {
        Set<Integer> oneAlleleSharedSet = VariantUtils.samplesShareAlternativeAllelesInVariant(samples1to3, variantA);
        Set<Integer> twoAllelesSharedSet = VariantUtils.samplesShareAlternativeAllelesInVariant(samples1to3, variantD);
        Set<Integer> notSharedAllelesSet = VariantUtils.samplesShareAlternativeAllelesInVariant(samples1to3, variantC);
        Set<Integer> allAllelesRefSet = VariantUtils.samplesShareAlternativeAllelesInVariant(samples1to3, variantE);

        // assert results
        assertTrue("'variant A' should share allele 1 in samples 1 to 3", oneAlleleSharedSet.size() == 1 && oneAlleleSharedSet.contains(1));
        assertTrue("'variant D' should share alleles 1 and 2 in samples 1 to 3",
                twoAllelesSharedSet.size() == 2 && twoAllelesSharedSet.contains(1) && twoAllelesSharedSet.contains(2));
        assertTrue("'Variant C' shouldn't share any allele in samples 1 to 3", notSharedAllelesSet.isEmpty());
        assertTrue("'Variant E' variant shouldn't share any allele in samples 1 to 3", allAllelesRefSet.isEmpty());
    }
}
