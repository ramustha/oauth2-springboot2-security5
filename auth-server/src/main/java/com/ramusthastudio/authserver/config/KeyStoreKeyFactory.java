package com.ramusthastudio.authserver.config;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPublicKeySpec;
import org.springframework.core.io.Resource;

/**
 * Factory for RSA key pairs from a JKS keystore file. User provides a {@link Resource} location of a keystore file and
 * the password to unlock it, and the factory grabs the keypairs from the store by name (and optionally password).
 *
 * @author Dave Syer
 */
public class KeyStoreKeyFactory {
  private final Resource resource;
  private final char[] password;

  private KeyStore store;
  private final Object lock = new Object();

  public KeyStoreKeyFactory(Resource aResource, char[] aPassword) {
    resource = aResource;
    password = aPassword;
  }

  public KeyPair getKeyPair(String aAlias) {
    return getKeyPair(aAlias, password);
  }

  public KeyPair getKeyPair(String aAlias, char[] password) {
    try {
      synchronized (lock) {
        if (store == null) {
          synchronized (lock) {
            store = KeyStore.getInstance("jks");
            store.load(resource.getInputStream(), password);
          }
        }
      }
      RSAPrivateCrtKey key = (RSAPrivateCrtKey) store.getKey(aAlias, password);
      RSAPublicKeySpec spec = new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
      PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);
      return new KeyPair(publicKey, key);
    } catch (Exception e) {
      throw new IllegalStateException("Cannot load keys from store: " + resource, e);
    }
  }
}
