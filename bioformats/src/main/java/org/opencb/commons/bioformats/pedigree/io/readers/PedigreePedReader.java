package org.opencb.commons.bioformats.pedigree.io.readers;

import org.opencb.commons.bioformats.pedigree.Individual;
import org.opencb.commons.bioformats.pedigree.Pedigree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author Alejandro Aleman Ramos <aaleman@cipf.es>
 */
public class PedigreePedReader implements PedigreeReader {

    private String filename;
    private Pedigree ped;
    private BufferedReader reader;

    public PedigreePedReader(String filename) {
        this.filename = filename;

        ped = new Pedigree();
    }

    @Override
    public boolean open() {
        boolean res = true;
        ped = new Pedigree();

        try {
            reader = new BufferedReader(new FileReader(this.filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            res = false;
        }

        return res;
    }

    @Override
    public boolean close() {

        boolean res = true;

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    @Override
    public boolean pre() {
        return false;
    }

    @Override
    public boolean post() {
        return false;
    }

    @Override
    public Pedigree read() {

        String line;
        Individual ind, father, mother;
        String[] fields;
        String sampleId, familyId, fatherId, motherId, sex, phenotype;
        Set<Individual> family;
        String[] auxFields = null;

        try {
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) {
                    this.parseHeader(line);
                } else {
                    fields = line.split("\t");
                    familyId = fields[0];
                    sampleId = fields[1];
                    fatherId = fields[2];
                    motherId = fields[3];
                    sex = fields[4];
                    phenotype = fields[5];

                    if (fields.length > 6) {
                        auxFields = Arrays.copyOfRange(fields, 6, fields.length);
                    }

                    family = ped.getFamily(familyId);
                    if (family == null) {
                        family = new TreeSet<>();
                        ped.addFamily(familyId, family);
                    }

                    ind = new Individual(sampleId, familyId, null, null, sex, phenotype, auxFields);
                    ind.setFatherId(fatherId);
                    ind.setMotherId(motherId);
                    ped.addIndividual(ind);
                    family.add(ind);


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, Individual> entry : ped.getIndividuals().entrySet()) {

            ind = entry.getValue();
            father = ped.getIndividual(ind.getFatherId());
            mother = ped.getIndividual(ind.getMotherId());

            ind.setFather(father);
            ind.setMother(mother);

            if (mother != null) {
                mother.addChild(ind);
            }
            if (father != null) {
                father.addChild(ind);

            }


        }
        return ped;
    }

    @Override
    public List<Pedigree> read(int batchSize) {
        return null;
    }

    private void parseHeader(String lineHeader) {

        String header = lineHeader.substring(1, lineHeader.length());
        String[] allFields = header.split("\t");

        allFields = Arrays.copyOfRange(allFields, 6, allFields.length);
        for (int i = 0; i < allFields.length; i++) {
            ped.getFields().put(allFields[i], i);
        }
    }
}
