package chapter7

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array

class Enemy(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    private var engineMusic: Music

    init {
        val fileNames = Array<String>()
        fileNames.add("assets/planeRed0.png")
        fileNames.add("assets/planeRed1.png")
        fileNames.add("assets/planeRed2.png")
        fileNames.add("assets/planeRed1.png")
        loadAnimationFromFiles(fileNames, .1f, true)

        setSpeed(100f)
        setMotionAngle(180f)
        setBoundaryPolygon(8)

        engineMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/242739__marlonhj__engine-1.wav"))
        engineMusic.volume = .25f
        engineMusic.isLooping = true
        engineMusic.play()
    }

    override fun act(dt: Float) {
        super.act(dt)
        applyPhysics(dt)
    }

    fun stopMusic() {
        engineMusic.stop()
    }
}
