/*
package chapter5.theMissingHomework

import chapter5.theMissingHomework.BaseGame

class HomeworkGame: BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(MenuScreen() as BaseScreen)
    }
}
*/

package chapter5.theMissingHomework

class HomeworkGame: BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(MenuScreen())
    }
}