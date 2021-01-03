package org.iptime.mascore.musiconcloud.SoundCloud;

import org.iptime.mascore.musiconcloud.GlobalSecrets;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Owner on 2017-02-19.
 */

public class SoundCloudSearchAPI extends SoundCloudAPI {
    static final String address = "http://api.soundcloud.com/search?client_id=" + GlobalSecrets.soundCloudAppKey + "&q=";

    public List searchMusics(String query) {
        try {
            String encodeResult = URLEncoder.encode(query, "UTF-8");
            URL url = new URL(address + encodeResult);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(false);
            connection.setDoInput(true);

            StringBuffer response = new StringBuffer();
            String line = null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            bufferedReader.close();
            return jsonParser(response.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List jsonParser(String obj) {
        List list = new ArrayList();
        Map map;
        try {
            JSONArray jsonArray = new JSONObject(obj).getJSONArray("collection");
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if("track".equals(jsonObject.get("kind"))) {
                    map = new HashMap();
                    map.put("title", jsonObject.get("title"));
                    map.put("id", jsonObject.get("id"));
                    list.add(map);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
