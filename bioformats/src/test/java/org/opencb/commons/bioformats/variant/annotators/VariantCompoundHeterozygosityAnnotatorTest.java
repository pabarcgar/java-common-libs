package org.opencb.commons.bioformats.variant.annotators;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import mockit.*;
import org.opencb.commons.bioformats.pedigree.Condition;
import org.opencb.commons.bioformats.pedigree.Individual;
import org.opencb.commons.bioformats.pedigree.Pedigree;
import org.opencb.commons.bioformats.variant.Variant;
import org.opencb.commons.bioformats.variant.annotators.VariantCompoundHeterozygosityAnnotator;
import org.opencb.commons.test.GenericTest;

import java.util.*;

/**
 * Created by parce on 3/12/14.
 */
public class VariantCompoundHeterozygosityAnnotatorTest extends GenericTest {

    @Mocked Pedigree pedigree;
    @Mocked Individual ind1;
    @Mocked Individual ind2;
    @Mocked Individual ind3;
    @Mocked Individual ind4;

    // variants to do the tests
    private Variant oneHomozygousUnaffectedSampleVariant = new Variant(null, 0, null, null);
    private Variant twoHomozygousSampleVariant = new Variant(null, 0, null, null);
    private Variant sharingOneAlleleVariant = new Variant(null, 0, null, null);
    private Variant sharingOneAlleleVariantB = new Variant(null, 0, null, null);
    private Variant sharingTwoAllelesVariant = new Variant(null, 0, null, null);
    private Variant notSharedAllelesVariant = new Variant(null, 0, null, null);
    private Variant missingValuesInUnaffectedVariant = new Variant(null, 0, null, null);
    private Variant noAlternativeAffectedSampleVariant = new Variant(null, 0, null, null);
    private Variant allAffectedMissingValues = new Variant(null, 0, null, null);
    private Variant homozygousUnaffectedButNotForAffectedSharedAllele = new Variant(null, 0, null, null);
    private Variant twoSharedAllelesAreHomozygousInUnaffectedVariant = new Variant(null, 0, null, null);
    private Variant twoSharedAllelesOneHomozygousInUnaffectedVariant = new Variant(null, 0, null, null);
    private Variant multiVariantFilterA = new Variant(null, 0, null, null);
    private Variant multiVariantFilterB = new Variant(null, 0, null, null);
    private Variant multiVariantFilterC = new Variant(null, 0, null, null);

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
            // record pedigree and individuals
            ind1.getCondition(); result = Condition.AFFECTED;
            ind2.getCondition(); result = Condition.AFFECTED;
            ind3.getCondition(); result = Condition.UNAFFECTED;
            ind4.getCondition(); result = Condition.UNAFFECTED;
            Map<String, Individual> individuals = new HashMap<>();
            individuals.put("s1", ind1);
            individuals.put("s2", ind2);
            individuals.put("s3", ind3);
            individuals.put("s4", ind4);
            pedigree.getIndividuals(); result = individuals;
            pedigree.getIndividual("s1"); result = ind1;
            pedigree.getIndividual("s2"); result = ind2;
            pedigree.getIndividual("s3"); result = ind3;
            pedigree.getIndividual("s4"); result = ind4;
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
        // sharing one allele variant B genotypes
        sharingOneAlleleVariantB.addSampleData(sample1, genotype12);
        sharingOneAlleleVariantB.addSampleData(sample2, genotype11);
        sharingOneAlleleVariantB.addSampleData(sample3, genotype02);
        sharingOneAlleleVariantB.addSampleData(sample4, genotype01);
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
        // multivariant filter variant tests
        multiVariantFilterA.addSampleData(sample1, genotype12);
        multiVariantFilterA.addSampleData(sample2, genotype12);
        multiVariantFilterA.addSampleData(sample3, genotype01);
        multiVariantFilterA.addSampleData(sample4, genotype02);
        multiVariantFilterB.addSampleData(sample1, genotype01);
        multiVariantFilterB.addSampleData(sample2, genotype01);
        multiVariantFilterB.addSampleData(sample3, genotype01);
        multiVariantFilterB.addSampleData(sample4, genotype01);
        multiVariantFilterC.addSampleData(sample1, genotype01);
        multiVariantFilterC.addSampleData(sample2, genotype01);
        multiVariantFilterC.addSampleData(sample3, genotype01);
        multiVariantFilterC.addSampleData(sample4, genotype00);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testAnnot() throws Exception {
        // TODO: implementar este test
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

        // tested method executions
        // private List<Variant> affectedSamplesSharedAllelesHomozygousInUnaffectedSamplesFilter(List<Variant> variantsToBeFiltered, sharedAllelesMap)
        List<Variant> filteredVariants = Deencapsulation.invoke(annotator, "affectedSamplesSharedAllelesHomozygousInUnaffectedSamplesFilter", variantsToBeFiltered, sharedAllelesMap);

        // assert results
        Assert.assertTrue("'Missing values in all unaffected samples' variant should pass the filter",
                filteredVariants.contains(missingValuesInUnaffectedVariant) &&
                        sharedAllelesMap.get(missingValuesInUnaffectedVariant).size() == 1 &&
                        sharedAllelesMap.get(missingValuesInUnaffectedVariant).contains(1));
        Assert.assertFalse("'Not Shared alleles' variant shouldn't pass the filter", filteredVariants.contains(notSharedAllelesVariant));
        Assert.assertFalse("'One homozygous unaffected sample' variant shouldn't pass the filter", filteredVariants.contains(oneHomozygousUnaffectedSampleVariant));
        Assert.assertFalse("'No alternative alleles in affected sample' variant shouldn't pass the filter", filteredVariants.contains(noAlternativeAffectedSampleVariant));
        Assert.assertFalse("'Two homozygous affected samples not sharing alleles' variant shouldn't pass the filter", filteredVariants.contains(twoHomozygousSampleVariant));
        Assert.assertTrue("'Affected Samples sharing one allele not homozyguos in unaffected' variant should pass the filter",
                filteredVariants.contains(sharingOneAlleleVariant) &&
                        sharedAllelesMap.get(sharingOneAlleleVariant).size() == 1 &&
                        sharedAllelesMap.get(sharingOneAlleleVariant).contains(1));
        Assert.assertFalse("'all affected missing values' variant shouldn't pass the filter", filteredVariants.contains(allAffectedMissingValues));
        Assert.assertTrue("'Affected Samples sharing one allele and a different allele is homozyguos in unaffected' variant should pass the filter",
                filteredVariants.contains(homozygousUnaffectedButNotForAffectedSharedAllele) &&
                        sharedAllelesMap.get(homozygousUnaffectedButNotForAffectedSharedAllele).size() == 1 &&
                        sharedAllelesMap.get(homozygousUnaffectedButNotForAffectedSharedAllele).contains(1));
        Assert.assertTrue("'Affected Samples sharing two alleles' variant should pass the filter",
                filteredVariants.contains(sharingTwoAllelesVariant) &&
                        sharedAllelesMap.get(sharingTwoAllelesVariant).size() == 2 &&
                        sharedAllelesMap.get(sharingTwoAllelesVariant).contains(1) &&
                        sharedAllelesMap.get(sharingTwoAllelesVariant).contains(2));
        Assert.assertTrue("'Affected Samples sharing two alleles and one of them is homozyougs in some unaffected sample' variant should pass the filter",
                filteredVariants.contains(twoSharedAllelesOneHomozygousInUnaffectedVariant) &&
                        sharedAllelesMap.get(twoSharedAllelesOneHomozygousInUnaffectedVariant).size() == 1 &&
                        sharedAllelesMap.get(twoSharedAllelesOneHomozygousInUnaffectedVariant).contains(1));
        Assert.assertFalse("'all affected share two alleles and both of them are homozygous in some unaffected sample' variant shouldn't pass the filter",
                filteredVariants.contains(twoSharedAllelesAreHomozygousInUnaffectedVariant));
    }

