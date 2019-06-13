package chapter6.rythmTapper

class RecorderGame : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(RecorderScreen())
    }
}