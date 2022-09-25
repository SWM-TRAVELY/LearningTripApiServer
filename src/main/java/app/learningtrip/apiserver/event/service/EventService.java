package app.learningtrip.apiserver.event.service;

import app.learningtrip.apiserver.event.domain.Event;
import app.learningtrip.apiserver.event.repository.EventRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    /**
     * 행사, 축제 정보 조회
     */
    public Optional<List<Event>> getList() {

        // event table에서 값 찾기
        List<Event> eventList = eventRepository.findAll();

        return Optional.ofNullable(eventList);
    }

}
