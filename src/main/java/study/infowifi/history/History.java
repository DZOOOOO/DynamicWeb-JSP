package study.infowifi.history;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class History {
    private Long id;
    private String X;
    private String Y;
    private LocalDateTime createdAt = LocalDateTime.now();
}
