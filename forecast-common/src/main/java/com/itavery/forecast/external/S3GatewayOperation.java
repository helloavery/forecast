package com.itavery.forecast.external;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.Signature;

/**
 * File created by Avery Grimes-Farrow
 * Created on: 2018-12-18
 * https://github.com/helloavery
 */

@Service
public class S3GatewayOperation extends S3GatewayBase{

    @Override
    public void providerInit() {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public Cipher cipherGetInstance(String encryptionAlgorithm){
        try{
            return Cipher.getInstance(encryptionAlgorithm);
        }
        catch(Exception e){
            throw new RuntimeException();
        }
    }

    @Override
    public KeyPair generateKeyPair(String algorithm, int keysize){
        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            keyPairGenerator.initialize(keysize);
            return keyPairGenerator.generateKeyPair();
        }
        catch(Exception e){
            throw new RuntimeException();
        }
    }

    @Override
    public Signature initSignature(String algorithm){
        try{
            return Signature.getInstance(algorithm);
        }
        catch(Exception e){
            throw new RuntimeException();
        }
    }
}
