package chapter6.rhythmTapper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import java.util.ArrayList
import java.util.Collections

class RhythmScreen: BaseScreen() {
    private lateinit var keyList: ArrayList<String>
    private lateinit var colorList: ArrayList<Color>
    private lateinit var targetList: ArrayList<TargetBox>
    private lateinit var fallingList: ArrayList<ArrayList<FallingBox>>

    private lateinit var gameMusic: Music
    private var songData: SongData? = null
    private val leadTime = 3f
    private var advanceTimer = 0f
    private var spawnHeight = 650f
    private var noteSpeed = (spawnHeight - targetList.get(0).getY()) / leadTime

    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/space.png")
        background.setSize(800f, 600f)
        BaseActor.setWorldBounds(background)

        keyList = ArrayList<String>()
        val keyArray = arrayOf("F", "G", "H", "J")
        Collections.addAll(keyList, *keyArray)

        colorList = ArrayList<Color>()
        val colorArray = arrayOf(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE)
        Collections.addAll(colorList, *colorArray)

        val targetTable = Table()
        targetTable.setFillParent(true)
        targetTable.add().colspan(4).expandY()
        targetTable.row()
        mainStage.addActor(targetTable)

        targetList = ArrayList<TargetBox>()
        for (i in 0..3) {
            val tb = TargetBox(0f, 0f, mainStage, keyList.get(i), colorList.get(i))
            targetList.add(tb)
            targetTable.add(tb).pad(32f)
        }

        fallingList = ArrayList<ArrayList<FallingBox>>()
        for (i in 0..3) {
            fallingList.add(ArrayList<FallingBox>())
        }

        advanceTimer = 0f
        spawnHeight = 650f
        noteSpeed = (spawnHeight - targetList.get(0).getY()) / leadTime

        val startButton = TextButton("Start", BaseGame.textButtonStyle)
        startButton.addListener { e: Event ->
            if (isTouchDownEvent(e)) {
                val dataFileHandle = Gdx.files.internal("assets/FunkyJunky.key") // hard coded path
                songData = SongData()
                songData!!.readFromFile(dataFileHandle)
                songData!!.resetIndex()

                val songFileHandle = Gdx.files.internal("assets/" + songData!!.songName)
                gameMusic = Gdx.audio.newMusic(songFileHandle)
                startButton.isVisible = false
            }
            false
        }

        uiTable.add(startButton)
    }

    override fun update(dt: Float) {
        if (songData == null)
            return

        if (advanceTimer < leadTime && advanceTimer + dt > leadTime)
            gameMusic.play()

        if (advanceTimer < leadTime)
            advanceTimer += dt
        else
            advanceTimer = leadTime + gameMusic.position

        while (!songData!!.isFinished && advanceTimer >= songData!!.currentKeyTime.time!!) {
            val key = songData!!.currentKeyTime.key
            val i = keyList.indexOf(key)

            val fb = FallingBox(targetList.get(i).getX(), spawnHeight, mainStage)
            fb.setSpeed(noteSpeed)
            fb.setMotionAngle(270f)
            /*fb.setColor(colorList.get(i))*/
            fb.color = colorList[i]

            fallingList.get(i).add(fb)

            songData!!.advanceIndex()
        }
    }

    override fun keyDown(keycode: Int): Boolean { return false }
}
