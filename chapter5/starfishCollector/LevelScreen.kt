package chapter5

import com.badlogic.gdx.scenes.scene2d.actions.Actions

class LevelScreen: BaseScreen() {

    private lateinit var turtle: Turtle
    private var win: Boolean = false

    override fun initialize() {
        val ocean = BaseActor(0f, 0f, mainStage)
        ocean.loadTexture("assets/water-border.jpg")
        ocean.setSize(1200f, 900f)
        BaseActor.setWorldBounds(ocean)

        Starfish(400f, 400f, mainStage)
        Starfish(500f, 100f, mainStage)
        Starfish(100f, 450f, mainStage)
        Starfish(200f, 250f, mainStage)

        Rock(200f, 150f, mainStage)
        Rock(100f, 300f, mainStage)
        Rock(300f, 350f, mainStage)
        Rock(4500f, 200f, mainStage)

        turtle = Turtle(20f, 20f, mainStage)
    }

    override fun update(dt: Float) {
        for (rockActor: BaseActor in BaseActor.getList(mainStage, Rock::class.java.canonicalName)) {
            turtle.preventOverlap(rockActor)
        }

        for ( starfishActor: BaseActor in BaseActor.getList(mainStage, Starfish::class.java.canonicalName)) {
            var starfish = starfishActor as Starfish
            if (turtle.overlaps(starfish) && !starfish.isCollected()) {
                starfish.collect()

                val whirl = Whirlpool(0f, 0f, mainStage)
                whirl.centerAtActor(starfish)
                whirl.setOpacity(.25f)
            }
        }

        if(BaseActor.count(mainStage, Starfish::class.java.canonicalName) == 0 && !win) {
            win = true
            val youWinMessage = BaseActor(0f, 0f, uiStage)
            youWinMessage.loadTexture("assets/you-win.png")
            youWinMessage.centerAtPosition(400f, 300f)
            youWinMessage.setOpacity(0f)
            youWinMessage.addAction(Actions.delay(1f))
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1f)))
        }
    }
}