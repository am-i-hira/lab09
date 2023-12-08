/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    // Abstraction function:
    //   vertices represents a list graph with labelled immutable veritces
    // Representation invariant:
    //   vertices contain alphabets and numbers only
    // Safety from rep exposure:
    //   names of vertices are already immutable, but the object 
    //   itself isn't so return a copy of the reference
    
    // TODO constructor
    public ConcreteVerticesGraph() {

    }
    
    // TODO checkRep
    private void checkRep() {
    	for (Vertex vertex : vertices) {
			assert vertex != null;
		}
    }
    
    
    @Override public boolean add(String vertex) {
        for (Vertex _vertex : vertices) {
            if (_vertex.getLabel() == vertex) return false;
        }
        vertices.add(new Vertex(vertex));

        checkRep();
        return true;
    }
    
    @Override public int set(String source, String target, int weight) {
        int oldWeight = 0;

        for (Vertex vertex : vertices) {
            if (vertex.getLabel() == source) {
                for (Vertex vertex2 : vertices) {
                    if (vertex2.getLabel() == target) {
                        oldWeight = vertex.connect(target, weight);
                        checkRep();
                        return oldWeight;
                    }
                }
                vertices.add(new Vertex(target));
                oldWeight = vertex.connect(target, weight);
                checkRep();
                return oldWeight;
            }
        }

        Vertex vertex = new Vertex(source);
        vertices.add(vertex);
        for (Vertex vertex2 : vertices) {
            if (vertex2.getLabel() == target) {
                oldWeight = vertex.connect(target, weight);
                checkRep();
                return oldWeight;
            }
        }
        vertices.add(new Vertex(target));
        oldWeight = vertex.connect(target, weight);

        checkRep();
        return oldWeight;
    }
    
    @Override public boolean remove(String vertex) {
        Vertex deletee = null;
        for (Vertex _vertex : vertices) {
            if (_vertex.getLabel() == vertex) {
                deletee = _vertex;
                continue;
            }

            for (String label : _vertex.getMaps().keySet()) {
                if (label == vertex) _vertex.disconnect(label);
            }

        }
        checkRep();

        if (deletee != null) {
            vertices.remove(vertices.indexOf(deletee));
            return true;
        }
        else return false;
    }
    
    @Override public Set<String> vertices() {
        Set<String> labels = new HashSet<>();
        for (Vertex vertex : vertices) {
            labels.add(vertex.getLabel());
        }
        return labels;
    }
    
    @Override public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Vertex vertex : vertices) {
            if (vertex.getLabel() == target) continue;
            for (String label : vertex.getMaps().keySet()) {
                if (label == target) sources.put(vertex.getLabel(), vertex.getWeightOfConnection(target));
            }
        }
        return sources;
    }
    
    @Override public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Vertex vertex : vertices) {
            if (vertex.getLabel() == source) {
                for (String label : vertex.getMaps().keySet()) targets.put(label, vertex.getWeightOfConnection(label));
            }
        }
        return targets;
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Vertex vertex : vertices) {
            result.append(vertex.toString());
        }

        return result.toString();
    }


    
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex {
    
    private final char symbols[] = {'@', '!', '#', '%', '^', '&', '*'};
    private String label;
    private Map<String, Integer> maps = new HashMap<>();

    // Abstraction function:
    //   represents a named point on a graph with wights
    // Representation invariant:
    //   vertex label only contains alphabets and numbers
    //   map weight always > 0
    // Safety from rep exposure:
    //   label is a string so it is always immutable
    //   map is mutable to use defensive copying to return maps of a vertex
    
    // TODO constructor
    public Vertex(String label) {
        this.label = label;
        checkRep();
    }

    // TODO checkRep
    public void checkRep() {
        // Check if the first character of the label is one of the allowed symbols
        char firstChar = this.label.charAt(0);
        boolean validSymbol = false;
        for (char symbol : symbols) {
            if (firstChar == symbol) {
                validSymbol = true;
                break;
            }
        }
        assert validSymbol : "Vertex label must start with one of the allowed symbols";

        // Check that all map weights are greater than 0
        for (Integer weight : maps.values()) {
            assert weight > 0 : "Map weights must be greater than 0";
        }
    }

    
    // TODO methods
    /**
     * Add a connection to another vertex in the graph.
     * 
     * @param label name of the vertex
     * @param weight weight of the edge
     * @return previous weight of the edge if it existed, zero otherwise
     */
    public int connect(String label, Integer weight) {
        int oldWeight = 0;

        if (maps.containsKey(label)) {
            oldWeight = maps.get(label);
            maps.put(label, weight);
        }
        else maps.put(label, weight);

        checkRep();
        return oldWeight;
    }

    /**
     * Removes a connection to another vertex in the graph.
     * 
     * @param label name of the vertex
     * @return true if the vertex existed and the rep is satisfied after removal, false if no such vertex was already connected to
     */
    public boolean disconnect(String label) {
        if (maps.containsKey(label)) maps.remove(label);
        else return false;

        checkRep();
        return true;
    }

    /**
     * Get the name of the label
     * @return name of the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Get the connecions of the vertex
     * 
     * @return a new map containing all connections of a vertex up to that point
     */
    public Map<String, Integer> getMaps(){
        return new HashMap<String, Integer>(maps);
    }

    public Integer getWeightOfConnection(String label){
        return new Integer(maps.get(label));
    }


    // TODO toString()
    @Override
    public String toString() {
        String connectionMap = "";
        connectionMap = connectionMap.concat(getLabel()).concat("\t->");

        // Use a TreeMap to ensure a consistent order for the connections
        Map<String, Integer> sortedMap = new TreeMap<>(maps);

        for (String label : sortedMap.keySet()) {
            connectionMap = connectionMap.concat(label).concat(", ");
        }
        
        connectionMap.concat("\n");

        return connectionMap;
    }

    



}
