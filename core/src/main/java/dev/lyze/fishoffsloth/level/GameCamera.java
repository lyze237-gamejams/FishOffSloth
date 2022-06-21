package dev.lyze.fishoffsloth.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import lombok.var;

public class GameCamera extends OrthographicCamera {
    private final float movementSpeed = 5f;
    private final float zoomSpeed = 0.5f;

    private final Vector3 oldPosition = new Vector3();
    private float oldZoom = 0;

    public void update(Vector2 pos1, Vector2 pos2, Rectangle bounds, float delta) {
        oldZoom = zoom;
        oldPosition.set(position);

        this.lerpToPlayers(pos1, pos2, delta);
        this.zoomToPlayers(pos1, pos2, delta);
        if (this.keepInBoundaries(bounds)) {
            zoom = oldZoom;
            position.set(oldPosition);
        }

        this.update();
    }

    private final Vector3 firstPlayerViewport = new Vector3();
    private final Vector3 secondPlayerViewport = new Vector3();
    public void zoomToPlayers(Vector2 pos1, Vector2 pos2, float delta) {
        firstPlayerViewport.set(pos1.x / Gdx.graphics.getWidth(), pos1.y / Gdx.graphics.getHeight(), 0);
        secondPlayerViewport.set(pos2.x / Gdx.graphics.getWidth(), pos2.y / Gdx.graphics.getHeight(), 0);

        this.project(firstPlayerViewport);
        this.project(secondPlayerViewport);

        var viewportDist = firstPlayerViewport.dst(secondPlayerViewport);
        if (viewportDist > 0.8f) {
            this.zoom = this.zoom + zoomSpeed * delta;
        }
        if (viewportDist < 0.7f) {
            this.zoom = this.zoom - zoomSpeed * delta;
        }
        if (this.zoom < 0.8f)
            this.zoom = 0.8f;
    }

    public void lerpToPlayers(Vector2 pos1, Vector2 pos2, float delta) {
        var avgX = (pos1.x + pos2.x) / 2f;
        var avgY = (pos1.y + pos2.y) / 2f;

        this.position.x = this.position.x + (avgX - this.position.x) * movementSpeed * delta;
        this.position.y = this.position.y + (avgY - this.position.y) * movementSpeed * delta;
    }

    public boolean keepInBoundaries(Rectangle boundaries) {
        var halfViewportWidth = (this.viewportWidth * this.zoom) / 2f;

        if (viewportWidth * this.zoom > boundaries.getWidth())
            return true;

        this.position.x = MathUtils.clamp(this.position.x, boundaries.getX() + halfViewportWidth, boundaries.getX() + boundaries.getWidth() - halfViewportWidth);
        return false;
    }
}

