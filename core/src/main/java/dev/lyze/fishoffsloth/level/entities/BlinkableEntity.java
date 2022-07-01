package dev.lyze.fishoffsloth.level.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.lyze.fishoffsloth.level.EntityWorld;
import dev.lyze.fishoffsloth.level.Level;
import lombok.Getter;

public class BlinkableEntity extends Entity {
    @Getter private float blinkTime;
    private final float blinkMaxTime = 0.3f;

    public BlinkableEntity(float x, float y, float width, float height, Level level) {
        super(x, y, width, height, level);
    }

    @Override
    public void update(EntityWorld world, float delta) {
        checkBlink(delta);
        super.update(world, delta);
    }

    private void checkBlink(float delta) {
        if (blinkTime > 0)
            blinkTime -= delta;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (blinkTime > 0) {
            int blinkTimeMs = (int) (blinkTime * 1000) / 100;
            batch.setColor(1, 1, 1, blinkTimeMs % 2);
        }

        super.render(batch);
    }

    public void startBlink() {
        blinkTime = blinkMaxTime;
    }
}
