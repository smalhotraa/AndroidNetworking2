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

public class PhotosAsyncTask extends AsyncTask<String,Void,ArrayList<Photos>> {

    PhotoDownloadListener listener;

    PhotosAsyncTask(PhotoDownloadListener listener){

        this.listener = listener;
    }

    @Override
    protected ArrayList<Photos> doInBackground(String... strings) {
        String urlString = strings[0];
        ArrayList<Photos> photosArrayList = new ArrayList<>();

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

                String name = object.getString("title");
                String thumbnailUrl = object.getString("thumbnailUrl");

                Photos photos1 = new Photos(name,thumbnailUrl);
                photosArrayList.add(photos1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photosArrayList;

    }

    @Override
    protected void onPostExecute(ArrayList<Photos> photosArrayList) {
        super.onPostExecute(photosArrayList);

        listener.onPhotoDownloadListener(photosArrayList);
    }
}
