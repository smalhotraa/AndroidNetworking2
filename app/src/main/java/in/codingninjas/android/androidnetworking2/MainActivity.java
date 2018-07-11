package in.codingninjas.android.androidnetworking2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> albumTitles = new ArrayList<>();
    ArrayList<Album> albumArrayList = new ArrayList<>();
    ProgressBar progressBar;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Albums");

        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressBar);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,albumTitles);

        listView.setAdapter(adapter);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId",-1);


        getAllAlbums(userId);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(userId == -1){

                    Bundle bundle =  new Bundle();

                    Album album = albumArrayList.get(i);

                    bundle.putInt("userId",album.getUserId());
                    bundle.putInt("albumId",album.getId());
                    bundle.putString("title",album.getTitle());

                    Intent intent1 = new Intent(MainActivity.this,AlbumDetailsActivity.class);
                    intent1.putExtras(bundle);

                    startActivity(intent1);
                }else{

                    Toast.makeText(MainActivity.this, albumTitles.get(i), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getAllAlbums(int userId) {

        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        String url;

        if(userId == -1){

           url = "https://jsonplaceholder.typicode.com/albums";

        }else{

            url = "https://jsonplaceholder.typicode.com/albums?userId=" + userId;
        }

        AlbumAsyncTask albumAsyncTask = new AlbumAsyncTask(new AlbumDownloadListener() {
            @Override
            public void onAlbumDownloadListener(ArrayList<Album> albums) {
                albumArrayList.clear();
                albumTitles.clear();

                albumArrayList.addAll(albums);
                for(int i = 0; i < albumArrayList.size(); ++i){

                    albumTitles.add(albumArrayList.get(i).getTitle());
                }

                adapter.notifyDataSetChanged();
                listView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });

        albumAsyncTask.execute(url);

    }

}
