package dev.lyze.fishoffsloth.map.properties;

import dev.lyze.fishoffsloth.map.MapSpawnerProperties;
import dev.lyze.fishoffsloth.utils.Direction;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatrolDirectionChangeSpawnerProperties extends MapSpawnerProperties {
    private Direction direction;
}
