package chapter08

class RectangleDestroyer : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(MenuScreen())
    }
}
