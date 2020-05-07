package unitTests.core.utils

import de.umr.core.utils.ExtensionList
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ExtensionList_Test {

    val x = ExtensionList<Int>()

    @BeforeEach fun listSetup() {
        x.addAll(listOf(11, 12, 13, 66, 34))
    }


    @Test
    fun constructorTest() {
        val x = ExtensionList<Int>()
        assertEquals(0, x.size)
        assertFalse(x.contains(1))
    }

    @Test
    fun constructorFromCollectionTest() {
        assertEquals(5, x.size)
        assertTrue(x.contains(11))
        assertTrue(x.contains(22))
        assertFalse(x.contains(1))
    }

    @Test
    fun addTest() {
        val x: ExtensionList<Int> = ExtensionList()
        x.add(3)
        assertTrue(x.contains(3))
        assertFalse(x.contains(1))
    }

    @Test
    fun addAllTest() {
        val y = ExtensionList<Int>()
        y.addAll(listOf(11, 12, 13))
        assertEquals(3, y.size)
        assertTrue(y.contains(11))
        assertTrue(y.contains(12))
        assertFalse(y.contains(-11))
        assertFalse(y.contains(0))
    }

    @Test
    fun removeFromEndTest() {
        val x = ExtensionList<Int>()
        x.addAll(listOf(11, 12, 13, 66, 34))
        x.removeFromEnd(2)
        assertEquals(listOf(11, 12, 13), x)
    }

    @Test
    fun setTest() {
        assertTrue(x.contains(11))
        x[0] = 11
        assertTrue(x.contains(11))
        x[0] = 67
        assertFalse(x.contains(11))
        assertTrue(x.contains(67))
    }
}