package chapter09.cardPickup52

import com.badlogic.gdx.scenes.scene2d.Stage

class Pile(x: Float, y: Float, s: Stage): DropTargetActor(x, y, s) {
    private lateinit var cardList: ArrayList<Card>

    init {
        cardList = ArrayList()
        loadTexture("assets/pile.png")
        setSize(100f, 120f)
        setBoundaryRectangle()
    }

    fun addCard(c: Card) { cardList.add(0, c) }
    fun getTopCard(): Card { return cardList[0] }
    fun getSize(): Int { return cardList.size }
}
