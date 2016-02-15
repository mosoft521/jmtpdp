package com.gmail.mosoft521.jmtpdp.ch10tss.example;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class ThreadSpecificSecureRandom {
    //该类的唯一实例
    public static final ThreadSpecificSecureRandom INSTANCE = new ThreadSpecificSecureRandom();

    /*
    SECURE_RANDOM相当于模式角色：ThreadSpecificStorage.TSObjectProxy。
    SecureRandom相当于模式角色：ThreadSpecificStorage.TSObject。
    */
    private static final ThreadLocal<SecureRandom> SECURE_RANDOM = new ThreadLocal<SecureRandom>() {

        @Override
        protected SecureRandom initialValue() {
            SecureRandom srnd;
            try {
                srnd = SecureRandom.getInstance("SHA1PRNG");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                srnd = new SecureRandom();
            }
            return srnd;
        }
    };

    // 私有构造器
    private ThreadSpecificSecureRandom() {

    }

    public int nextInt(int upperBound) {
        SecureRandom secureRnd = SECURE_RANDOM.get();
        return secureRnd.nextInt(upperBound);
    }

    public void setSeed(long seed) {
        SecureRandom secureRnd = SECURE_RANDOM.get();
        secureRnd.setSeed(seed);
    }
}