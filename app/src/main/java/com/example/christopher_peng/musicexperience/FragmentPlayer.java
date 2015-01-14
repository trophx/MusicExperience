package com.example.christopher_peng.musicexperience;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Christopher_Peng on 1/13/2015.
 */
public class FragmentPlayer extends Fragment implements View.OnClickListener{

    private ImageView albumThumb, playPause;
    private TextView song, artist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);
        albumThumb= (ImageView) view.findViewById(R.id.iv_albumThumbnail);
        playPause = (ImageView) view.findViewById(R.id.iv_playPause);
        playPause.setOnClickListener(this);

        song = (TextView) view.findViewById(R.id.tv_songTitle);
        artist = (TextView) view.findViewById(R.id.tv_artistName);



        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
