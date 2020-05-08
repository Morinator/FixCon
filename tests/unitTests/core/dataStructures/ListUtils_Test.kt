package unitTests.core.dataStructures

import de.umr.core.dataStructures.ListUtils.duplicateHead
import de.umr.core.dataStructures.ListUtils.incrementHead
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

internal class ListUtils_Test {

    @Test
    fun incrementHead() {
        var x = arrayListOf(1, 2, 5, 3)
        x.incrementHead()
        assertEquals(x, listOf(2, 2, 5, 3))

        x = arrayListOf(-1)
        x.incrementHead()
        assertEquals(x, listOf(0))
    }

    @Test
    fun duplicateHead() {
        var x : Deque<Int> = LinkedList(listOf(1,2,5,3))
        x.duplicateHead()
        assertEquals(x, LinkedList(listOf(1, 1, 2, 5, 3)))

        x = LinkedList(listOf(3))
        x.duplicateHead()
        assertEquals(x, LinkedList(listOf(3,3)))
    }
}