package org.iptime.mascore.musiconcloud.Melon;

import org.iptime.mascore.musiconcloud.Melon.MelonAPI;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Owner on 2017-02-19.
 */

public class MelonSearchAPI extends MelonAPI {
    private String APIurl = "http://apis.skplanetx.com/melon/songs?version=1&page=0&count=100&searchKeyword=";

    public List getMelonSearchResult(String keyword) {
        try {
            return getMelonResponse(new URL(APIurl + keyword), 1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
