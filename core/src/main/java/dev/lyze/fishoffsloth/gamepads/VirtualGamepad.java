package dev.lyze.fishoffsloth.gamepads;

import dev.lyze.fishoffsloth.level.players.Player;
import lombok.Getter;

public abstract class VirtualGamepad {
    @Getter
    protected final Player player;
    @Getter
    protected final int playerNumber;

    @Getter
    protected float leftPressed;
    @Getter
    protected float rightPressed;
    @Getter
    protected boolean jumpJustPressed;
    @Getter
    protected boolean shootPressed;

    public VirtualGamepad(Player player, int playerNumber) {
        this.player = player;
        this.playerNumber = playerNumber;
    }

    public abstract void update(float delta);

    public abstract void reset(float delta);

    public abstract void vibrate(int durationInMs, float strength);
}
