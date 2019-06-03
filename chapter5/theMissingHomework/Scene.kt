package chapter5.theMissingHomework

import chapter5.theMissingHomework.SceneSegment
import com.badlogic.gdx.scenes.scene2d.Actor
import java.util.ArrayList

class Scene : Actor() {
    private val segmentList: ArrayList<SceneSegment>
    private var index: Int = 0

    val isSegmentFinished: Boolean
        get() = segmentList[index].isFinished()

    val isLastSegment: Boolean
        get() = index >= segmentList.size - 1

    val isSceneFinished: Boolean
        get() = isLastSegment && isSegmentFinished

    init {
        segmentList = ArrayList()
        index = -1
    }

    fun addSegment(segment: SceneSegment) {
        segmentList.add(segment)
    }

    fun clearSegments() {
        segmentList.clear()
    }

    fun start() {
        index = 0
        segmentList[index].start()
    }

    override fun act(dt: Float) {
        if (isSegmentFinished && !isLastSegment)
            loadNextSegment()
    }

    fun loadNextSegment() {
        if (isLastSegment)
            return

        segmentList[index].finish()
        index++
        segmentList[index].start()
    }
}