    @Test
    public void multiVariantFilter() throws Exception {
//        1. sharingOneAlleleVariant
//        2. missingValuesInUnaffectedVariant
//        3. homozygousUnaffectedButNotForAffectedSharedAllele
//        4. sharingTwoAllelesVariant
//        5. twoSharedAllelesOneHomozygousInUnaffectedVariant


        // TODO: change the assertions. Now the variants wont be filtered, just annotated

        // first of all, execute the 'single variant filter' step (method "affectedSamplesSharedAllelesHomozygousInUnaffectedSamplesFilter"),
        // just to obtain the 'shared alleles map':
        // all variants should pass the filter
        VariantCompoundHeterozygosityAnnotator annotator = new VariantCompoundHeterozygosityAnnotator(pedigree);
        List<Variant> variantsToBeAnnotated = new ArrayList<>();
        Map<Variant, Set<Integer>> sharedAllelesMap = new HashMap<>();
        variantsToBeAnnotated.add(sharingOneAlleleVariant);
        variantsToBeAnnotated.add(missingValuesInUnaffectedVariant);
        variantsToBeAnnotated.add(homozygousUnaffectedButNotForAffectedSharedAllele);
        variantsToBeAnnotated.add(sharingTwoAllelesVariant);
        variantsToBeAnnotated.add(twoSharedAllelesOneHomozygousInUnaffectedVariant);
        variantsToBeAnnotated.add(multiVariantFilterA);
        variantsToBeAnnotated.add(multiVariantFilterB);
        variantsToBeAnnotated.add(multiVariantFilterC);
        Collection<Variant> filteredVariants = Deencapsulation.invoke(annotator, "affectedSamplesSharedAllelesHomozygousInUnaffectedSamplesFilter", variantsToBeAnnotated, sharedAllelesMap);
        Assert.assertTrue("All variants should pass the first step filter", filteredVariants.size() == 8);

        // private Set<Variant> compoundHeterozygosityFilter(List<Variant> variantsToBeFiltered, Map<Variant, Set<Integer>> sharedAllelesMap);

        // both variants has the affected allele present in the same unaffected sample. This pair shouldn't pass the filter
        variantsToBeAnnotated.clear();
        variantsToBeAnnotated.add(sharingOneAlleleVariant);
        variantsToBeAnnotated.add(sharingTwoAllelesVariant);
        filteredVariants = Deencapsulation.invoke(annotator, "compoundHeterozygosityFilter", variantsToBeAnnotated, sharedAllelesMap);
        Assert.assertTrue("The unaffected sample s3 has both variants: this pair shouldn't pass the filter", filteredVariants.size() == 0);

        // one variant has an 'affected' allele in one unaffected sample, the other variant has the affected allele in other
        // different unaffected sample. This pair should pass the filter
        variantsToBeAnnotated.clear();
        variantsToBeAnnotated.add(sharingOneAlleleVariant);
        variantsToBeAnnotated.add(twoSharedAllelesOneHomozygousInUnaffectedVariant);
        filteredVariants = Deencapsulation.invoke(annotator, "compoundHeterozygosityFilter", variantsToBeAnnotated, sharedAllelesMap);
        Assert.assertTrue("No unaffected sample share the affected common allele of all variants: this pair should pass the filter", filteredVariants.size() == 2);

        // - variante a y b no tienen los unaffected tocados -> pasan

        // missing values in unaffected sample, this pair should pass the filter
        variantsToBeAnnotated.clear();
        variantsToBeAnnotated.add(sharingOneAlleleVariant);
        variantsToBeAnnotated.add(missingValuesInUnaffectedVariant);
        filteredVariants = Deencapsulation.invoke(annotator, "compoundHeterozygosityFilter", variantsToBeAnnotated, sharedAllelesMap);
        Assert.assertTrue("All unaffected individuals have missing values in one variant: this pair should pass the filter", filteredVariants.size() == 2);

        // two combinations of affected alleles, both of them present in unaffected samples. This pair shouldn't pass the filter
        variantsToBeAnnotated.clear();
        variantsToBeAnnotated.add(multiVariantFilterA);
        variantsToBeAnnotated.add(multiVariantFilterB);
        filteredVariants = Deencapsulation.invoke(annotator, "compoundHeterozygosityFilter", variantsToBeAnnotated, sharedAllelesMap);
        Assert.assertTrue("The unaffected sample s3 has allele combination 1-1, and unaffected sample has allele combination 2-1: this pair shouldn't pass the filter", filteredVariants.size() == 0);

        // two combinations of affected alleles, but just one of them present in any unaffected samples. This pair should pass the filter
        variantsToBeAnnotated.clear();
        variantsToBeAnnotated.add(multiVariantFilterA);
        variantsToBeAnnotated.add(multiVariantFilterC);
        filteredVariants = Deencapsulation.invoke(annotator, "compoundHeterozygosityFilter", variantsToBeAnnotated, sharedAllelesMap);
        Assert.assertTrue("Allele combination 2-1 is not in any unaffected sample: this pair should pass the filter", filteredVariants.size() == 2);

    }
}
