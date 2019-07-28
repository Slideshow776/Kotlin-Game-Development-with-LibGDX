package chapter06.spaceRocks

class SpaceGame : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(MenuScreen())
    }
}
