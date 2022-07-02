package dev.lyze.fishoffsloth.level.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.dongbat.jbump.CollisionFilter;
import dev.lyze.fishoffsloth.level.EntityWorld;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.entities.data.BulletData;
import dev.lyze.fishoffsloth.utils.Direction;
import lombok.Getter;
import lombok.Setter;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class ShooterEntity extends GravityEntity {
    protected boolean wantsToShoot;

    private long lastShot = System.currentTimeMillis();

    @Getter private final BulletData bulletData;

    private long showShootAnimation = 200;

    @Getter @Setter private Animation<TextureAtlas.AtlasRegion> shoot;

    public ShooterEntity(float x, float y, float width, float height, BulletData bulletData, Level level, CollisionFilter collisionFilter) {
        super(x, y, width, height, level, collisionFilter);

        this.bulletData = bulletData;
    }

    @Override
    public void update(EntityWorld world, float delta) {
        checkShooting();

        super.update(world, delta);
    }

    @Override
    protected void updateAnimation() {
        super.updateAnimation();

        if (getCurrentAnimation() == getIdle() && System.currentTimeMillis() - lastShot < showShootAnimation)
            setAnimation(shoot);
    }

    private void checkShooting() {
        if (!wantsToShoot)
            return;

        if (System.currentTimeMillis() - lastShot < bulletData.getTimeBetweenShots())
            return;

        shoot();
    }

    protected void shoot() {
        level.getEntityWorld().addEntity(new BulletEntity(position.x + width / 2f + bulletData.getOffsetX() * (isFacingRight ? 1 : -1) + (isFacingRight ? 0 : -bulletData.getWidth()), position.y + bulletData.getOffsetY(), isFacingRight ? Direction.Right : Direction.Left, bulletData, level));
        lastShot = System.currentTimeMillis();
    }

    @Override
    public void debugRender(ShapeDrawer drawer, BitmapFont font) {
        super.debugRender(drawer, font);

        drawer.setColor(Color.RED);
        drawer.circle(position.x + width / 2f + bulletData.getOffsetX() * (isFacingRight ? 1 : -1), position.y + bulletData.getOffsetY(), 10);
    }
}
