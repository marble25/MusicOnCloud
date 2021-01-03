package org.iptime.mascore.musiconcloud;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import org.iptime.mascore.musiconcloud.Database.DatabaseController;
import org.iptime.mascore.musiconcloud.Melon.MelonController;
import org.iptime.mascore.musiconcloud.SoundCloud.SoundCloudController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Owner on 2017-03-01.
 */

public class Global extends Activity{

    public static DatabaseController databaseController = new DatabaseController();
    public static MelonController melonController = new MelonController();
    public static SoundCloudController soundCloudController = new SoundCloudController();

    public static int djStep = 1;
    public static int djFirstIndex = 0;
    public static int djSecondIndex = 0;

    public static SharedPreferences sharedPreferences;
    public static Context context;

    public static AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String title = ((TextView)view.findViewById(R.id.text_songname)).getText().toString();
            String artists = ((TextView)view.findViewById(R.id.text_artists)).getText().toString();
            String album = ((TextView)view.findViewById(R.id.text_album)).getText().toString();
            String songId = ((TextView)view.findViewById(R.id.text_songId)).getText().toString();
            BusProvider.getInstance().post(new PushEvent(title, artists, album, songId, "OnItemClicked"));

        }
    };

    public static String getCorrectMusic(ArrayList arrayList) { // TODO
        for(int i = 0; i < arrayList.size(); i++) {
            String title = (String)((HashMap)arrayList.get(i)).get("title");
            title = title.toLowerCase();
            if(title.contains("piano") || title.contains("피아노")) {
                continue;
            } else if(title.contains("cover") || title.contains("커버")) {
                continue;
            } else if(title.contains("버전") || title.contains("버젼") || title.contains("version") || title.contains("ver")) {
                continue;
            } else if(title.contains("티져") || title.contains("teaser") || title.contains("티저")) {
                continue;
            } else if(title.contains("mv") || title.contains("m.v") || title.contains("뮤비")) {
                continue;
            } else if(title.contains("nightcore") || title.contains("bass boosted")) {
                continue;
            } else if(title.contains("remix") || title.contains("리믹스")) {
                continue;
            } else if(title.contains("edition") || title.contains("에디션")) {
                continue;
            }
            System.out.println(title);
            return String.valueOf(((HashMap)arrayList.get(i)).get("id"));
        }
        String title = (String)((HashMap)arrayList.get(0)).get("title");
        System.out.println(title);
        return String.valueOf(((HashMap)arrayList.get(0)).get("id"));
    }

    public static String userName;
    public static int userNum;

    public static List playlist = new ArrayList();
    public static List playlistItem = new ArrayList();

    public static int currentSelectedPlaylistNum = 0;

    public static List currentPlayList = new ArrayList();
    public static int currentPlayListNum = 0;
    public static boolean isRandom = false;
    public static boolean isPlaying = false;

    public static AsyncTask musicPlay;

    public static MediaPlayer mediaPlayer = new MediaPlayer();
}
