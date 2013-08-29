package org.opencb.commons.bioformats.commons.core.vcfstats;

/**
 * Created with IntelliJ IDEA.
 * User: aaleman
 * Date: 8/29/13
 * Time: 11:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class VcfGlobalStat {

    private int variantsCount;
    private int samplesCount;
    private int snpsCount;
    private int indelsCount;
    private int passCount;
    private int transitionsCount;
    private int transversionsCount;
    private int biallelicsCount;
    private int multiallelicsCount;
    private float accumQuality;

    public VcfGlobalStat() {

        this.variantsCount = 0;
        this.samplesCount = 0;
        this.snpsCount = 0;
        this.indelsCount = 0;
        this.passCount = 0;
        this.transitionsCount = 0;
        this.transversionsCount = 0;
        this.biallelicsCount = 0;
        this.multiallelicsCount = 0;
        this.accumQuality = 0;
    }

    public void updateStats(int variants_count, int samples_count, int snps_count, int indels_count, int pass_count, int transitions_count, int transversions_count, int biallelics_count, int multiallelics_count, float accum_quality) {
        this.variantsCount += variants_count;
        if(this.samplesCount == 0)
            this.samplesCount += samples_count;
        this.snpsCount += snps_count;
        this.indelsCount += indels_count;
        this.passCount += pass_count;
        this.transitionsCount += transitions_count;
        this.transversionsCount += transversions_count;
        this.biallelicsCount += biallelics_count;
        this.multiallelicsCount += multiallelics_count;
        this.accumQuality += accum_quality;
    }

    public int getVariantsCount() {
        return variantsCount;
    }

    public void setVariantsCount(int variantsCount) {
        this.variantsCount = variantsCount;
    }

    public int getSamplesCount() {
        return samplesCount;
    }

    public void setSamplesCount(int samplesCount) {
        this.samplesCount = samplesCount;
    }

    public int getSnpsCount() {
        return snpsCount;
    }

    public void setSnpsCount(int snpsCount) {
        this.snpsCount = snpsCount;
    }

    public int getIndelsCount() {
        return indelsCount;
    }

    public void setIndelsCount(int indelsCount) {
        this.indelsCount = indelsCount;
    }

    public int getPassCount() {
        return passCount;
    }

    public void setPassCount(int passCount) {
        this.passCount = passCount;
    }

    public int getTransitionsCount() {
        return transitionsCount;
    }

    public void setTransitionsCount(int transitionsCount) {
        this.transitionsCount = transitionsCount;
    }

    public int getTransversionsCount() {
        return transversionsCount;
    }

    public void setTransversionsCount(int transversionsCount) {
        this.transversionsCount = transversionsCount;
    }

    public int getBiallelicsCount() {
        return biallelicsCount;
    }

    public void setBiallelicsCount(int biallelicsCount) {
        this.biallelicsCount = biallelicsCount;
    }

    public int getMultiallelicsCount() {
        return multiallelicsCount;
    }

    public void setMultiallelicsCount(int multiallelicsCount) {
        this.multiallelicsCount = multiallelicsCount;
    }

    public float getAccumQuality() {
        return accumQuality;
    }

    public void setAccumQuality(float accumQuality) {
        this.accumQuality = accumQuality;
    }
}
