package study.infowifi.bookMark;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookMark {
    private Long id;
    private String bookMarkName;
    private Long turn;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = null;
}
