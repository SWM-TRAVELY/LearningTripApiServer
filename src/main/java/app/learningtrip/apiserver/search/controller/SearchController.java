package app.learningtrip.apiserver.search.controller;

import app.learningtrip.apiserver.search.dto.PlaceSearchResult;
import app.learningtrip.apiserver.search.service.SearchServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchServiceImpl searchService;

    @GetMapping("search/autocomplete/{keyword}")
    public ResponseEntity<List<String>> autoComplete(@PathVariable("keyword") String keyword){
        return ResponseEntity.ok().body(searchService.autoComplete(keyword));
    }

    @GetMapping("search/result/{keyword}")
    public ResponseEntity<List<PlaceSearchResult>> searchResult(@PathVariable("keyword") String keyword){
        return ResponseEntity.ok().body(searchService.searchResult(keyword));
    }

}
