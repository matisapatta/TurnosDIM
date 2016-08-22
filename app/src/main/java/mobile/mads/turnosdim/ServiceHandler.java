package mobile.mads.turnosdim;


import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by mati on 8/18/16.
 */

public class ServiceHandler {

    public ServiceHandler(){

    }
    OkHttpClient client = new OkHttpClient();

    public String doGetRequest(String url) throws IOException {


            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response responses = null;

            try {
                responses = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonData = responses.body().string();

        return jsonData;
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public String doPostRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


}