package com.spaikergrn.vk_client.imageloader.Utils;

import android.util.Log;

import com.spaikergrn.vk_client.serviceclasses.Constants;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileNameGenerator {

    private static final int RADIX = 10 + 26; // 10 digits + 26 letters

     public String generate(final String pImageUri) {
        final byte[] md5 = getMD5(pImageUri.getBytes());
        final BigInteger bi = new BigInteger(md5).abs();
        return bi.toString(RADIX);
    }

    private byte[] getMD5(final byte[] pData) {
        byte[] hash = null;
        try {
            final MessageDigest digest = MessageDigest.getInstance(Constants.ImgLoader.HASH_ALGORITHM);
            digest.update(pData);
            hash = digest.digest();
        } catch (final NoSuchAlgorithmException pE) {
            Log.d(Constants.MY_TAG, pE.getMessage());
        }
        return hash;
    }
}
