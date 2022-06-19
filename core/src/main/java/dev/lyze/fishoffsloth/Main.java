package dev.lyze.fishoffsloth;

import com.badlogic.gdx.Game;
import lombok.CustomLog;

@CustomLog
public class Main extends Game {
	@Override
	public void create() {
		setScreen(new FirstScreen());
	}
}