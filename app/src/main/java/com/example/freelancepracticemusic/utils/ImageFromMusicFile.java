package com.example.freelancepracticemusic.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.widget.ImageView;

public class ImageFromMusicFile {
    private static ImageFromMusicFile sInstance;

    private ImageFromMusicFile() {
        // Constructor
    }

    public static ImageFromMusicFile getInstance() {
        if  (sInstance == null) {
            sInstance = new ImageFromMusicFile();
        }

        return sInstance;
    }

    public void setImage(ImageView imageView, String path) {
        try {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(path);
            byte [] data = mediaMetadataRetriever.getEmbeddedPicture();
            if (data != null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                imageView.setImageBitmap(bitmap);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
