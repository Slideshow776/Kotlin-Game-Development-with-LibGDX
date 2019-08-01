package chapter11

import kotlin.math.abs
import com.badlogic.gdx.Input.Keys

class LevelScreen : BaseScreen() {
    private lateinit var jack: Koala

    override fun initialize() {
        val tma = TilemapActor("assets/map.tmx", mainStage)

        for (obj in tma.getRectangleList("solid")) {
            val props = obj.properties
            Solid(
                props.get("x") as Float,
                props.get("y") as Float,
                props.get("width") as Float,
                props.get("height") as Float,
                mainStage
                )
        }

        val startPoint = tma.getRectangleList("start")[0]
        val startProps = startPoint.properties
        jack = Koala(startProps.get("x") as Float, startProps.get("y") as Float, mainStage)
    }

    override fun update(dt: Float) {
        for (actor in BaseActor.getList(mainStage, Solid::class.java.canonicalName)) {
            val solid = actor as Solid

            if (jack.overlaps(solid) && solid.enabled) {
                val offset = jack.preventOverlap(solid)

                if (offset != null) {
                    // collision in X direction
                    if (abs(offset.x) > abs(offset.y))
                        jack.velocityVec.x = 0f
                    else
                        jack.velocityVec.y = 0f
                }
            }
        }
    }

    override fun keyDown(keyCode: Int): Boolean {
        if (keyCode == Keys.SPACE) {
            if (jack.isOnSolid())
                jack.jump()
        }
        return false
    }
}
