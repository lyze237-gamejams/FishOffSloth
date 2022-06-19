package dev.lyze.fishoffsloth;

import com.badlogic.gdx.Game;

public class Main extends Game {
	@Override
	public void create() {
		setScreen(new MainMenuScreen());
	}
}
/*
@CustomLog
public class Main extends ManagedGame<ManagedScreen, ScreenTransition> {
	private SpriteBatch batch;

	@Override
	public void create() {
		super.create();

		log.logInfo("Hello World!");

		setupScreens();
	}

	private void setupScreens() {
		batch = new SpriteBatch();

		screenManager.addScreen(MainMenuScreen.class.getName(), new MainMenuScreen());

		screenManager.addScreenTransition(BlendingTransition.class.getName(), new BlendingTransition(batch, 1F, Interpolation.pow2In));
		screenManager.addScreenTransition(SlidingOutTransition.class.getName(), new SlidingOutTransition(batch, SlidingDirection.DOWN, 0.35F));
		screenManager.addScreenTransition(HorizontalSlicingTransition.class.getName(), new HorizontalSlicingTransition(batch, 5, 1F, Interpolation.exp5In));

		screenManager.pushScreen(MainMenuScreen.class.getName(), null);
	}

	@Override
	public void resize(int width, int height) {
		if (width == 0 && height == 0)
			return;

		super.resize(width, height);

		batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
	}
}
*/