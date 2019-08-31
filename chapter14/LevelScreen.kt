package chapter14

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color

class LevelScreen : BaseScreen() {
    private lateinit var maze: Maze
    private lateinit var hero: Hero
    private lateinit var ghost: Ghost

    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/white.png")
        background.color = Color.GRAY
        background.setSize(768f, 700f)

        maze = Maze(mainStage)

        hero = Hero(0f, 0f, mainStage)
        hero.centerAtActor(maze.getRoom(0, 0)!!)

        ghost = Ghost(0f, 0f, mainStage)
        ghost.centerAtActor(maze.getRoom(11, 9)!!)
    }

    override fun update(dt: Float) {
        for (wall in BaseActor.getList(mainStage, Wall::class.java.canonicalName)) {
            hero.preventOverlap(wall)
        }

        if (ghost.actions.size == 0) {
            maze.resetRooms()
            ghost.findPath(maze.getRoom(ghost)!!, maze.getRoom(hero)!!)
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.R) {
            dispose()
            BaseGame.setActiveScreen(LevelScreen())
        }
        return false
    }
}
