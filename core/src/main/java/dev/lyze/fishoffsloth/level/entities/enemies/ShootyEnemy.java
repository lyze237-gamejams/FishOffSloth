package dev.lyze.fishoffsloth.level.entities.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.level.EntityWorld;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.collisionFilters.PatrollingEnemyCollisionFilter;
import dev.lyze.fishoffsloth.level.entities.ShooterEntity;
import dev.lyze.fishoffsloth.level.entities.data.BulletData;
import dev.lyze.fishoffsloth.level.players.Player;
import dev.lyze.fishoffsloth.utils.Direction;
import dev.lyze.fishoffsloth.utils.Sloth;
import lombok.var;

public class ShootyEnemy extends ShooterEntity {
    private Direction direction;

    public ShootyEnemy(float x, float y, Direction direction, Sloth sloth, Level level) {
        super(x, y, 75, 200, BulletData.builder()
                .animation(new Animation<>(0.2f, Statics.mainAtlas.findRegions("enemies/bullet"), Animation.PlayMode.LOOP))
                .width(40).height(20)
                .lightColor(new Color(1, 1, 1, 0.4f))
                .offsetX(150).offsetY(87)
                .animationOffsetX(100)
                .travelDistance(900)
                .enemy(true)
                .damage(1)
                .timeBetweenShots(1500)
                .lightDistance(50)
                .lightOffsetX(-5).lightOffsetY(10)
                .build(), level, PatrollingEnemyCollisionFilter.instance);

        setDoDeathAnimation(true);
        
        this.direction = direction;
        if (direction == Direction.Left && isFacingRight)
            flip();

        Animation<TextureAtlas.AtlasRegion> animation = null;
        switch (sloth) {
            case Clogg:
                animation = new Animation<>(0.15f, Statics.mainAtlas.findRegions("enemies/clogg/move"), Animation.PlayMode.LOOP);
                break;
            case Beauty:
                animation = new Animation<>(0.15f, Statics.mainAtlas.findRegions("enemies/beauty/move"), Animation.PlayMode.LOOP);
                break;
        }

        setIdle(animation);
        setRun(animation);

        setAnimationXOffset(-20);
        setAnimationYOffset(-10f);
    }

    @Override
    public void update(EntityWorld world, float delta) {
        float closestPlayerDistance = Float.MAX_VALUE;
        Player closestPlayer = null;

        for (var player : level.getPlayers().getPlayers()) {
            var distance = player.getPosition().dst(position);

            if (distance < closestPlayerDistance) {
                closestPlayerDistance = distance;
                closestPlayer = player;
            }
        }

        if (closestPlayer != null) {
            if (closestPlayer.getPosition().x < position.x && isFacingRight) {
                flip();
            } else if (closestPlayer.getPosition().x > position.x && !isFacingRight) {
                flip();
            }
        }

        wantsToShoot = closestPlayerDistance < getBulletData().getTravelDistance();

        super.update(world, delta);
    }

    @Override
    protected void die(Direction from) {
        super.die(from);

        for (int i = 0; i < 25; i++)
            level.getEntityWorld().addEntity(new EnemyExplosion(position.x + width / 2f, position.y, level));
    }
}
