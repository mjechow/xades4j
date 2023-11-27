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
package xades4j.verification;

import jakarta.inject.Inject;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Iterator;
import javax.security.auth.x500.X500Principal;
import xades4j.properties.QualifyingProperty;
import xades4j.properties.SigningCertificateProperty;
import xades4j.properties.data.CertRef;
import xades4j.properties.data.SigningCertificateData;
import xades4j.providers.MessageDigestEngineProvider;
import xades4j.verification.QualifyingPropertyVerificationContext.CertificationChainData;

/**
 * XAdES section G.2.2.5
 * @author Luís
 */
class SigningCertificateVerifier implements QualifyingPropertyVerifier<SigningCertificateData>
{
    private final MessageDigestEngineProvider messageDigestProvider;
    private final DistinguishedNameComparer dnComparer;

    @Inject
    public SigningCertificateVerifier(
            MessageDigestEngineProvider messageDigestProvider,
            DistinguishedNameComparer dnComparer)
    {
        this.messageDigestProvider = messageDigestProvider;
        this.dnComparer = dnComparer;
    }

    @Override
    public QualifyingProperty verify(
            SigningCertificateData propData,
            QualifyingPropertyVerificationContext ctx) throws SigningCertificateVerificationException
    {
        Collection<CertRef> certRefs = propData.getCertRefs();
        CertificationChainData certChainData = ctx.getCertChainData();

        Iterator<X509Certificate> certPathIter = certChainData.getCertificateChain().iterator();

        /* Check the signing certificate */

        // "If the verifier does not find any reference matching the signing certificate,
        // the validation of this property should be taken as failed."
        X509Certificate signingCert = certPathIter.next();
        CertRef signingCertRef = CertRefUtils.findCertRef(signingCert, certRefs, this.dnComparer);
        if (null == signingCertRef)
            throw new SigningCertificateReferenceNotFoundException(signingCert);

        // "If the ds:KeyInfo contains the ds:X509IssuerSerial element, check that
        // the issuer and the serial number indicated in both, that one and IssuerSerial
        // from SigningCertificate, are the same."
        X500Principal keyInfoIssuer = certChainData.getValidationCertIssuer();
        if (keyInfoIssuer != null &&
                (!this.dnComparer.areEqual(keyInfoIssuer, signingCertRef.getIssuerDN()) ||
                !signingCertRef.getSerialNumber().equals(certChainData.getValidationCertSerialNumber())))
            throw new SigningCertificateIssuerSerialMismatchException(
                    signingCertRef.getIssuerDN(),
                    signingCertRef.getSerialNumber(),
                    keyInfoIssuer.getName(),
                    certChainData.getValidationCertSerialNumber());

        try
        {
            CertRefUtils.checkCertRef(signingCertRef, signingCert, messageDigestProvider);
        } catch (CertRefUtils.InvalidCertRefException ex)
        {
            throw new SigningCertificateReferenceException(signingCert, signingCertRef, ex);
        }

        /* Check the other certificates in the certification path */

        int nMatchedRefs = 1;

        while (certPathIter.hasNext())
        {
            X509Certificate cert = certPathIter.next();
            CertRef certRef = CertRefUtils.findCertRef(cert, certRefs, this.dnComparer);
            // "Should one or more certificates in the certification path not be
            // referenced by this property, the verifier should assume that the
            // verification is successful (...)"
            if (null == certRef)
                continue;
            nMatchedRefs++;
            try
            {
                CertRefUtils.checkCertRef(certRef, cert, messageDigestProvider);
            } catch (CertRefUtils.InvalidCertRefException ex)
            {
                throw new SigningCertificateReferenceException(cert, certRef, ex);
            }
        }

        // "Should this property contain one or more references to certificates
        // other than those present in the certification path, the verifier should
        // assume that a failure has occurred during the verification."
        if (nMatchedRefs < certRefs.size())
            throw new SigningCertificateCertsNotInCertPathException();

        return new SigningCertificateProperty(certChainData.getCertificateChain());
    }
}
