package dev.lyze.fishoffsloth.map.properties;

import dev.lyze.fishoffsloth.map.MapSpawnerProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerSpawnerProperties extends MapSpawnerProperties {
    private int player;
}
