//
// This path was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this path will be lost upon recompilation of the source schema.
// Generated on: 2010.03.15 at 03:58:18 PM CET 
//


package org.opencb.commons.bioformats.network.psi.v253jaxb;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Describes one set of experimental parameters.
 * <p/>
 * <p>Java class for experimentType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="experimentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{net:sf:psidev:mi}namesType" minOccurs="0"/>
 *         &lt;element name="bibref" type="{net:sf:psidev:mi}bibrefType"/>
 *         &lt;element name="xref" type="{net:sf:psidev:mi}xrefType" minOccurs="0"/>
 *         &lt;element name="hostOrganismList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="hostOrganism" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{net:sf:psidev:mi}bioSourceType">
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="interactionDetectionMethod" type="{net:sf:psidev:mi}cvType"/>
 *         &lt;element name="participantIdentificationMethod" type="{net:sf:psidev:mi}cvType" minOccurs="0"/>
 *         &lt;element name="featureDetectionMethod" type="{net:sf:psidev:mi}cvType" minOccurs="0"/>
 *         &lt;element name="confidenceList" type="{net:sf:psidev:mi}confidenceListType" minOccurs="0"/>
 *         &lt;element name="attributeList" type="{net:sf:psidev:mi}attributeListType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "experimentType", propOrder = {
        "names",
        "bibref",
        "xref",
        "hostOrganismList",
        "interactionDetectionMethod",
        "participantIdentificationMethod",
        "featureDetectionMethod",
        "confidenceList",
        "attributeList"
})
public class ExperimentType {

    protected NamesType names;
    @XmlElement(required = true)
    protected BibrefType bibref;
    protected XrefType xref;
    protected ExperimentType.HostOrganismList hostOrganismList;
    @XmlElement(required = true)
    protected CvType interactionDetectionMethod;
    protected CvType participantIdentificationMethod;
    protected CvType featureDetectionMethod;
    protected ConfidenceListType confidenceList;
    protected AttributeListType attributeList;
    @XmlAttribute(required = true)
    protected int id;

    /**
     * Gets the value of the names property.
     *
     * @return possible object is
     *         {@link NamesType }
     */
    public NamesType getNames() {
        return names;
    }

    /**
     * Sets the value of the names property.
     *
     * @param value allowed object is
     *              {@link NamesType }
     */
    public void setNames(NamesType value) {
        this.names = value;
    }

    /**
     * Gets the value of the bibref property.
     *
     * @return possible object is
     *         {@link BibrefType }
     */
    public BibrefType getBibref() {
        return bibref;
    }

    /**
     * Sets the value of the bibref property.
     *
     * @param value allowed object is
     *              {@link BibrefType }
     */
    public void setBibref(BibrefType value) {
        this.bibref = value;
    }

    /**
     * Gets the value of the xref property.
     *
     * @return possible object is
     *         {@link XrefType }
     */
    public XrefType getXref() {
        return xref;
    }

    /**
     * Sets the value of the xref property.
     *
     * @param value allowed object is
     *              {@link XrefType }
     */
    public void setXref(XrefType value) {
        this.xref = value;
    }

    /**
     * Gets the value of the hostOrganismList property.
     *
     * @return possible object is
     *         {@link ExperimentType.HostOrganismList }
     */
    public ExperimentType.HostOrganismList getHostOrganismList() {
        return hostOrganismList;
    }

    /**
     * Sets the value of the hostOrganismList property.
     *
     * @param value allowed object is
     *              {@link ExperimentType.HostOrganismList }
     */
    public void setHostOrganismList(ExperimentType.HostOrganismList value) {
        this.hostOrganismList = value;
    }

    /**
     * Gets the value of the interactionDetectionMethod property.
     *
     * @return possible object is
     *         {@link CvType }
     */
    public CvType getInteractionDetectionMethod() {
        return interactionDetectionMethod;
    }

    /**
     * Sets the value of the interactionDetectionMethod property.
     *
     * @param value allowed object is
     *              {@link CvType }
     */
    public void setInteractionDetectionMethod(CvType value) {
        this.interactionDetectionMethod = value;
    }

    /**
     * Gets the value of the participantIdentificationMethod property.
     *
     * @return possible object is
     *         {@link CvType }
     */
    public CvType getParticipantIdentificationMethod() {
        return participantIdentificationMethod;
    }

    /**
     * Sets the value of the participantIdentificationMethod property.
     *
     * @param value allowed object is
     *              {@link CvType }
     */
    public void setParticipantIdentificationMethod(CvType value) {
        this.participantIdentificationMethod = value;
    }

    /**
     * Gets the value of the featureDetectionMethod property.
     *
     * @return possible object is
     *         {@link CvType }
     */
    public CvType getFeatureDetectionMethod() {
        return featureDetectionMethod;
    }

    /**
     * Sets the value of the featureDetectionMethod property.
     *
     * @param value allowed object is
     *              {@link CvType }
     */
    public void setFeatureDetectionMethod(CvType value) {
        this.featureDetectionMethod = value;
    }

    /**
     * Gets the value of the confidenceList property.
     *
     * @return possible object is
     *         {@link ConfidenceListType }
     */
    public ConfidenceListType getConfidenceList() {
        return confidenceList;
    }

    /**
     * Sets the value of the confidenceList property.
     *
     * @param value allowed object is
     *              {@link ConfidenceListType }
     */
    public void setConfidenceList(ConfidenceListType value) {
        this.confidenceList = value;
    }

    /**
     * Gets the value of the attributeList property.
     *
     * @return possible object is
     *         {@link AttributeListType }
     */
    public AttributeListType getAttributeList() {
        return attributeList;
    }

    /**
     * Sets the value of the attributeList property.
     *
     * @param value allowed object is
     *              {@link AttributeListType }
     */
    public void setAttributeList(AttributeListType value) {
        this.attributeList = value;
    }

    /**
     * Gets the value of the id property.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     */
    public void setId(int value) {
        this.id = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * <p/>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p/>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="hostOrganism" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{net:sf:psidev:mi}bioSourceType">
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "hostOrganism"
    })
    public static class HostOrganismList {

        @XmlElement(required = true)
        protected List<HostOrganism> hostOrganism;

        /**
         * Gets the value of the hostOrganism property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hostOrganism property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHostOrganism().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link ExperimentType.HostOrganismList.HostOrganism }
         */
        public List<HostOrganism> getHostOrganism() {
            if (hostOrganism == null) {
                hostOrganism = new ArrayList<HostOrganism>();
            }
            return this.hostOrganism;
        }


        /**
         * <p>Java class for anonymous complex type.
         * <p/>
         * <p>The following schema fragment specifies the expected content contained within this class.
         * <p/>
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{net:sf:psidev:mi}bioSourceType">
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class HostOrganism
                extends BioSourceType {


        }

    }

}
