package chapter6.rythmTapper

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

class RecorderScreen: BaseScreen(){
    lateinit var music: Music
    lateinit var songData: SongData
    var lastSongPosition: Float = 0.0f
    var recording: Boolean = false
    lateinit var loadButton: TextButton
    lateinit var recordButton: TextButton
    lateinit var saveButton: TextButton

    override fun initialize() {
        recording = false

        loadButton = TextButton("Load Music File", BaseGame.textButtonStyle)
        loadButton.addListener { e: Event ->
            if (isTouchDownEvent(e)) {
                val musicFile = FileUtils.showOpenDialog()
                if (musicFile != null) {
                    music = Gdx.audio.newMusic(musicFile)
                    songData = SongData()
                    songData.songName = musicFile.name()
                }
            }
            false
        }

        recordButton = TextButton("Record Keystrokes", BaseGame.textButtonStyle)
        recordButton.addListener{ e: Event ->
            if (isTouchDownEvent(e)) {
                if (!recording) {
                    music.play()
                    recording = true
                    lastSongPosition = 0f
                }
            }
            false
        }

        saveButton = TextButton("Save Keystroke File", BaseGame.textButtonStyle)
        saveButton.addListener{ e: Event ->
            if (isTouchDownEvent(e)) {
                val textFile = FileUtils.showSaveDialog()

                if (textFile != null)
                    songData.writeToFile(textFile)
            }
            false
        }

        uiTable.add(loadButton)
        uiTable.row()
        uiTable.add(recordButton)
        uiTable.row()
        uiTable.add(saveButton)

    }

    override fun update(dt: Float) {
        if (recording) {
            if (music.isPlaying())
                lastSongPosition = music.position
            else { // song just finished
                recording = false
                songData.songDuration = lastSongPosition
            }
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (recording) {
            val key = Input.Keys.toString(keycode)
            val time = music.position
            songData.addKeyTime(key, time)
        }
        return false
    }

}
