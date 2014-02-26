package org.opencb.commons.bioformats.variant.vcf4.io.writers;

import org.opencb.commons.bioformats.variant.Variant;
import org.opencb.commons.bioformats.variant.vcf4.VcfRecord;
import org.opencb.commons.io.DataWriter;

/**
 * @author Alejandro Aleman Ramos <aaleman@cipf.es>
 */
public interface VariantWriter extends DataWriter<Variant> {

    void includeStats(boolean stats);

    void includeSamples(boolean samples);

    void includeEffect(boolean effect);
}
