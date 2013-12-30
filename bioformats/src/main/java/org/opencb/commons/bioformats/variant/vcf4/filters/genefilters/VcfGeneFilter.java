package org.opencb.commons.bioformats.variant.vcf4.filters.genefilters;


import org.opencb.commons.bioformats.variant.vcf4.VcfRecord;

import java.util.List;

/**
 * Created by parce on 12/30/13.
 */
public abstract class VcfGeneFilter {

    public abstract List<VcfRecord> apply(List<VcfRecord> variantsToBeFiltered);

}