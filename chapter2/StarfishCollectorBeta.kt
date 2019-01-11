package chapter2

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage

class StarfishCollectorBeta : GameBeta() {

    private lateinit var turtle: Turtle
    private lateinit var starfish: ActorBeta
    private lateinit var ocean: ActorBeta
    private lateinit var winMessage: ActorBeta

    private val win: Boolean = false

    override fun initialize() {
        ocean = ActorBeta()
        ocean.setTexture(Texture(Gdx.files.internal("assets/water.jpg")))
        mainStage.addActor(ocean)

        starfish = ActorBeta()
        starfish.setTexture(Texture(Gdx.files.internal("assets/starfish.png")))
        starfish.setPosition(380f, 380f)
        mainStage.addActor(starfish)

        turtle = Turtle()
        turtle.setTexture(Texture(Gdx.files.internal("assets/turtle-1.png")))
        turtle.setPosition(20f, 20f)
        mainStage.addActor(turtle)

        winMessage = ActorBeta()
        winMessage.setTexture(Texture(Gdx.files.internal("assets/you-win.png")))
        winMessage.setPosition(180f, 180f)
        winMessage.isVisible = false
        mainStage.addActor(winMessage)
    }

    override fun update(dt: Float) {
        // check win condition: turtle must be overlapping starfish
        if (turtle.overlaps(starfish)) {
            starfish.remove()
            winMessage.isVisible = true
        }
    }
}
