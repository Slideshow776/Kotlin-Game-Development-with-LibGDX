package chapter06.rhythmTapper

class RhythmGame: BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(RhythmScreen())
    }
}
