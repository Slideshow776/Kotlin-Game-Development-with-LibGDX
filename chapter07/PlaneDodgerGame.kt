package chapter07

class PlaneDodgerGame : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(MenuScreen())
    }
}
