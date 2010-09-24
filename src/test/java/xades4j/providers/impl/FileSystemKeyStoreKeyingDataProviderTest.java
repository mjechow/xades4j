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
package xades4j.providers.impl;

import xades4j.providers.impl.FileSystemKeyStoreKeyingDataProvider;
import java.io.FileInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Luís
 */
public class FileSystemKeyStoreKeyingDataProviderTest
{
    FileSystemKeyStoreKeyingDataProvider keyingProvider;
    X509Certificate signCert;

    @Before
    public void setUp() throws Exception
    {
        keyingProvider = new FileSystemKeyStoreKeyingDataProvider(
                "pkcs12",
                ".\\src\\test\\cert\\my\\LG.pfx",
                new FirstCertificateSelector(),
                new DirectPasswordProvider("mykeypass"),
                new DirectPasswordProvider("mykeypass"), true);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        signCert = (X509Certificate)cf.generateCertificate(new FileInputStream(".\\src\\test\\cert\\my\\LG.cer"));
    }

    @Test
    public void testGetSigningKey() throws Exception
    {
        keyingProvider.getSigningKey(signCert);
    }

    @Test
    public void testGetSigningCertificateChain() throws Exception
    {
        List<X509Certificate> certChain = keyingProvider.getSigningCertificateChain();
        assertEquals(certChain.size(), 3);
        assertEquals(certChain.get(0), signCert);
    }
}
