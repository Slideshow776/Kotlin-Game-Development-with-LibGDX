package chapter09.crazyEights

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions


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

    init {
        self = this
        isDraggable = true

        addListener(
            object : InputListener() {
                override fun touchDown(
                    event: InputEvent?,
                    eventOffsetX: Float,
                    eventOffsetY: Float,
                    pointer: Int,
                    button: Int
                ): Boolean {
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
                    val deltaX = eventOffsetX - self.grabOffsetX
                    val deltaY = eventOffsetY - self.grabOffsetY

                    self.moveBy(deltaX, deltaY)
                }

                override fun touchUp(
                    event: InputEvent?,
                    eventOffsetX: Float,
                    eventOffsetY: Float,
                    pointer: Int,
                    button: Int
                ) {
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
                }
            }
        )

    }
    fun hasDropTarget(): Boolean { return dropTarget != null }

    open fun onDragStart() {}
    open fun onDrop() {}

    fun moveToActor(other: BaseActor, duration: Float = .5f, interpolation: Interpolation = Interpolation.pow3) {
        val x = other.x + (other.width - this.width) / 2
        val y = other.y + (other.height - this.height) / 2
        addAction(Actions.moveTo(x, y, duration, interpolation))
    }

    fun moveToStart() {
        addAction(Actions.moveTo(startPositionX, startPositionY, 0.50f, Interpolation.pow3))
    }

    override fun act(dt: Float) {
        super.act(dt)
    }
}