package chapter05.theMissingHomework

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Background(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    var hallway: Animation<TextureRegion>
    var classroom: Animation<TextureRegion>
    var scienceLab: Animation<TextureRegion>
    var library: Animation<TextureRegion>

    init {
        hallway = loadTexture("assets/bg-hallway.jpg")
        classroom = loadTexture("assets/bg-classroom.jpg")
        scienceLab = loadTexture("assets/bg-science-lab.jpg")
        library = loadTexture("assets/bg-library.jpg")
        setSize(800f, 600f)
    }
}
