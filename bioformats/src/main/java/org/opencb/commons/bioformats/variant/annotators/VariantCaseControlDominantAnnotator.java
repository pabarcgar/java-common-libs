package org.opencb.commons.bioformats.variant.annotators;

import org.opencb.commons.bioformats.feature.Genotype;
import org.opencb.commons.bioformats.pedigree.Individual;
import org.opencb.commons.bioformats.pedigree.Pedigree;
import org.opencb.commons.bioformats.variant.Variant;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lcruz on 31/03/14.
 */
public class VariantCaseControlDominantAnnotator implements VariantAnnotator {
    Map<String, Individual> mapAffected = new HashMap<String, Individual>();
    Map<String, Individual> mapUnaffected = new HashMap<String, Individual>();

    public VariantCaseControlDominantAnnotator (Pedigree pedigreeFile){

    }

    @Override
    public void annot(List<Variant> batch) {

        if(batch == null || batch.isEmpty()){
            throw new UnsupportedOperationException("The list of variants is empty");
        } else {
            // Iterate the list of variants to check if this variant is mutated
            for(Variant variant: batch){
                Map<String, Map<String, String>> map = variant.getSamplesData();
                boolean isMutatedInControls = false;

                Iterator it = map.keySet().iterator();
                while(it.hasNext() && !isMutatedInControls){
                    String sample = (String)it.next();

                    // Get the genotype of the GT atribute
                    Genotype genotype = new Genotype(variant.getSampleData(sample, "GT"));

                    if(!genotype.get){
                        isMutatedInControls = true;
                    }
                }

                // If the variant is mutated in a control, set its score to 0
                if(isMutatedInControls){
                    variant.addAttribute("DCC_Score", new String("0"));
                }
            }
        }
    }

    @Override
    public void annot(Variant elem) {
        throw new UnsupportedOperationException("This annotator doesn't work on individual variants");
    }
}
