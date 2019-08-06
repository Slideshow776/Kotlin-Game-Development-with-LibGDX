package chapter11

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label


class InstructionsScreen: BaseScreen() {
    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/overlay.png")
        background.width = 800f
        background.height = 640f

        val label = Label("Collect the key to get to the flag!", BaseGame.labelStyle)
        label.color = Color.RED
        val group = Group()
        group.addActor(label)
        group.addAction(Actions.sequence(
            Actions.scaleTo(.75f, .75f, 0f),
            Actions.delay(2f),
            Actions.run { BaseGame.setActiveScreen(LevelScreen()) }
        ));
        label.setPosition( group.width / 2f - label.width / 2f, group.height)

        uiTable.add(group)
    }

    override fun update(dt: Float) {}
}
