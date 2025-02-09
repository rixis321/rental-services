package com.example.rentalservices.security.captcha;

public interface RecaptchaService {

    boolean verify(String token);
}
