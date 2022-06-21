package dev.lyze.fishoffsloth.gamepads;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dev.lyze.fishoffsloth.level.players.Player;

public class KeyboardGamepad extends VirtualGamepad {
    public KeyboardGamepad(Player player, int playerNumber) {
        super(player, playerNumber);
    }

    @Override
    public void update(float delta) {
        leftPressed = Gdx.input.isKeyPressed(player.isFirstPlayer() ? Input.Keys.A : Input.Keys.LEFT) ? 1 : 0;
        rightPressed = Gdx.input.isKeyPressed(player.isFirstPlayer() ? Input.Keys.D : Input.Keys.RIGHT) ? 1 : 0;
        jumpJustPressed = Gdx.input.isKeyJustPressed(player.isFirstPlayer() ? Input.Keys.W : Input.Keys.UP);
    }

    @Override
    public void reset(float delta) {

    }

    @Override
    public void vibrate(int durationInMs, float strength) {

    }
}
