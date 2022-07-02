package dev.lyze.fishoffsloth.level.players;

import lombok.Getter;

public enum PlayerType {
    LYZE("players/lyze/idle", 1), SUNNY("players/sunny/idle", 1), BOTH("enemies/clogg/move", 1);

    @Getter private final String imagePath;
    @Getter private final int index;

    PlayerType(String imagePath, int index) {
        this.imagePath = imagePath;
        this.index = index;
    }
}
