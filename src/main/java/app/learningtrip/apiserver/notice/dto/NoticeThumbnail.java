package app.learningtrip.apiserver.notice.dto;

import app.learningtrip.apiserver.notice.domain.Notice;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeThumbnail {

    private String name;

    private String date;

    public static NoticeThumbnail toThumbnail(Notice notice) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(notice.getDate());

        return NoticeThumbnail.builder()
            .name(notice.getName())
            .date(date)
            .build();
    }
}
