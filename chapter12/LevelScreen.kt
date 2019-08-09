package chapter12

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys

class LevelScreen : BaseScreen() {
    lateinit var hero: Hero

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
        hero = Hero(startProps.get("x") as Float, startProps.get("y") as Float, mainStage)
    }

    override fun update(dt: Float) {
        // hero movement controls
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            hero.accelerateAtAngle(180f)
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            hero.accelerateAtAngle(0f)
        if (Gdx.input.isKeyPressed(Keys.UP))
            hero.accelerateAtAngle(90f)
        if (Gdx.input.isKeyPressed(Keys.DOWN))
            hero.accelerateAtAngle(270f)

        for (solid in BaseActor.getList(mainStage, Solid::class.java.canonicalName)) {
            hero.preventOverlap(solid)
        }
    }
}
