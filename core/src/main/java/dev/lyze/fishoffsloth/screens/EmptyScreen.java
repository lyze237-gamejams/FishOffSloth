package dev.lyze.fishoffsloth.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import dev.lyze.fishoffsloth.utils.ManagedScreenAdapter;

public class EmptyScreen extends ManagedScreenAdapter  {
    @Override
    protected void create() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.TEAL);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            ((ManagedGame) Gdx.app.getApplicationListener()).getScreenManager().pushScreen(TestScreen.class.getName(), BlendingTransition.class.getName());
        }
    }

    @Override
    public void resize(int width, int height) {

    }
}
