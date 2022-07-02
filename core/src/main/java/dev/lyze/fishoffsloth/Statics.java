package dev.lyze.fishoffsloth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

public class Statics {
    public static boolean debug = false;
    public static TextureAtlas mainAtlas = new TextureAtlas("atlases/main.atlas");

    public static Music music = Gdx.audio.newMusic(Gdx.files.internal("music/Cute-8-Bit-Monsters.mp3"));
    public static Sound coin1 = Gdx.audio.newSound(Gdx.files.internal("sounds/Coin 1.mp3"));
    public static Sound coin2 = Gdx.audio.newSound(Gdx.files.internal("sounds/Coin 2.mp3"));
    public static Sound coinTotalWin1 = Gdx.audio.newSound(Gdx.files.internal("sounds/Coin Total Win.mp3"));
    public static Sound coinTotalWin2 = Gdx.audio.newSound(Gdx.files.internal("sounds/Coin Total Win 2.mp3"));

    public static Sound jump1 = Gdx.audio.newSound(Gdx.files.internal("sounds/Jump 1.mp3"));
    public static Sound jump2 = Gdx.audio.newSound(Gdx.files.internal("sounds/Jump 3.mp3"));
    public static Sound jump3 = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.ogg"));

    public static Sound hit = Gdx.audio.newSound(Gdx.files.internal("sounds/hitHurt.ogg"));

    public static void playSound(Sound... sound) {
        sound[MathUtils.random(sound.length - 1)].play(0.4f);
    }

    public static void playSmallPitch(Sound... sounds) {
        sounds[MathUtils.random(sounds.length - 1)].play(0.4f, (MathUtils.random() / 3f) + 0.9f, 0);
    }

    public static void playBigPitch(Sound... sounds) {
        sounds[MathUtils.random(sounds.length - 1)].play(0.4f, MathUtils.random() + 0.8f, 0);
    }
}
