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

import xades4j.properties.data.PropertyDataObject;
import xades4j.properties.data.SignaturePolicyData;
import xades4j.xml.bind.xades.XmlDigestAlgAndValueType;
import xades4j.xml.bind.xades.XmlSignaturePolicyIdType;
import xades4j.xml.bind.xades.XmlSignaturePolicyIdentifierType;
import xades4j.xml.bind.xades.XmlSignedPropertiesType;
import xades4j.xml.bind.xmldsig.XmlDigestMethodType;

/**
 *
 * @author Luís
 */
class ToXmlSignaturePolicyConverter implements SignedPropertyDataToXmlConverter
{
    @Override
    public void convertIntoObjectTree(
            PropertyDataObject propData,
            XmlSignedPropertiesType xmlProps)
    {
        SignaturePolicyData sigPolicyData = (SignaturePolicyData)propData;
        XmlSignaturePolicyIdentifierType xmlSigPolicy = new XmlSignaturePolicyIdentifierType();

        if (null == sigPolicyData.getIdentifier())
            xmlSigPolicy.setSignaturePolicyImplied();
        else
        {
            XmlSignaturePolicyIdType xmlSigPolicyId = new XmlSignaturePolicyIdType();
            xmlSigPolicyId.setSigPolicyId(ToXmlUtils.getXmlObjectId(sigPolicyData.getIdentifier()));
            xmlSigPolicyId.setSigPolicyHash(getDigest(sigPolicyData));

            xmlSigPolicy.setSignaturePolicyId(xmlSigPolicyId);
        }
        xmlProps.getSignedSignatureProperties().setSignaturePolicyIdentifier(xmlSigPolicy);
    }

    private XmlDigestAlgAndValueType getDigest(SignaturePolicyData sigPolicyData)
    {
        XmlDigestMethodType xmlDigestMethod = new XmlDigestMethodType();
        xmlDigestMethod.setAlgorithm(sigPolicyData.getDigestAlgorithm());

        XmlDigestAlgAndValueType xmlDigest = new XmlDigestAlgAndValueType();
        xmlDigest.setDigestMethod(xmlDigestMethod);
        xmlDigest.setDigestValue(sigPolicyData.getDigestValue());

        return xmlDigest;
    }
}
