package chapter16.rectangleDestroyer3D

class RectangleDestroyer3D : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }
}
