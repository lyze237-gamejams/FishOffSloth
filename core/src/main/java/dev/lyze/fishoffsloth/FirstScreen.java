package dev.lyze.fishoffsloth;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class FirstScreen extends ScreenAdapter {
	private final ExtendViewport viewport = new ExtendViewport(1920, 1080);
	private final SpriteBatch batch = new SpriteBatch();
	private final Texture lyze, sunny;

	public FirstScreen() {
		lyze = new Texture("lyzeidle.png");
		sunny = new Texture("sunnyidle.png");
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(Color.TEAL);
		viewport.apply();

		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();
		batch.draw(lyze, 0, 400);
		batch.draw(sunny, 300, 400);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}
}