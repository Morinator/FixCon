package de.umr.core.dataStructures

/**
 *
 */
class StringToIntMap() {

    private val forwardMap = HashMap<String, Int>()
    private val backwardMap = HashMap<Int, String>()
    private var ctr = 0

    constructor(list: List<Pair<String, String>>) : this() {
        list.forEach {
            addMappingIfStringIsNew(it.first)
            addMappingIfStringIsNew(it.second)
        }
    }

    constructor(x: Collection<String>) : this() {
        x.forEach { addMappingIfStringIsNew(it) }
    }


    private fun addMappingIfStringIsNew(s: String) {
        if (s !in forwardMap.keys) {
            forwardMap[s] = ctr
            backwardMap[ctr] = s
            ctr++
        }
    }

    fun intMapping(stringValue: String) = forwardMap[stringValue]!!

    fun stringMapping(intValue: Int) = backwardMap[intValue]!!
}