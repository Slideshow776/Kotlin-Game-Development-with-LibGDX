package chapter12

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class LevelScreen : BaseScreen() {
    lateinit var hero: Hero
    lateinit var sword: Sword

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

        sword = Sword(0f, 0f, mainStage)
        sword.isVisible = false

        for (obj in tma.getTileList("bush")) {
            val props = obj.properties
            Bush(
                props.get("x") as Float,
                props.get("y") as Float,
                mainStage
            )
        }

        for (obj in tma.getTileList("rock")) {
            val props = obj.properties
            Rock(
                props.get("x") as Float,
                props.get("y") as Float,
                mainStage
            )
        }

        hero.toFront();
    }

    override fun update(dt: Float) {

        if (!sword.isVisible) {
            // hero movement controls
            if (Gdx.input.isKeyPressed(Keys.LEFT))
                hero.accelerateAtAngle(180f)
            if (Gdx.input.isKeyPressed(Keys.RIGHT))
                hero.accelerateAtAngle(0f)
            if (Gdx.input.isKeyPressed(Keys.UP))
                hero.accelerateAtAngle(90f)
            if (Gdx.input.isKeyPressed(Keys.DOWN))
                hero.accelerateAtAngle(270f)
        }

        for (solid in BaseActor.getList(mainStage, Solid::class.java.canonicalName)) {
            hero.preventOverlap(solid)
        }

        if (sword.isVisible) {
            for (bush in BaseActor.getList(mainStage, Bush::class.java.canonicalName)) {
                if (sword.overlaps(bush))
                    bush.remove()
            }
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.S)
            swingSword()
        return false
    }

    fun swingSword() {
        // visibility determines if sword is currently swinging
        if (sword.isVisible)
            return

        hero.setSpeed(0f)

        val facingAngle = hero.facingAngle

        val offset = Vector2()
        when (facingAngle) {
            0f -> offset.set(.5f, .2f)
            90f -> offset.set(.65f, .5f)
            180f -> offset.set(.4f, .2f)
            else -> offset.set(.25f, .2f)
        }

        sword.setPosition(hero.x, hero.y)
        sword.moveBy(offset.x * hero.width, offset.y * hero.height)

        val swordArc = 90f
        sword.rotation = facingAngle - swordArc / 2
        sword.originX = 0f

        sword.isVisible = true
        sword.addAction(Actions.rotateBy(swordArc, .25f))
        sword.addAction(Actions.after(Actions.visible(false)))

        // hero should appear in front of sword when facing north or west
        if (facingAngle == 90f || facingAngle == 180f)
            hero.toFront()
        else
            sword.toFront()
    }
}
