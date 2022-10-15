package app.learningtrip.apiserver.notice.controller;

import app.learningtrip.apiserver.notice.domain.Notice;
import app.learningtrip.apiserver.notice.dto.NoticeResponse;
import app.learningtrip.apiserver.notice.dto.NoticeThumbnail;
import app.learningtrip.apiserver.notice.service.NoticeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/notice/{notice_id}")
    public ResponseEntity info(@PathVariable(name = "notice_id") long notice_id) {
        NoticeResponse noticeResponse = noticeService.getInfo(notice_id);

        return ResponseEntity.ok().body(noticeResponse);
    }

    @GetMapping("/notice/list")
    public ResponseEntity noticeList() {
        List<NoticeThumbnail> noticeThumbnailList = noticeService.getList();

        return ResponseEntity.ok().body(noticeThumbnailList);
    }

}
