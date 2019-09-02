package chapter14

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label

class LevelScreen : BaseScreen() {
    private lateinit var maze: Maze
    private lateinit var hero: Hero
    private lateinit var ghost: Ghost

    lateinit var coinsLabel: Label
    lateinit var messageLabel: Label

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

        for (room in BaseActor.getList(mainStage, Room::class.java.canonicalName)) {
            val coin = Coin(0f, 0f, mainStage)
            coin.centerAtActor(room)
        }

        ghost.toFront()

        coinsLabel = Label("Coins left:", BaseGame.labelStyle)
        coinsLabel.color = Color.GOLD
        messageLabel = Label("...", BaseGame.labelStyle)
        messageLabel.setFontScale(2f)
        messageLabel.isVisible = false

        uiTable.pad(10f)
        uiTable.add(coinsLabel)
        uiTable.row()
        uiTable.add(messageLabel).expandY()
    }

    override fun update(dt: Float) {
        for (wall in BaseActor.getList(mainStage, Wall::class.java.canonicalName)) {
            hero.preventOverlap(wall)
        }

        if (ghost.actions.size == 0) {
            maze.resetRooms()
            ghost.findPath(maze.getRoom(ghost)!!, maze.getRoom(hero)!!)
        }

        for (coin in BaseActor.getList(mainStage, Coin::class.java.canonicalName)) {
            if (hero.overlaps(coin))
                coin.remove()
        }

        val coins = BaseActor.count(mainStage, Coin::class.java.canonicalName)
        coinsLabel.setText("Coins left: $coins")

        if (coins == 0) {
            ghost.remove()
            ghost.setPosition(-1000f, -1000f)
            ghost.clearActions()
            ghost.addAction(Actions.forever(Actions.delay(1f)))
            messageLabel.setText("You win!")
            messageLabel.setColor(Color.GREEN)
            messageLabel.isVisible = true
        }

        if (hero.overlaps(ghost)) {
            hero.remove()
            hero.setPosition(-1000f, -1000f)
            ghost.addAction(Actions.forever(Actions.delay(1f)))
            messageLabel.setText("Game Over")
            messageLabel.color = Color.RED
            messageLabel.isVisible = true
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
