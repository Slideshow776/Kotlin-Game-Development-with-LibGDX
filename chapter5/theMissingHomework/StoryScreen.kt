package chapter5.theMissingHomework

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type


class StoryScreen : BaseScreen() {

    lateinit var scene: Scene
    lateinit var background: Background
    lateinit var kelsoe: Kelsoe
    lateinit var dialogBox: DialogBox
    lateinit var continueKey: BaseActor
    lateinit var buttonTable: Table
    lateinit var theEnd: BaseActor

    var haveKey = false

    override fun initialize() {
        background = Background(0f, 0f, mainStage)
        background.setOpacity(0f)
        BaseActor.setWorldBounds(background)

        kelsoe = Kelsoe(0f, 0f, mainStage)

        dialogBox = DialogBox(0f, 0f, mainStage)
        dialogBox.setDialogSize(600f, 150f)
        dialogBox.setBackgroundColor(Color(.2f, .2f, .2f, 1f))
        dialogBox.isVisible = false

        continueKey = BaseActor(0f, 0f, uiStage)
        continueKey.loadTexture("assets/key-C.png")
        continueKey.setSize(32f, 32f)
        continueKey.isVisible = false

        dialogBox.addActor(continueKey)
        continueKey.setPosition(dialogBox.width - continueKey.width, 0f)

        buttonTable = Table()
        buttonTable.isVisible = false

        uiTable.add().expandY
        uiTable.row()
        uiTable.add(buttonTable)
        uiTable.row()
        uiTable.add(dialogBox)
        uiTable.bottom()

        theEnd = BaseActor(0f, 0f, mainStage)
        theEnd.loadTexture("assets/the-end.png")
        theEnd.centerAtActor(background)
        theEnd.setScale(2f)
        theEnd.setOpacity(0f)

        scene = Scene()
        mainStage.addActor(scene)
        hallway()
    }

