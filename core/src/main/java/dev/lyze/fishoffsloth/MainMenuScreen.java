package dev.lyze.fishoffsloth;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.CustomLog;
import lombok.var;

@CustomLog
public class MainMenuScreen extends ScreenAdapter {
	private Viewport viewport;
	private SpriteBatch batch;
	private Texture texture;

	private World world;
	private Box2DDebugRenderer box2DDebugRenderer;
	private RayHandler rayHandler;

	public MainMenuScreen() {
		batch = new SpriteBatch();
		texture = new Texture("lyzeidle.png");

		viewport = new FitViewport(500, 500);

		world = new World(new Vector2(0, 0), true);
		box2DDebugRenderer = new Box2DDebugRenderer();

		rayHandler = new RayHandler(world);

		new PointLight(rayHandler, 500, new Color(1, 1, 1, 1), 500, 250, 200);

		var bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;
		bodyDef.position.set(0, 300);
		var body = world.createBody(bodyDef);
		var shape = new PolygonShape();
		shape.setAsBox(10000, 20);
		body.createFixture(shape, 0.0f);
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(Color.BLACK);

		viewport.apply();

		world.step(delta, 6, 2);

		/*
		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();
		batch.draw(texture, 20, 20);
		batch.end();
		 */

		rayHandler.setCombinedMatrix(((OrthographicCamera) viewport.getCamera()));
		rayHandler.updateAndRender();

		box2DDebugRenderer.render(world, viewport.getCamera().combined);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		rayHandler.useCustomViewport(viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
	}
}