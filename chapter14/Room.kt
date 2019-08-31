package chapter14

import com.badlogic.gdx.scenes.scene2d.Stage
import kotlin.math.floor

class Room(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    companion object {
        const val NORTH: Int = 0
        const val SOUTH: Int = 1
        const val EAST: Int = 2
        const val WEST: Int = 3
        val directionArray = listOf<Int>(NORTH, SOUTH, EAST, WEST)
    }

    private var wallArray = arrayOfNulls<Wall>(4)
    private var neighborArray = arrayOfNulls<Room>(4)

    var connected = false

    var visited = false
    var previousRoom: Room? = null

    init {
        loadTexture("assets/dirt.png")

        val wallThickness = 6f // value is in pixels

        wallArray = arrayOfNulls(4)
        wallArray[SOUTH] = Wall(x, y, width, wallThickness, s)
        wallArray[WEST] = Wall(x, y, wallThickness, height, s)
        wallArray[NORTH] = Wall(x, y + height - wallThickness, width, wallThickness, s)
        wallArray[EAST] =  Wall(x + width - wallThickness, y, wallThickness, height, s)

        neighborArray = arrayOfNulls(4)
        // contents of this array will be initialized by Maze class
    }

    fun setNeighbor(direction: Int, neighbor: Room) {
        neighborArray[direction] = neighbor
    }

    fun hasNeighbor(direction: Int): Boolean {
        return neighborArray[direction] != null
    }

    fun getNeighbor(direction: Int): Room? {
        return neighborArray[direction]
    }

    // check if wall in this direction still exists (has not yet been removed from stage)
    fun hasWall(direction: Int): Boolean {
        return wallArray[direction]?.stage != null
    }

    fun removeWalls(direction: Int) {
        neighborArray[direction]?.let { removeWallsBetween(it) }
    }

    fun removeWallsBetween(other: Room?) {
        when (other) {
            neighborArray[NORTH] -> {
                this.wallArray[NORTH]?.remove()
                other!!.wallArray[SOUTH]?.remove()
            }
            neighborArray[SOUTH] -> {
                this.wallArray[SOUTH]?.remove()
                other!!.wallArray[NORTH]?.remove()
            }
            neighborArray[EAST] -> {
                this.wallArray[EAST]?.remove()
                other!!.wallArray[WEST]?.remove()
            }
            else -> { // (other == neighborArray[WEST])
                this.wallArray[WEST]?.remove()
                other!!.wallArray[EAST]?.remove()
            }
        }
    }

    fun hasUnconnectedNeighbor(): Boolean {
        for (direction in directionArray) {
            if (hasNeighbor(direction) && !getNeighbor(direction)?.connected!!)
                return true
        }
        return false
    }

    fun getRandomUnconnectedNeighbor(): Room? {
        val directionList = ArrayList<Int>()

        for (direction in directionArray) {
            if (hasNeighbor(direction) && !getNeighbor(direction)?.connected!!)
                directionList.add(direction)
        }

        val directionIndex = floor(Math.random() * directionList.size).toInt()
        val direction = directionList[directionIndex]
        return getNeighbor(direction)
    }

    // ghost
    fun unvisitedPathList(): ArrayList<Room> {
        val list = ArrayList<Room>()
        for (direction in directionArray) {
            if (hasNeighbor(direction) && !hasWall(direction) && !getNeighbor(direction)?.visited!!)
                getNeighbor(direction)?.let { list.add(it) }
        }
        return list
    }
}
