package dev.lyze.fishoffsloth.map;

import com.badlogic.gdx.math.GridPoint2;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.map.properties.PlayerSpawnerProperties;
import lombok.CustomLog;
import lombok.var;

import java.util.HashMap;

@CustomLog
public class PlayerSpawner extends MapSpawner<PlayerSpawnerProperties> {
    public PlayerSpawner(Level level, Map map) {
        super(level, map, PlayerSpawnerProperties.class);
    }

    @Override
    protected void spawnInternal(float x, float y, PlayerSpawnerProperties data) {
        log.logInfo("Spawning player: First player: " + data.isFirstPlayer() + " at " + x + " /" + y);

        var player = data.isFirstPlayer() ? level.getPlayers().getPlayer1() : level.getPlayers().getPlayer2();
        player.getPosition().set(x, y);
        level.getEntityWorld().getWorld().update(player.getItem(), x, y);
    }
}
