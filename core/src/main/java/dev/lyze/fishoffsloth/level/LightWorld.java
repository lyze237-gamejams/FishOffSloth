package dev.lyze.fishoffsloth.level;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.text.View;

public class LightWorld {
    private final World lightWorld = new World(new Vector2(0, 0), true);
    private final Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
    private final RayHandler rayHandler = new RayHandler(lightWorld);

    public LightWorld() {
        rayHandler.setCulling(true);
    }

    public void update(float delta) {
        lightWorld.step(delta, 1, 1);
        rayHandler.update();
    }

    public void render(Viewport viewport) {
        rayHandler.setCombinedMatrix(((OrthographicCamera) viewport.getCamera()));
    }

    public void debugRender(Viewport viewport) {
        box2DDebugRenderer.render(lightWorld, viewport.getCamera().combined);
    }

    public void resize(int width, int height, Viewport viewport) {
        rayHandler.resizeFBO((int) Math.ceil(width / 4f), (int) Math.ceil(height / 4f));
        rayHandler.useCustomViewport(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
    }
}
