package chapter6.rhythmTapper

class RecorderGame : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(RecorderScreen())
    }
}