package in.codingninjas.android.androidnetworking2;

public class Photos {

    String title,thumbnailUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Photos(String title, String thumbnailUrl) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
    }
}
