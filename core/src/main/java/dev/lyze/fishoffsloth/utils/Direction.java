package dev.lyze.fishoffsloth.utils;

import com.badlogic.gdx.math.Vector2;
import lombok.Getter;

public enum Direction {
    Up(0, 1), Down(0, -1), Left(-1, 0), Right(1, 0);

    @Getter private final Vector2 value;

    Direction(int x, int y) {
        value = new Vector2(x, y);
    }
}
