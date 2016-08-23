package com.chicken.invasion.service;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Albin on 2016-08-13.
 */
public class MusicPlayer {
    private static Music bgMusic;

    public static void playMusic(String filename) {
        Music music = Gdx.audio.newMusic(Gdx.files.internal(filename));
        music.setLooping(false);
        music.setVolume(1.0f);
        music.play();
    }

    public static void playBgMusic(String filename) {
        if (bgMusic != null) {
            stopBgMusic();
        }

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal(filename));
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.5f);
        bgMusic.play();
    }

    public static void stopBgMusic() {
        bgMusic.stop();
    }


}
