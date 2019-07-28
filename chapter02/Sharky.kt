package chapter02

import kotlin.random.Random.Default.nextBoolean

class Sharky : ActorBeta() {

    private var timeX = 0
    private var randomTimeX = (60..360).random()
    private var randomDirectionX = nextBoolean()

    private var timeY = 0
    private var randomTimeY = (60..360).random()
    private var randomDirectionY = nextBoolean()

    private var flip: Boolean = false

    override fun act(dt: Float) {
        super.act(dt)
        moveX()
        moveY()
        textureRegion.flip(true, false)
        if (flip)
            textureRegion.flip(!textureRegion.isFlipX, textureRegion.isFlipY)
        else
            textureRegion.flip(textureRegion.isFlipX, textureRegion.isFlipY)
    }

    private fun moveX() {
        if (timeX < randomTimeX) {
            timeX++
        } else {
            randomTimeX = (60..360).random()
            timeX = 0
            randomDirectionX = !randomDirectionX
        }

        if (randomDirectionX && x < 750) {
            this.moveBy(1f, 0f)
            if (flip) flip = false
        }
        else if (!randomDirectionX && x > 50) {
            this.moveBy(-1f, 0f)
            if (!flip) flip = true
        }
    }

    private fun moveY() {
        if (timeY < randomTimeY) {
            timeY++
        } else {
            randomTimeY = (60..360).random()
            timeY = 0
            randomDirectionY = !randomDirectionY
        }

        if (randomDirectionY && y < 550)
            this.moveBy(0f, 1f)
        else if (!randomDirectionY && y > 50)
            this.moveBy(0f, -1f)
    }
}