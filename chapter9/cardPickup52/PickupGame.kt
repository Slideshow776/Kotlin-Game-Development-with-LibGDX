package chapter9.cardPickup52

class PickupGame : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }
}
