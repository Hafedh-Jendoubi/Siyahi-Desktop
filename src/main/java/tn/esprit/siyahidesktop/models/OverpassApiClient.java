package tn.esprit.siyahidesktop.models;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class OverpassApiClient {

    private static final String OVERPASS_API_URL = "https://overpass-api.de/api/interpreter";

    public JsonNode queryNearbyBanks(double latitude, double longitude, int radius) throws IOException {
        String query = "[out:json];(node[\"amenity\"=\"bank\"](around:" + radius + "," + latitude + "," + longitude + "););out center;";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(OVERPASS_API_URL);
        request.setEntity(new StringEntity(query));
        request.setHeader("Content-Type", "application/x-www-form-urlencoded");

        try {
            String jsonResponse = EntityUtils.toString(httpClient.execute(request).getEntity());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(jsonResponse);
        } finally {
            httpClient.close();
        }
    }

    public static void main(String[] args) {
        OverpassApiClient client = new OverpassApiClient();
        try {
            JsonNode response = client.queryNearbyBanks(48.8588443, 2.2943506, 1000);  // Example coordinates (Eiffel Tower)
            System.out.println(response.toPrettyString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
