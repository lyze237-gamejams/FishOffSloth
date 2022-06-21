package dev.lyze.fishoffsloth.gamepads;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dev.lyze.fishoffsloth.level.players.Player;

public class KeyboardGamepad extends VirtualGamepad {
    private boolean jumpHeld;

    public KeyboardGamepad(Player player, int playerNumber) {
        super(player, playerNumber);
    }

    @Override
    public void update(float delta) {
        leftPressed = Gdx.input.isKeyPressed(player.isFirstPlayer() ? Input.Keys.A : Input.Keys.LEFT) ? 1 : 0;
        rightPressed = Gdx.input.isKeyPressed(player.isFirstPlayer() ? Input.Keys.D : Input.Keys.RIGHT) ? 1 : 0;
        if (!jumpHeld)
            if (jumpJustPressed = Gdx.input.isKeyPressed(player.isFirstPlayer() ? Input.Keys.W : Input.Keys.UP))
                jumpHeld = true;

        if (!Gdx.input.isKeyPressed(player.isFirstPlayer() ? Input.Keys.W : Input.Keys.UP))
            jumpHeld = false;
    }

    @Override
    public void reset(float delta) {
        jumpJustPressed = false;
    }

    @Override
    public void vibrate(int durationInMs, float strength) {

    }
}
