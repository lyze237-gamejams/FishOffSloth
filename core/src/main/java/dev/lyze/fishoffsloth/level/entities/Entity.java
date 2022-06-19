package dev.lyze.fishoffsloth.level.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Item;
import com.dongbat.jbump.World;
import dev.lyze.fishoffsloth.level.Level;
import lombok.Getter;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Entity {
    protected final Level level;

    @Getter
    protected Vector2 position;
    @Getter
    protected float width, height;

    @Getter
    protected Item<Entity> item;

    public Entity(float x, float y, float width, float height, Level level) {
        this.level = level;

        position = new Vector2(x, y);
        this.width = width;
        this.height = height;
    }

    public void update(World<Entity> world, float delta) {
    }

    public void render(SpriteBatch batch) {
    }

    public void debugRender(ShapeDrawer shapes) {
        shapes.rectangle(position.x, position.y, width, height);
    }

    public void addToWorld(World<Entity> world) {
        world.add(item = new Item<>(this), this.position.x, this.position.y, this.width, this.height);
    }
}
