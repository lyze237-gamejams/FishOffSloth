package dev.lyze.fishoffsloth.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dongbat.jbump.World;
import dev.lyze.fishoffsloth.level.entities.Entity;
import lombok.Getter;
import lombok.var;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;

public class EntityWorld {
    @Getter
    private final World<Entity> world = new World<>(4);

    @Getter
    private final ArrayList<Entity> entities = new ArrayList<>();
    private final ArrayList<Entity> entitiesToAdd = new ArrayList<>();
    private final ArrayList<Entity> entitiesToRemove = new ArrayList<>();

    public EntityWorld() {

    }

    public void addEntity(Entity entity) {
        entitiesToAdd.add(entity);
        entity.addToWorld(world);
    }

    public void addStaticEntity(Entity entity) {
        entity.addToWorld(world);
    }

    public void removeEntity(Entity entity) {
        entitiesToRemove.add(entity);
    }

    public void update(float delta) {
        entities.forEach(e -> e.update(this, delta));

        if (entitiesToAdd.size() > 0) {
            entities.addAll(entitiesToAdd);
            entitiesToAdd.clear();
        }

        for (var e : entitiesToRemove) {
            entities.remove(e);
            world.remove(e.getItem());
        }
        entitiesToRemove.clear();
    }

    public void render(SpriteBatch batch) {
        entities.forEach(e -> e.render(batch));
    }

    public void debugRender(ShapeDrawer drawer, BitmapFont font) {
        for (Entity e : entities) {
            drawer.setColor(Color.WHITE);
            e.debugRender(drawer, font);
        }
    }
}
