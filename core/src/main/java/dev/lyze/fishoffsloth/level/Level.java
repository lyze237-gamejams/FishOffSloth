package dev.lyze.fishoffsloth.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gempukku.libgdx.lib.camera2d.FocusCameraController;
import com.gempukku.libgdx.lib.camera2d.constraint.FitAllCameraConstraint;
import com.gempukku.libgdx.lib.camera2d.constraint.LockedToCameraConstraint;
import com.gempukku.libgdx.lib.camera2d.constraint.MinimumViewportCameraConstraint;
import com.gempukku.libgdx.lib.camera2d.focus.EntityFocus;
import com.gempukku.libgdx.lib.camera2d.focus.FitAllCameraFocus;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.level.entities.Entity;
import dev.lyze.fishoffsloth.level.entities.EntityPositionProvider;
import dev.lyze.fishoffsloth.level.players.PlayerType;
import dev.lyze.fishoffsloth.level.players.Players;
import dev.lyze.fishoffsloth.map.Map;
import dev.lyze.fishoffsloth.utils.PixmapUtils;
import lombok.CustomLog;
import lombok.Getter;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.Arrays;

@CustomLog
public class Level {
    private final Viewport viewport = new ExtendViewport(1920, 1080);
    private final SpriteBatch batch = new SpriteBatch();
    private final ShapeDrawer drawer = new ShapeDrawer(batch, PixmapUtils.createTexture(1, 1, Color.WHITE));
    private final BitmapFont debugFont = new BitmapFont();

    @Getter private final EntityWorld entityWorld = new EntityWorld();
    @Getter private final LightWorld lightWorld = new LightWorld();

    @Getter private final Map map;
    @Getter private final Background background = new Background();

    @Getter private final Players players;

    private FocusCameraController cameraController;

    public Level(PlayerType playerType, TiledMap map) {
        this.map = new Map(this, map);

        this.players = new Players(playerType, this);

        drawer.setDefaultLineWidth(4);
    }

    public void initialize() {
        this.map.initialize();

        var cameraFoci = Arrays.stream(players.getPlayers().shrink()).map(player -> new EntityFocus(new EntityPositionProvider(player))).toArray(EntityFocus[]::new);
        cameraController = new FocusCameraController(viewport.getCamera(),
                new FitAllCameraFocus(cameraFoci),
                new LockedToCameraConstraint(new Vector2(0.5f, 0.5f)),
                new FitAllCameraConstraint(new Rectangle(0.2f, 0.2f, 0.6f, 0.6f), cameraFoci),
                new MinimumViewportCameraConstraint(1020, 1080));
    }

    public void update(float delta) {
        players.update(delta);
        entityWorld.update(delta);
        lightWorld.update(delta);
        cameraController.update(delta);
    }

    public void render() {
        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.setColor(Color.WHITE);
        batch.begin();
        background.draw(batch, drawer, map);
        batch.end();

        map.render(viewport);

        batch.begin();
        entityWorld.render(batch);
        batch.end();

        lightWorld.render(viewport);
        if (Statics.debug)
            lightWorld.debugRender(viewport);

        batch.setColor(Color.WHITE);
        batch.begin();
        players.render(batch);
        batch.end();

        map.renderForegroundLayer();

        if (Statics.debug) {
            batch.setColor(Color.WHITE);
            batch.begin();
            debugFont.draw(batch, "Fps: " + Gdx.graphics.getFramesPerSecond(), 16, 16);
            entityWorld.debugRender(drawer, debugFont);
            players.debugRender(drawer, debugFont);
            batch.end();
        }
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        lightWorld.resize(width, height, viewport);
    }

    public void removeEntity(Entity entity) {
        entityWorld.removeEntity(entity);
        lightWorld.getSyncer().removeEntity(entity);
    }
}
