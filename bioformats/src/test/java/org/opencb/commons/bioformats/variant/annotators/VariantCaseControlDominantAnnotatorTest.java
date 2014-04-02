package org.opencb.commons.bioformats.variant.annotators;

import mockit.Deencapsulation;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Before;
import org.junit.Test;
import org.opencb.commons.bioformats.pedigree.Condition;
import org.opencb.commons.bioformats.pedigree.Individual;
import org.opencb.commons.bioformats.pedigree.Pedigree;
import org.opencb.commons.bioformats.variant.Variant;
import org.opencb.commons.test.GenericTest;

import java.util.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by lcruz on 31/03/14.
 */
public class VariantCaseControlDominantAnnotatorTest extends GenericTest {
    @Mocked
    Pedigree pedigree;
    @Mocked Individual ind1;
    @Mocked Individual ind2;
    @Mocked Individual ind3;
    @Mocked Individual ind4;

    // variants to do the tests
    private Variant noAllelesInCases = new Variant("chr1", 0, null, null);
    private Variant oneAlleleInControl = new Variant("chr1", 0, null, null);
    private Variant oneAlleleNotInControl = new Variant("chr1", 0, null, null);
    private Variant twoAllelesNotInControl = new Variant("chr1", 0, null, null);
    private Variant twoAllelesOneInControl = new Variant("chr1", 0, null, null);
    private Variant twoAllelesBothInControl = new Variant("chr1", 0, null, null);
    private Variant allCasesMV = new Variant("chr1", 0, null, null);
    private Variant allControlsMV = new Variant("chr1", 0, null, null);

    private final String sample1 = "A";
    private final String sample2 = "B";
    private final String control1 = "C1";
    private final String control2 = "C2";

    private Map<String, String> genotypeMissingValue;
    private Map<String, String> genotype00;
    private Map<String, String> genotype01;
    private Map<String, String> genotype02;
    private Map<String, String> genotype10;
    private Map<String, String> genotype12;

    private final Set<String> allSamples = new HashSet<>();
    private final Set<String> samples1to3 = new HashSet<>();

