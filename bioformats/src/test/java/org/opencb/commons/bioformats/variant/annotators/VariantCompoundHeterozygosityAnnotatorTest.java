package org.opencb.commons.bioformats.variant.annotators;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import mockit.*;
import org.opencb.commons.bioformats.pedigree.Condition;
import org.opencb.commons.bioformats.pedigree.Pedigree;
import org.opencb.commons.bioformats.variant.Variant;
import org.opencb.commons.test.GenericTest;

import java.util.*;

/**
 * Created by parce on 3/12/14.
 */
public class VariantCompoundHeterozygosityAnnotatorTest extends GenericTest {

    @Mocked Pedigree pedigree;

    // variants to do the tests
    private Variant oneHomozygousUnaffectedSampleVariant = new Variant("chr1", 0, null, null);
    private Variant twoHomozygousSampleVariant = new Variant("chr1", 0, null, null);
    private Variant sharingOneAlleleVariant = new Variant("chr1", 0, null, null);
    private Variant sharingTwoAllelesVariant = new Variant("chr1", 0, null, null);
    private Variant notSharedAllelesVariant = new Variant("chr1", 0, null, null);
    private Variant missingValuesInUnaffectedVariant = new Variant("chr1", 0, null, null);
    private Variant noAlternativeAffectedSampleVariant = new Variant("chr1", 0, null, null);
    private Variant allAffectedMissingValues = new Variant("chr1", 0, null, null);
    private Variant homozygousUnaffectedButNotForAffectedSharedAllele = new Variant("chr1", 0, null, null);
    private Variant twoSharedAllelesAreHomozygousInUnaffectedVariant = new Variant("chr1", 0, null, null);
    private Variant twoSharedAllelesOneHomozygousInUnaffectedVariant = new Variant("chr1", 0, null, null);
    private Variant annotTestVariant1 = new Variant("chr1", 1, "A", "T");
    private Variant annotTestVariant2 = new Variant("chr1", 2, "C", "G");
    private Variant annotTestVariant3 = new Variant("chr1", 3, "T", "A");
    private Variant annotTestVariant4 = new Variant("chr1", 4, "G", "C");
    private Variant annotTestVariant5 = new Variant("chr1", 5, "A", "AC");
    private Variant annotTestVariant6 = new Variant("chr1", 6, "GC", "C");
    private Variant annotTestVariant7 = new Variant("chr1", 7, "A", "G,T");

    private final String sample1 = "s1";
    private final String sample2 = "s2";
    private final String sample3 = "s3";
    private final String sample4 = "s4";

    private Map<String, String> genotypeMissingValue;
    private Map<String, String> genotype00;
    private Map<String, String> genotype01;
    private Map<String, String> genotype02;
    private Map<String, String> genotype11;
    private Map<String, String> genotype12;
    private Map<String, String> genotype22;

    private final Set<String> allSamples = new HashSet<>();
    private final Set<String> samples1to3 = new HashSet<>();

