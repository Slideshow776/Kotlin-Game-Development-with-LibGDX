package chapter14

class MazeGame : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }
}
