package dev.lyze.fishoffsloth.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.entities.tiles.GroundTile;
import dev.lyze.fishoffsloth.utils.OrthogonalTiledMapRendererBleeding;
import lombok.CustomLog;
import lombok.Getter;
import lombok.var;

@CustomLog
public class Map {
    @Getter private final TiledMap map;
    @Getter private Rectangle boundaries;

    private final Level level;
    private final OrthogonalTiledMapRendererBleeding renderer;
    private TiledMapTileLayer foregroundLayer;

    public Map(Level level, TiledMap map) {
        this.level = level;
        this.map = map;

        renderer = new OrthogonalTiledMapRendererBleeding(map, 1);
    }

    public void initialize() {
        new MapSpawnersManager(level, this).initialize();

        parseCollision();
        parseLights();
        setupBoundaries();
        parseAmbientLight();

        foregroundLayer = ((TiledMapTileLayer) map.getLayers().get("Foreground"));
    }

    private void parseAmbientLight() {
        level.getLightWorld().setAmbientLight(map.getProperties().get("AmbientColor", Color.RED, Color.class));
    }

    private void setupBoundaries() {
        var boundariesLayer = map.getLayers().get("Boundaries");
        var objects = boundariesLayer.getObjects();

        if (objects.getCount() > 1) {
            log.logInfo("Boundaries has multiple collision objects attached. Taking first only.");
        }
        else if (objects.getCount() == 0) {
            log.logError("Boundaries has no collision objects attached.");
            return;
        }

        boundaries = new Rectangle(((RectangleMapObject) objects.get(0)).getRectangle());
        boundaries.setPosition(boundaries.getX(), boundaries.getY());
        boundaries.setSize(boundaries.getWidth(), boundaries.getHeight());
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


    public void renderForegroundLayer() {
        renderer.begin();
        renderer.renderTileLayer(foregroundLayer);
        renderer.end();
    }

    public int getWidth() {
        return foregroundLayer.getWidth() * foregroundLayer.getTileWidth();
    }
}
