package project.graph;

/**
 *
 * @author domenicocitera
 */
public class Edge implements Comparable<Edge> {

    public Vertex target;	//vertice destinazione
    public String nTrain;
    public int weight, oraA, oraP;

    //COSTRUTTORE
    public Edge(Vertex target, int weight, String nTrain, int oraP, int oraA) {
        this.target = target;
        this.weight = weight;
        this.nTrain = nTrain;
        this.oraA = oraA;
        this.oraP = oraP;
    }

    //RITORNA VERTICE ADIACENTE
    public Vertex getTarget() {
        return target;
    }

    //CONFRONTA PER ORA ARRIVO
    public int compareTo(Edge other) {
        return Double.compare(this.oraA, other.oraA);
    }
}
