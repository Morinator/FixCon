package de.umr

enum class GraphFile(val path: String,
                     val weighted: Boolean = false,
                     val dropLines: Int = 0) {

    InfUsAir("./graph_files/inf-USAir97.mtx"),
    BioDmela("../data/network repository/bio/bio-dmela.mtx", dropLines = 2),
    InfPower("../data/network repository/infrastructure/inf-power.mtx", dropLines = 3),
    SocBrightkite("../data/network repository/social/soc-brightkite/soc-brightkite.mtx", dropLines = 2),
    OutDolphins("../data/konect/undirected-simple-small/dolphins/out.dolphins", dropLines = 1),
    InfEuroRoad("../data/network repository/infrastructure/inf-euroroad.edges", dropLines = 2),
    Sample("./graph_files/sample"),
    CaSandiAuths("../data/network repository/collaboration/ca-sandi_auths.mtx", weighted = true, dropLines = 3),
    Hamming10_4("./graph_files/hamming10-4.mtx", dropLines = 2),
    PHat_1500_3("./graph_files/p-hat1500-3.mtx", dropLines = 2),
    InfOpenFlights("./graph_files/inf-openflights.edges"),
    CoPapersCiteseer("./graph_files/coPapersCiteseer.mtx"),
    CustomTree("./graph_files/CustomTree.txt"),
    BadGraph("./graph_files/badGraph.txt"),
    SocAdvogato("../data/network repository/social/soc-advogato/soc-advogato.txt"),
    MouseRetina("../data/network repository/brain/bn-mouse_retina_1.edges"),
    Heart2("../data/network repository/misc/heart2.edges", dropLines = 1),
    BioYeast("../data/network repository/bio/bio-yeast.mtx", dropLines = 2)
}