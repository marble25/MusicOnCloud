package org.iptime.mascore.musiconcloud;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2017-03-17.
 */

public class PushEvent {
    private List<String> pushlist;

    public PushEvent(String title, String artists, String album, String songId, String command)
    {
        pushlist = new ArrayList<>();
        pushlist.add(title);
        pushlist.add(artists);
        pushlist.add(album);
        pushlist.add(songId);
        pushlist.add(command);

    }

    /**
     * @return the pushlist
     */
    public List<String> getList()
    {
        return pushlist;
    }

}
