//package com.example.christopher_peng.musicexperience;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//import android.widget.Toast;
//
///**
// * Created by Christopher_Peng on 1/14/2015.
// */
//public class songListAdapter extends BaseAdapter {
//    String[] tempSongs, tempArtists, tempPaths;
//
//    songListAdapter(String[] songList, String[] artistList, String[] pathList) {
//        tempSongs = songList;
//        tempArtists = artistList;
//        tempPaths = pathList;
//    }
//
//    @Override
//    public int getCount() {
//        return tempSongs.length;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Log.d("tempSongs", tempSongs[position]);
//        LayoutInflater inflater = getLayoutInflater();
//        View row;
//        row = inflater.inflate(R.layout.song_item, parent, false);
//        row.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(getApplicationContext(), "pressed: ", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        TextView tvSong = (TextView) row.findViewById(R.id.tv_song_title);
//        TextView tvArtist = (TextView) row.findViewById(R.id.tv_song_artist);
//        tvSong.setText(tempSongs[position]);
//        tvArtist.setText(tempArtists[position]);
//        return (row);
//    }
//}