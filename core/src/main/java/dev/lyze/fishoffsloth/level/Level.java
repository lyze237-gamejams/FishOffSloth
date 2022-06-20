package dev.lyze.fishoffsloth.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.lyze.fishoffsloth.utils.PixmapUtils;
import lombok.CustomLog;
import lombok.Getter;
import space.earlygrey.shapedrawer.ShapeDrawer;

@CustomLog
public class Level {
    private final Viewport viewport = new ExtendViewport(1920, 1080);
    private final SpriteBatch batch = new SpriteBatch();
    private final ShapeDrawer drawer = new ShapeDrawer(batch, PixmapUtils.createTexture(1, 1, Color.WHITE));

    @Getter private final EntityWorld entityWorld = new EntityWorld();
    @Getter private final LightWorld lightWorld = new LightWorld();

    private final TextureAtlas atlas = new TextureAtlas("atlases/main.atlas");
    private final Animation<TextureAtlas.AtlasRegion> sunnyIdle, lyzeIdle;

    private float animationTime;

    public Level() {
        sunnyIdle = new Animation<>(0.15f, atlas.findRegions("players/sunny/idle"), Animation.PlayMode.LOOP_PINGPONG);
        lyzeIdle = new Animation<>(0.15f, atlas.findRegions("players/lyze/idle"), Animation.PlayMode.LOOP_PINGPONG);
    }

    public void update(float delta) {
        animationTime += delta;

        entityWorld.update(delta);
        lightWorld.update(delta);
    }

    public void render() {
        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.setColor(Color.WHITE);
        batch.begin();
        batch.draw(sunnyIdle.getKeyFrame(animationTime), 100, 100);
        batch.draw(lyzeIdle.getKeyFrame(animationTime), 300, 100);
        entityWorld.render(batch);
        batch.end();

        lightWorld.render(viewport);
        lightWorld.debugRender(viewport);

        batch.setColor(Color.WHITE);
        batch.begin();
        entityWorld.debugRender(drawer);
        batch.end();
    }


    public void resize(int width, int height) {
        viewport.update(width, height);
        lightWorld.resize(width, height, viewport);
    }
}
