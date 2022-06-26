package dev.lyze.fishoffsloth.level.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import dev.lyze.fishoffsloth.level.EntityWorld;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.collisionFilters.DeadCollisionFilter;

public class DeadEntity extends GravityEntity {
    private final float rotationSpeed = 350f;
    private final TextureAtlas.AtlasRegion lastFrame;

    private float destroyAfter = 10f;

    public DeadEntity(float x, float y, float width, float height, TextureAtlas.AtlasRegion lastFrame, Level level) {
        super(x, y, width, height, level, DeadCollisionFilter.instance);

        this.lastFrame = lastFrame;

        velocity.set(MathUtils.random(1f, 3f) * (MathUtils.randomBoolean() ? 1 : -1), MathUtils.random(10f, 15f));
        getTintColor().set(1, 1, 1, 0.5f);
    }

    @Override
    public void update(EntityWorld world, float delta) {
        if ((destroyAfter -= delta) < 0)
            die();

        setRotation(getRotation() + rotationSpeed * delta);

        applyGravity(delta);
        checkCollisionsAndApplyVelocity(world, delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        renderFrame(batch, lastFrame);
    }
}
