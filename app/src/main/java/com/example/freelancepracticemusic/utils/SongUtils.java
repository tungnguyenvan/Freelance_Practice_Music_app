package com.example.freelancepracticemusic.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.freelancepracticemusic.models.SongModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SongUtils implements Serializable {
    private static final String TAG = SongUtils.class.getName();
    private static SongUtils mInstance;
    private Activity mActivity;
    private List<SongModel> mMusics;

    private SongUtils(Activity activity) {
        this.mActivity = activity;
    }

    public static SongUtils getInstance(Activity activity) {
        if (mInstance == null) {
            mInstance = new SongUtils(activity);
        }

        return mInstance;
    }

    public List<SongModel> getMusic() {
        ContentResolver contentResolver = mActivity.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " !=0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(uri, null, selection, null , sortOrder);

        mMusics = new ArrayList<>();

        if (cursor != null) {
            int count = cursor.getCount();
            if (count > 0) {
                while (cursor.moveToNext()) {
                    String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    Log.d(TAG, data);

                    mMusics.add(new SongModel(data, title, artist));
                }
            }
        }

        cursor.close();
        return mMusics;
    }
}
