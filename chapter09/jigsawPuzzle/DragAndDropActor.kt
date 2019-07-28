package chapter09.jigsawPuzzle

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.TimeUtils
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

/*
* Enables drag-and-drop functionality
* */
open class DragAndDropActor(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    private val self: DragAndDropActor

    private var grabOffsetX: Float = 0f
    private var grabOffsetY: Float = 0f

    private var startPositionX: Float = 0f
    private var startPositionY: Float = 0f
    var isDraggable: Boolean = false
    var dropTarget: DropTargetActor? = null

    private var lastTapTime: Long = 0
    private var tapCountInterval = (0.4f * 1000000000L).toLong()

    init {
        self = this
        isDraggable = true

        addListener(
            object : ClickListener() {
                override fun touchDown(event: InputEvent?, eventOffsetX: Float, eventOffsetY: Float,
                                       pointer: Int, button: Int): Boolean {
                    if (!self.isDraggable)
                        return false

                    self.grabOffsetX = eventOffsetX
                    self.grabOffsetY = eventOffsetY

                    self.startPositionX = self.x
                    self.startPositionY = self.y

                    self.toFront()
                    self.addAction(Actions.scaleTo(1.1f, 1.1f, 0.25f))
                    self.onDragStart()

                    return true
                }

                override fun touchDragged(event: InputEvent?, eventOffsetX: Float, eventOffsetY: Float, pointer: Int) {
                    val cos = cos(self.rotation * MathUtils.degreesToRadians)
                    val sin = sin(self.rotation * MathUtils.degreesToRadians)

                    val tox = (eventOffsetX - self.grabOffsetX)
                    val toy = (eventOffsetY - self.grabOffsetY)

                    var deltaX = 0f
                    var deltaY = 0f

                    if (abs(cos) == 1f) {
                        deltaX = tox * cos
                        deltaY = toy * cos
                    } else {
                        deltaX = toy * -sin
                        deltaY = tox * sin
                    }

                    self.moveBy(deltaX, deltaY)
                }

                override fun touchUp(event: InputEvent?, eventOffsetX: Float, eventOffsetY: Float,
                                     pointer: Int, button: Int) {
                    self.dropTarget = null

                    var closestDistance = java.lang.Float.MAX_VALUE

                    for (actor in BaseActor.getList(self.stage, DropTargetActor::class.java.canonicalName)) {
                        val target = actor as DropTargetActor

                        if (target.isTargetable() && self.overlaps(target)) {
                            val currentDistance = Vector2.dst(self.x, self.y, target.x, target.y)

                            if (currentDistance < closestDistance) {
                                self.dropTarget = target
                                closestDistance = currentDistance
                            }
                        }
                    }

                    self.addAction(Actions.scaleTo(1.00f, 1.00f, 0.25f))
                    self.onDrop()

                    // double tap
                    val time = TimeUtils.nanoTime()
                    if (time - lastTapTime > tapCountInterval)
                        tapCount = 0
                    tapCount++
                    lastTapTime = time
                    if( tapCount == 2) // double click detected
                        doubleTap()
                }
            }
        )

    }
    fun hasDropTarget(): Boolean { return dropTarget != null }

    open fun onDragStart() {}
    open fun onDrop() {}
    open fun doubleTap() {}

    fun moveToActor(other: BaseActor) {
        val x = other.x + (other.width - this.width) / 2
        val y = other.y + (other.height - this.height) / 2
        addAction(Actions.moveTo(x, y, 0.50f, Interpolation.pow3))
    }

    fun moveToStart() {
        addAction(Actions.moveTo(startPositionX, startPositionY, 0.50f, Interpolation.pow3))
    }

    override fun act(dt: Float) {
        super.act(dt)
    }
}