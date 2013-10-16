package org.opencb.javalibs.bioformats.variant.vcf4.io.readers;

import com.google.common.base.Predicate;
import org.bioinfo.commons.io.utils.FileUtils;
import org.bioinfo.commons.utils.ListUtils;
import org.bioinfo.commons.utils.StringUtils;
import org.opencb.javalibs.bioformats.commons.exception.FileFormatException;
import org.opencb.javalibs.bioformats.variant.vcf4.*;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: aaleman
 * Date: 8/30/13
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class VariantVcfDataReader implements VariantDataReader {

    private static final int DEFAULT_NUMBER_RECORDS = 40000;
    private Vcf4 vcf4;
    private BufferedReader reader;
    private List<Predicate<VcfRecord>> vcfFilters;
    private Predicate<VcfRecord> andVcfFilters;
    private File file;
    private String filename;

    public VariantVcfDataReader(String filename) {
        this.filename = filename;
    }


    @Override
    public boolean open() {

        try {
            this.file = new File(this.filename);
            vcf4 = new Vcf4();
            FileUtils.checkFile(this.file);

            if (Files.probeContentType(file.toPath()).contains("gzip")) {
                this.reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(this.file))));
            } else {
                this.reader = new BufferedReader(new FileReader(this.file));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }


    @Override
    public boolean pre() {

        try {
            processHeader();
        } catch (IOException | FileFormatException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean close() {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public boolean post() {
        return true;
    }

    @Override
    public VcfRecord read() {
        String line;
        try {
            while ((line = reader.readLine()) != null && (line.trim().equals("") || line.startsWith("#"))) {
                ;
            }
            if (line != null) {
                String[] fields = line.split("\t");
                VcfRecord vcfRecord = null;
                if (fields.length == 8) {
                    vcfRecord = new VcfRecord(fields[0], Integer.parseInt(fields[1]), fields[2], fields[3], fields[4], fields[5], fields[6], fields[7]);
                    vcfRecord.setSampleIndex(vcf4.getSamples());
                } else {
                    if (fields.length > 8) {
                        vcfRecord = new VcfRecord(fields);
                        vcfRecord.setSampleIndex(vcf4.getSamples());
                    }
                }
                return vcfRecord;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public List<VcfRecord> read(int batchSize) {
        List<VcfRecord> listRecords = new ArrayList<>(batchSize);
        VcfRecord vcfRecord;
        int i = 0;

        while ((i < batchSize) && (vcfRecord = this.read()) != null) {

            if (vcfFilters != null && vcfFilters.size() > 0) {
                if (andVcfFilters.apply(vcfRecord)) {
                    vcfRecord.setSampleIndex(vcf4.getSamples());
                    listRecords.add(vcfRecord);
                    i++;
                }
            } else {
                vcfRecord.setSampleIndex(vcf4.getSamples());
                listRecords.add(vcfRecord);
                i++;
            }

        }
        return listRecords;
    }


    @Override
    public List<String> getSampleNames() {
        return this.vcf4.getSampleNames();
    }

    @Override
    public String getHeader() {
        StringBuilder header = new StringBuilder();
        header.append("##fileformat=").append(vcf4.getFileFormat()).append("\n");

        Iterator<String> iter = vcf4.getMetaInformation().keySet().iterator();
        String headerKey;
        while (iter.hasNext()) {
            headerKey = iter.next();
            header.append("##").append(headerKey).append("=").append(vcf4.getMetaInformation().get(headerKey)).append("\n");
        }

        for (VcfAlternateHeader vcfAlternateHeader : vcf4.getAlternate().values()) {
            header.append(vcfAlternateHeader.toString()).append("\n");
        }

        for (VcfFilterHeader vcfFilterHeader : vcf4.getFilter().values()) {
            header.append(vcfFilterHeader.toString()).append("\n");
        }

        for (VcfInfoHeader vcfInfoHeader : vcf4.getInfo().values()) {
            header.append(vcfInfoHeader.toString()).append("\n");
        }

        for (VcfFormatHeader vcfFormatHeader : vcf4.getFormat().values()) {
            header.append(vcfFormatHeader.toString()).append("\n");
        }

        header.append("#").append(ListUtils.toString(vcf4.getHeaderLine(), "\t")).append("\n");


        return header.toString();
    }


    private void processHeader() throws IOException, FileFormatException {
        VcfInfoHeader vcfInfoHeader;
        VcfFilterHeader vcfFilterHeader;
        VcfFormatHeader vcfFormatHeader;
        List<String> headerLine;
        String line;
        String[] fields;

        BufferedReader localBufferedReader;
        if (Files.probeContentType(file.toPath()).contains("gzip")) {
            localBufferedReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(this.file))));
        } else {
            localBufferedReader = new BufferedReader(new FileReader(this.file));
        }


        boolean header = false;
        while ((line = localBufferedReader.readLine()) != null && line.startsWith("#")) {

            if (line.startsWith("##fileformat")) {
                if (line.split("=").length > 1) {

                    vcf4.setFileFormat(line.split("=")[1].trim());
                } else {
                    throw new FileFormatException("");
                }
            } else if (line.startsWith("##INFO")) {

                vcfInfoHeader = new VcfInfoHeader(line);
                vcf4.getInfo().put(vcfInfoHeader.getId(), vcfInfoHeader);
            } else if (line.startsWith("##FILTER")) {

                vcfFilterHeader = new VcfFilterHeader(line);
                vcf4.getFilter().put(vcfFilterHeader.getId(), vcfFilterHeader);
            } else if (line.startsWith("##FORMAT")) {

                vcfFormatHeader = new VcfFormatHeader(line);
                vcf4.getFormat().put(vcfFormatHeader.getId(), vcfFormatHeader);
            } else if (line.startsWith("#CHROM")) {
                headerLine = StringUtils.toList(line.replace("#", ""), "\t");

                vcf4.setHeaderLine(headerLine);
                header |= true;
            } else {
                fields = line.replace("#", "").split("=", 2);
                vcf4.getMetaInformation().put(fields[0], fields[1]);
            }
        }
        if (!header) {
            System.err.println("VCF Header must be provided.");
            System.exit(-1);
        }
        localBufferedReader.close();
    }


}
