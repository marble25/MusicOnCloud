package org.iptime.mascore.musiconcloud.Melon;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Owner on 2017-03-01.
 */

public class MelonDJAPI extends MelonAPI {

    private static final String mainUrl = "http://apis.skplanetx.com/melon/melondj?version=1";

    private static final String categoryFrontUrl = "http://apis.skplanetx.com/melon/melondj/categories/";
    private static final String categoryBackUrl = "?version=1&page=0&count=100";


    public List getMelonDJCategory() {
        try {
            List list = getMelonResponse(new URL(mainUrl), 2);

            for(int i = 0;i < list.size();i++) {
                Map map = (HashMap)list.get(i);
                ((HashMap)list.get(i)).put("sub", getMelonResponse(new URL(categoryFrontUrl + (int)map.get("id") + categoryBackUrl), 3));
            }
            return list;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List getMelonDJDetail(int categoryId, int offeringId) {
        try {
            return getMelonResponse(new URL(categoryFrontUrl + categoryId + "/offerings/" + offeringId + categoryBackUrl), 4);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
