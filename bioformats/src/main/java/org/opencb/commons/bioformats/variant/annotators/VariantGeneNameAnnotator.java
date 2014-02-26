package org.opencb.commons.bioformats.variant.annotators;

import com.google.common.base.Joiner;
import org.opencb.commons.bioformats.variant.Variant;
import org.opencb.commons.bioformats.variant.utils.effect.VariantEffect;
import org.opencb.commons.bioformats.variant.effect.EffectCalculator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alejandro Aleman Ramos <aaleman@cipf.es>
 */
public class VariantGeneNameAnnotator implements VariantAnnotator {

    @Override
    public void annot(List<Variant> batch) {

        List<VariantEffect> batchEffect = EffectCalculator.getEffects(batch);

        for (Variant variant : batch) {

            annotVariantEffect(variant, batchEffect);
        }

    }

    private void annotVariantEffect(Variant variant, List<VariantEffect> batchEffect) {

        Set<String> geneNames = new HashSet<>();
        for (VariantEffect effect : batchEffect) {

            if (variant.getChromosome().equals(effect.getChromosome()) &&
                    variant.getPosition() == effect.getPosition()) {
            	// check that the effect gene name is not an empty string
            	if (effect.getGeneName() != null && !effect.getGeneName().equals("")) {
            		geneNames.add(effect.getGeneName());
            	}
            }

        }

        String geneNamesAll = Joiner.on(",").join(geneNames);

        if (geneNames.size() > 0) {
            variant.addAttribute("GeneNames", geneNamesAll);
//            variant.addInfoField("GeneNames=" + geneNamesAll);
        }

    }

    @Override
    public void annot(Variant elem) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
