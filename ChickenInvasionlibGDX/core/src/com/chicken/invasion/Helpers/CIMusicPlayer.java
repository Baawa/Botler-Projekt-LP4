package com.chicken.invasion.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Albin on 2016-08-13.
 */
public class CIMusicPlayer implements MusicPlayer {

    @Override
    public void playMusic(String filename) {
        Music music = Gdx.audio.newMusic(Gdx.files.internal(filename));
        music.setLooping(false);
        music.setVolume(1.0f);
        music.play();
    }
}
