package org.iptime.mascore.musiconcloud;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicPlay extends Service implements MediaPlayer.OnPreparedListener{
    public MusicPlay() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /** MediaPlayer가 준비되면 호출된다 */
    public void onPrepared(MediaPlayer player) {
        player.start();
    }
}
