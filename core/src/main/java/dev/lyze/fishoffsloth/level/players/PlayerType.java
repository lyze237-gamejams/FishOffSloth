package dev.lyze.fishoffsloth.level.players;

import lombok.Getter;

public enum PlayerType {
    LYZE("players/lyze/select"), SUNNY("players/sunny/select"), BOTH("enemies/clogg/move");

    @Getter private final String imagePath;

    PlayerType(String imagePath) {
        this.imagePath = imagePath;
    }
}
