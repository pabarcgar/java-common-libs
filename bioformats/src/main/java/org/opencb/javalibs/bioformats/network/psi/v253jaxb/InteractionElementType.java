//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.03.15 at 03:58:18 PM CET 
//


package org.opencb.javalibs.bioformats.network.psi.v253jaxb;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * A molecular interaction.
 * <p/>
 * <p>Java class for interactionElementType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="interactionElementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="names" type="{net:sf:psidev:mi}namesType" minOccurs="0"/>
 *         &lt;element name="xref" type="{net:sf:psidev:mi}xrefType" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="availabilityRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *           &lt;element name="availability" type="{net:sf:psidev:mi}availabilityType"/>
 *         &lt;/choice>
 *         &lt;element name="experimentList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice maxOccurs="unbounded">
 *                   &lt;element name="experimentRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="experimentDescription" type="{net:sf:psidev:mi}experimentType"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="participantList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="participant" type="{net:sf:psidev:mi}participantType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="inferredInteractionList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="inferredInteraction" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="participant" maxOccurs="unbounded" minOccurs="2">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;choice>
 *                                       &lt;element name="participantRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                       &lt;element name="participantFeatureRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                     &lt;/choice>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="experimentRefList" type="{net:sf:psidev:mi}experimentRefListType" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="interactionType" type="{net:sf:psidev:mi}cvType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="modelled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="intraMolecular" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="negative" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="confidenceList" type="{net:sf:psidev:mi}confidenceListType" minOccurs="0"/>
 *         &lt;element name="parameterList" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="parameter" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{net:sf:psidev:mi}parameterType">
 *                           &lt;sequence>
 *                             &lt;element name="experimentRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="uncertainty" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="attributeList" type="{net:sf:psidev:mi}attributeListType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="imexId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "interactionElementType", propOrder = {
        "names",
        "xref",
        "availabilityRef",
        "availability",
        "experimentList",
        "participantList",
        "inferredInteractionList",
        "interactionType",
        "modelled",
        "intraMolecular",
        "negative",
        "confidenceList",
        "parameterList",
        "attributeList"
})
@XmlSeeAlso({
        org.opencb.javalibs.bioformats.network.psi.v253jaxb.EntrySet.Entry.InteractionList.Interaction.class
})
public class InteractionElementType {

    protected NamesType names;
    protected XrefType xref;
    protected Integer availabilityRef;
    protected AvailabilityType availability;
    @XmlElement(required = true)
    protected InteractionElementType.ExperimentList experimentList;
    @XmlElement(required = true)
    protected InteractionElementType.ParticipantList participantList;
    protected InteractionElementType.InferredInteractionList inferredInteractionList;
    protected List<CvType> interactionType;
    protected Boolean modelled;
    @XmlElement(defaultValue = "false")
    protected Boolean intraMolecular;
    @XmlElement(defaultValue = "false")
    protected Boolean negative;
    protected ConfidenceListType confidenceList;
    protected InteractionElementType.ParameterList parameterList;
    protected AttributeListType attributeList;
    @XmlAttribute
    protected String imexId;
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
     * Gets the value of the availabilityRef property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getAvailabilityRef() {
        return availabilityRef;
    }

    /**
     * Sets the value of the availabilityRef property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setAvailabilityRef(Integer value) {
        this.availabilityRef = value;
    }

    /**
     * Gets the value of the availability property.
     *
     * @return possible object is
     *         {@link AvailabilityType }
     */
    public AvailabilityType getAvailability() {
        return availability;
    }

    /**
     * Sets the value of the availability property.
     *
     * @param value allowed object is
     *              {@link AvailabilityType }
     */
    public void setAvailability(AvailabilityType value) {
        this.availability = value;
    }

    /**
     * Gets the value of the experimentList property.
     *
     * @return possible object is
     *         {@link InteractionElementType.ExperimentList }
     */
    public InteractionElementType.ExperimentList getExperimentList() {
        return experimentList;
    }

    /**
     * Sets the value of the experimentList property.
     *
     * @param value allowed object is
     *              {@link InteractionElementType.ExperimentList }
     */
    public void setExperimentList(InteractionElementType.ExperimentList value) {
        this.experimentList = value;
    }

    /**
     * Gets the value of the participantList property.
     *
     * @return possible object is
     *         {@link InteractionElementType.ParticipantList }
     */
    public InteractionElementType.ParticipantList getParticipantList() {
        return participantList;
    }

    /**
     * Sets the value of the participantList property.
     *
     * @param value allowed object is
     *              {@link InteractionElementType.ParticipantList }
     */
    public void setParticipantList(InteractionElementType.ParticipantList value) {
        this.participantList = value;
    }

