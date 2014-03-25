package org.opencb.commons.bioformats.variant;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.Before;
import org.junit.Test;
import org.opencb.commons.bioformats.variant.filters.VariantCompoundHeterozygosityFilter;
import org.opencb.commons.test.GenericTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by parce on 3/24/14.
 */
public class VariantTest extends GenericTest {

    // TODO: si estas variables no se usan mas, meter dentro del metodo testSampleHasAllele
    private Variant testVariant;
    private String sample1 = "s1";
    private String sample2 = "s2";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        // create variant with sample data
        testVariant = new Variant("chr1", 10000, "T", "C,G");
        Map<String, String> sample1Data = new HashMap<>(),
                            sample2Data = new HashMap<>();
        sample1Data.put(Variant.GENOTYPE_TAG, "0/1");
        sample2Data.put(Variant.GENOTYPE_TAG, "1/2");
        testVariant.addSampleData("s1", sample1Data);
        testVariant.addSampleData("s2", sample2Data);
    }

    @Test
    public void testSampleHasAllele() throws Exception {
        // check if variant has alleles 1 and 2 for samples s1 and s2
        int allele1 = 1,
            allele2 = 2;
        Boolean sample1HasAllele1 = testVariant.sampleHasAllele(sample1, allele1);
        Boolean sample1HasAllele2 = testVariant.sampleHasAllele(sample1, allele2);
        Boolean sample2HasAllele1 = testVariant.sampleHasAllele(sample2, allele1);
        Boolean sample2HasAllele2 = testVariant.sampleHasAllele(sample2, allele2);

        // assert results
        assertTrue("Sample s1 has allele '1'", sample1HasAllele1);
        assertFalse("Sample s1 doesn't have allele '2'",sample1HasAllele2);
        assertTrue("Sample s2 has allele '1'", sample2HasAllele1);
        assertTrue("Sample s2 has allele '2'", sample2HasAllele2);
    }
}
