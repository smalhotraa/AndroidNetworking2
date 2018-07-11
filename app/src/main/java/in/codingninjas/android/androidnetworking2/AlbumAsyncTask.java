package in.codingninjas.android.androidnetworking2;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class AlbumAsyncTask extends AsyncTask<String,Void,ArrayList<Album>>{

    AlbumDownloadListener listener;

    AlbumAsyncTask(AlbumDownloadListener listener){

        this.listener = listener;
    }

    @Override
    protected ArrayList<Album> doInBackground(String... strings) {
        ArrayList<Album> albums = new ArrayList<>();
        String urlString = strings[0];

        try {
            URL url = new URL(urlString);

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.connect();

            InputStream inputStream = httpsURLConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            String result = "";
            while(scanner.hasNext()){

                result = result + scanner.next();
            }

            JSONArray rootArray = new JSONArray(result);

            for(int i = 0; i < rootArray.length(); ++i){

                JSONObject object = rootArray.getJSONObject(i);

                String title = object.getString("title");
                int id = object.getInt("id");
                int userId = object.getInt("userId");

                Album album = new Album(id,userId,title);
                albums.add(album);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return albums;
    }

    @Override
    protected void onPostExecute(ArrayList<Album> albums) {
        super.onPostExecute(albums);

        listener.onAlbumDownloadListener(albums);
    }
}