    /**
     * Gets the value of the inferredInteractionList property.
     *
     * @return possible object is
     *         {@link InteractionElementType.InferredInteractionList }
     */
    public InteractionElementType.InferredInteractionList getInferredInteractionList() {
        return inferredInteractionList;
    }

    /**
     * Sets the value of the inferredInteractionList property.
     *
     * @param value allowed object is
     *              {@link InteractionElementType.InferredInteractionList }
     */
    public void setInferredInteractionList(InteractionElementType.InferredInteractionList value) {
        this.inferredInteractionList = value;
    }

    /**
     * Gets the value of the interactionType property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the interactionType property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInteractionType().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link CvType }
     */
    public List<CvType> getInteractionType() {
        if (interactionType == null) {
            interactionType = new ArrayList<CvType>();
        }
        return this.interactionType;
    }

    /**
     * Gets the value of the modelled property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isModelled() {
        return modelled;
    }

    /**
     * Sets the value of the modelled property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setModelled(Boolean value) {
        this.modelled = value;
    }

    /**
     * Gets the value of the intraMolecular property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isIntraMolecular() {
        return intraMolecular;
    }

    /**
     * Sets the value of the intraMolecular property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setIntraMolecular(Boolean value) {
        this.intraMolecular = value;
    }

    /**
     * Gets the value of the negative property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isNegative() {
        return negative;
    }

    /**
     * Sets the value of the negative property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setNegative(Boolean value) {
        this.negative = value;
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
     * Gets the value of the parameterList property.
     *
     * @return possible object is
     *         {@link InteractionElementType.ParameterList }
     */
    public InteractionElementType.ParameterList getParameterList() {
        return parameterList;
    }

