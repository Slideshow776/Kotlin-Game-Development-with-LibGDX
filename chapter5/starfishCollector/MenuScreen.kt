package chapter5

import chapter5.starfishCollector.BaseGame
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys

class MenuScreen: BaseScreen() {
    override fun initialize() {
        var ocean = BaseActor(0f, 0f, mainStage)
        ocean.loadTexture("assets/water.jpg")
        ocean.setSize(800f, 600f)

        var title = BaseActor(0f, 0f, mainStage)
        title.loadTexture("assets/starfish-collector.png")
        title.centerAtPosition(400f, 300f)
        title.moveBy(0f, 100f)

        var start = BaseActor(0f, 0f, mainStage)
        start.loadTexture("assets/message-start.png")
        start.centerAtPosition(400f, 300f)
        start.moveBy(0f, -100f)
    }

    override fun update(dt: Float) {
        if (Gdx.input.isKeyPressed(Keys.S)) {
            BaseGame.setActiveScreen(LevelScreen())
        }
    }
}