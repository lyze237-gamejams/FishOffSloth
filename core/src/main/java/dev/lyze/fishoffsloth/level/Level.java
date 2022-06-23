package dev.lyze.fishoffsloth.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.level.players.Players;
import dev.lyze.fishoffsloth.map.Map;
import dev.lyze.fishoffsloth.utils.PixmapUtils;
import lombok.CustomLog;
import lombok.Getter;
import space.earlygrey.shapedrawer.ShapeDrawer;

@CustomLog
public class Level {
    private final Viewport viewport = new ExtendViewport(1920, 1080, new GameCamera());
    private final SpriteBatch batch = new SpriteBatch();
    private final ShapeDrawer drawer = new ShapeDrawer(batch, PixmapUtils.createTexture(1, 1, Color.WHITE));
    private final BitmapFont debugFont = new BitmapFont();

    @Getter private final EntityWorld entityWorld = new EntityWorld();
    @Getter private final LightWorld lightWorld = new LightWorld();

    @Getter private final Map map;

    @Getter private final Players players;

    public Level(TiledMap map) {
        this.map = new Map(this, map);

        this.players = new Players(this);

        drawer.setDefaultLineWidth(4);
    }

    public void initialize() {
        this.map.initialize();

        for (int i = 0; i < 100; i++)
            ((GameCamera) viewport.getCamera()).update(players.getPlayer1().getPosition(), players.getPlayer2().getPosition(), map.getBoundaries(), 0.1f);
    }

    public void update(float delta) {
        players.update(delta);
        entityWorld.update(delta);
        lightWorld.update(delta);
        ((GameCamera) viewport.getCamera()).update(players.getPlayer1().getPosition(), players.getPlayer2().getPosition(), map.getBoundaries(), delta);
    }

    public void render() {
        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);

        map.render(viewport);

        batch.setColor(Color.WHITE);
        batch.begin();
        entityWorld.render(batch);
        batch.end();

        lightWorld.render(viewport);
        lightWorld.debugRender(viewport);

        batch.setColor(Color.WHITE);
        batch.begin();
        players.render(batch);
        batch.end();

        if (Statics.debug) {
            batch.setColor(Color.WHITE);
            batch.begin();
            entityWorld.debugRender(drawer, debugFont);
            players.debugRender(drawer, debugFont);
            batch.end();
        }
    }


    public void resize(int width, int height) {
        viewport.update(width, height);
        lightWorld.resize(width, height, viewport);
    }
}
