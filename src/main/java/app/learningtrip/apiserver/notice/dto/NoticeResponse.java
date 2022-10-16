package app.learningtrip.apiserver.notice.dto;

import app.learningtrip.apiserver.notice.domain.Notice;
import java.text.SimpleDateFormat;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeResponse {

    private Long id;

    private String name;

    private String content;

    private String date;

    public static NoticeResponse toResponse(Notice notice) {
        // 날짜 형식 지정
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(notice.getDate());

        return NoticeResponse.builder()
            .id(notice.getId())
            .name(notice.getName())
            .content(notice.getContent())
            .date(date)
            .build();
    }
}
