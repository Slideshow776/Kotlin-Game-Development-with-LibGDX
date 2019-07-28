package chapter01

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.Texture

class HelloWorldImage : Game() {

    private lateinit var texture: Texture
    private lateinit var batch: SpriteBatch

    override fun create() {
        val worldFile:FileHandle = Gdx.files.internal("world.png")
        texture = Texture(worldFile)
        batch = SpriteBatch()
    }

    override fun render () {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.begin()
        batch.draw(texture, 192f, 112f)
        batch.end()

    }
}
