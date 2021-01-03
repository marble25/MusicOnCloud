package org.iptime.mascore.musiconcloud.Melon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

public class MelonChartAPI extends MelonAPI{
    private static final String APIurl = "http://apis.skplanetx.com/melon/charts/realtime?version=1&page=1&count=100";

    public List getMelonChart() {
        try {
            return getMelonResponse(new URL(APIurl), 1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
