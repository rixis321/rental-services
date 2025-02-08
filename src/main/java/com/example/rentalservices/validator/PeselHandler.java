package com.example.rentalservices.validator;

import com.example.rentalservices.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;

@Slf4j
@Component
public class PeselHandler {
    Logger logger = LoggerFactory.getLogger(PeselHandler.class);
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12; // 12 bajtów IV dla AES-GCM
    private static final int GCM_TAG_LENGTH = 128; // 128-bitowy tag dla AES-GCM
    private static String SECRET_KEY;

    public PeselHandler(@Value("${secret_key}") String secretKey) {
        SECRET_KEY = secretKey;
    }


    public boolean isPeselValid(String pesel) {
        if (pesel == null || !pesel.matches("\\d{11}")) {
            throw new ValidationException("Invalid pesel number");
        }

        LocalDate birthDate = extractBirthDateFromPesel(pesel);
        if (birthDate == null) {
            throw new ValidationException("Cannot extract birth date from pesel");
        }

        LocalDate now = LocalDate.now();
        LocalDate adultDate = birthDate.plusYears(18);

        if (now.isBefore(adultDate)) {
            throw new ValidationException("Cannot create account. You are underage");
        }

        return true;
    }

    private LocalDate extractBirthDateFromPesel(String pesel) {
        int year = Integer.parseInt(pesel.substring(0, 2));
        int month = Integer.parseInt(pesel.substring(2, 4));
        int day = Integer.parseInt(pesel.substring(4, 6));

        // Obsługa wieków na podstawie wartości miesiąca
        if (month > 80) { // 1800-1899
            year += 1800;
            month -= 80;
        } else if (month > 60) { // 2200-2299
            year += 2200;
            month -= 60;
        } else if (month > 40) { // 2100-2199
            year += 2100;
            month -= 40;
        } else if (month > 20) { // 2000-2099
            year += 2000;
            month -= 20;
        } else { // 1900-1999
            year += 1900;
        }

        try {
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            return null;
        }
    }

    public String encryptPesel(String value) {
        try {
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
            byte[] iv = generateIV();

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);

            byte[] encryptedBytes = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));


            String ivBase64 = Base64.getEncoder().encodeToString(iv);
            String encryptedBase64 = Base64.getEncoder().encodeToString(encryptedBytes);

            return ivBase64 + "." + encryptedBase64;
        } catch (Exception ex) {
            throw new RuntimeException("Error encrypting value", ex);
        }
    }

    public String decryptPesel(String encryptedValue) {
        try {
            SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");


            String[] parts = encryptedValue.split("\\.");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid encrypted data format");
            }

            byte[] iv = Base64.getDecoder().decode(parts[0]);
            byte[] encryptedData = Base64.getDecoder().decode(parts[1]);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);

            byte[] decryptedBytes = cipher.doFinal(encryptedData);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new RuntimeException("Error decrypting value", ex);
        }
    }

    private byte[] generateIV() {
        byte[] iv = new byte[GCM_IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return iv;
    }
}