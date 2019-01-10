package chapter2

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage

class StarfishCollectorBeta : Game() {
    private lateinit var turtle: Turtle
    private lateinit var starfish: ActorBeta
    private lateinit var ocean: ActorBeta
    private lateinit var winMessage: ActorBeta

    private lateinit var mainStage: Stage
    private val win: Boolean = false

    override fun create() {
        mainStage = Stage()

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

    override fun render() {
        // check user input
        mainStage.act(1/60f)

        // check win condition: turtle must be overlapping starfish
        if (turtle.overlaps(starfish)) {
            starfish.remove()
            winMessage.isVisible = true
        }

        // clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // draw graphics
        mainStage.draw()
    }
}
