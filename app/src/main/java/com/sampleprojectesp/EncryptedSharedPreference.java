package com.sampleprojectesp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;
import java.security.GeneralSecurityException;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

/**
 * Manages sensitive data by encrypting and storing the values in shared preferences.
 * The key materials will be stored in TEE if available. Otherwise keystore will handle the key materials.
 */
public class EncryptedSharedPreference {

    private static final String TAG = "EncryptedSharedPreference";
    static final String sharedPreferenceFileName = "encrypted_udc_pref";
    private static volatile SharedPreferences encryptedSharedPrference;

    private EncryptedSharedPreference() throws IllegalAccessException {
        throw new IllegalAccessException("Can not access private constructor");
    }

    public static SharedPreferences getEncryptedSharedPreference(Context context) {
        if (encryptedSharedPrference == null) {
            synchronized (EncryptedSharedPreference.class) {
                if (encryptedSharedPrference == null) {
                    try {
                        String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
                        encryptedSharedPrference = EncryptedSharedPreferences.create(sharedPreferenceFileName, masterKeyAlias, context,
                                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
                    } catch (GeneralSecurityException | IOException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        }
        return encryptedSharedPrference;
    }


    public static String getString(Context context, String key) {
        return EncryptedSharedPreference.getEncryptedSharedPreference(context).getString(key, "");
    }

    public static boolean putString(Context context, String key, String value) {
        return EncryptedSharedPreference.getEncryptedSharedPreference(context).edit().putString(key, value).commit();
    }
}
