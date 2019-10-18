package chapter16.starfishCollector3D

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.ui.Label

class LevelScreen : BaseScreen() {
    lateinit var turtle: Turtle
    lateinit var starfishLabel: Label
    lateinit var messageLabel: Label

    override fun initialize() {
        val floor = Box(0f, 0f, 0f, mainStage3D)
        floor.loadTexture("assets/water.jpg")
        floor.setScale(500f, .1f, 500f)

        val skydome = Sphere(0f, 0f, 0f, mainStage3D)
        skydome.loadTexture("assets/sky-sphere.png")
        // when scaling the negative z-value inverts the sphere so that the texture is rendered on the inside
        skydome.setScale(500f, 500f, -500f)

        turtle = Turtle(0f, 0f, 15f, mainStage3D)
        turtle.setTurnAngle(90f)

        Rock(-15f, 1f, 0f, mainStage3D)
        Rock(-15f, 1f, 15f, mainStage3D)
        Rock(-15f, 1f, 30f, mainStage3D)
        Rock(0f, 1f, 0f, mainStage3D)
        Rock(0f, 1f, 30f, mainStage3D)
        Rock(15f, 1f, 0f, mainStage3D)
        Rock(15f, 1f, 15f, mainStage3D)
        Rock(15f, 1f, 30f, mainStage3D)

        Starfish(10f, 0f, 10f, mainStage3D)
        Starfish(10f, 0f, 20f, mainStage3D)
        Starfish(-10f, 0f, 10f, mainStage3D)
        Starfish(-10f, 0f, 20f, mainStage3D)

        mainStage3D.setCameraPosition(0f, 10f, 0f)
        mainStage3D.setCameraDirection(Vector3(0f, 0f, 0f))

        starfishLabel = Label("Starfish left: 4", BaseGame.labelStyle)
        starfishLabel.color = Color.CYAN

        messageLabel = Label("You Win!", BaseGame.labelStyle)
        messageLabel.color = Color.LIME
        messageLabel.setFontScale(2f)
        messageLabel.isVisible = false

        uiTable.pad(20f)
        uiTable.add(starfishLabel)
        uiTable.row()
        uiTable.add(messageLabel).expandY
    }

    override fun update(dt: Float) {
        val speed = 3f
        val rotateSpeed = 45f

        if (Gdx.input.isKeyPressed(Keys.UP))
            turtle.moveForward(speed * dt)
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            turtle.turn(-rotateSpeed * dt)
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            turtle.turn(rotateSpeed * dt)

        mainStage3D.setCameraDirection(turtle.getPosition())

        for (rock in BaseActor3D.getList(mainStage3D, Rock::class.java.canonicalName))
            turtle.preventOverlap(rock)

        for (starfish in BaseActor3D.getList(mainStage3D, Starfish::class.java.canonicalName))
            if (turtle.overlaps(starfish))
                starfish.remove()
        val starfishCount = BaseActor3D.count(mainStage3D, Starfish::class.java.canonicalName)
        starfishLabel.setText("Starfish left: $starfishCount")

        if (starfishCount == 0)
            messageLabel.isVisible = true
    }

}
