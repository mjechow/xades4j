//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2010.04.09 at 09:56:29 PM BST
//


package xades4j.xml.bind.xades;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CRLRefType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CRLRefType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DigestAlgAndValue" type="{http://uri.etsi.org/01903/v1.3.2#}DigestAlgAndValueType"/&gt;
 *         &lt;element name="CRLIdentifier" type="{http://uri.etsi.org/01903/v1.3.2#}CRLIdentifierType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CRLRefType", propOrder = {
    "digestAlgAndValue",
    "crlIdentifier"
})
public class XmlCRLRefType {

    @XmlElement(name = "DigestAlgAndValue", required = true)
    protected XmlDigestAlgAndValueType digestAlgAndValue;
    @XmlElement(name = "CRLIdentifier")
    protected XmlCRLIdentifierType crlIdentifier;

    /**
     * Gets the value of the digestAlgAndValue property.
     *
     * @return
     *     possible object is
     *     {@link XmlDigestAlgAndValueType }
     *
     */
    public XmlDigestAlgAndValueType getDigestAlgAndValue() {
        return digestAlgAndValue;
    }

    /**
     * Sets the value of the digestAlgAndValue property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlDigestAlgAndValueType }
     *
     */
    public void setDigestAlgAndValue(XmlDigestAlgAndValueType value) {
        this.digestAlgAndValue = value;
    }

    /**
     * Gets the value of the crlIdentifier property.
     *
     * @return
     *     possible object is
     *     {@link XmlCRLIdentifierType }
     *
     */
    public XmlCRLIdentifierType getCRLIdentifier() {
        return crlIdentifier;
    }

    /**
     * Sets the value of the crlIdentifier property.
     *
     * @param value
     *     allowed object is
     *     {@link XmlCRLIdentifierType }
     *
     */
    public void setCRLIdentifier(XmlCRLIdentifierType value) {
        this.crlIdentifier = value;
    }

}
