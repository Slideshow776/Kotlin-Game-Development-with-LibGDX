package chapter14

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions

class Ghost(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    var actorSpeed = 60f

    init {
        loadAnimationFromSheet("assets/ghost.png", 1, 3, .2f, true)
        setOpacity(.8f)
    }

    fun findPath(startRoom: Room, targetRoom: Room) {
        var currentRoom: Room? = startRoom

        val roomList = ArrayList<Room>()
        currentRoom!!.previousRoom = null
        currentRoom.visited = true
        roomList.add(currentRoom)

        while (roomList.size > 0) {
            currentRoom = roomList.removeAt(0)
            for (nextRoom in currentRoom.unvisitedPathList()) {
                nextRoom.previousRoom = currentRoom
                if (nextRoom == targetRoom) {
                    roomList.clear() // target found!!
                    break
                } else {
                    nextRoom.visited = true
                    roomList.add(nextRoom)
                }
            }
        }

        // create list of rooms corresponding to shortest path
        val pathRoomList = ArrayList<Room>()
        currentRoom = targetRoom

        while (currentRoom != null) {
            // add current room to beginning of list
            pathRoomList.add(0, currentRoom)
            currentRoom = currentRoom.previousRoom
        }

        // only move along a few steps of the path;
        // path will be recalculated when these actions are complete
        val maxStepCount = 2

        // to remove the pause between steps, start loop index at 1
        // but make ghost speed slower to compensate
        for (i in pathRoomList.indices) {
            if (i == maxStepCount)
                break
            val nextRoom = pathRoomList[i]
            val move = Actions.moveTo(nextRoom.x, nextRoom.y, 64 / actorSpeed, Interpolation.linear)
            addAction(move)
        }
    }
}
