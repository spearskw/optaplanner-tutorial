import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AngleDifficultyTest {

    @Test
    fun `angles are calculated correctly`() {
        assertEquals(45, Vector(1.0, 0.0).angleDegrees(Vector(1.0, 1.0)))
        assertEquals(135, Vector(1.0, 0.0).angleDegrees(Vector(-1.0, 1.0)))
        assertEquals(225, Vector(1.0, 0.0).angleDegrees(Vector(-1.0, -1.0)))
        assertEquals(315, Vector(1.0, 0.0).angleDegrees(Vector(1.0, -1.0)))
    }

    @Test
    fun `an angle of 45 is less than an angle of 90`() {
        val depot = Point(0, 0)
        val stop45 = Stop("1", Point(1, 1), 0)
        val stop90 = Stop("2", Point(0, 1), 0)
        val angle45 = AngleDifficulty(stop45, depot)
        val angle90 = AngleDifficulty(stop90, depot)
        assertTrue { angle90 > angle45 }
    }
}