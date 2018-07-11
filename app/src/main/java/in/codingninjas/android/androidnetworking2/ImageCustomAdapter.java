package in.codingninjas.android.androidnetworking2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageCustomAdapter extends BaseAdapter {

    ArrayList<Photos> photosArrayList;
    Context context;

    public ImageCustomAdapter(Context context, ArrayList<Photos> photosArrayList){

        this.context = context;
        this.photosArrayList = photosArrayList;
    }
    @Override
    public int getCount() {
        return photosArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class PhotoViewHolder{
        TextView textView;
        ImageView imageView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PhotoViewHolder photoViewHolder;

        if(view == null){
            photoViewHolder = new PhotoViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.photos_layout,viewGroup,false);

            photoViewHolder.textView = view.findViewById(R.id.imageTitle);
            photoViewHolder.imageView = view.findViewById(R.id.imageView);
            view.setTag(photoViewHolder);
        }else{

            photoViewHolder = (PhotoViewHolder) view.getTag();
        }
        Photos photos = photosArrayList.get(i);
        photoViewHolder.textView.setText(photos.getTitle());

        Picasso.get().load(photos.getThumbnailUrl()).into(photoViewHolder.imageView);

        return view;
    }
}
