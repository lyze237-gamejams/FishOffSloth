package box2dLight;

import com.badlogic.gdx.physics.box2d.World;

public class NestableRayHandler extends RayHandler {
    public NestableRayHandler(World world) {
        super(world);
    }

    @Override
    public void resizeFBO(int fboWidth, int fboHeight) {
        if (lightMap != null) {
            lightMap.dispose();
        }
        lightMap = new LightMap(this, fboWidth, fboHeight);
    }
}
