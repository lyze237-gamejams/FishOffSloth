package dev.lyze.fishoffsloth.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.map.Map;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Background {
    private final TextureAtlas.AtlasRegion background;
    private final Color topColor = new Color(133 / 255f, 226 / 255f, 252 / 255f, 1f), bottomColor = new Color(216 / 255f, 245 / 255f, 254 / 255f, 1f);

    public Background() {
        background = Statics.mainAtlas.findRegion("background");
    }

    public void draw(SpriteBatch batch, ShapeDrawer drawer, Map map) {
        drawer.filledRectangle(-map.getWidth(), 0, map.getWidth() * 2, background.getRegionHeight() * 10, topColor);
        for (int x = -map.getWidth(); x <= map.getWidth(); x += background.getRegionWidth()) {
            batch.draw(background, x, 0);
        }
        drawer.filledRectangle(-map.getWidth(), 0, map.getWidth() * 2, -background.getRegionHeight() * 10, bottomColor);
    }
}
