package app.learningtrip.apiserver.search.service;

import app.learningtrip.apiserver.search.dto.response.SearchByNameResponse;
import org.springframework.stereotype.Service;

@Service
public interface SearchService {
    SearchByNameResponse searchByName(String name);
}
