package com.example.rentalservices;

import com.example.rentalservices.exception.ValidationException;
import com.example.rentalservices.validator.PeselHandler;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class PeselHandlerTests {
    private PeselHandler peselHandler = new PeselHandler("secretkey123456");
    @Test
    void testIsPeselValid_validAdultPesel() {
        //pesel osoby pelnoletniej (ur. 22-02-1990)
        String validPesel = "20011012345";
        assertTrue(peselHandler.isPeselValid(validPesel), "PESEL powinien być poprawny i wskazywać na osobę pełnoletnią.");
    }
    @Test
    void testIsPeselValid_invalidLength() {
        //niepoprawny pesel wzgledem dlugosci znakow
        String invalidPesel = "1234567890";
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> peselHandler.isPeselValid(invalidPesel),
                "Invalid pesel number"
        );

        assertEquals("Invalid pesel number", exception.getMessage(), "Komunikat wyjątku niezgodny z oczekiwanym.");
    }
    @Test
    void testIsPeselValid_invalidCharacters() {
        // pesel z literami alfabetu
        String invalidPesel = "90022A12345";
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> peselHandler.isPeselValid(invalidPesel),
                "Invalid pesel number"
        );

        assertEquals("Invalid pesel number", exception.getMessage(), "Komunikat wyjątku niezgodny z oczekiwanym.");

    }
    @Test
    void testIsPeselValid_underagePesel() {
        // pesel osoby niepełnoletniej (ur. 22-02-2010)
        String underagePesel = "10222212345";
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> peselHandler.isPeselValid(underagePesel),
                "Cannot create account. You are underage"
        );

        assertEquals("Cannot create account. You are underage", exception.getMessage(), "Komunikat wyjątku niezgodny z oczekiwanym.");

    }

}
