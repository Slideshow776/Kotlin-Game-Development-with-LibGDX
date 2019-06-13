package chapter6.rhythmTapper

class RhythmGame: BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(RhythmScreen())
    }
}
