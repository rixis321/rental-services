package com.example.rentalservices.security.captcha;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Service
public class RecaptchaServiceImpl implements RecaptchaService {
    @Value("${secret_captcha}")
    private String secretKey;

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean verify(String token) {
        String url = RECAPTCHA_VERIFY_URL + "?secret=" + secretKey + "&response=" + token;

        ResponseEntity<Map> response = restTemplate.postForEntity(url, null, Map.class);

        if (response.getBody() == null) {
            return false;
        }

        return (Boolean) response.getBody().get("success");
    }
}
