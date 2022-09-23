package app.learningtrip.apiserver.event.controller;

import app.learningtrip.apiserver.event.domain.Event;
import app.learningtrip.apiserver.event.service.EventService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/home/banner")
    public ResponseEntity list(Model model) {
        Optional<List<Event>> eventList = eventService.getList();
        model. addAttribute("eventList", eventList);

        return ResponseEntity.ok().body(model);
    }
}
