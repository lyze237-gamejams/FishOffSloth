package dev.lyze.fishoffsloth.level.entities.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.level.Level;

public class CoinTile extends Tile {
    private final TextureAtlas.AtlasRegion texture;

    public CoinTile(float x, float y, Level level) {
        super(x, y, 75, 75, level);

        texture = Statics.mainAtlas.findRegion("coin");
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }
}
