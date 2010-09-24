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
package xades4j.production;

import com.google.inject.Inject;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import xades4j.properties.SignaturePolicyBase;
import xades4j.properties.SignedSignatureProperty;
import xades4j.properties.UnsignedSignatureProperty;
import xades4j.XAdES4jException;
import xades4j.providers.AlgorithmsProvider;
import xades4j.providers.DataObjectPropertiesProvider;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.SignaturePolicyInfoProvider;
import xades4j.providers.SignaturePropertiesProvider;
import xades4j.utils.PropertiesUtils;
import xades4j.xml.marshalling.SignedPropertiesMarshaller;
import xades4j.xml.marshalling.UnsignedPropertiesMarshaller;

/**
 * Produces XAdES-T signatures. Doesn't extend SignerEPES because XAdES-T may be
 * based on XAdES-BES. If T+EPES is needed, a {@code SignaturePolicyInfoProvider}
 * will be injected after construction via {@code setPolicyProvider}.
 * @author Luís
 */
class SignerT extends SignerBES
{
    private SignaturePolicyInfoProvider policyInfoProvider;

    @Inject
    protected SignerT(
            KeyingDataProvider keyingProvider,
            SignaturePropertiesProvider signaturePropsProvider,
            DataObjectPropertiesProvider dataObjPropsProvider,
            PropertiesDataObjectsGenerator propsDataObjectsGenerator,
            AlgorithmsProvider algorithmsProvider,
            SignedPropertiesMarshaller signedPropsMarshaller,
            UnsignedPropertiesMarshaller unsignedPropsMarshaller)
    {
        super(keyingProvider, signaturePropsProvider, dataObjPropsProvider, propsDataObjectsGenerator, algorithmsProvider, signedPropsMarshaller, unsignedPropsMarshaller);
    }

    @Inject(optional = true)
    void setPolicyProvider(SignaturePolicyInfoProvider p)
    {
        this.policyInfoProvider = p;
    }

    @Override
    protected void getFormatSpecificSignatureProperties(
            Collection<SignedSignatureProperty> formatSpecificSignedSigProps,
            Collection<UnsignedSignatureProperty> formatSpecificUnsignedSigProps,
            List<X509Certificate> signingCertificateChain) throws XAdES4jException
    {
        super.getFormatSpecificSignatureProperties(
                formatSpecificSignedSigProps, formatSpecificUnsignedSigProps, signingCertificateChain);

        // Check if this is based on XAdES-EPES.
        if (this.policyInfoProvider != null)
        {
            PropertiesUtils.addXadesEpesProperties(formatSpecificSignedSigProps, this.policyInfoProvider);
        }
        // Add XAdES-T.
        PropertiesUtils.addXadesTProperties(formatSpecificUnsignedSigProps);
    }
}
