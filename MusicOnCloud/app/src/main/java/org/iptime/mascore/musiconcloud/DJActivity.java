package org.iptime.mascore.musiconcloud;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DJActivity extends AppCompatActivity {

    public void setDJCategory() {
        ListView listView = (ListView) findViewById(R.id.list_djDetail);
        DJListViewAdapter adapter = new DJListViewAdapter();

        listView.setAdapter(adapter);

        List melonDJ = Global.melonController.getMelonDJCategory();
        Map map = (HashMap) melonDJ.get(Global.djFirstIndex);
        ((TextView)findViewById(R.id.text_djname)).setText((String)map.get("name"));
        List arrayList = (ArrayList) map.get("sub");
        for (int i = 0; i < arrayList.size(); i++) {
            map = (HashMap) arrayList.get(i);
            adapter.addItem((String) map.get("name"));

        }
        listView.setOnItemClickListener(djCategoryClickListener);
    }

    public void setDJSong() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                final ArrayList arrayList = (ArrayList)Global.melonController.getMelonDJDetail(Global.djFirstIndex, Global.djSecondIndex);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ListView listView = (ListView) findViewById(R.id.list_djDetail);

                        findViewById(R.id.header).setVisibility(View.VISIBLE);

                        SongListViewAdapter adapter = new SongListViewAdapter();
                        listView.setAdapter(adapter);

                        Map map;

                        for(int i = 0; i < arrayList.size(); i++) {
                            map = (HashMap) arrayList.get(i);
                            adapter.addItem("", getResources().getDrawable(R.drawable.more),
                                    (String)map.get("songName"),
                                    TextUtils.join(", ", (ArrayList)map.get("artists")),
                                    (String)map.get("albumName"),
                                    String.valueOf(map.get("songId")));
                        }
                    }
                });

                return null;
            }
        }.execute();
    }

    private AdapterView.OnItemClickListener djCategoryClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Global.djStep = 3;
            Global.djSecondIndex = position;
            setDJSong();
        }
    };

    public void onBackButtonClickOnDJList(View view) {
        if(Global.djStep == 2) {
            finish();
        } else if(Global.djStep == 3) {
            Global.djStep = 2;
            setDJCategory();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dj);
        setDJCategory();
    }
}
