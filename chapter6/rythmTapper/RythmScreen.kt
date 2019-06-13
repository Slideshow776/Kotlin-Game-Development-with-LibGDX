package chapter6.rythmTapper

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import java.util.ArrayList
import java.util.Collections

class RythmScreen: BaseScreen() {
    private lateinit var keyList: ArrayList<String>
    private lateinit var colorList: ArrayList<Color>
    private lateinit var targetList: ArrayList<TargetBox>
    private lateinit var fallingList: ArrayList<ArrayList<FallingBox>>

    override fun initialize() {
        val background = BaseActor(0f, 0f, mainStage)
        background.loadTexture("assets/space.png")
        background.setSize(800f, 600f)
        BaseActor.setWorldBounds(background)

        keyList = ArrayList<String>()
        val keyArray = arrayOf("F", "G", "H", "J")
        Collections.addAll(keyList, *keyArray)

        colorList = ArrayList<Color>()
        val colorArray = arrayOf(Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE)
        Collections.addAll(colorList, *colorArray)

        val targetTable = Table()
        targetTable.setFillParent(true)
        targetTable.add().colspan(4).expandY()
        targetTable.row()
        mainStage.addActor(targetTable)

        targetList = ArrayList<TargetBox>()
        for (i in 0..3) {
            val tb = TargetBox(0f, 0f, mainStage, keyList.get(i), colorList.get(i))
            targetList.add(tb)
            targetTable.add(tb).pad(32f)
        }

        fallingList = ArrayList<ArrayList<FallingBox>>()
        for (i in 0..3) {
            fallingList.add(ArrayList<FallingBox>())
        }
    }

    override fun update(dt: Float) {}
    override fun keyDown(keycode: Int): Boolean { return false }
}
