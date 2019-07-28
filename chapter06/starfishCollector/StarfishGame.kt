package chapter06.starfishCollector

class StarfishGame: BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(MenuScreen())
    }
}
