package com.itavery.forecast.external;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.Signature;

/**
 * File created by Avery Grimes-Farrow
 * Created on: 2018-12-17
 * https://github.com/helloavery
 */

public abstract class S3GatewayBase {

    protected void setup(){
        providerInit();
    }

    public abstract void providerInit();

    public abstract Cipher cipherGetInstance(String encryptionAlgorithm);

    public abstract KeyPair generateKeyPair(String algorithm, int keysize);

    public abstract Signature initSignature(String algorithm);

}
