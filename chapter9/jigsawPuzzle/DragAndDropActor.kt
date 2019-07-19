package chapter9.jigsawPuzzle

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions


/*
* Enables drag-and-drop functionality
* */
class DragAndDropActor(x: Float, y: Float, s: Stage): BaseActor(x, y, s) {
    private val self = this

    private var grabOffsetX = 0f
    private var grabOffsetY = 0f

    private var dropTarget: DropTargetActor? = null

    private var draggable = true

    private var startPositionX = self.x
    private var startPositionY = self.y

    init {
        addListener(
            object : InputListener() {
                override fun touchDown(event: InputEvent, eventOffsetX: Float,
                                       eventOffsetY: Float, pointer: Int, button: Int): Boolean {
                    if (!self.isDraggable())
                        return false
                    self.grabOffsetX = eventOffsetX
                    self.grabOffsetY = eventOffsetY

                    self.toFront()

                    self.addAction(Actions.scaleTo(1.1f, 1.1f, .25f))

                    self.onDragStart()

                    return true // returning true indicates other touch methods are called
                }

                override fun touchDragged(event: InputEvent, eventOffsetX: Float, eventOffsetY: Float, pointer: Int) {
                    val deltaX = eventOffsetX - self.grabOffsetX
                    val deltaY = eventOffsetY - self.grabOffsetY

                    self.moveBy(deltaX, deltaY)
                }

                override fun touchUp(event: InputEvent, eventOffsetX: Float, eventOffsetY: Float, pointer: Int, button: Int) {
                    self.dropTarget = null

                    // keep track of distance to closest object
                    var closestDistance = Float.MAX_VALUE

                    for (actor: BaseActor in BaseActor.getList(self.stage, DropTargetActor::class.java.canonicalName)) {
                        val target = actor as DropTargetActor

                        if (target.isTargetable() && self.overlaps(target)) {
                            val currentDistance = Vector2.dst(self.x, self.y, target.x, target.y)

                            // check if this target is even closer
                            if (currentDistance < closestDistance) {
                                self.dropTarget = target
                                closestDistance = currentDistance
                            }
                        }
                    }
                    self.addAction(Actions.scaleTo(1f, 1f, .25f))

                    self.onDrop()
                }
            }
        )
    }

    override fun act(dt: Float) {
        super.act(dt)
    }

    fun hasDropTarget():Boolean { return dropTarget != null }
    fun setDropTarget(dt: DropTargetActor) { dropTarget = dt }
    fun getDropTarget(): DropTargetActor? { return dropTarget }
    fun setDraggable(d: Boolean) { draggable = d }
    fun isDraggable(): Boolean { return draggable }
    fun onDragStart() {}
    fun onDrop() {}

    fun moveToActor(other: BaseActor) {
        val x = other.x + (other.width - this.width) / 2
        val y = other.y + (other.height - this.height) / 2
        addAction(Actions.moveTo(x, y, .5f, Interpolation.pow3))
    }

    fun moveToStart() {
        addAction(Actions.moveTo(startPositionX, startPositionY, .5f, Interpolation.pow3))
    }
}