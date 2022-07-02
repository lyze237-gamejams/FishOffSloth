package dev.lyze.fishoffsloth.level.entities.tiles;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.level.EntityWorld;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.collisionFilters.EnemyExplosionCollisionFilter;
import dev.lyze.fishoffsloth.level.entities.enemies.EnemyExplosion;
import lombok.var;

public class CoinTile extends Tile {
    private final TextureAtlas.AtlasRegion texture;

    public CoinTile(float x, float y, Level level) {
        super(x, y, 75, 75, level);

        texture = Statics.mainAtlas.findRegion("carlcoinbig");
    }

    @Override
    public void update(EntityWorld world, float delta) {
        if (collidesWithPlayer(world))
            despawn();
    }

    private boolean collidesWithPlayer(EntityWorld world) {
        var response = world.getWorld().move(item, position.x, position.y, EnemyExplosionCollisionFilter.instance);
        return response.projectedCollisions.size() > 0;
    }

    private void despawn() {
        Statics.playSound(Statics.coinTotalWin1, Statics.coinTotalWin2);

        for (int i = 0; i < (Gdx.app.getType() == Application.ApplicationType.WebGL ? 3 : 25); i++)
            level.getEntityWorld().addEntity(new EnemyExplosion(position.x + width / 2f, position.y + height / 2f, level));

        level.removeEntity(this);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }
}
