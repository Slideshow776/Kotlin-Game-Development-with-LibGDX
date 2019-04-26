package chapter4

class LevelScreen : BaseScreen() {

    override fun initialize() {
        var space = BaseActor(0f, 0f, mainStage)
        space.loadTexture("assets/space.png")
        space.setSize(800f, 600f)

        BaseActor.setWorldBounds(space)

        var spaceship = Spaceship(400f, 300f, mainStage)
    }

    override fun update(dt: Float) {}
}
