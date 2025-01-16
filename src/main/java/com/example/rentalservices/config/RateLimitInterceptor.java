package com.example.rentalservices.config;

import com.example.rentalservices.model.EventLog;
import com.example.rentalservices.model.enums.EventType;
import com.example.rentalservices.repository.EventLogRepository;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {
    private final ConcurrentHashMap<String, Bucket> bucketCache = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, LocalDateTime> logTimestamps = new ConcurrentHashMap<>();
    private final EventLogRepository eventLogRepository;

    public RateLimitInterceptor(EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    // 15 requests per minute
    private Bucket createBucket() {
        Bandwidth limit = Bandwidth.classic(15, Refill.intervally(15, Duration.ofMinutes(1)));
        return Bucket4j.builder().addLimit(limit).build();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String clientIp = request.getRemoteAddr(); // Identyfikacja klienta po adresie IP
        Bucket bucket = bucketCache.computeIfAbsent(clientIp, k -> createBucket());

        if (!bucket.tryConsume(1)) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many requests - try again later");

            // saving one log per 10 minut for one IP address
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime lastLogTime = logTimestamps.getOrDefault(clientIp, LocalDateTime.MIN);

            if (Duration.between(lastLogTime, now).toMinutes() >= 10) {
                logTimestamps.put(clientIp, now);

                EventLog eventLog = new EventLog();
                eventLog.setUuid(UUID.randomUUID());
                eventLog.setEventType(EventType.TOO_MANY_REQUESTS);
                eventLog.setMessage("Too many requests from " + clientIp);
                eventLog.setTimestamp(now);
                eventLogRepository.save(eventLog);
            }
            return false;
        }
        return true;
    }
}

