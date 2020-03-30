package de.umr.core.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphFileReaders_Test {

    @Test
    void read_GraphFile() throws IOException {
        List<Integer[]> edges = GraphFile_Readers.readFile_NetworkRepository_Format(".//data//sample");
        assertEquals(1, edges.get(0)[0]);
        assertEquals(2, edges.get(0)[1]);
        assertEquals(2, edges.get(1)[0]);
        assertEquals(3, edges.get(1)[1]);
        assertEquals(3, edges.get(1)[1]);

        assertEquals(15, edges.get(20)[0]);
        assertEquals(17, edges.get(20)[1]);
        assertEquals(16, edges.get(21)[0]);
        assertEquals(17, edges.get(21)[1]);
    }
}