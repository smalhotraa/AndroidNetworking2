package in.codingninjas.android.androidnetworking2;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class UserAsyncTask extends AsyncTask<String,Void,User> {

    UserDownloadedListener listener;

    UserAsyncTask(UserDownloadedListener listener){
        this.listener = listener;
    }

    @Override
    protected User doInBackground(String... strings) {

        String urlString = strings[0];
        User user = null;
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

            JSONObject jsonObject = new JSONObject(result);
            String name = jsonObject.getString("name");
            String username = jsonObject.getString("username");
            String email = jsonObject.getString("email");
            int id = jsonObject.getInt("id");

            user = new User(name,username,email,id);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
        listener.onUserDownloadListener(user);

    }
}
