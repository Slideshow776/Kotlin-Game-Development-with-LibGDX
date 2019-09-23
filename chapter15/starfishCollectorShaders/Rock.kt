package chapter15.starfishCollectorShaders

import chapter15.starfishCollectorShaders.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage

class Rock(x: Float, y: Float, s: Stage): BaseActor(x, y, s) {
    init {
        loadTexture("assets/rock.png")
        setBoundaryPolygon(8)
    }
}