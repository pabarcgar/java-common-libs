package org.opencb.commons.bioformats.variant.vcf4;

import java.util.Comparator;

/**
 * Created by parce on 12/23/13.
 */
public class VcfRecordCoordinateComparator implements Comparator<VcfRecord> {

    /**
     * Compare the coordinates of two vcf records
     * @param vcf1
     * @param vcf2
     * @return
     */
    public int compare(VcfRecord vcf1, VcfRecord vcf2) {
        if (!vcf1.getChromosome().equals(vcf2.getChromosome())) {
            try {
                int chr1 = Integer.parseInt(vcf1.getChromosome());
                int chr2 = Integer.parseInt(vcf2.getChromosome());
                return chr1 - chr2;
            } catch (NumberFormatException e) {
                return vcf2.getPosition() - vcf1.getPosition();
            }
        } else {
            return vcf1.getPosition() - vcf2.getPosition();
        }
    }



}