    /**
     * Sets the value of the parameterList property.
     *
     * @param value allowed object is
     *              {@link InteractionElementType.ParameterList }
     */
    public void setParameterList(InteractionElementType.ParameterList value) {
        this.parameterList = value;
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
     * Gets the value of the imexId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getImexId() {
        return imexId;
    }

    /**
     * Sets the value of the imexId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setImexId(String value) {
        this.imexId = value;
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
     *       &lt;choice maxOccurs="unbounded">
     *         &lt;element name="experimentRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="experimentDescription" type="{net:sf:psidev:mi}experimentType"/>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "experimentRefOrExperimentDescription"
    })
    public static class ExperimentList {

        @XmlElements({
                @XmlElement(name = "experimentDescription", type = ExperimentType.class),
                @XmlElement(name = "experimentRef", type = Integer.class)
        })
        protected List<Object> experimentRefOrExperimentDescription;

        /**
         * Gets the value of the experimentRefOrExperimentDescription property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the experimentRefOrExperimentDescription property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getExperimentRefOrExperimentDescription().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link ExperimentType }
         * {@link Integer }
         */
        public List<Object> getExperimentRefOrExperimentDescription() {
            if (experimentRefOrExperimentDescription == null) {
                experimentRefOrExperimentDescription = new ArrayList<Object>();
            }
            return this.experimentRefOrExperimentDescription;
        }

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
     *         &lt;element name="inferredInteraction" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="participant" maxOccurs="unbounded" minOccurs="2">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;choice>
     *                             &lt;element name="participantRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                             &lt;element name="participantFeatureRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                           &lt;/choice>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="experimentRefList" type="{net:sf:psidev:mi}experimentRefListType" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
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
            "inferredInteraction"
    })
    public static class InferredInteractionList {

        @XmlElement(required = true)
        protected List<InferredInteraction> inferredInteraction;

        /**
         * Gets the value of the inferredInteraction property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the inferredInteraction property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInferredInteraction().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link InteractionElementType.InferredInteractionList.InferredInteraction }
         */
        public List<InferredInteraction> getInferredInteraction() {
            if (inferredInteraction == null) {
                inferredInteraction = new ArrayList<InferredInteraction>();
            }
            return this.inferredInteraction;
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
         *         &lt;element name="participant" maxOccurs="unbounded" minOccurs="2">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;choice>
         *                   &lt;element name="participantRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                   &lt;element name="participantFeatureRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                 &lt;/choice>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="experimentRefList" type="{net:sf:psidev:mi}experimentRefListType" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "participant",
                "experimentRefList"
        })
        public static class InferredInteraction {

            @XmlElement(required = true)
            protected List<Participant> participant;
            protected ExperimentRefListType experimentRefList;

            /**
             * Gets the value of the participant property.
             * <p/>
             * <p/>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the participant property.
             * <p/>
             * <p/>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getParticipant().add(newItem);
             * </pre>
             * <p/>
             * <p/>
             * <p/>
             * Objects of the following type(s) are allowed in the list
             * {@link InteractionElementType.InferredInteractionList.InferredInteraction.Participant }
             */
            public List<Participant> getParticipant() {
                if (participant == null) {
                    participant = new ArrayList<Participant>();
                }
                return this.participant;
            }

            /**
             * Gets the value of the experimentRefList property.
             *
             * @return possible object is
             *         {@link ExperimentRefListType }
             */
            public ExperimentRefListType getExperimentRefList() {
                return experimentRefList;
            }

            /**
             * Sets the value of the experimentRefList property.
             *
             * @param value allowed object is
             *              {@link ExperimentRefListType }
             */
            public void setExperimentRefList(ExperimentRefListType value) {
                this.experimentRefList = value;
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
             *       &lt;choice>
             *         &lt;element name="participantRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *         &lt;element name="participantFeatureRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *       &lt;/choice>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                    "participantRef",
                    "participantFeatureRef"
            })
            public static class Participant {

                protected Integer participantRef;
                protected Integer participantFeatureRef;

                /**
                 * Gets the value of the participantRef property.
                 *
                 * @return possible object is
                 *         {@link Integer }
                 */
                public Integer getParticipantRef() {
                    return participantRef;
                }

                /**
                 * Sets the value of the participantRef property.
                 *
                 * @param value allowed object is
                 *              {@link Integer }
                 */
                public void setParticipantRef(Integer value) {
                    this.participantRef = value;
                }

                /**
                 * Gets the value of the participantFeatureRef property.
                 *
                 * @return possible object is
                 *         {@link Integer }
                 */
                public Integer getParticipantFeatureRef() {
                    return participantFeatureRef;
                }

                /**
                 * Sets the value of the participantFeatureRef property.
                 *
                 * @param value allowed object is
                 *              {@link Integer }
                 */
                public void setParticipantFeatureRef(Integer value) {
                    this.participantFeatureRef = value;
                }

            }

        }

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
     *         &lt;element name="parameter" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{net:sf:psidev:mi}parameterType">
     *                 &lt;sequence>
     *                   &lt;element name="experimentRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="uncertainty" type="{http://www.w3.org/2001/XMLSchema}decimal" />
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
            "parameter"
    })
    public static class ParameterList {

        @XmlElement(required = true)
        protected List<Parameter> parameter;

        /**
         * Gets the value of the parameter property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the parameter property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getParameter().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link InteractionElementType.ParameterList.Parameter }
         */
        public List<Parameter> getParameter() {
            if (parameter == null) {
                parameter = new ArrayList<Parameter>();
            }
            return this.parameter;
        }


        /**
         * <p>Java class for anonymous complex type.
         * <p/>
         * <p>The following schema fragment specifies the expected content contained within this class.
         * <p/>
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{net:sf:psidev:mi}parameterType">
         *       &lt;sequence>
         *         &lt;element name="experimentRef" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *       &lt;/sequence>
         *       &lt;attribute name="uncertainty" type="{http://www.w3.org/2001/XMLSchema}decimal" />
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
                "experimentRef"
        })
        public static class Parameter
                extends ParameterType {

            protected int experimentRef;
            @XmlAttribute
            protected BigDecimal uncertainty;

            /**
             * Gets the value of the experimentRef property.
             */
            public int getExperimentRef() {
                return experimentRef;
            }

            /**
             * Sets the value of the experimentRef property.
             */
            public void setExperimentRef(int value) {
                this.experimentRef = value;
            }

            /**
             * Gets the value of the uncertainty property.
             *
             * @return possible object is
             *         {@link java.math.BigDecimal }
             */
            public BigDecimal getUncertainty() {
                return uncertainty;
            }

            /**
             * Sets the value of the uncertainty property.
             *
             * @param value allowed object is
             *              {@link java.math.BigDecimal }
             */
            public void setUncertainty(BigDecimal value) {
                this.uncertainty = value;
            }

        }

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
     *         &lt;element name="participant" type="{net:sf:psidev:mi}participantType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "participant"
    })
    public static class ParticipantList {

        @XmlElement(required = true)
        protected List<ParticipantType> participant;

        /**
         * Gets the value of the participant property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the participant property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getParticipant().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link ParticipantType }
         */
        public List<ParticipantType> getParticipant() {
            if (participant == null) {
                participant = new ArrayList<ParticipantType>();
            }
            return this.participant;
        }

    }

}
