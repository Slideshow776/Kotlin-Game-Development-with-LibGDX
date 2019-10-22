package chapter16.rectangleDestroyer3D

class Brick(x: Float, y: Float, z: Float, s: Stage3D) : Box(x, y, z, s) {
    init {
        loadTexture("assets/brick-gray.png")
        setScale(1.15f, .5f, .5f)
        setBaseRectangle()
    }
}

