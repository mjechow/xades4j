package xades4j.production;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERBMPString;
import org.bouncycastle.asn1.DERUTF8String;
import org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.RFC4519Style;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import xades4j.algorithms.EnvelopedSignatureTransform;
import xades4j.properties.DataObjectDesc;
import xades4j.providers.impl.DirectKeyingDataProvider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author Artem R. Romanenko
 * @version 02/04/2018
 */
public class SignerSpecificTest extends SignerTestBase
{
    private static final String NATIONAL_DN_CYRILLIC = "National name '\u043F\u0440\u0438\u043C\u0435\u0440'";
    private static final String NATIONAL_DN_ARABIC = "National name '\u0645\u062B\u0627\u0644'";

    public static List<ASN1Encodable> data()
    {
        return List.of(
                new DERBMPString(NATIONAL_DN_CYRILLIC),
                new DERUTF8String(NATIONAL_DN_CYRILLIC),
                new DERBMPString(NATIONAL_DN_ARABIC),
                new DERUTF8String(NATIONAL_DN_ARABIC)
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    void signWithNationalCertificate(ASN1Encodable commonName) throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
        keyGen.initialize(1024, new SecureRandom());
        Date validityBeginDate = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        long add = (365L * 24L * 60L * 60L * 1000L);  //1 year
        Date validityEndDate = new Date(System.currentTimeMillis() + add);
        KeyPair keyPair = keyGen.generateKeyPair();


        X509Certificate certWithNationalSymbols;
        {
            //generate certificate with national symbols in DN
            X500NameBuilder x500NameBuilder = new X500NameBuilder();
            AttributeTypeAndValue attr = new AttributeTypeAndValue(RFC4519Style.cn, commonName);
            x500NameBuilder.addRDN(attr);
            X500Name dn = x500NameBuilder.build();
            X509v3CertificateBuilder builder = new JcaX509v3CertificateBuilder(
                    dn, // issuer authority
                    BigInteger.valueOf(new Random().nextInt()), //serial number of certificate
                    validityBeginDate, // start of validity
                    validityEndDate, //end of certificate validity
                    dn, // subject name of certificate
                    keyPair.getPublic()); // public key of certificate
            // key usage restrictions
            builder.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.keyCertSign
                    | KeyUsage.digitalSignature | KeyUsage.keyEncipherment
                    | KeyUsage.dataEncipherment | KeyUsage.cRLSign));
            builder.addExtension(Extension.basicConstraints, false, new BasicConstraints(true));
            certWithNationalSymbols = new JcaX509CertificateConverter().getCertificate(builder
                    .build(new JcaContentSignerBuilder("SHA256withRSA").setProvider(BouncyCastleProvider.PROVIDER_NAME).
                            build(keyPair.getPrivate())));
        }


        XadesSigner signer = new XadesBesSigningProfile(new DirectKeyingDataProvider(certWithNationalSymbols, keyPair.getPrivate())).newSigner();
        Document doc1 = getTestDocument();
        Element elemToSign = doc1.getDocumentElement();
        DataObjectDesc obj1 = new DataObjectReference('#' + elemToSign.getAttribute("Id")).withTransform(new EnvelopedSignatureTransform());
        SignedDataObjects signDataObject = new SignedDataObjects(obj1);
        signer.sign(signDataObject, doc1.getDocumentElement());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        outputDOM(doc1, baos);
        String str = baos.toString();
        //expected without parsing exception
        Document doc = parseDocument(new ByteArrayInputStream(baos.toByteArray()));
    }
}
