package chapter7

class LevelScreen : BaseScreen() {
    override fun initialize() {
        Sky(0f, 0f, mainStage)
        Sky(800f, 0f, mainStage)
        Ground(0f, 0f, mainStage)
        Ground(800f, 0f, mainStage)
    }

    override fun update(dt: Float) {

    }
}
