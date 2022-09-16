package app.learningtrip.apiserver.search.controller;

import app.learningtrip.apiserver.search.dto.response.SearchByNameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    @GetMapping("name/{keyword}")
    public ResponseEntity<SearchByNameResponse> searchByName(@PathVariable("keyword") String keyword){
        return ResponseEntity.ok().body(new SearchByNameResponse());
    }
}
