package chapter9.cardPickup52

import com.badlogic.gdx.scenes.scene2d.Stage

class Card(x: Float, y: Float, s: Stage) : DragAndDropActor(x, y, s) {

    var rankValue: Int = 0
    var suitValue: Int = 0

    val rankName: String
        get() = rankNames[rankValue]

    val suitName: String
        get() = suitNames[suitValue]

    fun setRankSuitValues(r: Int, s: Int) {
        rankValue = r
        suitValue = s
        val imageFileName = ("assets/card" + suitName + rankName + ".png")
        loadTexture(imageFileName)
        setSize(80f, 100f)
        setBoundaryRectangle()
    }

    override fun onDrop() {
        if (hasDropTarget()) {
            val pile = dropTarget as Pile?
            val topCard = pile!!.getTopCard()

            if (this.rankValue == topCard.rankValue + 1 && this.suitValue == topCard.suitValue) {
                moveToActor(pile)
                pile.addCard(this)
                isDraggable = false
            } else {
                // avoid blocking view of pile when incorrect.
                moveToStart()
            }
        }
    }

    override fun act(dt: Float) {
        super.act(dt)
        boundToWorld()
    }

    companion object {
        var rankNames = arrayOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
        var suitNames = arrayOf("Clubs", "Hearts", "Spades", "Diamonds")
    }
}