    @Before
    public void setUp() throws Exception {
        super.setUp();
        // samples
        allSamples.add(sample1);
        allSamples.add(sample2);
        allSamples.add(sample3);
        allSamples.add(sample4);
        samples1to3.add(sample1);
        samples1to3.add(sample2);
        samples1to3.add(sample3);

        new NonStrictExpectations() {{
            Set<String> affectedIndividuals = new HashSet<>();
            affectedIndividuals.add(sample1);
            affectedIndividuals.add(sample2);
            Set<String> unaffectedIndividuals = new HashSet<>();
            unaffectedIndividuals.add(sample3);
            unaffectedIndividuals.add(sample4);
            pedigree.getIndividuals(Condition.AFFECTED);result = affectedIndividuals;
            pedigree.getIndividuals(Condition.UNAFFECTED);result = unaffectedIndividuals;
        }};

        // genotypes
        genotypeMissingValue = new HashMap<>();
        genotype00 = new HashMap<>();
        genotype01 = new HashMap<>();
        genotype02 = new HashMap<>();
        genotype11 = new HashMap<>();
        genotype12 = new HashMap<>();
        genotype22 = new HashMap<>();
        genotypeMissingValue.put(Variant.GENOTYPE_TAG, "./.");
        genotype00.put(Variant.GENOTYPE_TAG, "0/0");
        genotype01.put(Variant.GENOTYPE_TAG, "0/1");
        genotype02.put(Variant.GENOTYPE_TAG, "0/2");
        genotype11.put(Variant.GENOTYPE_TAG, "1/1");
        genotype12.put(Variant.GENOTYPE_TAG, "1/2");
        genotype22.put(Variant.GENOTYPE_TAG, "2/2");

        // samples genotypes in variants
        // missing values in unaffected samples variant
        missingValuesInUnaffectedVariant.addSampleData(sample1, genotype11);
        missingValuesInUnaffectedVariant.addSampleData(sample2, genotypeMissingValue);
        missingValuesInUnaffectedVariant.addSampleData(sample3, genotypeMissingValue);
        missingValuesInUnaffectedVariant.addSampleData(sample4, genotypeMissingValue);
        // not shared alleles in affected samples variant
        notSharedAllelesVariant.addSampleData(sample1, genotype02);
        notSharedAllelesVariant.addSampleData(sample2, genotype01);
        notSharedAllelesVariant.addSampleData(sample3, genotype01);
        notSharedAllelesVariant.addSampleData(sample4, genotype00);
        // no alternative alleles in one affected sample variant
        noAlternativeAffectedSampleVariant.addSampleData(sample1, genotype01);
        noAlternativeAffectedSampleVariant.addSampleData(sample2, genotype00);
        noAlternativeAffectedSampleVariant.addSampleData(sample3, genotype01);
        noAlternativeAffectedSampleVariant.addSampleData(sample4, genotype00);
        // one homozygous unaffected sample variant
        oneHomozygousUnaffectedSampleVariant.addSampleData(sample1, genotype01);
        oneHomozygousUnaffectedSampleVariant.addSampleData(sample2, genotype11);
        oneHomozygousUnaffectedSampleVariant.addSampleData(sample3, genotype11);
        oneHomozygousUnaffectedSampleVariant.addSampleData(sample4, genotype00);
        // two homozygous samples Variant genotypes
        twoHomozygousSampleVariant.addSampleData(sample1, genotype11);
        twoHomozygousSampleVariant.addSampleData(sample2, genotype22);
        twoHomozygousSampleVariant.addSampleData(sample3, genotype01);
        twoHomozygousSampleVariant.addSampleData(sample4, genotype22);
        // sharing one allele Variant genotypes
        sharingOneAlleleVariant.addSampleData(sample1, genotype12);
        sharingOneAlleleVariant.addSampleData(sample2, genotype11);
        sharingOneAlleleVariant.addSampleData(sample3, genotype01);
        sharingOneAlleleVariant.addSampleData(sample4, genotype02);
        // affected samples share one allele and one unaffected sample is homozygous for a diferent allele Variant genotypes
        homozygousUnaffectedButNotForAffectedSharedAllele.addSampleData(sample1, genotype01);
        homozygousUnaffectedButNotForAffectedSharedAllele.addSampleData(sample2, genotype01);
        homozygousUnaffectedButNotForAffectedSharedAllele.addSampleData(sample3, genotype22);
        homozygousUnaffectedButNotForAffectedSharedAllele.addSampleData(sample4, genotype00);
        // all affected missing values Variant genotypes
        allAffectedMissingValues.addSampleData(sample1, genotypeMissingValue);
        allAffectedMissingValues.addSampleData(sample2, genotypeMissingValue);
        allAffectedMissingValues.addSampleData(sample3, genotype01);
        allAffectedMissingValues.addSampleData(sample4, genotype00);
        // all affected share two alleles Variant genotypes
        sharingTwoAllelesVariant.addSampleData(sample1, genotype12);
        sharingTwoAllelesVariant.addSampleData(sample2, genotype12);
        sharingTwoAllelesVariant.addSampleData(sample3, genotype12);
        sharingTwoAllelesVariant.addSampleData(sample4, genotype12);
        // 'all affected share two alleles and one of them is homzygous in unaffected' Variant genotypes
        twoSharedAllelesOneHomozygousInUnaffectedVariant.addSampleData(sample1, genotype12);
        twoSharedAllelesOneHomozygousInUnaffectedVariant.addSampleData(sample2, genotype12);
        twoSharedAllelesOneHomozygousInUnaffectedVariant.addSampleData(sample3, genotype22);
        twoSharedAllelesOneHomozygousInUnaffectedVariant.addSampleData(sample4, genotype12);
        // 'all affected share two alleles and both of them are homzygous for some unaffected' Variant genotypes
        twoSharedAllelesAreHomozygousInUnaffectedVariant.addSampleData(sample1, genotype12);
        twoSharedAllelesAreHomozygousInUnaffectedVariant.addSampleData(sample2, genotype12);
        twoSharedAllelesAreHomozygousInUnaffectedVariant.addSampleData(sample3, genotype11);
        twoSharedAllelesAreHomozygousInUnaffectedVariant.addSampleData(sample4, genotype22);
        // annotation test variants
        annotTestVariant1.addSampleData(sample1, genotype12);
        annotTestVariant1.addSampleData(sample2, genotype11);
        annotTestVariant1.addSampleData(sample3, genotype01);
        annotTestVariant1.addSampleData(sample4, genotype02);
        annotTestVariant2.addSampleData(sample1, genotype11);
        annotTestVariant2.addSampleData(sample2, genotypeMissingValue);
        annotTestVariant2.addSampleData(sample3, genotypeMissingValue);
        annotTestVariant2.addSampleData(sample4, genotypeMissingValue);
        annotTestVariant3.addSampleData(sample1, genotype12);
        annotTestVariant3.addSampleData(sample2, genotype12);
        annotTestVariant3.addSampleData(sample3, genotype12);
        annotTestVariant3.addSampleData(sample4, genotype12);
        annotTestVariant4.addSampleData(sample1, genotype12);
        annotTestVariant4.addSampleData(sample2, genotype12);
        annotTestVariant4.addSampleData(sample3, genotype22);
        annotTestVariant4.addSampleData(sample4, genotype12);
        annotTestVariant5.addSampleData(sample1, genotype12);
        annotTestVariant5.addSampleData(sample2, genotype12);
        annotTestVariant5.addSampleData(sample3, genotype01);
        annotTestVariant5.addSampleData(sample4, genotype02);
        annotTestVariant6.addSampleData(sample1, genotype01);
        annotTestVariant6.addSampleData(sample2, genotype01);
        annotTestVariant6.addSampleData(sample3, genotype01);
        annotTestVariant6.addSampleData(sample4, genotype01);
        annotTestVariant7.addSampleData(sample1, genotype01);
        annotTestVariant7.addSampleData(sample2, genotype01);
        annotTestVariant7.addSampleData(sample3, genotype01);
        annotTestVariant7.addSampleData(sample4, genotype00);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void affectedSamplesSharedAllelesHomozygousInUnaffectedSamplesFilter() throws Exception {

        VariantCompoundHeterozygosityAnnotator annotator = new VariantCompoundHeterozygosityAnnotator(pedigree);
        // empty map that the method will use to return the variant affected samples shared alternative alleles
        Map<Variant, Set<Integer>> sharedAllelesMap = new HashMap<>();
        List<Variant> variantsToBeFiltered = new ArrayList<Variant>();
        variantsToBeFiltered.add(missingValuesInUnaffectedVariant);
        variantsToBeFiltered.add(notSharedAllelesVariant);
        variantsToBeFiltered.add(oneHomozygousUnaffectedSampleVariant);
        variantsToBeFiltered.add(noAlternativeAffectedSampleVariant);
        variantsToBeFiltered.add(twoHomozygousSampleVariant);
        variantsToBeFiltered.add(sharingOneAlleleVariant);
        variantsToBeFiltered.add(allAffectedMissingValues);
        variantsToBeFiltered.add(homozygousUnaffectedButNotForAffectedSharedAllele);
        variantsToBeFiltered.add(sharingTwoAllelesVariant);
        variantsToBeFiltered.add(twoSharedAllelesOneHomozygousInUnaffectedVariant);
        variantsToBeFiltered.add(twoSharedAllelesAreHomozygousInUnaffectedVariant);

        // give names to the variants and print them
        String missingValuesInUnaffectedVariantName = "'Missing values in all unaffected samples'";
        printVariantFilterTest(missingValuesInUnaffectedVariant, true, missingValuesInUnaffectedVariantName, true);
        String notSharedAllelesVariantName = "'Not shared alleles between affected samples'";
        printVariantFilterTest(notSharedAllelesVariant, false, notSharedAllelesVariantName, false);
        String oneHomozygousUnaffectedSampleVariantName = "'One homozygous unaffected sample'";
        printVariantFilterTest(oneHomozygousUnaffectedSampleVariant, false, oneHomozygousUnaffectedSampleVariantName, false);
        String noAlternativeAffectedSampleVariantName = "'No alternative alleles in affected sample'";
        printVariantFilterTest(noAlternativeAffectedSampleVariant, false, noAlternativeAffectedSampleVariantName, false);
        String twoHomozygousSampleVariantName = "'Two homozygous affected samples not sharing alleles'";
        printVariantFilterTest(twoHomozygousSampleVariant, false, twoHomozygousSampleVariantName, false);
        String sharingOneAlleleVariantName = "'Affected samples sharing one allele not homozyguos in unaffected'";
        printVariantFilterTest(sharingOneAlleleVariant, true, sharingOneAlleleVariantName, false);
        String allAffectedMissingValuesName = "'All affected missing values'";
        printVariantFilterTest(allAffectedMissingValues, false, allAffectedMissingValuesName, false);
        String homozygousUnaffectedButNotForAffectedSharedAlleleName = "'Affected samples sharing one allele and a different allele is homozyguos in unaffected'";
        printVariantFilterTest(homozygousUnaffectedButNotForAffectedSharedAllele, true, homozygousUnaffectedButNotForAffectedSharedAlleleName, false);
        String sharingTwoAllelesVariantName = "'All samples sharing two alleles'";
        printVariantFilterTest(sharingTwoAllelesVariant, true, sharingTwoAllelesVariantName, false);
        String twoSharedAllelesOneHomozygousInUnaffectedVariantName = "'Affected samples sharing two alleles and one of them is homozyougs in some unaffected sample'";
        printVariantFilterTest(twoSharedAllelesOneHomozygousInUnaffectedVariant, true, twoSharedAllelesOneHomozygousInUnaffectedVariantName, false);
        String twoSharedAllelesAreHomozygousInUnaffectedVariantName = "'all affected share two alleles and both of them are homozygous in some unaffected sample'";
        printVariantFilterTest(twoSharedAllelesAreHomozygousInUnaffectedVariant, false, twoSharedAllelesAreHomozygousInUnaffectedVariantName, false);

        // tested method executions
        // private List<Variant> affectedSamplesSharedAllelesHomozygousInUnaffectedSamplesFilter(List<Variant> variantsToBeFiltered, sharedAllelesMap)
        List<Variant> filteredVariants = Deencapsulation.invoke(annotator, "affectedSamplesSharedAllelesHomozygousInUnaffectedSamplesFilter", variantsToBeFiltered, sharedAllelesMap);

        // assert results
        Assert.assertTrue(missingValuesInUnaffectedVariantName + " variant should pass the filter",
                filteredVariants.contains(missingValuesInUnaffectedVariant) &&
                        sharedAllelesMap.get(missingValuesInUnaffectedVariant).size() == 1 &&
                        sharedAllelesMap.get(missingValuesInUnaffectedVariant).contains(1));
        Assert.assertFalse(notSharedAllelesVariant +  " variant shouldn't pass the filter", filteredVariants.contains(notSharedAllelesVariant));
        Assert.assertFalse(oneHomozygousUnaffectedSampleVariantName + " variant shouldn't pass the filter", filteredVariants.contains(oneHomozygousUnaffectedSampleVariant));
        Assert.assertFalse(noAlternativeAffectedSampleVariantName + " variant shouldn't pass the filter", filteredVariants.contains(noAlternativeAffectedSampleVariant));
        Assert.assertFalse(twoHomozygousSampleVariantName + " variant shouldn't pass the filter", filteredVariants.contains(twoHomozygousSampleVariant));
        Assert.assertTrue(sharingOneAlleleVariantName + " variant should pass the filter",
                filteredVariants.contains(sharingOneAlleleVariant) &&
                        sharedAllelesMap.get(sharingOneAlleleVariant).size() == 1 &&
                        sharedAllelesMap.get(sharingOneAlleleVariant).contains(1));
        Assert.assertFalse(allAffectedMissingValuesName + " variant shouldn't pass the filter", filteredVariants.contains(allAffectedMissingValues));
        Assert.assertTrue(homozygousUnaffectedButNotForAffectedSharedAlleleName + " variant should pass the filter",
                filteredVariants.contains(homozygousUnaffectedButNotForAffectedSharedAllele) &&
                        sharedAllelesMap.get(homozygousUnaffectedButNotForAffectedSharedAllele).size() == 1 &&
                        sharedAllelesMap.get(homozygousUnaffectedButNotForAffectedSharedAllele).contains(1));
        Assert.assertTrue(sharingTwoAllelesVariantName + " variant should pass the filter",
                filteredVariants.contains(sharingTwoAllelesVariant) &&
                        sharedAllelesMap.get(sharingTwoAllelesVariant).size() == 2 &&
                        sharedAllelesMap.get(sharingTwoAllelesVariant).contains(1) &&
                        sharedAllelesMap.get(sharingTwoAllelesVariant).contains(2));
        Assert.assertTrue(twoSharedAllelesOneHomozygousInUnaffectedVariantName + " variant should pass the filter",
                filteredVariants.contains(twoSharedAllelesOneHomozygousInUnaffectedVariant) &&
                        sharedAllelesMap.get(twoSharedAllelesOneHomozygousInUnaffectedVariant).size() == 1 &&
                        sharedAllelesMap.get(twoSharedAllelesOneHomozygousInUnaffectedVariant).contains(1));
        Assert.assertFalse(twoSharedAllelesAreHomozygousInUnaffectedVariantName + " variant shouldn't pass the filter",
                filteredVariants.contains(twoSharedAllelesAreHomozygousInUnaffectedVariant));
    }

    @Test
    public void annot() throws Exception {
        VariantCompoundHeterozygosityAnnotator annotator = new VariantCompoundHeterozygosityAnnotator(pedigree);
        // use two lists of variants
        List<Variant> testAVariants = new ArrayList<>(),
                      testBVariants = new ArrayList<>();
        // list A
        testAVariants.add(annotTestVariant1);
        testAVariants.add(annotTestVariant2);
        testAVariants.add(annotTestVariant3);
        testAVariants.add(annotTestVariant4);
        // list B
        testBVariants.add(annotTestVariant5);
        testBVariants.add(annotTestVariant6);
        testBVariants.add(annotTestVariant7);

        // execute the annotator and print the results
        annotator.annot(testAVariants);
        annotator.annot(testBVariants);
        this.printVariantsAnnotTest(testAVariants);
        this.printVariantsAnnotTest(testBVariants);

        // assert the results
        String variant1CHTagValue = annotTestVariant1.getAttribute(VariantCompoundHeterozygosityAnnotator.COMPOUND_HETEROZYGOUS_TAG);
        String variant2CHTagValue = annotTestVariant2.getAttribute(VariantCompoundHeterozygosityAnnotator.COMPOUND_HETEROZYGOUS_TAG);
        String variant3CHTagValue = annotTestVariant3.getAttribute(VariantCompoundHeterozygosityAnnotator.COMPOUND_HETEROZYGOUS_TAG);
        String variant4CHTagValue = annotTestVariant4.getAttribute(VariantCompoundHeterozygosityAnnotator.COMPOUND_HETEROZYGOUS_TAG);
        String variant5CHTagValue = annotTestVariant5.getAttribute(VariantCompoundHeterozygosityAnnotator.COMPOUND_HETEROZYGOUS_TAG);
        String variant6CHTagValue = annotTestVariant6.getAttribute(VariantCompoundHeterozygosityAnnotator.COMPOUND_HETEROZYGOUS_TAG);
        String variant7CHTagValue = annotTestVariant7.getAttribute(VariantCompoundHeterozygosityAnnotator.COMPOUND_HETEROZYGOUS_TAG);
        Assert.assertTrue("Variants 1 and 2 should be annotated as a 'Compound Heterozygous' pair of variants",
                variant1CHTagValue.contains(this.getCHTag(annotTestVariant2)) && variant2CHTagValue.contains(this.getCHTag(annotTestVariant1)));
        Assert.assertFalse("Variants 1 and 2 shouldn't be annotated as a 'Compound Heterozygous' pair of variants",
                variant1CHTagValue.contains(this.getCHTag(annotTestVariant3)) || variant3CHTagValue.contains(this.getCHTag(annotTestVariant1)));
        Assert.assertTrue("Variants 1 and 4 should be annotated as a 'Compound Heterozygous' pair of variants",
                variant1CHTagValue.contains(this.getCHTag(annotTestVariant4)) && variant4CHTagValue.contains(this.getCHTag(annotTestVariant1)));
        Assert.assertTrue("Variants 2 and 3 should be annotated as a 'Compound Heterozygous' pair of variants",
                variant2CHTagValue.contains(this.getCHTag(annotTestVariant3)) && variant3CHTagValue.contains(this.getCHTag(annotTestVariant2)));
        Assert.assertTrue("Variants 2 and 4 should be annotated as a 'Compound Heterozygous' pair of variants",
                variant2CHTagValue.contains(this.getCHTag(annotTestVariant4)) && variant4CHTagValue.contains(this.getCHTag(annotTestVariant2)));
        Assert.assertFalse("Variants 3 and 4 should be annotated as a 'Compound Heterozygous' pair of variants",
                variant3CHTagValue.contains(this.getCHTag(annotTestVariant4)) || variant4CHTagValue.contains(this.getCHTag(annotTestVariant3)));
        Assert.assertFalse("Variants 5 and 6 shouldn't be annotated as a 'Compound Heterozygous' pair of variants",
                variant5CHTagValue.contains(this.getCHTag(annotTestVariant6)) || (variant6CHTagValue != null && variant6CHTagValue.contains(this.getCHTag(annotTestVariant5))));
        Assert.assertTrue("Variants 5 and 7 should be annotated as a 'Compound Heterozygous' pair of variants",
                variant5CHTagValue.contains(this.getCHTag(annotTestVariant7)) && variant7CHTagValue.contains(this.getCHTag(annotTestVariant5)));
        Assert.assertFalse("Variants 6 and 7 shouldn't be annotated as a 'Compound Heterozygous' pair of variants",
                (variant6CHTagValue != null && variant6CHTagValue.contains(this.getCHTag(annotTestVariant7))) || variant7CHTagValue.contains(this.getCHTag(annotTestVariant6)));
    }

    private String getCHTag(Variant variant) {
        StringBuilder chTagBuilder = new StringBuilder(variant.getChromosome()).append('_').append(variant.getPosition()).append('_').append(variant.getReference()).append('_').append(variant.getAlternate());
        return chTagBuilder.toString();
    }

    private void printVariantFilterTest(Variant variant, boolean passFilter, String nameOfVariant, boolean printHeader) {
        if (printHeader) {
            System.out.println("\nS1*\tS2*\tS3\tS4\tShould pass filter\tName Of variant");
            System.out.println("=======================================================================");
        }
        System.out.println(variant.getSampleData(sample1).get(Variant.GENOTYPE_TAG) + "\t" +
                variant.getSampleData(sample2).get(Variant.GENOTYPE_TAG) + "\t" +
                variant.getSampleData(sample3).get(Variant.GENOTYPE_TAG) + "\t" +
                variant.getSampleData(sample4).get(Variant.GENOTYPE_TAG) + "\t" + passFilter + "\t\t\t\t" +nameOfVariant );
    }

    private void printVariantsAnnotTest(List<Variant> variants) {
        System.out.println("\nCHR\t\tPOS\tREF\tALT\tS1*\tS2*\tS3\tS4\tCH");
        System.out.println("=======================================================================");
        for (Variant variant: variants) {
            System.out.println(variant.getChromosome() + "\t" + variant.getPosition() + "\t" + variant.getReference() + "\t" +
                    variant.getAlternate() + "\t" + variant.getSampleData(sample1).get(Variant.GENOTYPE_TAG) + "\t" +
                    variant.getSampleData(sample2).get(Variant.GENOTYPE_TAG) + "\t" +
                    variant.getSampleData(sample3).get(Variant.GENOTYPE_TAG) + "\t" +
                    variant.getSampleData(sample4).get(Variant.GENOTYPE_TAG) + "\t" +
                    (variant.getAttribute(VariantCompoundHeterozygosityAnnotator.COMPOUND_HETEROZYGOUS_TAG) == null? "" : variant.getAttribute(VariantCompoundHeterozygosityAnnotator.COMPOUND_HETEROZYGOUS_TAG)));

        }
    }
}
