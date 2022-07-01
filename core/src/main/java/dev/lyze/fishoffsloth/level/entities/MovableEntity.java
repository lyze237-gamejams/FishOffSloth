package dev.lyze.fishoffsloth.level.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Collisions;
import com.dongbat.jbump.Response;
import dev.lyze.fishoffsloth.level.EntityWorld;
import dev.lyze.fishoffsloth.level.Level;
import lombok.*;

@CustomLog
public class MovableEntity extends BlinkableEntity {
    @Getter @Setter private float speed = 4f;

    protected final Vector2 velocity = new Vector2();
    protected final Vector2 inputVelocity = new Vector2();

    @Getter @Setter protected boolean isFacingRight = true;

    protected float wantsToMoveLeft, wantsToMoveRight;

    @Getter private boolean isGrounded;
    @Getter private long lastGrounded;

    @Getter @Setter private Integer health;
    @Getter @Setter private boolean doDeathAnimation;
    @Getter private Color tintColor = new Color(1, 1, 1, 1);

    @Getter private boolean dead;

    @Getter @Setter private float rotation;

    @Getter @Setter
    private Animation<TextureAtlas.AtlasRegion> idle, run, death;
    @Getter private Animation<TextureAtlas.AtlasRegion> currentAnimation;
    private Sprite sprite;

    @Getter @Setter(AccessLevel.PROTECTED)
    private float animationXOffset, animationYOffset;

    private float animationTime;

    @Getter @Setter
    private CollisionFilter collisionFilter;

    @Getter
    private final Collisions tempCollisions = new Collisions();

    public MovableEntity(float x, float y, float width, float height, Level level, CollisionFilter collisionFilter) {
        super(x, y, width, height, level);
        this.collisionFilter = collisionFilter;
        this.sprite = new Sprite();
    }

    @Override
    public void update(EntityWorld world, float delta) {
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

        renderFrame(batch, currentAnimation.getKeyFrame(animationTime));
    }

    protected void renderFrame(SpriteBatch batch, TextureAtlas.AtlasRegion frame) {
        if (frame == null)
            return;

        var drawX = isFacingRight ? position.x + animationXOffset : position.x + width - animationXOffset;
        var drawY = position.y + animationYOffset;
        var drawWidth = isFacingRight ? frame.getRegionWidth() : -frame.getRegionWidth();
        var drawHeight = frame.getRegionHeight();

        sprite.setRegion(frame);
        sprite.setBounds(drawX, drawY, drawWidth, drawHeight);
        sprite.setOriginCenter();
        sprite.setRotation(rotation);
        sprite.setColor(tintColor.equals(Color.WHITE) ? batch.getColor() : tintColor);
        sprite.draw(batch);
    }

    private void setInput() {
        inputVelocity.set(wantsToMoveLeft > 0.2f ? -speed: wantsToMoveRight > 0.2f ? speed : 0, 0);
    }

    private void checkMovementDirection() {
        if (isFacingRight && inputVelocity.x < 0) {
            flip();
        }
        else if (!isFacingRight && inputVelocity.x > 0) {
            flip();
        }
    }

    protected void flip() {
        isFacingRight = !isFacingRight;
    }

    private void applyInput(float delta) {
        if (inputVelocity.x > 0) {
            velocity.x = speed * wantsToMoveRight;
        } else if (inputVelocity.x < 0) {
            velocity.x = -speed * wantsToMoveLeft;
        }
    }

    private void applyFriction(float delta) {
        if (inputVelocity.x == 0)
            velocity.x = 0;
    }

    protected void checkCollisionsAndApplyVelocity(EntityWorld world, float delta) {
        var response = world.getWorld().move(item, position.x + velocity.x, position.y + velocity.y, collisionFilter);

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

    private void checkGround(EntityWorld world) {
        world.getWorld().project(item, position.x, position.y, width, height, position.x, position.y - 10f, getCollisionFilter(), getTempCollisions());
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
        if (velocity.x > 0 || velocity.x < 0)
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

    protected void beforeApplyVelocity(EntityWorld world, float delta) { }

    public void damage(int amount) {
        if (health == null)
            return;

        health = Math.max(health - amount, 0);

        startBlink();

        if (health <= 0)
            die();
    }

    protected void die() {
        level.removeEntity(this);

        dead = true;

        if (!doDeathAnimation)
            return;

        TextureAtlas.AtlasRegion keyFrame = null;
        if (currentAnimation != null)
            keyFrame = currentAnimation.getKeyFrame(animationTime);

        level.getEntityWorld().addEntity(new DeadEntity(position.x, position.y, width, height, keyFrame, level));
    }
}
