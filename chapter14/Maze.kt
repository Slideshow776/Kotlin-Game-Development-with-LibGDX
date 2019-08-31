package chapter14

import com.badlogic.gdx.scenes.scene2d.Stage
import kotlin.math.floor
import kotlin.math.roundToInt

class Maze(s: Stage) {
    // maze size constants
    private val roomCountX = 12
    private val roomCountY = 10
    private val roomWidth = 64
    private val roomHeight = 64

    // private var roomGrid = arrayOfNulls<arrayOfNulls<Room>>(4)
    private var roomGrid = Array(roomCountX) { arrayOfNulls<Room>(roomCountY) }

    init {
        for (gridY in 0 until roomCountY) {
            for (gridX in 0 until roomCountX) {
                val pixelX = gridX * roomWidth
                val pixelY = gridY * roomHeight
                val room = Room(pixelX.toFloat(), pixelY.toFloat(), s)
                roomGrid[gridX][gridY] = room
            }
        }

        // neighbor relations
        for (gridY in 0 until roomCountY) {
            for (gridX in 0 until roomCountX) {
                val room = roomGrid[gridX][gridY]
                if (gridY > 0)
                    roomGrid[gridX][gridY - 1]?.let { room?.setNeighbor(Room.SOUTH, it) }
                if (gridY < roomCountY - 1)
                    roomGrid[gridX][gridY + 1]?.let { room?.setNeighbor(Room.NORTH, it) }
                if (gridX > 0)
                    roomGrid[gridX - 1][gridY]?.let { room?.setNeighbor(Room.WEST, it) }
                if (gridX < roomCountX - 1)
                    roomGrid[gridX + 1][gridY]?.let { room?.setNeighbor(Room.EAST, it) }
            }
        }

        val activeRoomList = ArrayList<Room>()

        var currentRoom = roomGrid[0][0]
        currentRoom!!.connected = true
        activeRoomList.add(0, currentRoom)

        // chance of returning to a random connected room to create a branching path from that room
        val branchProbability = .5f

        while (activeRoomList.size > 0) {
            currentRoom = if (Math.random() < branchProbability) {
                // get random previously visited room
                val roomIndex = (Math.random() * activeRoomList.size).toInt()
                activeRoomList[roomIndex]
            } else {
                // get the most recently visited room
                activeRoomList[activeRoomList.size - 1]
            }

            if (currentRoom.hasUnconnectedNeighbor()) {
                val nextRoom = currentRoom.getRandomUnconnectedNeighbor()
                currentRoom.removeWallsBetween(nextRoom)
                nextRoom?.connected = true
                activeRoomList.add(0, nextRoom!!)
            } else {
                // this room has no more adjacent unconnected rooms so there is no reason to keep it in the list
                activeRoomList.remove(currentRoom)
            }
        }

        var wallsToRemove = 24
        while (wallsToRemove > 0) {
            val gridX = floor(Math.random() * roomCountX).toInt()
            val gridY = floor(Math.random() * roomCountY).toInt()
            val direction = floor(Math.random() * 4).toInt()
            val room = roomGrid[gridX][gridY]

            if (room!!.hasNeighbor(direction) && room.hasWall(direction)) {
                room.removeWalls(direction)
                wallsToRemove--
            }

        }
    }

    fun getRoom(gridX: Int, gridY: Int): Room? {
        return roomGrid[gridX][gridY]
    }

    // ghost
    fun getRoom(actor: BaseActor): Room? {
        val gridX = (actor.x / roomWidth).roundToInt()
        val gridY = (actor.y / roomHeight).roundToInt()
        return getRoom(gridX, gridY)
    }

    fun resetRooms() {
        for (gridY in 0 until roomCountY) {
            for (gridX in 0 until roomCountX) {
                roomGrid[gridX][gridY]!!.visited = false
                roomGrid[gridX][gridY]!!.previousRoom = null
            }
        }

    }
}
