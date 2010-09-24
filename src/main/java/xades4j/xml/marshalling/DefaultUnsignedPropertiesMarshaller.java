/*
 * XAdES4j - A Java library for generation and verification of XAdES signatures.
 * Copyright (C) 2010 Luis Goncalves.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package xades4j.xml.marshalling;

import xades4j.properties.data.SigAndDataObjsPropertiesData;
import org.w3c.dom.Node;
import xades4j.properties.QualifyingProperty;
import xades4j.properties.data.ArchiveTimeStampData;
import xades4j.properties.data.CertificateValuesData;
import xades4j.properties.data.CompleteCertificateRefsData;
import xades4j.properties.data.CompleteRevocationRefsData;
import xades4j.properties.data.RevocationValuesData;
import xades4j.properties.data.SigAndRefsTimeStampData;
import xades4j.properties.data.SignatureTimeStampData;
import xades4j.xml.bind.xades.ObjectFactory;
import xades4j.xml.bind.xades.XmlUnsignedDataObjectPropertiesType;
import xades4j.xml.bind.xades.XmlUnsignedPropertiesType;
import xades4j.xml.bind.xades.XmlUnsignedSignaturePropertiesType;

/**
 * Default implementation of {@link UnsignedPropertiesMarshaller}. Based on JAXB.
 * <p>
 * Supports all the property data obejcts corresponding to XAdES 1.4.1 up to XAdES-C
 * (except attribute validation data refs) plus {@code GenericDOMData}.
 * @author Luís
 */
public class DefaultUnsignedPropertiesMarshaller
        implements UnsignedPropertiesMarshaller
{
    // So that the BaseJAXBMarshaller class is not exposed through public API.
    private final InternalUnsignedPropertiesMarshaller internalMarshaller;

    public DefaultUnsignedPropertiesMarshaller()
    {
        internalMarshaller = new InternalUnsignedPropertiesMarshaller();
    }

    @Override
    public void marshal(SigAndDataObjsPropertiesData props, String propsId,
            Node qualifyingPropsNode) throws MarshalException
    {
        XmlUnsignedPropertiesType xmlUnsignedProps = new XmlUnsignedPropertiesType();
        internalMarshaller.doMarshal(props, qualifyingPropsNode, xmlUnsignedProps);
    }
}

/**
 * So that the BaseJAXBMarshaller class is not exposed through public API.
 * @author Luís
 */
class InternalUnsignedPropertiesMarshaller
        extends BaseJAXBMarshaller<XmlUnsignedPropertiesType>
{
    InternalUnsignedPropertiesMarshaller()
    {
        super(7, QualifyingProperty.UNSIGNED_PROPS_TAG);
        // Unsigned signature properties
        super.putConverter(SignatureTimeStampData.class, new ToXmlSignatureTimeStampConverter());
        super.putConverter(CompleteCertificateRefsData.class, new ToXmlCompleteCertRefsConverter());
        super.putConverter(CompleteRevocationRefsData.class, new ToXmlCompleteRevocRefsConverter());
        super.putConverter(SigAndRefsTimeStampData.class, new ToXmlSigAndRefsTimeStampConverter());
        super.putConverter(CertificateValuesData.class, new ToXmlCertificateValuesConverter());
        super.putConverter(RevocationValuesData.class, new ToXmlRevocationValuesConverter());
        super.putConverter(ArchiveTimeStampData.class, new ToXmlArchiveTimeStampConverter());
        /* The CounterSignature property is marshalled directly using DOM because
         * it is easier that way (it is represented by a GenericDOMData instance).
         */

        // Unsigned data object properties
         /* No unsigned data object properties are defined in the current version
         * of XAdES.
         */
    }

    @Override
    protected void prepareSigProps(XmlUnsignedPropertiesType xmlProps)
    {
        // Create UnsignedSignatureProperties and add it to UnsignedProperties
        XmlUnsignedSignaturePropertiesType xmlUnsignedSignatureProps = new XmlUnsignedSignaturePropertiesType();
        xmlProps.setUnsignedSignatureProperties(xmlUnsignedSignatureProps);
    }

    @Override
    protected void prepareDataObjsProps(XmlUnsignedPropertiesType xmlProps)
    {
        // Create UnsignedDataObjectProperties and add it to UnsignedProperties
        XmlUnsignedDataObjectPropertiesType xmlUnsignedDataObjProps = new XmlUnsignedDataObjectPropertiesType();
        xmlProps.setUnsignedDataObjectProperties(xmlUnsignedDataObjProps);
    }

    @Override
    protected Object createPropsXmlElem(
            ObjectFactory objFact,
            XmlUnsignedPropertiesType xmlProps)
    {
        return objFact.createUnsignedProperties(xmlProps);
    }
}
