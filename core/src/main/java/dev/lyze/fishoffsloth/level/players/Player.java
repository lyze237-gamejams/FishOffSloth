package dev.lyze.fishoffsloth.level.players;

import dev.lyze.fishoffsloth.gamepads.VirtualGamepadGroup;
import dev.lyze.fishoffsloth.level.EntityWorld;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.collisionFilters.PlayerCollisionFilter;
import dev.lyze.fishoffsloth.level.entities.GravityEntity;
import lombok.Getter;
import lombok.Setter;

public class Player extends GravityEntity {
    @Getter private final boolean firstPlayer;

    @Setter private VirtualGamepadGroup gamepad;

    public Player(boolean firstPlayer, Level level) {
        super(0, 0, 75, 200, level, PlayerCollisionFilter.instance);

        this.firstPlayer = firstPlayer;
    }

    @Override
    public void update(EntityWorld world, float delta) {
        checkInput();
        super.update(world, delta);
    }

    private void checkInput() {
        wantsToMoveLeft = gamepad.getLeftPressed();
        wantsToMoveRight = gamepad.getRightPressed();

        wantsToJump = gamepad.isJumpJustPressed();
    }
}
