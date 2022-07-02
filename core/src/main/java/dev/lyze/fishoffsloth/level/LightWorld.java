package dev.lyze.fishoffsloth.level;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;
import lombok.var;

public class LightWorld {
    private final World lightWorld = new World(new Vector2(0, 0), true);
    private final Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
    private final RayHandler rayHandler = new RayHandler(lightWorld);
    @Getter private final LightWorldEntitySyncer syncer = new LightWorldEntitySyncer();

    public LightWorld() {
        rayHandler.setCulling(true);
        RayHandler.useDiffuseLight(true);
    }

    public Body createRectangle(float x, float y, float width, float height, BodyDef.BodyType type) {
        var bodyDef = new BodyDef();
        bodyDef.type = type;
        bodyDef.position.set(x + width / 2f, y + height / 2f);

        var shape = new PolygonShape();
        shape.setAsBox(width / 2f, height / 2f);

        var body = lightWorld.createBody(bodyDef);
        body.createFixture(shape, 0.0f);

        shape.dispose();

        return body;
    }

    public PointLight createPointLight(float x, float y, Color color, float distance, int rays) {
        var light = new PointLight(rayHandler, rays, color, distance, x, y);
        light.setSoft(false);

        return light;
    }

    public void setAmbientLight(Color color) {
        rayHandler.setAmbientLight(color);
    }

    public void update(float delta) {
        syncer.update(delta);
        lightWorld.step(delta, 1, 1);
        rayHandler.update();
    }

    public void render(Viewport viewport) {
        if (Gdx.app.getType() == Application.ApplicationType.WebGL)
            return;

        rayHandler.setCombinedMatrix(((OrthographicCamera) viewport.getCamera()));

        rayHandler.render();
    }

    public void debugRender(Viewport viewport) {
        box2DDebugRenderer.render(lightWorld, viewport.getCamera().combined);
    }

    public void resize(int width, int height, Viewport viewport) {
        rayHandler.resizeFBO((int) Math.ceil(width / 4f), (int) Math.ceil(height / 4f));
        rayHandler.useCustomViewport(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
    }
}
