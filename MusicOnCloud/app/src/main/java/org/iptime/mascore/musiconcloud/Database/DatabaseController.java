package org.iptime.mascore.musiconcloud.Database;

import android.content.SharedPreferences;
import android.os.Build;
import android.widget.EditText;
import android.widget.Toast;

import org.iptime.mascore.musiconcloud.Global;
import org.iptime.mascore.musiconcloud.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Owner on 2017-02-21.
 */

public class DatabaseController extends DatabaseAPI{

    public int checkUserName(String userName) { // [userName]이 있는지 체크하는 함수
        String module = "checkUserExists.php";
        String body = "&userName=" + userName;

        String result = databaseConnect(module, body);
        if(result.contains("Fail")) { // 오류
            return -1;
        } else if(result.contains("Already")) { // [userName]이 이미 있다면
            return 0;
        } else if(result.contains("Success")) { // 없는 이름이라면
            // 데이터 출력 : Success/[userNum]

            int userNum = Integer.parseInt(result.split("/")[1]);

            // [userNum]과 [userName]을 사용자 기기에 저장
            SharedPreferences.Editor editor = Global.sharedPreferences.edit();
            editor.putInt("userNum", userNum);
            editor.putString("userName", userName);
            editor.commit();

            // Global variable에 [userNum]과 [userName]을 저장
            Global.userNum = userNum;
            Global.userName = userName;
            return 1;
        }
        return -1;
    }

    public void insertCurrentAccess() { // 기기가 앱에 접속했다고 로그를 남기는 함수
        String module = "insertCurrentAccess.php";
        String body = "&userName=" + Global.userName + "&phoneName=" + Build.MODEL;

        databaseConnect(module, body);
    }

    public int insertMusic(ArrayList arrayList) { // 새로운 음악을 추가하는 함수
        /*
            arrayList
             * [0] => 타이틀
             * [1] => 아티스트
             * [2] => 앨범
             * [3] => musicId (멜론 상의 아이디)
             * [5] => trackId (SoundCloud 상의 아이디)
         */
        String module = "insertMusic.php";
        String body = "&title=" + (String)arrayList.get(0) + "&artists=" + (String)arrayList.get(1) + "&album=" + (String)arrayList.get(2)
                + "&musicId=" + (String)arrayList.get(3) + "&trackId=" + (String)arrayList.get(5);

        String result = databaseConnect(module, body);
        if (result.contains("Success")) { // 성공
            return 1;
        } else if(result.contains("Same")) { // 이미 같은 음악이 있으면
            return 0;
        } else if(result.contains("Fail")) { // 실패
            return -1;
        }
        return -1;
    }

    public int insertMusicPlaylist(String name) { // [name]이라는 이름의 음악 플레이리스트를 추가하는 함수
        String module = "insertMusicPlaylist.php";
        String body = "&userId=" + Global.userNum + "&name=" + name;

        String result = databaseConnect(module, body);

        if(result.contains("Fail")) { // 실패
            return -1;
        } else if(result.contains("Already")) { // 동일한 플레이리스트가 이미 존재
            return 0;
        } else if(result.contains("Success")) { // 성공
            // 데이터 출력 : Success/[idx]

            String[] playlists = result.split("/");

            Map map = new HashMap();
            map.put("idx", playlists[1]);
            map.put("name", name);
            Global.playlist.add(map);

            return 1;
        }
        return -1;
    }

    public void getMusicPlaylist() { // 유저의 플레이리스트 목록을 가져오는 함수
        String module = "getMusicPlaylist.php";
        String body = "&userId=" + Global.userNum;

        String result = databaseConnect(module, body);
        if(result.contains("Success")) { // 성공
            // 데이터 출력 : Success/[idx]/[name]/[idx]/[name]...

            String[] playlists = result.split("/");

            Map map;
            for(int i = 1;i < playlists.length;i += 2) {
                map = new HashMap();
                map.put("idx", playlists[i]);
                map.put("name", playlists[i + 1]);
                Global.playlist.add(map);
            }

        }
    }

    public void getMusicPlaylistContent(int num) { // [num] 인덱스의 플레이리스트 안에 음악들을 가져오는 함수
        String module = "getMusicPlaylistContent.php";
        String body = "&playId=" + num;

        String result = databaseConnect(module, body);
        Global.playlistItem = new ArrayList();
        if (result.contains("Success")) { // 성공
            // 데이터 출력 : Success/[trackId]/[songName]/[artists]/[album]/[trackId]/[songName]/[artists]/[album]...

            String[] playlists = result.split("/");

            Map map;
            for (int i = 1; i < playlists.length; i += 4) {
                map = new HashMap();
                map.put("trackId", playlists[i]);
                map.put("songName", playlists[i + 1]);
                map.put("artists", playlists[i + 2]);
                map.put("album", playlists[i + 3]);
                Global.playlistItem.add(map);
            }

        }
    }

    public String getMusic(String musicNum) { // [musicNum]을 인덱스로 갖는 음악을 가져오는 함수
        String module = "getMusic.php";
        String body = "&playId=" + musicNum;

        String result = databaseConnect(module, body);
        if (result.contains("Success")) {
            String[] playlists = result.split("/");

            return playlists[1]; // 음악의 [title] return
        } else {
            return "";
        }
    }

}
