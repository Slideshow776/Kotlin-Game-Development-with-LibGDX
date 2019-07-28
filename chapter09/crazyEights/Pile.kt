package chapter09.crazyEights

import com.badlogic.gdx.scenes.scene2d.Stage

class Pile(x: Float, y: Float, s: Stage): DropTargetActor(x, y, s) {
    private lateinit var cardList: ArrayList<Card>
    var isDroppable: Boolean = true

    init {
        cardList = ArrayList()
        loadTexture("assets/pile.png")
        setSize(100f, 120f)
        setBoundaryRectangle()
    }

    fun addCard(c: Card) { cardList.add(0, c) }
    fun removeCard(c: Card) { cardList.removeAt(cardList.size - 1)}
    fun getTopCard(): Card { return cardList[0] }
    fun getSize(): Int { return cardList.size }
}
