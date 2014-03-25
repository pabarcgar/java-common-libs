package org.opencb.commons.bioformats.variant.filters;


import org.opencb.commons.bioformats.variant.Variant;

import java.util.List;

/**
 * Created by parce on 12/30/13.
 */
public abstract class VariantGeneLevelFilter {

    private int priority;

    protected VariantGeneLevelFilter() {
        this(0);
    }

    protected VariantGeneLevelFilter(int priority) {
        this.priority = priority;
    }

    public abstract List<Variant> apply(List<Variant> variantsToBeFiltered);
}