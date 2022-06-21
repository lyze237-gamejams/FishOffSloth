package dev.lyze.fishoffsloth.level.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.dongbat.jbump.Collision;
import com.dongbat.jbump.CollisionFilter;
import com.dongbat.jbump.Response;
import dev.lyze.fishoffsloth.level.EntityWorld;
import dev.lyze.fishoffsloth.level.Level;
import lombok.CustomLog;
import lombok.Getter;
import lombok.Setter;
import space.earlygrey.shapedrawer.ShapeDrawer;

@CustomLog
public class GravityEntity extends MovableEntity {
    private final float gravity = -16f;
    @Getter @Setter private float jumpForce = 8f, doubleJumpForce = 5f;
    @Getter @Setter private Animation<TextureAtlas.AtlasRegion> jump, fall;

    private final double jumpAfterGroundLeftMax = 150;

    @Getter private boolean isJumping, isDoubleJumping;

    protected boolean wantsToJump;

    public GravityEntity(float x, float y, float width, float height, Level level, CollisionFilter collisionFilter) {
        super(x, y, width, height, level, collisionFilter);
    }

    @Override
    public void update(EntityWorld world, float delta) {
        checkJump();
        super.update(world, delta);
    }

    @Override
    protected void beforeApplyVelocity(EntityWorld world, float delta) {
        applyGravity(delta);
    }

    private void applyGravity(float delta) {
        velocity.y += gravity * delta;

        if (isJumping && velocity.y < 0) {
            isJumping = false;
            isDoubleJumping = false;
        }
    }

    private void checkJump() {
        if (!wantsToJump)
            return;

        if ((isGrounded() || (System.currentTimeMillis() - getLastGrounded()) < jumpAfterGroundLeftMax) && !isJumping)
            jump();

        if (!isGrounded() && isJumping && !isDoubleJumping)
            doubleJump();
    }

    protected void doubleJump() {
        if (velocity.y < 0)
            velocity.y = 0;

        velocity.y += doubleJumpForce;
        isJumping = true;
        isDoubleJumping = true;
    }

    protected void jump() {
        if (velocity.y < 0)
            velocity.y = 0;

        velocity.y += jumpForce;
        isJumping = true;
    }

    protected void onCollision(Collision collision) {
        if (collision.type != Response.slide)
            return;

        if (collision.normal.y != 0) {
            // ceiling or floor
            velocity.y = 0;

            if (collision.normal.y == 1) {
                isJumping = false;
            }
        }
    }

    @Override
    protected void updateAnimation() {
        if (isDead())
            setAnimation(getDeath());
        else if (isJumping)
            setAnimation(jump);
        else if (!isGrounded())
            setAnimation(fall);
        else if (velocity.x > 0 || velocity.x < 0)
            setAnimation(getRun());
        else
            setAnimation(getIdle());
    }

    @Override
    public void debugRender(ShapeDrawer drawer, BitmapFont font) {
        super.debugRender(drawer, font);

        font.draw(drawer.getBatch(), "Grounded: " + isGrounded(), position.x, position.y);
        font.draw(drawer.getBatch(), "Facing Right: " + isFacingRight(), position.x, position.y + 16);
    }
}
