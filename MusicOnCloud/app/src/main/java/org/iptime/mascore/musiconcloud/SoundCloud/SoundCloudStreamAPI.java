package org.iptime.mascore.musiconcloud.SoundCloud;

import android.os.AsyncTask;

import org.iptime.mascore.musiconcloud.Global;
import org.iptime.mascore.musiconcloud.GlobalSecrets;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by Owner on 2017-02-27.
 */

public class SoundCloudStreamAPI extends SoundCloudAPI {
    static final String frontAddress = "http://api.soundcloud.com/tracks/";
    static final String backAddress = "/stream?client_id=";

    public String getMusicUrl(String trackId) {
        return frontAddress + trackId + backAddress + GlobalSecrets.soundCloudAppKey;
    }

}
