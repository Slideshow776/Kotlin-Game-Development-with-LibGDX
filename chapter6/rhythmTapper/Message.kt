package chapter6.rhythmTapper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Message(x: Float, y: Float, s: Stage): BaseActor(x, y, s)  {
    var perfect: Animation<TextureRegion>
    var great: Animation<TextureRegion>
    var good: Animation<TextureRegion>
    var almost: Animation<TextureRegion>
    var miss: Animation<TextureRegion>

    private var countDown3: Animation<TextureRegion>
    private var countDown2: Animation<TextureRegion>
    private var countDown1: Animation<TextureRegion>
    private var countdownGo: Animation<TextureRegion>
    private var congratulations: Animation<TextureRegion>
    private var blip: Sound
    private var tone: Sound

    init {
        perfect = loadTexture("assets/perfect.png")
        great = loadTexture("assets/great.png")
        good = loadTexture("assets/good.png")
        almost = loadTexture("assets/almost.png")
        miss = loadTexture("assets/miss.png")

        countDown3 = loadTexture("assets/countdown-3.png")
        countDown2 = loadTexture("assets/countdown-2.png")
        countDown1 = loadTexture("assets/countdown-1.png")
        countdownGo = loadTexture("assets/countdown-go.png")
        congratulations = loadTexture("assets/congratulations.png")
        blip = Gdx.audio.newSound(Gdx.files.internal("assets/blip.wav"))
        tone = Gdx.audio.newSound(Gdx.files.internal("assets/tone.wav"))
    }

    fun pulseFade() {
        setOpacity(1f)
        clearActions()
        val pulseFade = Actions.sequence(
            Actions.scaleTo(1.1f, 1.1f, .05f),
            Actions.scaleTo(1.0f, 1.0f, .05f),
            Actions.delay(1f),
            Actions.fadeOut(.5f)
        )
        addAction(pulseFade)
    }

    fun displayCountdown() {
        val countdown = Actions.sequence(
            Actions.run {setAnimation(countDown3)},
            Actions.run {blip.play()},
            Actions.alpha(1f),
            Actions.scaleTo(1.1f, 1.1f, .05f), Actions.scaleTo(1.0f, 1.0f, .05f),
            Actions.delay(.5f), Actions.fadeOut(.4f),
            Actions.run {setAnimation(countDown2)},
            Actions.run {blip.play()},
            Actions.alpha(1f),
            Actions.scaleTo(1.1f, 1.1f, .05f), Actions.scaleTo(1.0f, 1.0f, .05f),
            Actions.delay(.5f), Actions.fadeOut(.4f),
            Actions.run {setAnimation(countDown1)},
            Actions.run {blip.play()},
            Actions.alpha(1f),
            Actions.scaleTo(1.1f, 1.1f, .05f), Actions.scaleTo(1.0f, 1.0f, .05f),
            Actions.delay(.5f), Actions.fadeOut(.4f),
            Actions.run {setAnimation(countdownGo)},
            Actions.run { tone.play() },
            Actions.alpha(1f),
            Actions.fadeOut(1f)
        )
        addAction(countdown)
    }

    fun displayCongratulations() {
        setOpacity(0f)
        setAnimation(congratulations)
        setScale(2f)
        addAction(Actions.fadeIn(4f))
    }
}
