package chapter05.theMissingHomework

class TypewriterAction(t: String) : SetTextAction(t) {
    private var elapsedTime: Float
    private val charactersPerSecond: Float

    init {
        elapsedTime = 0f
        charactersPerSecond = 30f
    }

    override fun act(delta: Float): Boolean {
        elapsedTime += delta
        var numberOfCharacters = (elapsedTime * charactersPerSecond).toInt()
        if (numberOfCharacters > textToDisplay.length)
            numberOfCharacters = textToDisplay.length.toInt()
        val partialText = textToDisplay.substring(0, numberOfCharacters)
        val db = target as DialogBox
        db.setText(partialText)

        // action is complete when all characters have been displayed
        return (numberOfCharacters >= textToDisplay.length)
    }
}
