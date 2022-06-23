package dev.lyze.fishoffsloth.gamepads;

import com.badlogic.gdx.controllers.Controllers;
import dev.lyze.fishoffsloth.level.players.Player;
import lombok.var;

public class ControllerGamepad extends VirtualGamepad {
    private boolean jumpHeld;

    public ControllerGamepad(Player player, int playerNumber) {
        super(player, playerNumber);
    }

    @Override
    public void update(float delta) {
        if (Controllers.getControllers().size <= playerNumber)
            return;

        var controller = Controllers.getControllers().get(playerNumber);

        var mapping = controller.getMapping();

        if (!jumpHeld)
            if (jumpJustPressed = controller.getButton(mapping.buttonA))
                jumpHeld = true;

        if (!controller.getButton(mapping.buttonA))
            jumpHeld = false;

        leftPressed = controller.getAxis(mapping.axisLeftX) < 0 ? Math.abs(controller.getAxis(mapping.axisLeftX)) : 0;
        rightPressed = controller.getAxis(mapping.axisLeftX) > 0 ? Math.abs(controller.getAxis(mapping.axisLeftX)) : 0;
        shootPressed = controller.getButton(mapping.buttonR2) || controller.getButton(mapping.buttonR1);
    }

    @Override
    public void reset(float delta) {
        jumpJustPressed = false;
    }

    @Override
    public void vibrate(int durationInMs, float strength) {
        if (Controllers.getControllers().size <= playerNumber)
            return;

        var controller = Controllers.getControllers().get(playerNumber);

        if (!controller.canVibrate())
            return;

        controller.cancelVibration();
        controller.startVibration(durationInMs, strength);
    }
}
