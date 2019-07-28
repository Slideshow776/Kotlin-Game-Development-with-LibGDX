package chapter05.theMissingHomework

class HomeworkGame: BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(MenuScreen())
    }
}