    @Before
    public void setUp() throws Exception {
        super.setUp();
        // samples
        allSamples.add(sample1);
        allSamples.add(sample2);
        allSamples.add(control1);
        allSamples.add(control2);

        new NonStrictExpectations() {{
            // record pedigree and individuals
            ind1.getCondition(); result = Condition.AFFECTED;
            ind2.getCondition(); result = Condition.AFFECTED;
            ind3.getCondition(); result = Condition.UNAFFECTED;
            ind4.getCondition(); result = Condition.UNAFFECTED;
//            Map<String, Individual> affectedIndividuals = new HashMap<>();
//            affectedIndividuals.put("A", ind1);
//            affectedIndividuals.put("B", ind2);
//            Map<String, Individual> controlIndividuals = new HashMap<>();
//            controlIndividuals.put("C1", ind3);
//            controlIndividuals.put("C2", ind4);
            Set<String> controlIndividuals = new HashSet<>();
            controlIndividuals.add("C1");
            controlIndividuals.add("C2");
            Set<String> affectedIndividuals = new HashSet<>();
            affectedIndividuals.add("A");
            affectedIndividuals.add("B");
//            pedigree.getIndividuals(); result = individuals;
//            pedigree.getIndividual("A"); result = ind1;
//            pedigree.getIndividual("B"); result = ind2;
//            pedigree.getIndividual("C1"); result = ind3;
//            pedigree.getIndividual("C2"); result = ind4;
            pedigree.getIndividuals(Condition.UNAFFECTED); result = controlIndividuals;
            pedigree.getIndividuals(Condition.AFFECTED); result = affectedIndividuals;
        }};

        // genotypes
        genotypeMissingValue = new HashMap<>();
        genotype00 = new HashMap<>();
        genotype01 = new HashMap<>();
        genotype02 = new HashMap<>();
        genotype10 = new HashMap<>();
        genotype12 = new HashMap<>();
        genotypeMissingValue.put(Variant.GENOTYPE_TAG, "./.");
        genotype00.put(Variant.GENOTYPE_TAG, "0/0");
        genotype01.put(Variant.GENOTYPE_TAG, "0/1");
        genotype02.put(Variant.GENOTYPE_TAG, "0/2");
        genotype10.put(Variant.GENOTYPE_TAG, "1/0");
        genotype12.put(Variant.GENOTYPE_TAG, "1/2");

        // 0/0  0/0  0/1  0/0
        noAllelesInCases.addSampleData(sample1, genotype00);
        noAllelesInCases.addSampleData(sample2, genotype00);
        noAllelesInCases.addSampleData(control1, genotype01);
        noAllelesInCases.addSampleData(control2, genotype00);

        // 0/1  ./.  0/0  0/1
        oneAlleleInControl.addSampleData(sample1, genotype00);
        oneAlleleInControl.addSampleData(sample2, genotypeMissingValue);
        oneAlleleInControl.addSampleData(control1, genotype00);
        oneAlleleInControl.addSampleData(control2, genotype01);

        // 0/1  0/0  0/0  0/0
        oneAlleleNotInControl.addSampleData(sample1, genotype01);
        oneAlleleNotInControl.addSampleData(sample2, genotype00);
        oneAlleleNotInControl.addSampleData(control1, genotype00);
        oneAlleleNotInControl.addSampleData(control2, genotype00);

        // 0/1  0/2  0/0  0/0
        twoAllelesNotInControl.addSampleData(sample1, genotype01);
        twoAllelesNotInControl.addSampleData(sample2, genotype02);
        twoAllelesNotInControl.addSampleData(control1, genotype00);
        twoAllelesNotInControl.addSampleData(control2, genotype00);

        // 1/2  0/0  0/1  0/0
        twoAllelesOneInControl.addSampleData(sample1, genotype12);
        twoAllelesOneInControl.addSampleData(sample2, genotype00);
        twoAllelesOneInControl.addSampleData(control1, genotype01);
        twoAllelesOneInControl.addSampleData(control2, genotype00);

        // 1/2  ./.  1/0  0/2
        twoAllelesBothInControl.addSampleData(sample1, genotype12);
        twoAllelesBothInControl.addSampleData(sample2, genotypeMissingValue);
        twoAllelesBothInControl.addSampleData(control1, genotype10);
        twoAllelesBothInControl.addSampleData(control2, genotype02);

        // ./.  ./.  0/0  0/0
        allCasesMV.addSampleData(sample1, genotypeMissingValue);
        allCasesMV.addSampleData(sample2, genotypeMissingValue);
        allCasesMV.addSampleData(control1, genotype00);
        allCasesMV.addSampleData(control2, genotype00);

        // 0/1  0/0 ./.  ./.
        allControlsMV.addSampleData(sample1, genotype01);
        allControlsMV.addSampleData(sample2, genotype00);
        allControlsMV.addSampleData(control1, genotypeMissingValue);
        allControlsMV.addSampleData(control2, genotypeMissingValue);
    }

