package org.iptime.mascore.musiconcloud;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public boolean isFirst() { // 처음 앱을 설치했는지 체크
        Global.sharedPreferences = getSharedPreferences("setting", 0);
        String userName = Global.sharedPreferences.getString("userName", "");
        int userNum = Global.sharedPreferences.getInt("userNum", 0);
        if("".equals(userName)) {
            return true;
        } else {
            Global.userName = userName;
            Global.userNum = userNum;
            return false;
        }
    }

    public void setTab() { // 앱 시작시 탭에 요소 넣기
        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("", getResources().getDrawable(R.drawable.menu1))
                .setContent(R.id.tab1));

        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("", getResources().getDrawable(R.drawable.menu2))
                .setContent(R.id.tab2));

        tabHost.addTab(tabHost.newTabSpec("tab3")
                .setIndicator("", getResources().getDrawable(R.drawable.menu3))
                .setContent(R.id.tab3));

        tabHost.addTab(tabHost.newTabSpec("tab4")
                .setIndicator("", getResources().getDrawable(R.drawable.menu4))
                .setContent(R.id.tab4));

        tabHost.addTab(tabHost.newTabSpec("tab5")
                .setIndicator("", getResources().getDrawable(R.drawable.menu5))
                .setContent(R.id.tab5));

        tabHost.setCurrentTab(0);
    }

    public void setChart() { // 차트 1위~100위 세팅
        ListView listView = (ListView) findViewById(R.id.list_chart);

        SongListViewAdapter adapter  = new SongListViewAdapter();
        listView.setAdapter(adapter);

        List melonChart = Global.melonController.getMelonChart();
        for(int i = 0;i < melonChart.size(); i++) {
            Map map = (HashMap)melonChart.get(i);
            adapter.addItem(((Integer)map.get("currentRank")).toString(),
                    getResources().getDrawable(R.drawable.more),
                    (String)map.get("songName"),
                    TextUtils.join(", ", (ArrayList)map.get("artists")),
                    (String)map.get("albumName"),
                    String.valueOf(map.get("songId")));
        }
        listView.setOnItemClickListener(Global.onItemClickListener);
    }

    public void setDJList() { // 멜론 DJ 리스트 세팅
        ListView listView = (ListView) findViewById(R.id.list_dj);
        DJListViewAdapter adapter= new DJListViewAdapter();

        listView.setAdapter(adapter);

        List melonDJ = Global.melonController.getMelonDJCategory();
        Map map;
        for (int i = 0; i < melonDJ.size(); i++) {
            map = (HashMap) melonDJ.get(i);
            adapter.addItem((String) map.get("name"));
        }
        listView.setOnItemClickListener(itemClickListener);
    }

    public void setPlaylist() { // 멜론 DJ 리스트 내부의 플레이리스트 세팅
        LinearLayout linearLayoutPlaylists = (LinearLayout) findViewById(R.id.list_playlists);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout;

        for(int i = 0; i < Global.playlist.size(); i++) {
            linearLayout = (LinearLayout) inflater.inflate(R.layout.list_playlist, null);
            ((TextView)linearLayout.findViewById(R.id.text_playlist)).setText((String)((HashMap)Global.playlist.get(i)).get("name"));
            linearLayoutPlaylists.addView(linearLayout);
        }
    }


    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // 1단계 DJ 리스트 클릭시 2단계로
            Global.djStep = 2;
            Global.djFirstIndex = position;

            Intent intent = new Intent(getApplicationContext(), DJActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener onCardViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) { // DJ 리스트 보기
            FragmentManager fm = getSupportFragmentManager();
            PlaylistDialog playlistDialog = new PlaylistDialog();
            playlistDialog.show(fm, "setPlaylist");
        }
    };

    public void onCardViewClicked(View view) {
        String text = ((TextView)view.findViewById(R.id.text_playlist)).getText().toString();

        if("새 플레이리스트 만들기".equals(text)) {
            return;
        }

        Map map;
        for(int i = 0;i < Global.playlist.size(); i++) {
            map = (HashMap)Global.playlist.get(i);
            if(text.equals(map.get("name"))) {
                Global.currentSelectedPlaylistNum = Integer.parseInt((String)map.get("idx"));
                break;
            }
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Global.databaseController.getMusicPlaylistContent(Global.currentSelectedPlaylistNum);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Intent intent = new Intent(getApplicationContext(), PlaylistActivity.class);
                startActivity(intent);
            }
        }.execute();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Global.context = getApplicationContext();
        Global.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        BusProvider.getInstance().register(this);

        if(isFirst()) {
            FragmentManager fm = getSupportFragmentManager();
            UserNameDialog userNameDialog = new UserNameDialog();
            userNameDialog.show(fm, "setUserName");
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Global.databaseController.insertCurrentAccess();
                Global.melonController.setMelonChart();
                Global.melonController.setMelonDJCategory();
                Global.databaseController.getMusicPlaylist();

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setChart();
                setDJList();
                setPlaylist();
            }
        }.execute();

        setTab();

        SearchView searchView = (SearchView)findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        final ArrayList arrayList = (ArrayList)Global.melonController.getMelonSearchResult(query);
                        System.out.println(arrayList);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ListView listView = (ListView)findViewById(R.id.list_search);

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

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        View playlistView = findViewById(R.id.createPlaylist);
        CardView cardView = (CardView)playlistView.findViewById(R.id.createCardView);
        cardView.setOnClickListener(onCardViewClickListener);

    }
    @Override
    protected void onDestroy() {
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void FinishLoad(PushEvent mPushEvent) {
        final View view = findViewById(R.id.playbar);
        view.setVisibility(View.VISIBLE);
        final List<String> list = mPushEvent.getList();
        switch(list.get(4)) {
            case "OnItemClicked":
                if(Global.isPlaying) {
                    Global.mediaPlayer.release();
                    Global.mediaPlayer = new MediaPlayer();
                    if(Global.musicPlay != null) {
                        Global.musicPlay.cancel(true);
                    }
                    Global.isPlaying = false;
                }
                Global.musicPlay = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        String musicId = Global.databaseController.getMusic(list.get(3));
                        String url;
                        if("".equals(musicId)) {
                            String query = list.get(0) + " " + list.get(1);
                            ArrayList tempList = (ArrayList)Global.soundCloudController.searchMusics(query);
                            musicId = Global.getCorrectMusic(tempList);
                            url = Global.soundCloudController.getMusicUrl(musicId);

                            ArrayList arrayList = (ArrayList)list;
                            arrayList.add(musicId);
                            Global.databaseController.insertMusic(arrayList);

                        } else {
                            url = Global.soundCloudController.getMusicUrl(musicId);
                        }
                        try {
                            Global.mediaPlayer.setDataSource(url);
                            Global.mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Global.mediaPlayer.start();
                        Global.isPlaying = true;


                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        ((TextView)view.findViewById(R.id.text_play_songname)).setText(list.get(0));
                        ((TextView)view.findViewById(R.id.text_play_artists)).setText(list.get(1));
                        ((ImageView)view.findViewById(R.id.image_playStop)).setImageResource(R.drawable.play_pause);

                        HashMap map = new HashMap();
                        map.put("title", list.get(0));
                        map.put("artists", list.get(1));
                        map.put("album", list.get(2));

                        Global.currentPlayList.add(map);
                        Global.currentSelectedPlaylistNum = Global.currentPlayList.size() - 1;
                    }
                }.execute();


                break;
            case "OnItemLongClicked":
                break;
            case "OnDetailClicked":
                break;
        }

    }


}
