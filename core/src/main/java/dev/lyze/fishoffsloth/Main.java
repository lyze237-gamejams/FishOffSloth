package dev.lyze.fishoffsloth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import de.eskalon.commons.screen.transition.impl.HorizontalSlicingTransition;
import de.eskalon.commons.screen.transition.impl.SlidingDirection;
import de.eskalon.commons.screen.transition.impl.SlidingOutTransition;
import dev.lyze.fishoffsloth.screens.GameScreen;
import dev.lyze.fishoffsloth.screens.MainMenuScreen;
import dev.lyze.fishoffsloth.screens.SplashScreen;
import lombok.CustomLog;

@CustomLog
public class Main extends ManagedGame<ManagedScreen, ScreenTransition> {
	private SpriteBatch batch;

	@Override
	public void create() {
		super.create();

		log.logInfo("Hello World!");

		setupScreens();

		Statics.music.setVolume(0.3f);
		Statics.music.setLooping(true);
		Statics.music.play();
	}

	@Override
	public void render() {
		super.render();

		if (Gdx.input.isKeyJustPressed(Input.Keys.F9))
			Statics.debug = !Statics.debug;
	}

	private void setupScreens() {
		batch = new SpriteBatch();

		screenManager.addScreen(SplashScreen.class.getName(), new SplashScreen());
		screenManager.addScreen(MainMenuScreen.class.getName(), new MainMenuScreen());
		screenManager.addScreen(GameScreen.class.getName(), new GameScreen());

		screenManager.addScreenTransition(BlendingTransition.class.getName(), new BlendingTransition(batch, 1F, Interpolation.pow2In));
		screenManager.addScreenTransition(SlidingOutTransition.class.getName(), new SlidingOutTransition(batch, SlidingDirection.DOWN, 0.35F));
		screenManager.addScreenTransition(HorizontalSlicingTransition.class.getName(), new HorizontalSlicingTransition(batch, 5, 1F, Interpolation.exp5In));

		screenManager.pushScreen(SplashScreen.class.getName(), null);
	}

	@Override
	public void resize(int width, int height) {
		if (width == 0 && height == 0)
			return;

		super.resize(width, height);

		batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
	}
}