    @Test
    public void testAnnot() throws Exception {
        VariantCaseControlDominantAnnotator annotator = new VariantCaseControlDominantAnnotator(pedigree);

        // Test 1
        List<Variant> variantsToBeFiltered = new ArrayList<Variant>();
        variantsToBeFiltered.add(noAllelesInCases);
        variantsToBeFiltered.add(oneAlleleInControl);
        variantsToBeFiltered.add(oneAlleleNotInControl);
        variantsToBeFiltered.add(twoAllelesOneInControl);
        variantsToBeFiltered.add(twoAllelesBothInControl);
        variantsToBeFiltered.add(allCasesMV);
        variantsToBeFiltered.add(allControlsMV);

        annotator.annot(variantsToBeFiltered);

        System.out.println("--------------- TEST 1 ---------------");
        for(Variant variante: variantsToBeFiltered){
            System.out.println(variante.getSampleData(sample1) + "\t" +
                    variante.getSampleData(sample2) + "\t" + variante.getSampleData(control1) + "\t" +
                    variante.getSampleData(control2) + "\t" +
                    variante.getAttribute(VariantCaseControlDominantAnnotator.DCC_SCORE_TAG) + "\t");
        }
        System.out.println("--------------------------------------");

        assertTrue("'noAllelesInCases' variant should have score 0", noAllelesInCases.getAttribute(VariantCaseControlDominantAnnotator.DCC_SCORE_TAG).equals("0") );
        assertTrue("'oneAlleleInControl' variant should have score 0", oneAlleleInControl.getAttribute(VariantCaseControlDominantAnnotator.DCC_SCORE_TAG).equals("0") );
        assertTrue("'twoAllelesOneInControl' variant should have score 1/2", oneAlleleNotInControl.getAttribute(VariantCaseControlDominantAnnotator.DCC_SCORE_TAG).equals("1/2") );
        assertTrue("'twoAllelesBothInControl' variant should have score 1/2", twoAllelesOneInControl.getAttribute(VariantCaseControlDominantAnnotator.DCC_SCORE_TAG).equals("1/2") );
        assertTrue("'oneAlleleInControl' variant should have score 0", twoAllelesBothInControl.getAttribute(VariantCaseControlDominantAnnotator.DCC_SCORE_TAG).equals("0") );
        assertTrue("'allCasesMV' variant should have score 0", allCasesMV.getAttribute(VariantCaseControlDominantAnnotator.DCC_SCORE_TAG).equals("0") );
        assertTrue("'allControlsMV' variant should have score 1/2", allControlsMV.getAttribute(VariantCaseControlDominantAnnotator.DCC_SCORE_TAG).equals("1/2") );

        // Test 2
        List<Variant> variantsToBeFiltered2 = new ArrayList<Variant>();
        variantsToBeFiltered2.add(noAllelesInCases);
        variantsToBeFiltered2.add(oneAlleleInControl);
        variantsToBeFiltered2.add(twoAllelesNotInControl);
        variantsToBeFiltered2.add(twoAllelesOneInControl);
        variantsToBeFiltered2.add(twoAllelesBothInControl);
        variantsToBeFiltered2.add(allCasesMV);
        variantsToBeFiltered2.add(allControlsMV);

        annotator.annot(variantsToBeFiltered2);

        System.out.println("--------------- TEST 2 ---------------");
        for(Variant variante: variantsToBeFiltered2){
            System.out.println(variante.getSampleData(sample1) + "\t" +
                    variante.getSampleData(sample2) + "\t" + variante.getSampleData(control1) + "\t" +
                    variante.getSampleData(control2) + "\t" +
                    variante.getAttribute(VariantCaseControlDominantAnnotator.DCC_SCORE_TAG) + "\t");
        }
        System.out.println("--------------------------------------");

        assertTrue("'noAllelesInCases' variant should have score 0", noAllelesInCases.getAttribute(VariantCaseControlDominantAnnotator.DCC_SCORE_TAG).equals("0") );
        assertTrue("'oneAlleleInControl' variant should have score 0", oneAlleleInControl.getAttribute(VariantCaseControlDominantAnnotator.DCC_SCORE_TAG).equals("0") );
        assertTrue("'twoAllelesNotInControl' variant should have score 2/2", twoAllelesNotInControl.getAttribute(VariantCaseControlDominantAnnotator.DCC_SCORE_TAG).equals("2/2") );
        assertTrue("'twoAllelesOneInControl' variant should have score 2/2", twoAllelesOneInControl.getAttribute(VariantCaseControlDominantAnnotator.DCC_SCORE_TAG).equals("2/2") );
        assertTrue("'twoAllelesBothInControl' variant should have score 0", twoAllelesBothInControl.getAttribute(VariantCaseControlDominantAnnotator.DCC_SCORE_TAG).equals("0") );
        assertTrue("'allCasesMV' variant should have score 0", allCasesMV.getAttribute(VariantCaseControlDominantAnnotator.DCC_SCORE_TAG).equals("0") );
        assertTrue("'allControlsMV' variant should have score 1/2", allControlsMV.getAttribute(VariantCaseControlDominantAnnotator.DCC_SCORE_TAG).equals("2/2") );
    }
}