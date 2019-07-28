package chapter03

class StarfishGame: BaseGame() {
    override fun create() {
        setActiveScreen(MenuScreen())
    }
}