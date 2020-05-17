package unitTests.core.dataStructures

import de.umr.core.dataStructures.ListUtils.duplicateHead
import de.umr.core.dataStructures.ListUtils.incrementHead
import de.umr.fixcon.itarators.SubIteratorFromStart
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Exception
import java.lang.IndexOutOfBoundsException
import java.util.*
import kotlin.collections.ArrayList
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
    fun incrementHeadException() {
        val x = ArrayList<Int>()
        assertThrows(IndexOutOfBoundsException::class.java) { x.incrementHead()  }
    }

    @Test
    fun duplicateHead() {
        var x : Deque<Int> = LinkedList(listOf(1,2,5))
        x.duplicateHead()
        assertEquals(x, LinkedList(listOf(1, 1, 2, 5)))

        x = LinkedList(listOf(3))
        x.duplicateHead()
        assertEquals(x, LinkedList(listOf(3,3)))
    }

    @Test
    fun duplicateHeadException() {
        val x: Deque<Int> = LinkedList()
        assertThrows(IllegalStateException::class.java) {x.duplicateHead()}
    }
}