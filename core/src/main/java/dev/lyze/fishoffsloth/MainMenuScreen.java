package dev.lyze.fishoffsloth;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import dev.lyze.fishoffsloth.utils.ManagedScreenAdapter;
import lombok.CustomLog;

@CustomLog
public class MainMenuScreen extends ManagedScreenAdapter {
	@Override
	protected void create() {

	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(Color.TEAL);
	}

	@Override
	public void resize(int width, int height) {

	}
}