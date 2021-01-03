package org.iptime.mascore.musiconcloud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaylistActivity extends AppCompatActivity {

    public void setSong() {
        List arrayList = Global.playlistItem;

        findViewById(R.id.header).setVisibility(View.VISIBLE);

        ListView listView = (ListView) findViewById(R.id.list_playlistDetail);

        SongListViewAdapter adapter = new SongListViewAdapter();
        listView.setAdapter(adapter);

        Map map;

        for(int i = 0; i < arrayList.size(); i++) {
            map = (HashMap) arrayList.get(i);
            adapter.addItem("", getResources().getDrawable(R.drawable.more),
                    (String)map.get("songName"),
                    (String)map.get("artists"),
                    (String)map.get("albumName"),
                    String.valueOf(map.get("songId")));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        for(int i = 0;i < Global.playlist.size(); i++) {
            if(((String)((HashMap)Global.playlist.get(i)).get("idx")).equals(String.valueOf(Global.currentSelectedPlaylistNum))) {
                String name = (String)((HashMap)Global.playlist.get(i)).get("name");
                ((TextView)findViewById(R.id.text_playlistname)).setText(name);
            }
        }

        setSong();
    }
}
