package org.iptime.mascore.musiconcloud.Database;

import android.os.Build;

import org.iptime.mascore.musiconcloud.GlobalSecrets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Owner on 2017-02-27.
 */

public class DatabaseAPI {
    public static final String address = "http://mascore.iptime.org/moc/"; // 데이터베이스 사이트 URL

    public String databaseConnect(String module, String bodyAppend) { // 데이터베이스와 연결 ( module = 파일명, bodyAppend = post될 데이터)
        try {
            URL url = new URL(address + module);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            String body = "pw=" + GlobalSecrets.pw; // 인증 비밀번호
            body += bodyAppend;

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(body.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            StringBuffer response = new StringBuffer();
            String line = null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            bufferedReader.close();
            System.out.println(response.toString());
            return response.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
