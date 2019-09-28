package chapter15.starfishCollectorShaders

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group

open class ParticleActor(pfxFile: String, imageDirectory: String) : Group() {
    private var effect: ParticleEffect = ParticleEffect()
    private var renderingActor: ParticleRenderer

    private inner class ParticleRenderer internal constructor(private val effect: ParticleEffect) : Actor() {
        override fun draw(batch: Batch?, parentAlpha: Float) {
            effect.draw(batch)
        }
    }

    init {
        effect.load(Gdx.files.internal(pfxFile), Gdx.files.internal(imageDirectory))
        renderingActor = ParticleRenderer(effect)
        this.addActor(renderingActor)
    }

    fun start() { effect.start() }
    fun stop() { effect.allowCompletion() } // pauses continuous emitters
    fun isRunning(): Boolean { return !effect.isComplete }

    fun centerAtActor(other: Actor) {
        setPosition(
            other.x + other.width / 2,
            other.y + other.height / 2
        )
    }

    override fun act(delta: Float) {
        super.act(delta)
        effect.update(delta)

        if (effect.isComplete && !effect.emitters.first().isContinuous) {
            effect.dispose()
            this.remove()
        }
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
    }
}
