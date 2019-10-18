package chapter16.starfishCollector3D

class StarfishCollector3DGame : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }
}
