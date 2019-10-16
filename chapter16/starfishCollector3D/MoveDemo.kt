package chapter16.starfishCollector3D

import chapter16.starfishCollector3D.BaseGame
import chapter16.starfishCollector3D.DemoScreen

class MoveDemo : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(DemoScreen())
    }
}

