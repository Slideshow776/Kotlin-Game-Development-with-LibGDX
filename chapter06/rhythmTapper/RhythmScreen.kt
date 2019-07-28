package chapter06.rhythmTapper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.Align
import java.util.ArrayList
import java.util.Collections
import kotlin.math.roundToInt

class RhythmScreen: BaseScreen() {
    private lateinit var keyList: ArrayList<String>
    private lateinit var colorList: ArrayList<Color>
    private lateinit var targetList: ArrayList<TargetBox>
    private lateinit var fallingLists: ArrayList<ArrayList<FallingBox>>

    private lateinit var gameMusic: Music
    private var songData: SongData? = null
    private val leadTime = 3f
    private var advanceTimer = 0f
    private var spawnHeight = 650f
    private var noteSpeed = (spawnHeight - targetList.get(0).getY()) / leadTime

    private lateinit var message: Message
    private lateinit var scoreLabel: Label
    private var score = 0
    private var maxScore = 0
    private lateinit var timeLabel: Label
    private var songDuration = 0f

    private lateinit var applause: Sound

    private lateinit var statsLabel: Label
    private var perfects = 0
    private var greats = 0
    private var goods = 0
    private var almosts = 0
    private var misses = 0

    private lateinit var scorePercentLabel: Label

    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/space.png")
        background.setSize(800f, 600f)
        BaseActor.setWorldBounds(background)

        keyList = ArrayList()
        val keyArray = arrayOf("F", "G", "H", "J")
        Collections.addAll(keyList, *keyArray)

        colorList = ArrayList()
        val colorArray = arrayOf(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE)
        Collections.addAll(colorList, *colorArray)

        val targetTable = Table()
        targetTable.setFillParent(true)
        targetTable.add().colspan(4).expandY()
        targetTable.row()
        mainStage.addActor(targetTable)

        targetList = ArrayList()
        for (i in 0..3) {
            val tb = TargetBox(0f, 0f, mainStage, keyList[i], colorList[i])
            targetList.add(tb)
            targetTable.add(tb).pad(32f)
        }

        fallingLists = ArrayList()
        for (i in 0..3)
            fallingLists.add(ArrayList())

        advanceTimer = 0f
        spawnHeight = 650f
        noteSpeed = (spawnHeight - targetList[0].y) / leadTime

        val startButton = TextButton("Start", BaseGame.textButtonStyle)
        startButton.addListener { e: Event ->
            if (isTouchDownEvent(e)) {
                /*val dataFileHandle = Gdx.files.internal("assets/FunkyJunky.key") // hard coded path*/
                val dataFileHandle = Gdx.files.internal("assets/masterOfTheFeast.key") // hard coded path
                songData = SongData()
                songData!!.readFromFile(dataFileHandle)
                songData!!.resetIndex()

                val songFileHandle = Gdx.files.internal("assets/" + songData!!.songName)
                gameMusic = Gdx.audio.newMusic(songFileHandle)
                startButton.isVisible = false

                songDuration = songData!!.songDuration.toFloat()
                score = 0
                maxScore = 100 * songData!!.keyTimeCount()
                scoreLabel.setText("Score: $score\nMax: $maxScore")

                message.displayCountdown()
            }
            false
        }

        scoreLabel = Label("Score: 0\nMax: 0", BaseGame.labelStyle)
        scoreLabel.setAlignment(Align.right)
        timeLabel = Label("Time: 0\nEnd: 0", BaseGame.labelStyle)
        timeLabel.setAlignment(Align.right)
        message = Message(0f, 0f, uiStage)
        message.setOpacity(0f)

        statsLabel = Label(
            "Perfect: $perfects\n" +
                    "Great: $greats\n" +
                    "Good: $goods\n" +
                    "Almost: $almosts\n" +
                    "Miss: $misses"
            , BaseGame.labelStyle
        )
        statsLabel.setAlignment(Align.center)
        statsLabel.color = Color.PURPLE
        statsLabel.isVisible = false

