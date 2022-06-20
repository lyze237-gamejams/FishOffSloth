package dev.lyze.fishoffsloth.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.lyze.fishoffsloth.level.entities.PatrolEntity;
import dev.lyze.fishoffsloth.map.Map;
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

    @Getter private final Map map;

    public Level(TiledMap map) {
        this.map = new Map(this, map);
        this.map.initialize();

        this.entityWorld.addEntity(new PatrolEntity(180, 300, 75, 75, this));
    }

    public void update(float delta) {
        entityWorld.update(delta);
        lightWorld.update(delta);
    }

    public void render() {
        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);

        map.render(viewport);

        batch.setColor(Color.WHITE);
        batch.begin();
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
