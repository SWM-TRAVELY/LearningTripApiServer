package app.learningtrip.apiserver.search.dto;

import lombok.Getter;

@Getter
public class PlaceSearchResult {

    private long placeId;

    private String name;

    private String address;

    private String imageURL;

    public PlaceSearchResult(long placeId, String name, String address, String imageURL) {
        this.placeId = placeId;
        this.name = name;
        this.address = address;
        this.imageURL = imageURL;
    }
}