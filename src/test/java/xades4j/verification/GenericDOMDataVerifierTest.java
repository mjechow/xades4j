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

import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import static org.junit.Assert.*;
import xades4j.properties.QualifyingProperty;
import xades4j.properties.data.GenericDOMData;

/**
 *
 * @author Luís
 */
public class GenericDOMDataVerifierTest
{
    private static Map<QName, Class<?  extends QualifyingPropertyVerifier>> customElemVerifiers;
    private static Document testDocument;
    private static Injector injector;

    public GenericDOMDataVerifierTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
        customElemVerifiers = new HashMap<QName, Class<?  extends QualifyingPropertyVerifier>>(1);
        customElemVerifiers.put(new QName("http://test.generic.dom", "Elem"), TestElemDOMVerifier.class);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        testDocument = dbf.newDocumentBuilder().newDocument();

        injector = Guice.createInjector();
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void testVerify() throws Exception
    {
        GenericDOMData propData = new GenericDOMData(testDocument.createElementNS("http://test.generic.dom", "Elem"));
        QualifyingPropertyVerificationContext ctx = null;
        GenericDOMDataVerifier instance = new GenericDOMDataVerifier(injector, customElemVerifiers);

        QualifyingProperty result = instance.verify(propData, ctx);
        assertEquals(result.getName(), "Elem");
    }

    @Test(expected = InvalidPropertyException.class)
    public void testVerifyNoVerifier() throws Exception
    {
        GenericDOMData propData = new GenericDOMData(testDocument.createElementNS("http://test.generic.dom", "Elem"));
        QualifyingPropertyVerificationContext ctx = null;
        GenericDOMDataVerifier instance = new GenericDOMDataVerifier(injector, new HashMap<QName, Class<? extends QualifyingPropertyVerifier>>(0));

        instance.verify(propData, ctx);
    }
}

class TestElemDOMVerifier implements QualifyingPropertyVerifier<GenericDOMData>
{
    @Override
    public QualifyingProperty verify(
            GenericDOMData propData,
            QualifyingPropertyVerificationContext ctx) throws InvalidPropertyException
    {
        return new QualifyingProperty()
        {
            @Override
            public boolean isSigned()
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public boolean isSignature()
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getName()
            {
                return "Elem";
            }
        };
    }
}
