package mobile.mads.turnosdim;


import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by mati on 8/18/16.
 */

public class ServiceHandler {

    public ServiceHandler(){

    }

    public String makeCall(String url) throws IOException {

            OkHttpClient client = new OkHttpClient();
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

}