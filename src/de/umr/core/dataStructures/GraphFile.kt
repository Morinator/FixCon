package de.umr.core.dataStructures

const val repo = "../data/network repository/"
const val konect = "../data/konect/"

/**A database for graph files, which also stores some metadata about the graphs, namely:
 * [path] is the system-path the file is saved at
 * [skipLines] The number of lines that can be skipped at the start, because they are comments.*/
enum class GraphFile(val path: String,
                     val skipLines: Int = 0) {

    InfUsAir("./data/inf-USAir97.mtx"),
    BioDmela("${repo}bio/bio-dmela.mtx", skipLines = 2),
    InfPower("${repo}infrastructure/inf-power.mtx", skipLines = 3),
    SocBrightkite("${repo}social/soc-brightkite/soc-brightkite.mtx", skipLines = 2),
    OutDolphins("${konect}undirected-simple-small/dolphins/out.dolphins", skipLines = 1),
    InfEuroRoad("${repo}infrastructure/inf-euroroad.edges", skipLines = 2),
    Sample("./data/sample"),
    CaSandiAuths("${repo}collaboration/ca-sandi_auths.mtx", skipLines = 3),
    Hamming10_4("./data/hamming10-4.mtx", skipLines = 2),
    PHat_1500_3("./data/p-hat1500-3.mtx", skipLines = 2),
    InfOpenFlights("./data/inf-openflights.edges"),
    CoPapersCiteseer("./data/coPapersCiteseer.mtx"),
    CustomTree("./data/CustomTree.txt"),
    BadGraph("./data/badGraph.txt"),
    SocAdvogato("${repo}social/soc-advogato/soc-advogato.txt"),
    MouseRetina("${repo}brain/bn-mouse_retina_1.edges"),
    Heart2("${repo}misc/heart2.edges", skipLines = 1),
    BioYeast("${repo}bio/bio-yeast.mtx", skipLines = 2),
    MorenoZebra("${konect}undirected-simple-small/moreno_zebra/out.moreno_zebra_zebra", skipLines = 2),
    UcidataZachary("${konect}undirected-simple-small/ucidata-zachary/out.ucidata-zachary", skipLines = 2),
    Celegans("$repo/bio/bio-celegans.mtx", skipLines = 2),
    Diseasome("$repo/bio/bio-diseasome.mtx", skipLines = 2),
    MixedSpecies("$repo/brain/bn-cat-mixed-species_brain_1.edges"),
    CSphd("$repo/collaboration/ca-CSphd.mtx", skipLines = 3)
}