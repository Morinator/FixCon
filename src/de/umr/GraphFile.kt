package de.umr

enum class GraphFile(val path: String,
                     val weighted: Boolean = false,
                     val skipLines: Int = 0) {
    InfUsAir("./graph_files/inf-USAir97.mtx"),
    BioDmela("../data/network repository/bio/bio-dmela.mtx"),
    InfPower("../data/network repository/infrastructure/inf-power.mtx"),
    SocBrightkite("../data/network repository/social/soc-brightkite/soc-brightkite.mtx"),
    OutDolphins("../data/konect/undirected-simple-small/dolphins/out.dolphins"),
    InfEuroRoad("../data/network repository/infrastructure/inf-euroroad.edges"),
    Sample("./graph_files/sample"),
    CaSandiAuths("../data/network repository/collaboration/ca-sandi_auths.mtx", true),
    Hamming10_4("./graph_files/hamming10-4.mtx"),
    PHat_1500_3("./graph_files/p-hat1500-3.mtx"),
    InfOpenFlights("./graph_files/inf-openflights.edges"),
    CoPapersCiteseer("./graph_files/coPapersCiteseer.mtx"),
    CustomTree("./graph_files/CustomTree.txt"),
    BadGraph("./graph_files/badGraph.txt"),
    SocAdvogato("../data/network repository/social/soc-advogato/soc-advogato.txt"),
    MouseRetina("../data/network repository/brain/bn-mouse_retina_1.edges"),
    Heart2("../data/network repository/misc/heart2.edges", skipLines = 1)
}