    fun hallway() {
        background.setAnimation(background.hallway)
        dialogBox.setText("")
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0f))

        scene.addSegment(SceneSegment(background, Actions.fadeIn(1f)))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1f)))
        scene.addSegment(SceneSegment(dialogBox, Actions.show()))

        addTextSequence("My name is Kelsoe Kismet. I am a student at Aureus Ludus Academy.")
        addTextSequence("I can be a little forgetful sometimes. Right now, I'm looking for my homework.")

        scene.addSegment(SceneSegment(dialogBox, Actions.hide()))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToOutsideRight(1f)))
        scene.addSegment(SceneSegment(background, Actions.fadeOut(1f)))

        scene.addSegment(SceneSegment(background, Actions.run { classroom() }))

        scene.start()
    }

    fun classroom() {
        scene.clearSegments()
        background.setAnimation(background.classroom)
        dialogBox.setText("")
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0f))

        scene.addSegment(SceneSegment(background, Actions.fadeIn(1f)))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1f)))
        scene.addSegment(SceneSegment(dialogBox, Actions.show()))

        addTextSequence("This is my classroom. My homework isn't here though.")
        addTextSequence("Where should I go look for my homework next?")

        scene.addSegment(SceneSegment(buttonTable, Actions.show()))

        // Set up options
        val scienceLabButton = TextButton("Look in the Science Lab", BaseGame.textButtonStyle)
        scienceLabButton.addListener { e: Event ->
            if (e is InputEvent && e.type == Type.touchDown) {
                scene.addSegment(SceneSegment(buttonTable, Actions.hide()))
                addTextSequence("That's a great idea. I'll check the science lab.")
                scene.addSegment(SceneSegment(dialogBox, Actions.hide()))
                scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToOutsideLeft(1f)))
                scene.addSegment(SceneSegment(background, Actions.fadeOut(1f)))
                scene.addSegment(SceneSegment(background, Actions.run { scienceLab() }))
            }
            false
        }

        val libraryButton = TextButton("Look in the Library", BaseGame.textButtonStyle)
        libraryButton.addListener { e: Event ->
            if (e is InputEvent && e.type == Type.touchDown) {
                scene.addSegment(SceneSegment(buttonTable, Actions.hide()))
                addTextSequence("That's a great idea. Maybe I left it in the library.")
                scene.addSegment(SceneSegment(dialogBox, Actions.hide()))
                scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToOutsideLeft(1f)))
                scene.addSegment(SceneSegment(background, Actions.fadeOut(1f)))
                scene.addSegment(SceneSegment(background, Actions.run { library() }))
            }
            false
        }

        buttonTable.clearChildren()
        buttonTable.add(scienceLabButton)
        buttonTable.row()
        buttonTable.add(libraryButton)

        scene.start()
    }

    fun scienceLab() {
        scene.clearSegments()

        background.setAnimation(background.scienceLab)
        dialogBox.setText("")
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0f))

        scene.addSegment(SceneSegment(background, Actions.fadeIn(1f)))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1f)))
        scene.addSegment(SceneSegment(dialogBox, Actions.show()))

        addTextSequence(("This is the science lab."))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.sad)))
        addTextSequence("My homework isn't here, though")
        scene.addSegment(SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.normal)))
        addTextSequence("Wait... this key is mine!")
        addTextSequence("Where should I go?")

        scene.addSegment(SceneSegment(buttonTable, Actions.show()))

        // Set up options
        val classroomButton = TextButton("Return to the Classroom", BaseGame.textButtonStyle)
        classroomButton.addListener { e: Event ->
            if (e is InputEvent && e.type == Type.touchDown) {
                scene.addSegment(SceneSegment(buttonTable, Actions.hide()))
                addTextSequence("Maybe someone found it and put it in the classroom. I'll go check")
                scene.addSegment(SceneSegment(dialogBox, Actions.hide()))
                scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToOutsideRight(1f)))
                scene.addSegment(SceneSegment(background, Actions.fadeOut(1f)))
                scene.addSegment(SceneSegment(background, Actions.run { classroom() }))
            }
            false
        }

        val libraryButton = TextButton("Look in the library", BaseGame.textButtonStyle)
        libraryButton.addListener { e: Event ->
            if (e is InputEvent && e.type == Type.touchDown) {
                scene.addSegment(SceneSegment(buttonTable, Actions.hide()))
                addTextSequence("That's a great idea. Maybe I left it in the library.")
                scene.addSegment(SceneSegment(dialogBox, Actions.hide()))
                scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToOutsideRight(1f)))
                scene.addSegment(SceneSegment(background, Actions.fadeOut(1f)))
                scene.addSegment(SceneSegment(background, Actions.run { library() }))
            }
            false
        }

        val takeKeyButton = TextButton("Take key", BaseGame.textButtonStyle)
        takeKeyButton.addListener {e: Event ->
            if (e is InputEvent && e.type == Type.touchDown) {
                scene.addSegment(SceneSegment(buttonTable, Actions.hide()))

                addTextSequence("I'm so forgetful.")
                scene.addSegment(SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.sad)))
                scene.addSegment(SceneSegment(background, Actions.delay(.5f)))
                scene.addSegment(SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.embarrased)))
                scene.addSegment(SceneSegment(background, Actions.delay(1f)))

                scene.addSegment((SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.lookLeft))))
                scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenLeft(2f)))
                scene.addSegment(SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.normal)))
                addTextSequence("I got the key.")
                scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenCenter(.5f)))
                addTextSequence("Where should I go?")

                scene.addSegment(SceneSegment(buttonTable, Actions.show()))
                haveKey = true
                buttonTable.removeActor(takeKeyButton)
            }
            false
        }

        buttonTable.clearChildren()
        buttonTable.add(classroomButton)
        buttonTable.row()
        if (!haveKey) {
            buttonTable.add(takeKeyButton)
            buttonTable.row()
        }
        buttonTable.add(libraryButton)

        scene.start()
    }

    fun library() {
        scene.clearSegments()

        background.setAnimation(background.library)
        dialogBox.setText("")
        kelsoe.addAction(SceneActions.moveToOutsideLeft(0f))

        scene.addSegment(SceneSegment(background, Actions.fadeIn(1f)))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenCenter(1f)))
        scene.addSegment(SceneSegment(dialogBox, Actions.show()))

        addTextSequence("This is the library.")
        addTextSequence("Let me check the table where I was working earlier...")
        scene.addSegment(SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.lookRight)))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenRight(2f)))
        scene.addSegment(SceneSegment(kelsoe, SceneActions.setAnimation(kelsoe.normal)))
        addTextSequence("Aha! Here it is!")

        if (haveKey) {
            addTextSequence("It's locked, thankfully I have my key.")
            scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenCenter(.5f)))
            addTextSequence("Thanks for helping me find it!")
            scene.addSegment(SceneSegment(dialogBox, Actions.hide()))

            scene.addSegment(SceneSegment(theEnd, Actions.fadeIn(4f)))
            scene.addSegment(SceneSegment(background, Actions.delay(5f)))
            scene.addSegment(SceneSegment(background, Actions.run { BaseGame.setActiveScreen(MenuScreen()) }))
        } else {
            addTextSequence("It's locked!")
            addTextSequence("I need to get my key, where could it be?")
            scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToScreenCenter(.5f)))
            addTextSequence("Where should I go next?")

            scene.addSegment(SceneSegment(buttonTable, Actions.show()))
            // Set up options
            val scienceLabButton = TextButton("Look in the Science Lab", BaseGame.textButtonStyle)
            scienceLabButton.addListener { e: Event ->
                if (e is InputEvent && e.type == Type.touchDown) {
                    scene.addSegment(SceneSegment(buttonTable, Actions.hide()))
                    addTextSequence("That's a great idea. I'll check the science lab.")
                    scene.addSegment(SceneSegment(dialogBox, Actions.hide()))
                    scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToOutsideLeft(1f)))
                    scene.addSegment(SceneSegment(background, Actions.fadeOut(1f)))
                    scene.addSegment(SceneSegment(background, Actions.run { scienceLab() }))
                }
                false
            }

            val classroomButton = TextButton("Look in the Classroom", BaseGame.textButtonStyle)
            classroomButton.addListener { e: Event ->
                if (e is InputEvent && e.type == Type.touchDown) {
                    scene.addSegment(SceneSegment(buttonTable, Actions.hide()))
                    addTextSequence("That's a great idea. Maybe I left it in the Classroom.")
                    scene.addSegment(SceneSegment(dialogBox, Actions.hide()))
                    scene.addSegment(SceneSegment(kelsoe, SceneActions.moveToOutsideLeft(1f)))
                    scene.addSegment(SceneSegment(background, Actions.fadeOut(1f)))
                    scene.addSegment(SceneSegment(background, Actions.run { classroom() }))
                }
                false
            }

            buttonTable.clearChildren()
            buttonTable.add(scienceLabButton)
            buttonTable.row()
            buttonTable.add(classroomButton)
        }

        scene.start()
    }

    override fun update(dt: Float) {}

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.C) {
            scene.loadNextSegment()
        }
        return false
    }

    fun addTextSequence(s: String) {
        scene.addSegment(SceneSegment(dialogBox, SceneActions.typeWriter(s)))
        scene.addSegment(SceneSegment(continueKey, Actions.show()))
        scene.addSegment(SceneSegment(background, SceneActions.pause()))
        scene.addSegment(SceneSegment(continueKey, Actions.hide()))
    }
}
