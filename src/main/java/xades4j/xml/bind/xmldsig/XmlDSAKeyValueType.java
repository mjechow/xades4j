//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2010.04.09 at 09:56:29 PM BST
//


package xades4j.xml.bind.xmldsig;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import xades4j.xml.bind.Base64XmlAdapter;


/**
 * <p>Java class for DSAKeyValueType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="DSAKeyValueType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;sequence minOccurs="0"&gt;
 *           &lt;element name="P" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary"/&gt;
 *           &lt;element name="Q" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary"/&gt;
 *         &lt;/sequence&gt;
 *         &lt;element name="G" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary" minOccurs="0"/&gt;
 *         &lt;element name="Y" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary"/&gt;
 *         &lt;element name="J" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary" minOccurs="0"/&gt;
 *         &lt;sequence minOccurs="0"&gt;
 *           &lt;element name="Seed" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary"/&gt;
 *           &lt;element name="PgenCounter" type="{http://www.w3.org/2000/09/xmldsig#}CryptoBinary"/&gt;
 *         &lt;/sequence&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DSAKeyValueType", propOrder = {
    "p",
    "q",
    "g",
    "y",
    "j",
    "seed",
    "pgenCounter"
})
public class XmlDSAKeyValueType {

    @XmlElement(name = "P", type = String.class)
    @XmlJavaTypeAdapter(Base64XmlAdapter .class)
    protected byte[] p;
    @XmlElement(name = "Q", type = String.class)
    @XmlJavaTypeAdapter(Base64XmlAdapter .class)
    protected byte[] q;
    @XmlElement(name = "G", type = String.class)
    @XmlJavaTypeAdapter(Base64XmlAdapter .class)
    protected byte[] g;
    @XmlElement(name = "Y", required = true, type = String.class)
    @XmlJavaTypeAdapter(Base64XmlAdapter .class)
    protected byte[] y;
    @XmlElement(name = "J", type = String.class)
    @XmlJavaTypeAdapter(Base64XmlAdapter .class)
    protected byte[] j;
    @XmlElement(name = "Seed", type = String.class)
    @XmlJavaTypeAdapter(Base64XmlAdapter .class)
    protected byte[] seed;
    @XmlElement(name = "PgenCounter", type = String.class)
    @XmlJavaTypeAdapter(Base64XmlAdapter .class)
    protected byte[] pgenCounter;

    /**
     * Gets the value of the p property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public byte[] getP() {
        return p;
    }

    /**
     * Sets the value of the p property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setP(byte[] value) {
        this.p = value;
    }

    /**
     * Gets the value of the q property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public byte[] getQ() {
        return q;
    }

    /**
     * Sets the value of the q property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setQ(byte[] value) {
        this.q = value;
    }

    /**
     * Gets the value of the g property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public byte[] getG() {
        return g;
    }

    /**
     * Sets the value of the g property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setG(byte[] value) {
        this.g = value;
    }

    /**
     * Gets the value of the y property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public byte[] getY() {
        return y;
    }

    /**
     * Sets the value of the y property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setY(byte[] value) {
        this.y = value;
    }

    /**
     * Gets the value of the j property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public byte[] getJ() {
        return j;
    }

    /**
     * Sets the value of the j property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setJ(byte[] value) {
        this.j = value;
    }

    /**
     * Gets the value of the seed property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public byte[] getSeed() {
        return seed;
    }

    /**
     * Sets the value of the seed property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSeed(byte[] value) {
        this.seed = value;
    }

    /**
     * Gets the value of the pgenCounter property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public byte[] getPgenCounter() {
        return pgenCounter;
    }

    /**
     * Sets the value of the pgenCounter property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPgenCounter(byte[] value) {
        this.pgenCounter = value;
    }

}
