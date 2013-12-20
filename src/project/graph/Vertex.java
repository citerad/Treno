package project.graph;

import java.util.ArrayList;

/**
 *
 * @author domenicocitera
 */
public class Vertex implements Comparable<Vertex> {

    public String name;
    public Vertex previousV;	//il padre
    public Edge previousE;	//l'arco che lo collega al padre
    public ArrayList<Edge> adjacencies;
    public int minDistance = Integer.MAX_VALUE;
    public int oraAp;	//ora arrivo treno precedente

    //COSTRUTTORE
    public Vertex(String argName) {
        name = argName;
        adjacencies = new ArrayList<Edge>();
    }

    //RITORNA NOME
    public String getID() {
        return name;
    }

    //AGGIUNGI ARCO
    public void addEdge(Edge e) {
        adjacencies.add(e);
    }

    //RITORNA LISTA ADIACENTI
    public ArrayList<Edge> getAdjaciencies() {
        return adjacencies;
    }

    //CONFRONTA PER DISTANZA MINIMA
    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }
}
