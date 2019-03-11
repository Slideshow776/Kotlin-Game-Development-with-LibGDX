package chapter3

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.utils.Array

/**
*   Extend functionality of the LibGDX Actor class.
*/
class BaseActor(x: Float, y: Float, s: Stage) : Actor() {

    private lateinit var animation: Animation<TextureRegion>
    private var elapsedTime: Float = 0.toFloat()
    private var animationPaused: Boolean = false

    init {
        setPosition(x, y)
        s.addActor(this)
    }

    fun setAnimation(anim: Animation<TextureRegion>) {
        animation = anim
        val tr: TextureRegion = animation.getKeyFrame(0.toFloat())
        val w: Float = tr.regionWidth.toFloat()
        val h: Float = tr.regionHeight.toFloat()
        setSize(w, h)
        setOrigin(w/2, h/2)
    }

    fun setAnimationPaused(pause: Boolean) {
        animationPaused = pause
    }

    override fun act(dt: Float) {
        super.act(dt)

        if (!animationPaused)
            elapsedTime += dt
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        //  apply color tint effect
        val c: Color = color
        batch.setColor(c.r, c.g, c.b, c.a)

        if (animation != null && isVisible) {
            batch.draw(
                animation.getKeyFrame(elapsedTime),
                x,
                y,
                originX,
                originY,
                width,
                height,
                scaleX,
                scaleY,
                rotation
            )
        }
    }

    fun loadAnimationFromFiles(fileNames: Array<String>, frameDuration: Float, loop: Boolean): Animation<TextureRegion> {
        val fileCount: Int = fileNames.size
        // val textureArray: Array<TextureRegion> = Array<TextureRegion>()
        val textureArray: Array<TextureRegion> = Array<TextureRegion>()

        for (i in 0..fileCount) {
            val fileName: String = fileNames[i]
            val texture: Texture = Texture(Gdx.files.internal(fileName))
            texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
            textureArray.add(TextureRegion(texture))
        }

        val anim: Animation<TextureRegion> = Animation<TextureRegion>(frameDuration, textureArray)

        if (loop)
            anim.playMode = Animation.PlayMode.LOOP
        else
            anim.playMode = Animation.PlayMode.NORMAL

        if (animation == null)
            setAnimation(anim)

        return anim
    }

    fun loadAnimationFromSheet(fileName: String, rows: Int, cols: Int, frameDuration: Float, loop: Boolean): Animation<TextureRegion> {
        val texture: Texture = Texture(Gdx.files.internal(fileName), true)
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
        val frameWidth: Int = texture.width / cols
        val frameHeight: Int = texture.height / rows

        val temp = TextureRegion.split(texture, frameWidth, frameHeight)
        val textureArray: Array<TextureRegion> = Array<TextureRegion>()

        for (r in 0..rows) {
            for (c in 0..cols) {
                textureArray.add(temp[r][c])
            }
        }

        val anim: Animation<TextureRegion> = Animation(frameDuration, textureArray)

        if (loop)
            anim.playMode = Animation.PlayMode.LOOP
        else
            anim.playMode = Animation.PlayMode.NORMAL

        if (animation == null)
            setAnimation(anim)

        return anim
    }

    fun loadTexture(fileName: String): Animation<TextureRegion> {
        val fileNames: Array<String> = Array(1)
        fileNames[0] = fileName
        return loadAnimationFromFiles(fileNames, 1.toFloat(), true)
    }

    fun isAnimationFinished(): Boolean {
        return animation.isAnimationFinished(elapsedTime)
    }
}

