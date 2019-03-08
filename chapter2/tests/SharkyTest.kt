package chapter2.tests

import chapter2.Sharky
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class SharkyTest {

    val sharky = Sharky()

    @Test
    fun `testing if positions is within boundaries`() {
        sharky.setPosition(100.toFloat(), 100.toFloat())
        for (i in 0..1_000_000) { // 1*10^6 iterations amounts to about (1*10^6)/60 = 4.63 hours
            sharky.act(i.toFloat())
            assertTrue(sharky.x in 50.0..750.0)
            assertTrue(sharky.y in 50.0..550.0)

        }
    }
}