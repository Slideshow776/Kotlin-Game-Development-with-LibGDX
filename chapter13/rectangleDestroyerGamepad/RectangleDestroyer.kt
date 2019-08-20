package chapter13.rectangleDestroyerGamepad

class RectangleDestroyer : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(MenuScreen())
    }
}
