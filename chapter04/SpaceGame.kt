package chapter04

class SpaceGame : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(MenuScreen())
    }
}
