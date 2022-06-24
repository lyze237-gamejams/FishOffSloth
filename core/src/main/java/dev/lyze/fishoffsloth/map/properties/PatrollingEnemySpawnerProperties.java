package dev.lyze.fishoffsloth.map.properties;

import dev.lyze.fishoffsloth.map.MapSpawnerProperties;
import dev.lyze.fishoffsloth.utils.Direction;
import dev.lyze.fishoffsloth.utils.Sloth;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatrollingEnemySpawnerProperties extends MapSpawnerProperties {
    private Direction direction;
    private Sloth sloth;
}
