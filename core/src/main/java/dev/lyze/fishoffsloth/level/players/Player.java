package dev.lyze.fishoffsloth.level.players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Collision;
import dev.lyze.fishoffsloth.Statics;
import dev.lyze.fishoffsloth.gamepads.VirtualGamepadGroup;
import dev.lyze.fishoffsloth.level.EntityWorld;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.level.collisionFilters.PlayerCollisionFilter;
import dev.lyze.fishoffsloth.level.entities.ShooterEntity;
import dev.lyze.fishoffsloth.level.entities.data.BulletData;
import dev.lyze.fishoffsloth.level.entities.tiles.CoinTile;
import dev.lyze.fishoffsloth.utils.Direction;
import lombok.Getter;
import lombok.Setter;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Player extends ShooterEntity {
    @Getter private final boolean firstPlayer;

    @Setter private VirtualGamepadGroup gamepad;
    private final Vector2 lastPosition = new Vector2();

    private float lastPositionTimer;
    private final float lastPositionMaxTimer = 0.5f;

    @Getter @Setter private String heartPath;

    public Player(boolean firstPlayer, BulletData data, Level level) {
        super(0, 0, 75, 200, data, level, PlayerCollisionFilter.instance);

        this.firstPlayer = firstPlayer;
        setDoDeathAnimation(true);
    }

    @Override
    public void update(EntityWorld world, float delta) {
        if (isDead())
            return;

        checkBounds();
        checkInput();
        super.update(world, delta);

        saveLastPosition(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (isDead())
            return;

        super.render(batch);
    }

    @Override
    protected void onCollision(Collision collision) {
        super.onCollision(collision);

        if (collision.other.userData instanceof CoinTile)
            level.removeEntity(((CoinTile) collision.other.userData));
    }

    private void checkBounds() {
        if (level.getMap().getBoundaries().contains(position.x, position.y))
            return;

        damage(1, Direction.Left);
        velocity.set(0, 0);
        position.set(lastPosition);
        level.getEntityWorld().getWorld().update(getItem(), position.x, position.y);
    }

    private void saveLastPosition(float delta) {
        if (isGrounded()) {
            if ((lastPositionTimer -= delta) < 0) {
                lastPosition.set(position);
                lastPositionTimer = lastPositionMaxTimer;
            }
        } else {
            lastPositionTimer = lastPositionMaxTimer;
        }
    }

    private void checkInput() {
        wantsToMoveLeft = gamepad.getLeftPressed();
        wantsToMoveRight = gamepad.getRightPressed();

        wantsToJump = gamepad.isJumpJustPressed();

        wantsToShoot = gamepad.isShootPressed();
    }

    @Override
    public void debugRender(ShapeDrawer drawer, BitmapFont font) {
        super.debugRender(drawer, font);

        drawer.setColor(Color.YELLOW);
        drawer.circle(lastPosition.x, lastPosition.y, 10);
    }

    @Override
    protected void jump() {
        super.jump();

        Statics.playSound(Statics.jump1, Statics.jump2);
    }

    @Override
    protected void doubleJump() {
        super.doubleJump();

        Statics.playSound(Statics.jump3);
    }

    @Override
    public void damage(int amount, Direction from) {
        if (getBlinkTime() > 0)
            return;

        Statics.playSound(Statics.hit);

        super.damage(amount, from);

        level.getHud().updateHud();
    }
}
