package dev.lyze.fishoffsloth.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import lombok.experimental.UtilityClass;
import lombok.var;

@UtilityClass
public class PixmapUtils {
    public TextureRegion createTexture(int width, int height, Color color) {
        var pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();

        return new TextureRegion(new Texture(pixmap));
    }
}
