package unitTests.core.dataStructures

import de.umr.core.dataStructures.StringToIntMap
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StringToIntMap_Test {

    @Nested
    internal inner class fromList_Test {
        private val stringList = listOf("Hund", "Katze", "Giraffe", "Komodowaran")
        private val map = StringToIntMap(stringList)

        @Test
        fun list_test() {

            for (i in 0..3) {
                assertEquals(i, map.intMapping(stringList[i]))
                assertEquals(stringList[i], map.stringMapping(i))
            }
        }

        @Test
        fun exception_test() {
            assertThrows(KotlinNullPointerException::class.java) { map.intMapping("Affe") }
            assertThrows(KotlinNullPointerException::class.java) { map.stringMapping(4) }
        }
    }

    @Nested
    internal inner class fromSet_Test {
        private val stringSet = setOf("Hund", "Katze", "Giraffe", "Komodowaran")
        private val map = StringToIntMap(stringSet)

        @Test
        fun set_test() {

            for (i in 0..3) {
                assertTrue(map.stringMapping(i) in stringSet)
            }

            stringSet.forEach { assertTrue { map.intMapping(it) in 0..3 } }
        }

        @Test
        fun exception_test() {
            assertThrows(KotlinNullPointerException::class.java) { map.intMapping("Affe") }
            assertThrows(KotlinNullPointerException::class.java) { map.stringMapping(4) }
        }
    }

    @Nested
    internal inner class fromPairList_Test {
        private val pairList = listOf("1" to "2", "2" to "7", "3" to "5", "1" to "2")
        private val map = StringToIntMap(pairList)

        @Test
        fun set_test() {
            listOf("1", "2", "7", "3", "5").withIndex().forEach { assertEquals(it.index, map.intMapping(it.value)) }
        }

        @Test
        fun exception_test() {
            assertThrows(KotlinNullPointerException::class.java) { map.intMapping("99") }
            assertThrows(KotlinNullPointerException::class.java) { map.stringMapping(5) }
        }
    }

}