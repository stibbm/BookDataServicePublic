package book.data.service.service.time;

import java.time.Instant;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TimeService {
    public Long getCurrentTimestamp() {
        Long currentTimestamp = Instant.now().toEpochMilli();
        return currentTimestamp;
    }

    public LocalDateTime getCurrentLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        return now;
    }

    public Long getCurrentLocalDateTimeLong() {
        return Instant.now().toEpochMilli();
    }
}
