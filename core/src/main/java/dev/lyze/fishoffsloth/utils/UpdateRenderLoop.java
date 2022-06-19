package dev.lyze.fishoffsloth.utils;

import lombok.var;

public class UpdateRenderLoop {
    private final UpdateInterface update;
    private final RenderInterface render;

    private float actualDeltaTime = 0.0f;
    private final float targetDeltaTime = 0.01f;
    private double currentTime = System.currentTimeMillis();
    private float accumulator = 0f;


    public UpdateRenderLoop(UpdateInterface update, RenderInterface render) {
        this.update = update;
        this.render = render;
    }

    public void updateAndRender() {
        var newTime = System.currentTimeMillis();
        var frameTime = (newTime - currentTime) / 1000f;
        accumulator += frameTime;
        currentTime = newTime;

        while (accumulator >= targetDeltaTime) {
            update.update(actualDeltaTime);

            accumulator -= targetDeltaTime;
            actualDeltaTime = targetDeltaTime;
        }

        render.render();
    }

    @FunctionalInterface
    public static interface UpdateInterface {
        void update(float delta);
    }

    public static interface RenderInterface {
        void render();
    }
}
