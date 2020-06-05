package de.umr

/**Stores the paths to frequency used graph files.
 *
 * Assumes the repository "data", which contains the graph files, is inside the same folder the root
 * of this project is in.*/
object FilePaths {
    const val infUsAir = "./graph_files/inf-USAir97.mtx"
    const val bioDmela = "../data/network repository/bio/bio-dmela.mtx"
    const val infPower = "../data/network repository/infrastructure/inf-power.mtx"
    const val socBrightkite = "../data/network repository/social/soc-brightkite/soc-brightkite.mtx"
    const val outDolphins = "../data/konect/undirected-simple-small/dolphins/out.dolphins"
    const val infEuroroad = "../data/network repository/infrastructure/inf-euroroad.edges"
    const val sample = "./graph_files/sample"
    const val caSandiAuths = "../data/network repository/collaboration/ca-sandi_auths.mtx"
    const val hamming10_4 = "./graph_files/hamming10-4.mtx"
    const val pHat_1500_3 = "./graph_files/p-hat1500-3.mtx"
    const val infOpenFlights = "./graph_files/inf-openflights.edges"
    const val coPapersCiteseer = "./graph_files/coPapersCiteseer.mtx"
    const val customTree = "./graph_files/CustomTree.txt"

    val allPaths = listOf(infUsAir, bioDmela, infPower, socBrightkite, outDolphins, infEuroroad, sample, caSandiAuths, hamming10_4, pHat_1500_3, infOpenFlights, coPapersCiteseer, customTree)
}