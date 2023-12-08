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

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    // Abstraction function:
    //   Represents a graph with vertices and edges. Each vertex is a unique string.
    // Representation invariant:
    //   edges have positive weight
    // Safety from rep exposure:
    //   all fields are private and final;
    
    // TODO constructor
    /**
     * Create an empty ConcreteEdgesGraph
     */
    public ConcreteEdgesGraph() {
        checkRep();
    }

    // TODO checkRep
    private void checkRep(){
        assert vertices != null;
        for(Edge edge : edges){
            assert edge.getWeight() > 0;
        }
    }
    
    @Override public boolean add(String vertex) {
        boolean result = vertices.add(vertex);
        checkRep();
        return result;
    }
    
    @Override public int set(String source, String target, int weight) {
        // remove or modify existing edge
        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && 
                edge.getTarget().equals(target)) {
                int oldWeight = edge.getWeight();
                
                if (weight == 0) {
                    edges.remove(edge);
                    checkRep();
                    return oldWeight;
                }
                edges.remove(edge);
                edges.add(new Edge(source, target, weight));
                checkRep();
                return oldWeight;
            }
        }
        // else create new edge
        if (weight > 0) {
            if (!vertices.contains(source)) {
                this.add(source);
            }
            if (!vertices.contains(target)) {
                this.add(target);
            }  
            edges.add(new Edge(source, target, weight));
            checkRep();
            return 0;
        }
        checkRep();
        return 0;
    }
    
    @Override public boolean remove(String vertex) {
        if (!vertices.contains(vertex)) {
            checkRep();
            return false;
        }
        vertices.remove(vertex);
        checkRep();
        return true;

    }
    
    @Override public Set<String> vertices() {
        return new HashSet<String>(vertices);
    }
    
    @Override public Map<String, Integer> sources(String target) {
        Map<String, Integer> result = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                result.put(edge.getSource(), edge.getWeight());
            }
        }
        return result;
    }
    
    @Override public Map<String, Integer> targets(String source) {
        Map<String,Integer> result = new HashMap<>();
        for(Edge edge : edges){
            if(edge.getSource().equals(source)){
                result.put(edge.getTarget(),edge.getWeight());
            }
        }
        return result;
    }
    
    // TODO toString()
    @Override public String toString() {
        String result = "";
        for (Edge edge : edges) {
            result += edge.toString() + "\n"; 
        }
        return result;
    }
    
}

/**
 * TODO specification
 * This is an Immutable class.
 * An Edge has a source,  target and weight corresponding to it 
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge {
    
    // TODO fields

    private final String source;
    private final String target;
    private final Integer weight;

    // Abstraction function:
    //   Represents an edge  between 2 vertices with +ve weight
    // Representation invariant:
    //   'weights' of edges must be +ve
    // Safety from rep exposure:
    //   'vertices' and 'weight' are final and private. No external references are exposed.
    
    // TODO constructor
    /**
     * Create an Edge with a source name, target name, and weight.
     *  
     * @param source source vertex of the edge
     * @param target target vertex of the edge
     * @param weight weight of the edge
     */
    Edge(String source, String target, Integer weight){
        this.source = source;
        this.target = target;
        this.weight = weight;
    }
    
    // TODO checkRep
    private void checkRep() {
         assert source != null;
         assert target != null;
         assert weight > 0;
     }
    
    // TODO methods

    /**
     * Gets the source of the edge 
    * @return  source of the edge
    */
    public String getSource(){
        return source;
    }
    
    /**
     * Gets the target of the edge
    * @return  target of the edge
    */
    public String getTarget(){
        return target;
    }
    
    /**
     * Gets the weight of the edge
    * @return  weight of the edge
    */
    public Integer getWeight(){
        return weight;
    }
    
    // TODO toString()
    @Override
    public String toString(){
        return source + "->" + target + "(weight = " + weight + ")";
    }
    
    
}
