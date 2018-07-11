package in.codingninjas.android.androidnetworking2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class AlbumDetailsActivity extends AppCompatActivity {

    TextView titleTextView;
    TextView bodyTextView;
    TextView userTextView;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    ImageCustomAdapter imageCustomAdapter;
    GridView gridView;
    Intent intent;
    ArrayList<Photos> photos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);

        titleTextView = findViewById(R.id.titleTextView);
        userTextView = findViewById(R.id.userTextView);
        progressBar = findViewById(R.id.progressBar);

        gridView = findViewById(R.id.gridView);
        linearLayout = findViewById(R.id.linearLayout);

        intent = getIntent();
        int albumId = intent.getIntExtra("albumId",0);
        int userId = intent.getIntExtra("userId",0);

        imageCustomAdapter = new ImageCustomAdapter(this,photos);

        gridView.setAdapter(imageCustomAdapter);

        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);

        PhotosAsyncTask photosAsyncTask = new PhotosAsyncTask(new PhotoDownloadListener() {
            @Override
            public void onPhotoDownloadListener(ArrayList<Photos> photosArrayList) {
                progressBar.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                photos.clear();
                photos.addAll(photosArrayList);
                imageCustomAdapter.notifyDataSetChanged();

            }
        });
        photosAsyncTask.execute("https://jsonplaceholder.typicode.com/albums/"+ albumId +"/photos");

        UserAsyncTask userAsyncTask = new UserAsyncTask(new UserDownloadedListener() {
            @Override
            public void onUserDownloadListener(User user) {
                progressBar.setVisibility(View.GONE);
                getAndSetData(user);
            }
        });
        userAsyncTask.execute("https://jsonplaceholder.typicode.com/users/" + userId);
    }

    private void getAndSetData(final User user) {

        int id = intent.getIntExtra("postId",-1);
        int userId = intent.getIntExtra("userId",-1);
        String title = intent.getStringExtra("title");

        titleTextView.setText(title);
        userTextView.setText(user.getName());

        userTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AlbumDetailsActivity.this,MainActivity.class);
                intent.putExtra("userId",user.id);
                startActivity(intent);
            }
        });

    }
}
