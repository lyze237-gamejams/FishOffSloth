package dev.lyze.fishoffsloth.map;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.lyze.fishoffsloth.level.EntityWorld;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.LightWorld;
import dev.lyze.fishoffsloth.level.entities.tiles.GroundTile;
import dev.lyze.fishoffsloth.utils.OrthogonalTiledMapRendererBleeding;
import lombok.CustomLog;
import lombok.Getter;
import lombok.var;

@CustomLog
public class Map {
    @Getter private final TiledMap map;

    private final Level level;
    private final OrthogonalTiledMapRenderer renderer;

    public Map(Level level, TiledMap map) {
        this.level = level;
        this.map = map;

        renderer = new OrthogonalTiledMapRendererBleeding(map, 1);
    }

    public void initialize() {
        parseCollision();
        parseLights();
    }

    private void parseLights() {
        var lightWorld = level.getLightWorld();

        var layer = map.getLayers().get("Lights");

        for (var object : layer.getObjects()) {
            if (!(object instanceof RectangleMapObject)) {
                log.logError("Map Object on Lights Layer is not a rectangle");
                continue;
            }

            var rectangleMapObject = (RectangleMapObject) object;
            var rectangle = rectangleMapObject.getRectangle();

            var color = rectangleMapObject.getProperties().get("color", Color.WHITE, Color.class);
            var distance = rectangleMapObject.getProperties().get("distance", float.class);
            var rays = rectangleMapObject.getProperties().get("rays", 32, int.class);
            lightWorld.createPointLight(rectangle.x, rectangle.y, color, distance, rays);
        }
    }

    private void parseCollision() {
        var entityWorld = level.getEntityWorld();
        var lightWorld = level.getLightWorld();

        var layer = map.getLayers().get("Collisions");
        for (var object : layer.getObjects()) {
            if (!(object instanceof RectangleMapObject)) {
                log.logError("Map Object on Collisions Layer is not a rectangle");
                continue;
            }

            var rectangleMapObject = (RectangleMapObject) object;
            var rectangle = rectangleMapObject.getRectangle();

            entityWorld.addStaticEntity(new GroundTile(rectangle.x, rectangle.y, rectangle.width, rectangle.height, level));
            lightWorld.createRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height, BodyDef.BodyType.StaticBody);
        }
    }

    public void render(Viewport viewport) {
        renderer.setView(((OrthographicCamera) viewport.getCamera()));
        renderer.render();
    }
}
