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

import java.security.KeyStoreException;
import org.w3c.dom.Document;
import xades4j.providers.impl.DirectPasswordProvider;
import xades4j.providers.impl.FileSystemKeyStoreKeyingDataProvider;
import xades4j.providers.impl.FirstCertificateSelector;
import xades4j.providers.KeyingDataProvider;
import xades4j.utils.SignatureServicesTestBase;

/**
 *
 * @author Luís
 */
public class SignerTestBase extends SignatureServicesTestBase
{
    /**/
    static protected KeyingDataProvider keyingProviderMy;
    static protected KeyingDataProvider keyingProviderNist;

    static
    {
        try
        {
            keyingProviderMy = createFileSystemKeyingDataProvider("pkcs12", "my/LG.pfx", "mykeypass", true);
            keyingProviderNist = createFileSystemKeyingDataProvider("pkcs12", "csrc.nist/test4.p12", "password", false);
        } catch (KeyStoreException e)
        {
            throw new NullPointerException("SignerTestBase init failed: " + e.getMessage());
        }
    }

    static Document getTestDocument() throws Exception
    {
        return getDocument("document.xml");
    }

    protected static FileSystemKeyStoreKeyingDataProvider createFileSystemKeyingDataProvider(
            String keyStoreType,
            String keyStorePath,
            String keyStorePwd,
            boolean returnFullChain) throws KeyStoreException
    {
        keyStorePath = toPlatformSpecificCertDirFilePath(keyStorePath);
        return new FileSystemKeyStoreKeyingDataProvider(keyStoreType, keyStorePath,
                new FirstCertificateSelector(),
                new DirectPasswordProvider(keyStorePwd),
                new DirectPasswordProvider(keyStorePwd), returnFullChain);
    }
}
