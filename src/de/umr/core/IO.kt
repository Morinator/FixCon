package de.umr.core

import de.umr.defaultEdgeWeight
import org.jgrapht.graph.DefaultEdge
import org.jgrapht.graph.SimpleWeightedGraph
import java.io.File
import java.lang.Integer.parseInt

const val repo = "../data/network repository/"
const val konect = "../data/konect/"
enum class GraphFile(val path: String) {
    InfUsAir("./data/inf-USAir97.mtx"),
    BioDmela("${repo}bio/bio-dmela.mtx"),
    InfPower("${repo}infrastructure/inf-power.mtx"),
    SocBrightkite("${repo}social/soc-brightkite/soc-brightkite.mtx"),
    OutDolphins("${konect}undirected-simple-small/dolphins/out.dolphins"),
    InfEuroRoad("${repo}infrastructure/inf-euroroad.edges"),
    Sample("./data/sample"),
    CaSandiAuths("${repo}collaboration/ca-sandi_auths.mtx"),
    Hamming10_4("./data/hamming10-4.mtx"),
    PHat_1500_3("./data/p-hat1500-3.mtx"),
    InfOpenFlights("./data/inf-openflights.edges"),
    CoPapersCiteseer("./data/coPapersCiteseer.mtx"),
    CustomTree("./data/CustomTree.txt"),
    BadGraph("./data/badGraph.txt"),
    SocAdvogato("${repo}social/soc-advogato/soc-advogato.txt"),
    MouseRetina("${repo}brain/bn-mouse_retina_1.edges"),
    Heart2("${repo}misc/heart2.edges"),
    BioYeast("${repo}bio/bio-yeast.mtx"),
    MorenoZebra("${konect}undirected-simple-small/moreno_zebra/out.moreno_zebra_zebra"),
    UcidataZachary("${konect}undirected-simple-small/ucidata-zachary/out.ucidata-zachary"),
    Celegans("$repo/bio/bio-celegans.mtx"),
    Diseasome("$repo/bio/bio-diseasome.mtx"),
    MixedSpecies("$repo/brain/bn-cat-mixed-species_brain_1.edges"),
    CSphd("$repo/collaboration/ca-CSphd.mtx")
}

val validLine: (String) -> Boolean = { !it.startsWith("%") && !it.startsWith("#") && it.isNotEmpty() }

fun edgesFromFile(file: String, allowLoops: Boolean = false) = File(file).readLines().asSequence()
        .filter { validLine(it) }
        .map { it.split(Regex("""\s+""")) }
        .filter { it[0] != it[1] || allowLoops }
        .mapTo(ArrayList(), { Triple(parseInt(it[0]), parseInt(it[1]), defaultEdgeWeight) })

fun graphFromFile(file: String, allowLoops: Boolean = false) = graphFromWeightedEdges(edgesFromFile(file, allowLoops))

fun graphFromFile(f: GraphFile): SimpleWeightedGraph<Int, DefaultEdge> = graphFromWeightedEdges(edgesFromFile(f))

fun edgesFromFile(file: GraphFile, allowLoops: Boolean = false) = edgesFromFile(file.path, allowLoops)
