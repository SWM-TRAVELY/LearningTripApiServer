package app.learningtrip.apiserver.event.controller;

import app.learningtrip.apiserver.event.domain.Event;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
    @GetMapping("/home/banner")
    public Event eventList() {
        return Event.builer()
            .imgURL("https://i.ibb.co/PZcKz75/notice-A1-M7-E7-P2-X0-Q1-K5-Z4.jpg")
            .build();
    }
}
