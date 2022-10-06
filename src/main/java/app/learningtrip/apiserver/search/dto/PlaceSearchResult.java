package app.learningtrip.apiserver.search.dto;

import lombok.Getter;

@Getter
public class PlaceSearchResult {

    private long id;

    private String name;

    private String address;

    private String imageURL;

    public PlaceSearchResult(long id, String name, String address, String imageURL) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.imageURL = imageURL;
    }
}