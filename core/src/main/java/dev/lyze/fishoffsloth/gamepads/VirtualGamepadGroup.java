package dev.lyze.fishoffsloth.gamepads;

import dev.lyze.fishoffsloth.level.players.Player;
import lombok.Getter;

import java.util.ArrayList;

public class VirtualGamepadGroup {
    private ArrayList<VirtualGamepad> gamepads = new ArrayList<>();

    @Getter protected float leftPressed;
    @Getter protected float rightPressed;
    @Getter protected boolean jumpJustPressed;

    public VirtualGamepadGroup(Player player, int playerNumber) {
        gamepads.add(new KeyboardGamepad(player, playerNumber));

        gamepads.add(new ControllerGamepad(player, playerNumber));

        player.setGamepad(this);
    }

    public void update(float delta) {
        leftPressed = 0;
        rightPressed = 0;
        jumpJustPressed = false;

        for (VirtualGamepad g : gamepads) {
            g.update(delta);

            leftPressed = Math.max(leftPressed, g.leftPressed);
            rightPressed = Math.max(rightPressed, g.rightPressed);
            jumpJustPressed |= g.jumpJustPressed;
        }
    }

    public void reset(float delta) {
        gamepads.forEach(g -> g.reset(delta));
    }

    public void vibrate(int durationInMs, float strength) {
        gamepads.forEach(g -> g.vibrate(durationInMs, strength));
    }
}
