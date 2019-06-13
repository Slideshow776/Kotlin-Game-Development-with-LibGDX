package chapter6.rythmTapper

class RythmGame: BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(RythmScreen())
    }
}
