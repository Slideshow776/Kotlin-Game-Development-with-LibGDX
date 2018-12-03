package chapter2

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle

class StarfishCollectorAlpha : Game() {
    private lateinit var batch: SpriteBatch

    private lateinit var turtleTexture: Texture
    private lateinit var turtleRectangle: Rectangle
    private var turtleX: Float = 20f
    private var turtleY: Float = 20f

    private lateinit var starfishTexture: Texture
    private lateinit var starfishRectangle: Rectangle
    private var starfishX: Float = 380f
    private var starfishY: Float = 380f

    private lateinit var oceanTexture: Texture
    private lateinit var winMessageTexture: Texture

    private var win: Boolean = false

    override fun create() {
        batch = SpriteBatch()

        turtleTexture = Texture(Gdx.files.internal("assets/turtle-1.png"))
        turtleRectangle = Rectangle(
            turtleX,
            turtleY,
            turtleTexture.width.toFloat(),
            turtleTexture.height.toFloat()
        )

        starfishTexture = Texture(Gdx.files.internal("assets/starfish.png"))
        starfishRectangle = Rectangle(
            starfishX,
            starfishY,
            starfishTexture.width.toFloat(),
            starfishTexture.height.toFloat()
        )

        oceanTexture = Texture(Gdx.files.internal("assets/water.jpg"))
        winMessageTexture = Texture(Gdx.files.internal("assets/you-win.png"))
    }

    override fun render() {
        // check user input
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            turtleX -= 1
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            turtleX += 1
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            turtleY += 1
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            turtleY -= 1

        // update turtle rectangle location
        turtleRectangle.setPosition(turtleX, turtleY)

        // checks win condition: Turtle must be overlapping starfish
        if (turtleRectangle.overlaps(starfishRectangle))
            win = true

        // clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // draw graphics
        batch.begin()
        batch.draw(oceanTexture, 0f, 0f)
        if (!win)
            batch.draw(starfishTexture, starfishX, starfishY)
        batch.draw(turtleTexture, turtleX, turtleY)
        if (win)
            batch.draw(winMessageTexture, 180f, 180f)
        batch.end()
    }
}
