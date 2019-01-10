package chapter2

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input

class Turtle : ActorBeta {
    constructor() : super()

    override fun act(dt: Float) {
        super.act(dt)

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            this.moveBy(-1f, 0f)
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            this.moveBy(1f, 0f)
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            this.moveBy(0f, 1f)
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            this.moveBy(0f, -1f)
    }
}