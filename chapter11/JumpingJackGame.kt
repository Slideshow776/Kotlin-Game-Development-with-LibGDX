package chapter11

class JumpingJackGame : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(MenuScreen())
    }
}
