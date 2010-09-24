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
package xades4j.verification;

import java.math.BigInteger;

/**
 * Thrown during verification of the {@code SigningCertificate} property if the
 * issuer/serial on {@code KeyInfo} is different from the one in the signing certificate
 * reference.
 * @author Luís
 */
public class SigningCertificateIssuerSerialMismatchException extends SigningCertificateVerificationException
{
    private final String signingCertIssuerName, keyInfoIssuerName;
    private final BigInteger signingCertSerialNumber, keyInfoSerialNumber;

    public SigningCertificateIssuerSerialMismatchException(
            String signingCertIssuerName,
            BigInteger signingCertSerialNumber,
            String keyInfoIssuerName,
            BigInteger keyInfoSerialNumber)
    {
        this.signingCertIssuerName = signingCertIssuerName;
        this.keyInfoIssuerName = keyInfoIssuerName;
        this.signingCertSerialNumber = signingCertSerialNumber;
        this.keyInfoSerialNumber = keyInfoSerialNumber;
    }

    public String getKeyInfoIssuerName()
    {
        return keyInfoIssuerName;
    }

    public BigInteger getKeyInfoSerialNumber()
    {
        return keyInfoSerialNumber;
    }

    public String getSigningCertIssuerName()
    {
        return signingCertIssuerName;
    }

    public BigInteger getSigningCertSerialNumber()
    {
        return signingCertSerialNumber;
    }

    @Override
    protected String getVerificationMessage()
    {
        throw new UnsupportedOperationException("The issuer name and/or serial number on the SigningCertificate property and the KeyInfo element don't match");
    }
}
