package de.umr.core.dataStructures

class StringToIntMap() {

    private val forwardMap = HashMap<String, Int>()
    private val backwardMap = HashMap<Int, String>()
    private var counter = 0

    constructor(list: List<Pair<String, String>>) : this() {
        list.forEach { addMappingIfStringIsNew(it.first, it.second) }
    }

    constructor(x: Collection<String>) : this() {
        x.forEach { addMappingIfStringIsNew(it) }
    }

    private fun addMappingIfStringIsNew(vararg strings: String): Unit =
            strings.forEach {
                if (it !in forwardMap.keys) {
                    forwardMap[it] = counter
                    backwardMap[counter] = it
                    counter++
                }
            }

    fun intMapping(stringValue: String): Int? = forwardMap[stringValue]

    fun stringMapping(intValue: Int): String? = backwardMap[intValue]
}