package com.example.christopher_peng.musicexperience;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends Activity implements View.OnClickListener {
    private String[] mAudioPath;
    private String[] artist;
    private String[] songs;
    private String[] albumArt;
    private MediaPlayer mMediaPlayer;
    private String[] mMusicList;
    private String lastSong;
    private String lastSongPath;
    private ImageView pausePlay, ivAlbum;
    private TextView tv_songTitle, tv_artist;
    Bitmap songImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fullscreen);

        pausePlay = (ImageView) findViewById(R.id.iv_playPause);
        pausePlay.setOnClickListener(this);
        ivAlbum = (ImageView) findViewById(R.id.iv_albumThumbnail);

        mMediaPlayer = new MediaPlayer();
        tv_songTitle = (TextView) findViewById(R.id.tv_songTitle);
        tv_artist = (TextView) findViewById(R.id.tv_artistName);

        ListView mListView = (ListView) findViewById(R.id.listView1);

        mMusicList = getAudioList();

        mListView.setAdapter(new songListAdapter(songs, artist, albumArt, mAudioPath));

//        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, mMusicList);
//        mListView.setAdapter(mAdapter);
//
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//                try {
////                    Toast.makeText(getApplicationContext(), arg2 + "::" + mAudioPath[arg2], Toast.LENGTH_SHORT).show();
//                    playSong(arg2);
//                    lastSongPath = mAudioPath[arg2];
//                } catch (IllegalArgumentException e) {
//                    e.printStackTrace();
//                } catch (IllegalStateException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                    Log.d("exception", e.toString());
//                    Log.d("SEE THIS", mAudioPath[arg2]);
//                }
//            }
//        });
    }

    private String[] getAudioList() {
        final Cursor mCursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DATA}, null, null,
                "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");

        int count = mCursor.getCount();

        songs = new String[count];
        mAudioPath = new String[count];
        artist = new String[count];
//        albumArt = new String[count];
        int i = 0;
        if (mCursor.moveToFirst()) {
            do {

                songs[i] = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                artist[i] = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));

//                songs[i] = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                mAudioPath[i] = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
//                albumArt[i] = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART));
                i++;
            } while (mCursor.moveToNext());
        }

        mCursor.close();
        return songs;
    }

    private void playSong(int index) throws IllegalArgumentException,
            IllegalStateException, IOException {

        Log.d("ringtone", "playSong :: " + index);

        mMediaPlayer.reset();
        mMediaPlayer.setDataSource(mAudioPath[index]);
        tv_songTitle.setText(songs[index]);
        tv_artist.setText(artist[index]);
//mMediaPlayer.setLooping(true);
        mMediaPlayer.prepare();
        mMediaPlayer.start();
        Drawable pause = getResources().getDrawable(R.drawable.ic_pause_black_36dp);
        pausePlay.setImageDrawable(pause);
        MediaMetadataRetriever meta = new MediaMetadataRetriever();
        meta.setDataSource(mAudioPath[index]);
        byte[] art = meta.getEmbeddedPicture();
        try {
            songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
//            ivAlbumArt.setImageBitmap(songImage);
        } catch (NullPointerException e) {

            songImage = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_sample_album_art);
        }
        ivAlbum.setImageBitmap(songImage);

    }

    private void playPauseSong() throws IllegalArgumentException,
            IllegalStateException, IOException {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            Drawable play = getResources().getDrawable(R.drawable.ic_play_arrow_black_48dp);
            pausePlay.setImageDrawable(play);

        } else {
//
//            mMediaPlayer.setDataSource(lastSongPath);
//            mMediaPlayer.prepare();

            mMediaPlayer.start();
            Drawable pause = getResources().getDrawable(R.drawable.ic_pause_black_36dp);
            pausePlay.setImageDrawable(pause);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_playPause:
                try {
                    playPauseSong();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Log.d("exception", e.toString());
                }

                break;

        }
    }

    class songListAdapter extends BaseAdapter {
        String[] tempSongs, tempArtists, tempPaths, tempAlbumArt;

        songListAdapter(String[] songList, String[] artistList, String[] albumArtList, String[] pathList) {
            tempSongs = songList;
            tempArtists = artistList;
            tempPaths = pathList;
            tempAlbumArt = albumArtList;
        }

        @Override
        public int getCount() {
            return tempSongs.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //Metadata getter
            MediaMetadataRetriever meta = new MediaMetadataRetriever();
            meta.setDataSource(tempPaths[position]);


            Log.d("tempSongs", tempSongs[position]);
            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.song_item, parent, false);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "pressed: ", Toast.LENGTH_SHORT).show();
                    try {
//                    Toast.makeText(getApplicationContext(), arg2 + "::" + mAudioPath[arg2], Toast.LENGTH_SHORT).show();
                        playSong(position);

//                        lastSongPath = mAudioPath[position];
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        Log.d("exception", e.toString());
                        Log.d("SEE THIS", mAudioPath[position]);
                    }

                }
            });

//            Drawable art = Drawable.createFromPath(tempAlbumArt[position]);
            ImageView ivAlbumArt = (ImageView) row.findViewById(R.id.iv_song_albumArt);
//            ivAlbumArt.setImageDrawable(art);

            TextView tvSong = (TextView) row.findViewById(R.id.tv_song_title);
            TextView tvArtist = (TextView) row.findViewById(R.id.tv_song_artist);
            tvSong.setText(tempSongs[position]);
            tvArtist.setText(tempArtists[position]);


//            tvArtist.setText(meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));


            byte[] art = meta.getEmbeddedPicture();
            try {
                songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
                ivAlbumArt.setImageBitmap(songImage);
            } catch (NullPointerException e) {

//                songImage = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_sample_album_art);
            }

            return (row);
        }
    }
}
