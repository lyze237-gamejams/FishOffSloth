package dev.lyze.fishoffsloth.level.entities;

import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.CollisionFilter;
import dev.lyze.fishoffsloth.level.EntityWorld;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.utils.Direction;
import lombok.Getter;
import lombok.Setter;

public class ShooterEntity extends GravityEntity {
    protected boolean wantsToShoot;

    @Setter
    private float bulletDistance = 800;
    @Setter
    private long timeBetweenShots = 800;
    private long lastShot = System.currentTimeMillis();

    @Getter private Vector2 bulletOffset = new Vector2();

    public ShooterEntity(float x, float y, float width, float height, Level level, CollisionFilter collisionFilter) {
        super(x, y, width, height, level, collisionFilter);
    }

    @Override
    public void update(EntityWorld world, float delta) {
        checkShooting();

        super.update(world, delta);
    }

    private void checkShooting() {
        if (!wantsToShoot)
            return;

        if (System.currentTimeMillis() - lastShot < timeBetweenShots)
            return;

        level.getEntityWorld().addEntity(new BulletEntity(position.x + width / 2f + bulletOffset.x * (isFacingRight ? 1 : -1), position.y + bulletOffset.y, bulletDistance, isFacingRight ? Direction.Right : Direction.Left, level));
        lastShot = System.currentTimeMillis();
    }
}
