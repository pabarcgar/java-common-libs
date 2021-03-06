package org.opencb.commons.bioformats.variant.annotators;

import org.junit.Test;
import org.opencb.commons.bioformats.variant.Variant;
import org.opencb.commons.bioformats.variant.vcf4.io.readers.VariantVcfReader;
import org.opencb.commons.io.DataReader;
import org.opencb.commons.test.GenericTest;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Alejandro Aleman Ramos <aaleman@cipf.es>
 */
public class VariantControlMongoAnnotatorTest extends GenericTest {
    @Test
    public void testAnnot() throws Exception {

        DataReader<Variant> reader = new VariantVcfReader("/home/aaleman/tmp/small.vcf");

        VariantAnnotator control = new VariantControlMongoAnnotator();


        reader.open();
        reader.pre();

        List<Variant> batch = reader.read(100);

        while (!batch.isEmpty()) {


            control.annot(batch);


            for (Variant v : batch) {
                System.out.println(v);
            }


            // break;
            batch.clear();
            batch = reader.read(10);
        }

        reader.post();
        reader.close();


        assertTrue(1 == 1);

    }
}
