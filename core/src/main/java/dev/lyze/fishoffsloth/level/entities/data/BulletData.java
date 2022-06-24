package dev.lyze.fishoffsloth.level.entities.data;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BulletData {
    private float width, height;

    private float speed;

    private Animation<TextureAtlas.AtlasRegion> animation;
    private float offsetX, offsetY;
    private float animationOffsetX;

    private float travelDistance;
    private long timeBetweenShots;

    private Color lightColor;
    private float lightDistance;
    @Builder.Default
    private int lightRays = 32;
    private float lightOffsetX, lightOffsetY;
}
