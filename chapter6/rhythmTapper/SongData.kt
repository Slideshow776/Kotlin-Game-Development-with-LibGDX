package chapter6.rhythmTapper

import java.util.ArrayList
import com.badlogic.gdx.files.FileHandle

class SongData {
    var songName: String? = null
    var songDuration: Float = 0.toFloat()
    private val keyTimeList: ArrayList<KeyTimePair>
    private var keyTimeIndex: Int = 0

    val currentKeyTime: KeyTimePair
        get() = keyTimeList[keyTimeIndex]

    val isFinished: Boolean
        get() = keyTimeIndex >= keyTimeList.size

    inner class KeyTimePair(val key: String, val time: Float?)

    init {
        keyTimeList = ArrayList()
    }

    fun addKeyTime(k: String, t: Float?) {
        keyTimeList.add(KeyTimePair(k, t))
    }

    fun resetIndex() {
        keyTimeIndex = 0
    }

    fun advanceIndex() {
        keyTimeIndex++
    }

    fun keyTimeCount(): Int {
        return keyTimeList.size
    }

    fun writeToFile(file: FileHandle) {
        // boolean: true=append, false=overwrite.
        file.writeString(songName!! + "\n", false)
        file.writeString(songDuration.toString() + "\n", true)
        for (ktp in keyTimeList) {
            val data = ktp.key + "," + ktp.time + "\n"
            file.writeString(data, true)
        }
    }

    fun readFromFile(file: FileHandle) {
        val rawData = file.readString()
        val dataArray = rawData.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        songName = dataArray[0]
        songDuration = java.lang.Float.parseFloat(dataArray[1])
        keyTimeList.clear()
        for (i in 2 until dataArray.size) {
            val keyTimeData = dataArray[i].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val key = keyTimeData[0]
            val time = java.lang.Float.parseFloat(keyTimeData[1])
            keyTimeList.add(KeyTimePair(key, time))
        }
    }
}