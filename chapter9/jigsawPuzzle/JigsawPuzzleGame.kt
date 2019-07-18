package chapter9.jigsawPuzzle

class JigsawPuzzleGame : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }
}
