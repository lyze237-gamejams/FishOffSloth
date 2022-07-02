package dev.lyze.fishoffsloth.gamepads;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dev.lyze.fishoffsloth.level.players.Player;
import lombok.var;

public class KeyboardGamepad extends VirtualGamepad {
    private boolean jumpHeld;

    public KeyboardGamepad(Player player, int playerNumber) {
        super(player, playerNumber);
    }

    @Override
    public void update(float delta) {
        var singlePlayer = player.getLevel().getPlayers().getPlayers().size == 1;

        leftPressed = Gdx.input.isKeyPressed(player.isFirstPlayer() ? Input.Keys.A : Input.Keys.LEFT) || (singlePlayer && Gdx.input.isKeyPressed(Input.Keys.LEFT)) ? 1 : 0;
        rightPressed = Gdx.input.isKeyPressed(player.isFirstPlayer() ? Input.Keys.D : Input.Keys.RIGHT) || (singlePlayer && Gdx.input.isKeyPressed(Input.Keys.RIGHT)) ? 1 : 0;
        shootPressed = Gdx.input.isKeyPressed(player.isFirstPlayer() ? Input.Keys.SHIFT_LEFT : Input.Keys.SHIFT_RIGHT) || (singlePlayer && Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));

        if (!jumpHeld)
            if (jumpJustPressed = Gdx.input.isKeyPressed(player.isFirstPlayer() ? Input.Keys.W : Input.Keys.UP) || (singlePlayer && Gdx.input.isKeyPressed(Input.Keys.UP)))
                jumpHeld = true;

        if (!Gdx.input.isKeyPressed(player.isFirstPlayer() ? Input.Keys.W : Input.Keys.UP) || (singlePlayer && Gdx.input.isKeyPressed(Input.Keys.UP)))
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
