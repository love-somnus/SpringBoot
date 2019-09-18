package com.somnus.https;

import java.security.KeyStore;

public class KeyStoreMaterial {

    /**
     * 密码
     */
    private String password;

    /**
     * keyStore
     */
    private KeyStore keyStore;

    public KeyStoreMaterial() {
    }

    public KeyStoreMaterial(String password, KeyStore keyStore) {
        this.password = password;
        this.keyStore = keyStore;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public KeyStore getKeyStore() {
        return keyStore;
    }

    public void setKeyStore(KeyStore keyStore) {
        this.keyStore = keyStore;
    }

}
