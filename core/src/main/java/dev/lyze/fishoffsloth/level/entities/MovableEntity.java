package dev.lyze.fishoffsloth.level.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.*;
import dev.lyze.fishoffsloth.level.Level;
import dev.lyze.fishoffsloth.utils.MathUtils;
import lombok.*;

@CustomLog
public class MovableEntity extends Entity {
    private final float movementSpeedIncrease = 5f;
    private final float maxSpeed = 30f;
    private final float friction = 1000f;

    protected final Vector2 velocity = new Vector2();
    protected final Vector2 inputVelocity = new Vector2();

    @Getter @Setter protected boolean isFacingRight = true;

    protected float wantsToMoveLeft, wantsToMoveRight;

    @Getter private boolean isDead;
    @Getter private boolean isGrounded;
    @Getter private long lastGrounded;

    @Getter @Setter
    private Animation<TextureAtlas.AtlasRegion> idle, run, death;
    private Animation<TextureAtlas.AtlasRegion> currentAnimation;

    @Getter @Setter(AccessLevel.PROTECTED)
    private float animationXOffset;

    private float animationTime;

    @Getter @Setter
    private CollisionFilter collisionFilter;

    @Getter
    private final Collisions tempCollisions = new Collisions();

    public MovableEntity(float x, float y, float width, float height, Level level, CollisionFilter collisionFilter) {
        super(x, y, width, height, level);
        this.collisionFilter = collisionFilter;
    }

    @Override
    public void update(World<Entity> world, float delta) {
        super.update(world, delta);

        animationTime += delta;

        setInput();
        checkGround(world);
        checkMovementDirection();

        applyInput(delta);
        applyFriction(delta);

        beforeApplyVelocity(world, delta);
        checkCollisionsAndApplyVelocity(world, delta);

        updateAnimation();
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        if (currentAnimation == null)
            return;

        var frame = currentAnimation.getKeyFrame(animationTime);
        batch.draw(frame, isFacingRight ? position.x + animationXOffset : position.x + width - animationXOffset, position.y);
    }

    private void setInput() {
        inputVelocity.set(wantsToMoveLeft > 0.2f ? -movementSpeedIncrease : wantsToMoveRight > 0.2f ? movementSpeedIncrease : 0, 0);
    }

    private void checkMovementDirection() {
        if (isFacingRight && inputVelocity.x < 0) {
            flip();
        }
        else if (!isFacingRight && inputVelocity.x > 0) {
            flip();
        }
    }

    private void flip() {
        isFacingRight = !isFacingRight;
    }

    private void applyInput(float delta) {
        if (inputVelocity.x > 0) {
            velocity.x = MathUtils.approach(velocity.x, maxSpeed * wantsToMoveRight, inputVelocity.x * delta);
        } else if (inputVelocity.x < 0) {
            velocity.x = MathUtils.approach(velocity.x, -maxSpeed * wantsToMoveLeft, inputVelocity.x * delta);
        }
    }

    private void applyFriction(float delta) {
        if (inputVelocity.x == 0)
            velocity.x = MathUtils.approach(velocity.x, 0, friction * delta);
    }

    private void checkCollisionsAndApplyVelocity(World<Entity> world, float delta) {
        if (isDead)
            return;

        var response = world.move(item, position.x + velocity.x, position.y + velocity.y, collisionFilter);

        for (int i = 0; i < response.projectedCollisions.size(); i++)
            onCollision(response.projectedCollisions.get(i));

        position.set(response.goalX, response.goalY);
    }

    protected void onCollision(Collision collision) {
        if (collision.type != Response.slide)
            return;

        // wall
        if (collision.normal.x != 0) {
            velocity.x = 0;
        }
    }

    private void checkGround(World<Entity> world) {
        world.project(item, position.x, position.y, width, height, position.x, position.y - 2f, getCollisionFilter(), getTempCollisions());
        for (int i = 0; i < getTempCollisions().size(); i++) {
            if (getTempCollisions().get(i).type.equals(Response.slide)) {
                if (!isGrounded)
                    landed();

                lastGrounded = System.currentTimeMillis();
                isGrounded = true;

                return;
            }
        }

        isGrounded = false;
    }

    protected void updateAnimation() {
        if (isDead)
            setAnimation(death);
        else if (velocity.x > 0 || velocity.x < 0)
            setAnimation(run);
        else
            setAnimation(idle);
    }

    protected void setAnimation(Animation<TextureAtlas.AtlasRegion> newAnimation) {
        if (this.currentAnimation == newAnimation)
            return;

        if (newAnimation == null)
            return;

        this.currentAnimation = newAnimation;
        animationTime = 0;
    }

    protected void landed() {
    }

    protected void beforeApplyVelocity(World<Entity> world, float delta) { }
}
