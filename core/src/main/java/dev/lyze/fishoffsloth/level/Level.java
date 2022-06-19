package dev.lyze.fishoffsloth.level;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dongbat.jbump.World;
import dev.lyze.fishoffsloth.level.entities.Entity;
import dev.lyze.fishoffsloth.utils.PixmapUtils;
import lombok.CustomLog;
import lombok.Getter;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;

@CustomLog
public class Level {
    private final Viewport viewport = new ExtendViewport(1920, 1080);
    private final SpriteBatch batch = new SpriteBatch();
    private final ShapeDrawer drawer = new ShapeDrawer(batch, PixmapUtils.createTexture(1, 1, Color.WHITE));

    @Getter
    private final EntityWorld entityWorld = new EntityWorld();

    private final com.badlogic.gdx.physics.box2d.World lightWorld = new com.badlogic.gdx.physics.box2d.World(new Vector2(0, 0), true);
    private final Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
    private final RayHandler rayHandler = new RayHandler(lightWorld);

    public Level() {
        rayHandler.setCulling(true);
    }

    public void update(float delta) {
        entityWorld.update(delta);

        lightWorld.step(delta, 6, 2);

        rayHandler.update();
    }

    public void render() {
        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        rayHandler.setCombinedMatrix(((OrthographicCamera) viewport.getCamera()));

        batch.setColor(Color.WHITE);
        batch.begin();
        entityWorld.render(batch);
        batch.end();

        rayHandler.render();

        box2DDebugRenderer.render(lightWorld, viewport.getCamera().combined);

        batch.setColor(Color.WHITE);
        batch.begin();
        entityWorld.debugRender(drawer);
        batch.end();
    }


    public void resize(int width, int height) {
        viewport.update(width, height);

        rayHandler.resizeFBO((int) Math.ceil(width / 4f), (int) Math.ceil(height / 4f));
        rayHandler.useCustomViewport(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
    }
}
