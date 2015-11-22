//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.21 at 04:08:41 PM NST 
//
package com.github.xxbeanxx.noticeseditor.bindings;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="notice" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="title">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="english" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="french" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="text">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="english" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="french" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="display-date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="effective-date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="expiry-date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="date-created" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "notice"
})
@XmlRootElement(name = "notices")
public class Notices {

    protected List<Notices.Notice> notice;

    /**
     * Gets the value of the notice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNotice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Notices.Notice }
     * 
     * 
     */
    public List<Notices.Notice> getNotice() {
        if (notice == null) {
            notice = new ArrayList<Notices.Notice>();
        }
        return this.notice;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="title">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="english" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="french" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="text">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="english" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="french" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="display-date" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="effective-date" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="expiry-date" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="date-created" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "title",
        "text",
        "displayDate",
        "effectiveDate",
        "expiryDate",
        "dateCreated"
    })
    public static class Notice {

        @XmlElement(required = true)
        protected Notices.Notice.Title title;
        @XmlElement(required = true)
        protected Notices.Notice.Text text;
        @XmlElement(name = "display-date", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar displayDate;
        @XmlElement(name = "effective-date", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar effectiveDate;
        @XmlElement(name = "expiry-date", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar expiryDate;
        @XmlElement(name = "date-created", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar dateCreated;

        /**
         * Gets the value of the title property.
         * 
         * @return
         *     possible object is
         *     {@link Notices.Notice.Title }
         *     
         */
        public Notices.Notice.Title getTitle() {
            return title;
        }

        /**
         * Sets the value of the title property.
         * 
         * @param value
         *     allowed object is
         *     {@link Notices.Notice.Title }
         *     
         */
        public void setTitle(Notices.Notice.Title value) {
            this.title = value;
        }

        /**
         * Gets the value of the text property.
         * 
         * @return
         *     possible object is
         *     {@link Notices.Notice.Text }
         *     
         */
        public Notices.Notice.Text getText() {
            return text;
        }

        /**
         * Sets the value of the text property.
         * 
         * @param value
         *     allowed object is
         *     {@link Notices.Notice.Text }
         *     
         */
        public void setText(Notices.Notice.Text value) {
            this.text = value;
        }

        /**
         * Gets the value of the displayDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDisplayDate() {
            return displayDate;
        }

        /**
         * Sets the value of the displayDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDisplayDate(XMLGregorianCalendar value) {
            this.displayDate = value;
        }

        /**
         * Gets the value of the effectiveDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getEffectiveDate() {
            return effectiveDate;
        }

        /**
         * Sets the value of the effectiveDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setEffectiveDate(XMLGregorianCalendar value) {
            this.effectiveDate = value;
        }

        /**
         * Gets the value of the expiryDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getExpiryDate() {
            return expiryDate;
        }

        /**
         * Sets the value of the expiryDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setExpiryDate(XMLGregorianCalendar value) {
            this.expiryDate = value;
        }

        /**
         * Gets the value of the dateCreated property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDateCreated() {
            return dateCreated;
        }

        /**
         * Sets the value of the dateCreated property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDateCreated(XMLGregorianCalendar value) {
            this.dateCreated = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="english" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="french" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "english",
            "french"
        })
        public static class Text {

            @XmlElement(required = true)
            protected String english;
            @XmlElement(required = true)
            protected String french;

            /**
             * Gets the value of the english property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEnglish() {
                return english;
            }

            /**
             * Sets the value of the english property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEnglish(String value) {
                this.english = value;
            }

            /**
             * Gets the value of the french property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFrench() {
                return french;
            }

            /**
             * Sets the value of the french property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFrench(String value) {
                this.french = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="english" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="french" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "english",
            "french"
        })
        public static class Title {

            @XmlElement(required = true)
            protected String english;
            @XmlElement(required = true)
            protected String french;

            /**
             * Gets the value of the english property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEnglish() {
                return english;
            }

            /**
             * Sets the value of the english property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEnglish(String value) {
                this.english = value;
            }

            /**
             * Gets the value of the french property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFrench() {
                return french;
            }

            /**
             * Sets the value of the french property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFrench(String value) {
                this.french = value;
            }

        }

    }

}
