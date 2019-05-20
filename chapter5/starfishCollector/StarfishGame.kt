package chapter5

import chapter5.starfishCollector.BaseGame

class StarfishGame: BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(MenuScreen())
    }
}
