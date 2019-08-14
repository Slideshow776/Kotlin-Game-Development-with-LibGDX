package chapter12

class TreasureQuestGame : BaseGame() {
    override fun create() {
        super.create();
        setActiveScreen(MenuScreen())
    }
}
