package de.umr

enum class FilePaths(val path: String) {
    infUsAir("./graph_files/inf-USAir97.mtx"),
    bioDmela("../data/network repository/bio/bio-dmela.mtx"),
    infPower("../data/network repository/infrastructure/inf-power.mtx"),
    socBrightkite("../data/network repository/social/soc-brightkite/soc-brightkite.mtx"),
    outDolphins("../data/konect/undirected-simple-small/dolphins/out.dolphins"),
    infEuroroad("../data/network repository/infrastructure/inf-euroroad.edges"),
    sample("./graph_files/sample"),
    caSandiAuths("../data/network repository/collaboration/ca-sandi_auths.mtx"),
    hamming10_4("./graph_files/hamming10-4.mtx"),
    pHat_1500_3("./graph_files/p-hat1500-3.mtx"),
    infOpenFlights("./graph_files/inf-openflights.edges"),
    coPapersCiteseer("./graph_files/coPapersCiteseer.mtx"),
    customTree("./graph_files/CustomTree.txt"),
    badGraph("./graph_files/badGraph.txt"),
    socAdvogato("../data/network repository/social/soc-advogato/soc-advogato.txt")
}