        scorePercentLabel = Label("Score: 0%", BaseGame.labelStyle)
        scorePercentLabel.color = Color.RED
        scorePercentLabel.isVisible = false

        /*uiTable.debug()*/
        uiTable.top()
        uiTable.pad(10f)
        uiTable.add(startButton).width(200f).left()
        uiTable.add(timeLabel).width(150f)
        uiTable.add(scoreLabel).width(200f).right()
        uiTable.row()
        uiTable.add(message).colspan(3).expandX().fillY()
        uiTable.row()
        uiTable.add(statsLabel).colspan(3).expandX().fillY()
        uiTable.row()
        uiTable.add(scorePercentLabel).colspan(3).expandX().fillY()

        applause = Gdx.audio.newSound(Gdx.files.internal("assets/applause7.wav"))
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

            val fb = FallingBox(targetList[i].x, spawnHeight, mainStage)
            fb.setSpeed(noteSpeed)
            fb.setMotionAngle(270f)
            fb.color = colorList[i]

            fallingLists[i].add(fb)

            songData!!.advanceIndex()
        }

        if (gameMusic.isPlaying)
            timeLabel.setText(
                "Time: ${gameMusic.position.toInt()}\n" +
                        "End: ${songDuration.toInt()}"
            )

        for (i in 0..3) {
            val fallingList = fallingLists[i]

            if (fallingList.size > 0) {
                val fb = fallingList.get(0)
                val tb = targetList[i]
                if (fb.y < tb.y && !fb.overlaps(tb)) {
                    message.setAnimation(message.miss)
                    message.pulseFade()
                    fallingList.remove(fb)
                    /*fb.remove() // remove from stage immediately*/
                    fb.setSpeed(0f)
                    fb.flashOut()
                }
            }
        }

        if (songData!!.isFinished && !gameMusic.isPlaying) {
            message.displayCongratulations()
            applause.play()
            songData = null

            statsLabel.isVisible = true
            statsLabel.addAction(Actions.fadeOut(0f))
            statsLabel.addAction(Actions.fadeIn(2f))
            statsLabel.setText(
                "Perfect: $perfects\n" +
                        "Great: $greats\n" +
                        "Good: $goods\n" +
                        "Almost: $almosts\n" +
                        "Miss: $misses"
            )

            scorePercentLabel.isVisible = true
            scorePercentLabel.addAction(Actions.fadeOut(0f))
            scorePercentLabel.addAction(Actions.fadeIn(3f))
            scorePercentLabel.setText(
                "Score: ${((score.toFloat()/maxScore.toFloat())*100).roundToInt()}%"
            )
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (songData == null)
            return false

        val keyString = Keys.toString(keycode)
        if (keyList.contains(keyString)) {
            val i = keyList.indexOf(keyString)
            val tb = targetList[i]
            tb.pulse()
            val fallingList = fallingLists[i]

            if (fallingList.size == 0) {
                message.setAnimation(message.miss)
                message.pulseFade()
            } else {
                val fb = fallingList[0]
                val distance = Math.abs(fb.y - tb.y)

                when {
                    distance < 8 -> {
                        message.setAnimation(message.perfect)
                        score += 100
                        perfects += 1
                    }
                    distance < 16 -> {
                        message.setAnimation(message.great)
                        score += 80
                        greats += 1
                    }
                    distance < 24 -> {
                        message.setAnimation(message.good)
                        score += 50
                        goods +=1
                    }
                    distance < 32 -> {
                        message.setAnimation(message.almost)
                        score += 20
                        almosts += 1
                    }
                    else -> {
                        message.setAnimation(message.miss)
                        misses += 1
                    }
                }

                message.pulseFade()
                scoreLabel.setText("Score: $score\nMax: $maxScore")

                fallingList.remove(fb)
                fb.setSpeed(0f)
                /*fb.remove()*/
                fb.setSpeed(0f)
                fb.flashOut()
            }
        }

        return false
    }
}
