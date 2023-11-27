/*
 * XAdES4j - A Java library for generation and verification of XAdES signatures.
 * Copyright (C) 2010 Luis Goncalves.
 *
 * XAdES4j is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or any later version.
 *
 * XAdES4j is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with XAdES4j. If not, see <http://www.gnu.org/licenses/>.
 */
package xades4j.production;

import java.security.MessageDigest;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import xades4j.properties.QualifyingProperty;
import xades4j.UnsupportedAlgorithmException;
import xades4j.properties.data.BaseCertRefsData;
import xades4j.properties.data.CertRef;
import xades4j.properties.data.PropertyDataObject;
import xades4j.providers.MessageDigestEngineProvider;
import xades4j.providers.X500NameStyleProvider;


/**
 *
 * @author Luís
 */
class DataGenBaseCertRefs
{
    private final SignatureAlgorithms signatureAlgorithms;
    private final MessageDigestEngineProvider messageDigestProvider;
    private final X500NameStyleProvider x500NameStyleProvider;

    protected DataGenBaseCertRefs(
            SignatureAlgorithms signatureAlgorithms,
            MessageDigestEngineProvider messageDigestProvider,
            X500NameStyleProvider x500NameStyleProvider)
    {
        this.signatureAlgorithms = signatureAlgorithms;
        this.messageDigestProvider = messageDigestProvider;
        this.x500NameStyleProvider = x500NameStyleProvider;
    }

    protected PropertyDataObject generate(
            Collection<X509Certificate> certs,
            BaseCertRefsData certRefsData,
            QualifyingProperty prop) throws PropertyDataGenerationException
    {
        if (null == certs)
        {
            throw new PropertyDataGenerationException(prop, "certificates not provided");
        }

        try
        {
            String digestAlgUri = this.signatureAlgorithms.getDigestAlgorithmForReferenceProperties();
            MessageDigest messageDigest = this.messageDigestProvider.getEngine(digestAlgUri);

            for (X509Certificate cert : certs)
            {
                // "DigestValue contains the base-64 encoded value of the digest
                // computed on the DER-encoded certificate."
                // The base-64 encoding is done by JAXB with the configured
                // adapter (Base64XmlAdapter).
                // For X509 certificates the encoded form return by getEncoded is DER.
                byte[] digestValue = messageDigest.digest(cert.getEncoded());

                certRefsData.addCertRef(new CertRef(
                        this.x500NameStyleProvider.toString(cert.getIssuerX500Principal()),
                        cert.getSerialNumber(),
                        digestAlgUri,
                        digestValue));
            }
            return certRefsData;

        } catch (UnsupportedAlgorithmException ex)
        {
            throw new PropertyDataGenerationException(prop, ex.getMessage(), ex);
        } catch (CertificateEncodingException ex)
        {
            throw new PropertyDataGenerationException(prop, "cannot get encoded certificate", ex);
        }
    }
}
