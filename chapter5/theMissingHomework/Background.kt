package chapter5.theMissingHomework

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.graphics.g2d.Animation

class Background(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    var hallway: Animation<*>
    var classroom: Animation<*>
    var scienceLab: Animation<*>
    var library: Animation<*>

    init {
        hallway = loadTexture("assets/bg-hallway.jpg")
        classroom = loadTexture("assets/bg-classroom.jpg")
        scienceLab = loadTexture("assets/bg-science-lab.jpg")
        library = loadTexture("assets/bg-library.jpg")
        setSize(800f, 600f)
    }
}
