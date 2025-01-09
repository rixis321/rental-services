package com.example.rentalservices;

import com.example.rentalservices.validator.PeselHandler;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class PeselHandlerTests {
    private PeselHandler peselHandler = new PeselHandler("secretkey123456");
    @Test
    void testIsPeselValid_validAdultPesel() {
        //pesel osoby pelnoletniej (ur. 22-02-1990)
        String validPesel = "90022212345";
        assertTrue(peselHandler.isPeselValid(validPesel), "PESEL powinien być poprawny i wskazywać na osobę pełnoletnią.");
    }
    @Test
    void testIsPeselValid_invalidLength() {
        //niepoprawny pesel wzgledem dlugosci znakow
        String invalidPesel = "1234567890";
        assertFalse(peselHandler.isPeselValid(invalidPesel), "PESEL o niepełnej długości powinien być niepoprawny.");
    }
    @Test
    void testIsPeselValid_invalidCharacters() {
        // pesel z literami alfabetu
        String invalidPesel = "90022A12345";
        assertFalse(peselHandler.isPeselValid(invalidPesel), "PESEL zawierający litery powinien być niepoprawny.");
    }
    @Test
    void testIsPeselValid_underagePesel() {
        // pesel osoby niepełnoletniej (ur. 22-02-2010)
        String underagePesel = "10222212345";
        assertFalse(peselHandler.isPeselValid(underagePesel), "PESEL osoby niepełnoletniej powinien być niepoprawny.");
    }

}
