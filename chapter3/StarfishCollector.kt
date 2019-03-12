package chapter3

class StarfishCollector: GameBeta() {

    private lateinit var turtle: Turtle
    private lateinit var starfish: Starfish
    private lateinit var ocean: BaseActor

    override fun initialize() {
        ocean = BaseActor(0.toFloat(), 0.toFloat(), mainStage)
        ocean.loadTexture("assets/water.jpg")
        ocean.setSize(800.toFloat(), 600.toFloat())

        starfish = Starfish(380.toFloat(), 380.toFloat(), mainStage)
        turtle = Turtle(20.toFloat(), 20.toFloat(), mainStage)
    }

    override fun update(dt: Float) {
        // code will be added later
    }
}