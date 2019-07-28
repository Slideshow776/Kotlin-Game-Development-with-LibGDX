package chapter05.theMissingHomework

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Actor

class SceneSegment(private val actor: Actor, private val action: Action) {

    fun start() {
        actor.clearActions()
        actor.addAction(action)
    }

    fun isFinished() = actor.actions.size == 0

    fun finish() {
        // simulate 100_000 seconds = 1.67 hours
        if (actor.hasActions())
            actor.actions.first().act(100_000f)

        //remove any remaining actions
        actor.clearActions()
    }
}
