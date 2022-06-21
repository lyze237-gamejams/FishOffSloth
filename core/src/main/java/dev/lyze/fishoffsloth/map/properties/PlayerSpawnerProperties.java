package dev.lyze.fishoffsloth.map.properties;

import dev.lyze.fishoffsloth.map.MapProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerSpawnerProperties extends MapProperties {
    private boolean firstPlayer;
}
