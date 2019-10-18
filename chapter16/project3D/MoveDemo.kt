package chapter16.project3D

class MoveDemo : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(DemoScreen())
    }
}

