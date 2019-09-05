# Maze Game
This chapter examines how to develop a maze-based game, where the character is to collect all the coins in a maze whilst avoiding a ghost. The chapter expositions algorithm on how to generate random mazes using a depth-first algorithm, as well as finding the shortest path to the character from  the ghost using a breadth-first algorithm

As suggested by the book the following features have been added:
* Added a menu screen
* Added more audio effects
* Added a timer to the UI
* Increasing ghost speed with time
* Hero health points
* Added more ghosts
* Implemented a larger maze

Bonus features:
* camera/character alignment
* camera zoom
* camera boundaries with zoom

![image](https://user-images.githubusercontent.com/4059636/64230245-8aee7d80-ceec-11e9-8ff1-414dbc0569d5.png)

## Maze Generation
Instead of using [Tiled](https://www.mapeditor.org) to create several maps a unique map may be generated each time a new level starts. Such procedural content generation greatly adds to replayability.

The general undertaking is as follows:
1. Create a rectangular grid of rooms, all surrounded with walls
2. Remove walls between rooms such that every room may be reached.
    1. Select a room as a starting location; mark it as connected and add it to the list
    2. While there are still rooms remaining in the list:
        1. Let `currentRoom` be the most recently added room from the list
        2. If `currentRoom` has any unconnected neighbors
            1. let `nextRoom` be a random unconnected neighbor of `currentRoom`
            2. remove the walls between `currentRoom` and `nextRoom`
            3. mark `nextRoom` as connected; and
            4. add `nextRoom` to the end of the list
        3. If `currentRoom` does not have any unconnected neighbors, remove it from the list
3. Remove additional random walls to create multiple paths to rooms.

This depth-first algorithm creates a random path through the maze for as long as possible and then starts anew.

## Pathfinding
The ghost follows the shortest path to the character. All rooms that are one square away will be checked first, and so forth. This is a breadth-first search algorithm.
Pseudocode for the algorithm:
1. Identify `startRoom` and `targetRoom`.
2. Set `currentRoom` equal to `startRoom`.
3. Mark `currentRoom` as visited, set its previous room to `null`, and add `currentRoom` to a list.
4. While the list is not empty, do the following:
    1. Set `currentRoom`to the first element in the list and remove it from the list.
    2. For each unvisited neighbor (called `nextRoom`) of `currentRoom`,
        1. set `nextRoom`'s previous room to `currentRoom`.
        2. end the algorithm if `nextRoom` is `targetRoom`; and
        3. if `nextRoom` is not `targetRoom`, then mark `nextRoom` as visited and add `nextRoom`to the end of the list.

## Camera
A new camera functionality was added to `BaseActor`, the ability to zoom:
```
fun zoomCamera(zoom: Float) {
    if (this.stage != null) {
        val camera = this.stage.camera as OrthographicCamera
        camera.zoom = zoom

        bindCameraToWorld(camera)
        camera.update()
    }
}
```

This required an update to the `bindCameraToWorld` function, as it now also must take into account the zoom level:
```
private fun bindCameraToWorld(camera: OrthographicCamera) {
    camera.position.x = MathUtils.clamp(
        camera.position.x,
        (camera.viewportWidth * camera.zoom) / 2,
        worldBounds.width - (camera.viewportWidth * .9f) / 2
    )
    camera.position.y = MathUtils.clamp(
        camera.position.y,
        (camera.viewportHeight * camera.zoom) / 2,
        worldBounds.height - (camera.viewportHeight * .9f) / 2
    )
}
```

## New Imports
No new imports