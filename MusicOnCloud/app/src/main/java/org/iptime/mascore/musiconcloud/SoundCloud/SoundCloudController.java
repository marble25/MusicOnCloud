package org.iptime.mascore.musiconcloud.SoundCloud;

import java.util.List;

/**
 * Created by Owner on 2017-02-27.
 */

public class SoundCloudController {
    private final SoundCloudSearchAPI soundCloudSearchAPI = new SoundCloudSearchAPI();
    private final SoundCloudStreamAPI soundCloudStreamAPI = new SoundCloudStreamAPI();

    public List searchMusics(String query) {
        return soundCloudSearchAPI.searchMusics(query);
    }

    public String getMusicUrl(String trackId) {
        return soundCloudStreamAPI.getMusicUrl(trackId);
    }

}
