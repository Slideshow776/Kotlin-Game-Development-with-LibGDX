package chapter3

import com.badlogic.gdx.scenes.scene2d.actions.Actions

class StarfishCollector: GameBeta() {

    private lateinit var turtle: Turtle
    private lateinit var starfish: Starfish
    private lateinit var ocean: BaseActor

    override fun initialize() {
        ocean = BaseActor(0.toFloat(), 0.toFloat(), mainStage)
        ocean.loadTexture("assets/water.jpg")
        ocean.setSize(800.toFloat(), 600.toFloat())

        starfish = Starfish(380.toFloat(), 380.toFloat(), mainStage)
        turtle = Turtle(20.toFloat(), 20.toFloat(), mainStage)
    }

    override fun update(dt: Float) {
        if (turtle.overlaps(starfish) && !starfish.isCollected()) {
            starfish.collect()

            val whirl = Whirlpool(0f, 0f, mainStage)
            whirl.centerAtActor(starfish)
            whirl.setOpacity(.25f)

            val youWinMessage = BaseActor(0f, 0f, mainStage)
            youWinMessage.loadTexture("assets/you-win.png")
            youWinMessage.centerAtPosition(400f, 300f)
            youWinMessage.setOpacity(0f)
            youWinMessage.addAction(Actions.delay(1f))
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1f)))

        }
    }
}