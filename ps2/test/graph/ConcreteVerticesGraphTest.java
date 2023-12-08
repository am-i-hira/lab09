/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }

    @Test
    public void testToStringEmptyGraph() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        assertEquals("toString should return an empty string for an empty graph", "", graph.toString());
    }

    // covers single vertex, no edges
    @Test
    public void testToStringSingleVertexNoEdges() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("@Vertex1");
        assertEquals("toString should return the correct string representation for a single vertex with no edges",
                "@Vertex1\t->", graph.toString());
    }

    // covers single vertex, single edge
    @Test
    public void testToStringSingleVertexSingleEdge() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("@Vertex1");
        graph.set("@Vertex1", "@Vertex2", 5);
        assertEquals("toString should return the correct string representation for a single vertex with a single edge",
                "@Vertex1\t->@Vertex2, @Vertex2\t->", graph.toString());
    }

    // covers multiple vertices, multiple edges
    @Test
    public void testToStringMultipleVerticesMultipleEdges() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("@Vertex1");
        graph.add("@Vertex2");
        graph.add("@Vertex3");
        graph.set("@Vertex1", "@Vertex2", 5);
        graph.set("@Vertex1", "@Vertex3", 10);
        graph.set("@Vertex2", "@Vertex3", 8);
        assertEquals(
                "toString should return the correct string representation for multiple vertices and multiple edges",
                "@Vertex1\t->@Vertex2, @Vertex3, @Vertex2\t->@Vertex3, @Vertex3\t->", graph.toString());
    }

    /*
     * Testing Vertex...
     */

    // Testing strategy for Vertex constructor
    // label: valid, invalid
    // TODO tests for Vertex constructor

    // covers label: valid
    @Test
    public void testConstructorValidLabel() {
        Vertex v = new Vertex("@Vertex1");
        assertEquals("Vertex label should be initialized correctly", "@Vertex1", v.getLabel());
    }

    // covers label: invalid
    @Test(expected = AssertionError.class)
    public void testConstructorInvalidLabel() {
        // Attempt to create a vertex with an invalid label
        new Vertex("InvalidLabel");
    }

    // Testing strategy for Vertex.connect()
    // connecting to a new vertex, connecting to an existing vertex with a new
    // weight,
    // connecting with a negative weight
    // TODO tests for Vertex.connect()

    // covers connecting to a new vertex
    @Test
    public void testConnectNewVertex() {
        Vertex v = new Vertex("@Vertex1");
        int result = v.connect("@Vertex2", 5);
        assertEquals("Connecting to a new vertex should return 0", 0, result);
        assertTrue("Vertex should have the new connection", v.getMaps().containsKey("@Vertex2"));
    }

    // covers connecting to an existing vertex with a new weight
    @Test
    public void testConnectExistingVertex() {
        Vertex v = new Vertex("@Vertex1");
        v.connect("@Vertex2", 5);
        int result = v.connect("@Vertex2", 10);
        assertEquals("Connecting to an existing vertex with a new weight should return the old weight", 5, result);
        assertEquals("Vertex should have the updated weight", 10, v.getWeightOfConnection("@Vertex2").intValue());
    }

    // covers connecting with a negative weight
    @Test(expected = AssertionError.class)
    public void testConnectNegativeWeight() {
        Vertex v = new Vertex("@Vertex1");
        v.connect("@Vertex2", -5);
    }

    // covers disconnecting from an existing vertex
    @Test
    public void testDisconnectExistingVertex() {
        Vertex v = new Vertex("@Vertex1");
        v.connect("@Vertex2", 5);
        assertTrue("Disconnecting from an existing vertex should return true", v.disconnect("@Vertex2"));
        assertFalse("Vertex should not have the disconnected connection", v.getMaps().containsKey("@Vertex2"));
    }

    // covers disconnecting from a non-existing vertex
    @Test
    public void testDisconnectNonExistingVertex() {
        Vertex v = new Vertex("@Vertex1");
        assertFalse("Disconnecting from a non-existing vertex should return false", v.disconnect("@Vertex2"));
    }

    // Testing strategy for Vertex.getLabel()
    // vertex with a valid label
    // TODO tests for Vertex.getLabel()

    // covers vertex with a valid label
    @Test
    public void testGetLabel() {
        Vertex v = new Vertex("@Vertex1");
        assertEquals("getLabel should return the correct label", "@Vertex1", v.getLabel());
    }

    // Testing strategy for Vertex.getMaps()
    // maps with existing connections, maps without connections
    // TODO tests for Vertex.getMaps()

    // covers maps with existing connections
    @Test
    public void testGetMapsWithConnections() {
        Vertex v = new Vertex("@Vertex1");
        v.connect("@Vertex2", 5);
        v.connect("@Vertex3", 10);
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("@Vertex2", 5);
        expectedMap.put("@Vertex3", 10);
        assertEquals("getMaps should return a map with existing connections", expectedMap, v.getMaps());
    }

    // covers maps without connections
    @Test
    public void testGetMapsWithoutConnections() {
        Vertex v = new Vertex("@Vertex1");
        assertTrue("getMaps should return an empty map when there are no connections", v.getMaps().isEmpty());
    }

    // Testing strategy for Vertex.getWeightOfConnection()
    // getting weight of an existing connection, getting weight of a non-existing
    // connection
    // TODO tests for Vertex.getWeightOfConnection()

    // covers getting weight of an existing connection
    @Test
    public void testGetWeightOfExistingConnection() {
        Vertex v = new Vertex("@Vertex1");
        v.connect("@Vertex2", 5);
        assertEquals("getWeightOfConnection should return the correct weight", 5,
                v.getWeightOfConnection("@Vertex2").intValue());
    }

    // covers getting weight of a non-existing connection
    @Test(expected = NullPointerException.class)
    public void testGetWeightOfNonExistingConnection() {
        Vertex v = new Vertex("@Vertex1");
        v.getWeightOfConnection("@Vertex2");
    }

    // Testing strategy for Vertex.toString()
    // toString with connections, toString without connections
    // TODO tests for Vertex.toString()

    // covers toString with connections
    @Test
    public void testToStringWithConnections() {
        Vertex v = new Vertex("@Vertex1");
        v.connect("@Vertex2", 5);
        v.connect("@Vertex3", 10);
        String expected = "@Vertex1\t->@Vertex2, @Vertex3, ";
        assertEquals("toString should return the correct string representation with connections", expected,
                v.toString());
    }

    // covers toString without connections
    @Test
    public void testToStringWithoutConnections() {
        Vertex v = new Vertex("@Vertex1");
        String expected = "@Vertex1\t->";
        assertEquals("toString should return the correct string representation with no connections", expected,
                v.toString());
    }

}
