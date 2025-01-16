package com.example.rentalservices.validator;
import com.example.rentalservices.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDate;
import java.util.Base64;

@Component
public class PeselHandler {

    private static final String AES_ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private static String  SECRET_KEY;
    public PeselHandler(@Value("${secret_key}") String secretKey) {
        SECRET_KEY = secretKey;
    }

    // Sprawdza poprawność numeru PESEL
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

        if(now.isBefore(adultDate)){
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

    // Szyfrowanie numeru PESEL
    public String encryptPesel(String pesel) throws Exception {
        // Dekodowanie klucza Base64
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);

        // Sprawdzenie długości klucza
        if (decodedKey.length != 16 && decodedKey.length != 24 && decodedKey.length != 32) {
            throw new IllegalArgumentException("Invalid AES key length: " + decodedKey.length + " bytes");
        }

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKey key = new SecretKeySpec(decodedKey, AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(pesel.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Odszyfrowanie numeru PESEL
    public String decryptPesel(String encryptedPesel) throws Exception {
        // Dekodowanie klucza z Base64
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);

        // checking length od key
        if (decodedKey.length != 16 && decodedKey.length != 24 && decodedKey.length != 32) {
            throw new IllegalArgumentException("Invalid AES key length: " + decodedKey.length);
        }

        // AES inicialization
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKey key = new SecretKeySpec(decodedKey, AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

        // decrypting data
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPesel));
        return new String(decryptedBytes);
    }

}
