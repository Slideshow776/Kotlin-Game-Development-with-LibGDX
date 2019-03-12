package chapter3

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array

class Turtle(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        val fileNames: Array<String> = Array<String>()
        fileNames.add("assets/turtle-1.png")
        fileNames.add("assets/turtle-2.png")
        fileNames.add("assets/turtle-3.png")
        fileNames.add("assets/turtle-4.png")
        fileNames.add("assets/turtle-5.png")
        fileNames.add("assets/turtle-6.png")

        loadAnimationFromFiles(fileNames, .1f, true)
    }
}