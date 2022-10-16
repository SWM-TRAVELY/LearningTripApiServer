package app.learningtrip.apiserver.notice.service;

import app.learningtrip.apiserver.notice.domain.Notice;
import app.learningtrip.apiserver.notice.dto.NoticeResponse;
import app.learningtrip.apiserver.notice.dto.NoticeThumbnail;
import app.learningtrip.apiserver.notice.repository.NoticeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    /**
     * 공지사항 내용 조회
     */
    public NoticeResponse getInfo(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        notice.orElseThrow(() -> new NoSuchElementException("존재하지 않은 Notice입니다."));

        NoticeResponse noticeResponse = NoticeResponse.toResponse(notice.get());

        return noticeResponse;
    }

    /**
     * 공지사항 리스트 조회
     */
    public List<NoticeThumbnail> getList() {
        List<Notice> noticeList = noticeRepository.findAll();

        List<NoticeThumbnail> noticeThumbnailList = new ArrayList<NoticeThumbnail>();
        for (Notice notice : noticeList) {
            noticeThumbnailList.add(NoticeThumbnail.toThumbnail(notice));
        }

        return noticeThumbnailList;
    }

}
