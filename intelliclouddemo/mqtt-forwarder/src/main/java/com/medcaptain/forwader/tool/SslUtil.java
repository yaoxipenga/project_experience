package com.medcaptain.forwader.tool;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.X509Certificate;

public class SslUtil {
    static public SSLSocketFactory getSocketFactory (final String caCrtFile, final String crtFile, final String keyFile, final String password) throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());

        // load CA certificate
        ClassPathResource classPathResource = new ClassPathResource(caCrtFile);

        PEMParser parser = new PEMParser(new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(caCrtFile)))));
        //PEMParser parser = new PEMParser(new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(caCrtFile)))));
//        X509Certificate caCert = (X509Certificate)parser.readObject();
        X509Certificate caCert = new JcaX509CertificateConverter().getCertificate((X509CertificateHolder)parser.readObject());
        parser.close();

        // load client certificate
        parser = new PEMParser(new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(crtFile)))));
//        X509Certificate cert = (X509Certificate)parser.readObject();
        X509Certificate cert = new JcaX509CertificateConverter().getCertificate((X509CertificateHolder)parser.readObject());
        parser.close();

        // load client private key
        parser = new PEMParser(new InputStreamReader(new ByteArrayInputStream(Files.readAllBytes(Paths.get(keyFile))))
//                ,
//                new PasswordFinder() {
//                    @Override
//                    public char[] getPassword() {
//                        return password.toCharArray();
//                    }
//                }
        );
        KeyPair key = new JcaPEMKeyConverter().getKeyPair((PEMKeyPair) parser.readObject());
        parser.close();

        // CA certificate is used to authenticate server
        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
        caKs.load(null, null);
        caKs.setCertificateEntry("ca-certificate", caCert);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(caKs);

        // client key and certificates are sent to server so it can authenticate us
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);
        ks.setCertificateEntry("certificate", cert);
        ks.setKeyEntry("private-key", key.getPrivate(), password.toCharArray(), new java.security.cert.Certificate[]{cert});
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password.toCharArray());

        // finally, create SSL socket factory
        SSLContext context = SSLContext.getInstance("TLSv1");
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return context.getSocketFactory();
    }
}
