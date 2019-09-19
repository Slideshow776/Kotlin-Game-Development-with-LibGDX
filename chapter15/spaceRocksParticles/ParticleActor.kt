package chapter15.spaceRocksParticles

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g3d.particles.renderers.ParticleControllerRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group

class ParticleActor(pfxFile: String, imageDirectory: String) : Group() {
    private lateinit var effect: ParticleEffect
    private lateinit var renderingActor: ParticleRenderer

    private class ParticleRenderer(e: ParticleEffect) : Actor() {
        private var effect: ParticleEffect = e

        override fun draw(batch: Batch?, parentAlpha: Float) {
            super.draw(batch, parentAlpha)
            effect.draw(batch)
        }
    }

    init {
        effect = ParticleEffect()
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
