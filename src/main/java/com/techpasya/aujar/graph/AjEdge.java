package com.techpasya.aujar.graph;

public class AjEdge {

    private Class<?> source;
    private Class<?> destination;
    private EdgeType typeOfRelation;

    public AjEdge(Class<?> source, Class<?> destination, EdgeType typeOfRelation) {
        this.source = source;
        this.destination = destination;
        this.typeOfRelation = typeOfRelation;
    }

    public Class<?> getSource() {
        return source;
    }

    public void setSource(Class<?> source) {
        this.source = source;
    }

    public Class<?> getDestination() {
        return destination;
    }

    public void setDestination(Class<?> destination) {
        this.destination = destination;
    }

    public EdgeType getTypeOfRelation() {
        return typeOfRelation;
    }

    public void setTypeOfRelation(EdgeType typeOfRelation) {
        this.typeOfRelation = typeOfRelation;
    }
}
