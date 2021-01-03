package org.iptime.mascore.musiconcloud.Melon;

import org.iptime.mascore.musiconcloud.GlobalSecrets;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Owner on 2017-02-19.
 */

public class MelonAPI {

    /*
     * param
     * String 타입의 HTML 문서
     *
     * return
     * Map을 가지는 ArrayList
     *  Map : songId, songName, albumName, (currentRank), artists
     */

    public List jsonMusicParser(String obj) { // 멜론 음악 API에서 Json 파싱
        List list = new ArrayList();
        Map map;
        List artistList;
        try {
            JSONArray jsonArray = new JSONObject(obj).getJSONObject("melon").getJSONObject("songs").getJSONArray("song");
            for(int i = 0;i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                map = new HashMap();
                map.put("songId", jsonObject.get("songId"));
                map.put("songName", jsonObject.get("songName"));
                map.put("albumName", jsonObject.get("albumName"));
                if(jsonObject.has("currentRank")) {
                    map.put("currentRank", jsonObject.get("currentRank"));
                }

                JSONArray artists = jsonObject.getJSONObject("artists").getJSONArray("artist");

                artistList = new ArrayList();
                for(int j = 0;j < artists.length();j++) {
                    artistList.add(artists.getJSONObject(j).get("artistName"));
                }
                map.put("artists", artistList);
                list.add(map);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * param
     * String 타입의 HTML 문서
     *
     * return
     * Map을 가지는 ArrayList
     *  Map : categoryId, categoryName
     */

    public List jsonDJMainParser(String obj) { // 멜론 DJ API에서 Json 파싱
        List list = new ArrayList();
        Map map;

        try {
            JSONArray jsonArray = new JSONObject(obj).getJSONObject("melon").getJSONObject("categories").getJSONArray("category");
            for(int i = 0;i < jsonArray.length(); i++) {
                map = new HashMap();
                map.put("id", jsonArray.getJSONObject(i).get("categoryId"));
                map.put("name", jsonArray.getJSONObject(i).get("categoryName"));

                list.add(map);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * param
     * String 타입의 HTML 문서
     *
     * return
     * Map을 가지는 ArrayList
     *  Map : offeringId (자기 자신의 Id), categoryId (DJ 메인의 Id), name
     */

    public List jsonDJCategoryParser(String obj) { // 멜론 DJ 카테고리 API에서 Json 파싱
        List list = new ArrayList();
        Map map;

        try {
            JSONArray jsonArray = new JSONObject(obj).getJSONObject("melon").getJSONObject("subCategories").getJSONArray("subCategory");
            for(int i = 0;i < jsonArray.length(); i++) {
                map = new HashMap();
                map.put("offeringId", jsonArray.getJSONObject(i).get("offeringId"));
                map.put("categoryId", jsonArray.getJSONObject(i).get("categoryId"));
                map.put("name", jsonArray.getJSONObject(i).get("offeringTitle"));

                list.add(map);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * param
     * String 타입의 HTML 문서
     *
     * return
     * Map을 가지는 ArrayList
     *  Map : songId, songName, albumName, artists
     */

    public List jsonDJDetailParser(String obj) { // DJ 세부사항으로 음악들을 받아옴
        List list = new ArrayList();
        Map map;
        List artistList;
        try {
            JSONArray jsonArray = new JSONObject(obj).getJSONObject("melon").getJSONObject("songs").getJSONArray("song");
            for(int i = 0;i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                map = new HashMap();
                map.put("songId", jsonObject.get("songId"));
                map.put("songName", jsonObject.get("songName"));
                map.put("albumName", jsonObject.get("albumName"));

                JSONArray artists = jsonObject.getJSONObject("artists").getJSONArray("artist");

                artistList = new ArrayList();
                for(int j = 0;j < artists.length();j++) {
                    artistList.add(artists.getJSONObject(j).get("artistName"));
                }
                map.put("artists", artistList);
                list.add(map);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List getMelonResponse(URL url, int type) { // 멜론 API와 연결 (type : 1 -> 음악 파싱, 2 -> DJ메인 파싱, 3 -> DJ 카테고리 파싱, 4 -> DJ 상세 음악 파싱)
        while(true) { // 에러가 안 날 때까지
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 4.01; Windows NT)");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Content-Type", "text/html");
                connection.setRequestProperty("appKey", GlobalSecrets.melonAppKey);
                connection.setDoOutput(false);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.setDefaultUseCaches(false);

                StringBuffer response = new StringBuffer();
                String line = null;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                bufferedReader.close();

                if(type == 1) {
                    return jsonMusicParser(response.toString());
                } else if(type == 2) {
                    return jsonDJMainParser(response.toString());
                } else if(type == 3) {
                    return jsonDJCategoryParser(response.toString());
                } else if(type == 4){
                    return jsonDJDetailParser(response.toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
