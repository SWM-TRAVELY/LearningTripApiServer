package app.learningtrip.apiserver.course.domain;

import app.learningtrip.apiserver.configuration.ApiKey;
import java.io.IOException;
import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GoogleMapApi {

    private final ApiKey apiKey;

    private Integer distance;

    private Integer time;

    public GoogleMapApi(ApiKey apiKey) {
        this.apiKey = apiKey;
    }

    public void makeDistanceTime(double latitude1, double longitude1, double latitude2, double longitude2)
        throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();

        Request request = new Request.Builder()
            .url("https://maps.googleapis.com/maps/api/distancematrix/json?"
                + "units=metric"        // 거리 표현 단위 (km&meter: metric, mile=imperial)
                + "&mode=transit"
                + "&origins=" + String.valueOf(latitude1) + "," + String.valueOf(longitude1)
                + "&destinations=" + String.valueOf(latitude2) + "," + String.valueOf(longitude2)
                + "&region=KR"
                + "&key=" + apiKey.getGoogleMapApiKey())
            .get()
            .build();

        // Google Map Api 호출
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            ResponseBody body = response.body();

            if (body != null) {
                String result = body.string();
                body.close();

                JSONObject resultJson = new JSONObject(result);
                JSONArray rows = resultJson.getJSONArray("rows");

                JSONObject row = rows.getJSONObject(0);
                JSONArray elements = row.getJSONArray("elements");

                JSONObject element = elements.getJSONObject(0);
                JSONObject distance = element.getJSONObject("distance");
                JSONObject duration = element.getJSONObject("duration");

                this.distance = (Integer) distance.get("value");
                this.time = (Integer) duration.get("value");

            } else {
                System.err.println("GoogleMapApi Error Occurred");
            }
        }
    }
}
