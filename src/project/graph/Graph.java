package project.graph;

/**
 *
 * @author domenicocitera
 */
import java.util.*;

public class Graph {

    Map<String, Vertex> vertices;

    //COSTRUTTORE
    public Graph() {
        this.vertices = new HashMap<String, Vertex>();
    }

    //AGGIUNGE VERTICE
    public void addVertex(String name, Vertex v) {
        vertices.put(name, v);
    }

    //AGGIUNGE ARCO v->w
    public void addEdge(Vertex v, Vertex w, int weight, String nTreno, int oraA, int oraP) {
        v.adjacencies.add(new Edge(w, weight, nTreno, oraA, oraP));
    }

    //RITORNA VERTICE
    public Vertex getVertex(String v) {
        return vertices.get(v);
    }

    //RITORNA LISTA VERTICI
    public Collection<Vertex> getVertices() {
        return vertices.values();
    }

    /**
     * CALCOLA PESO ARCO input: un int tempo di arrivo, un int tempo di
     * partenza. output: tempo trascorso (peso arco)
     */
    public static int weightCalcul(int a, int p) {
        if (a > p) {
            return p + 1440 - a;
        }
        return p - a;
    }

    //DIJKSTRA
    public void computePaths(Vertex source, int start) {
        ArrayList<Vertex> listVertices = new ArrayList<Vertex>(getVertices());
        PriorityQueueHeap<Vertex, Integer> pHeap = new PriorityQueueHeap<Vertex, Integer>();

        for (Vertex v : listVertices) {	//inizializzo
            v.minDistance = Integer.MAX_VALUE;
            v.previousV = null;
            v.previousE = null;
            pHeap.insert(v, v.minDistance);
        }
        source.minDistance = 0;
        source.oraAp = start;

        pHeap.changePriority(source, 0);

        while (!pHeap.isEmpty()) {

            Vertex u = pHeap.extraxtMin();
            // Visito ogni adiacente
            for (Edge e : u.adjacencies) {
                Vertex v = e.target;
                int weight = e.weight;
                int distanceThroughU = u.minDistance + weightCalcul(u.oraAp, e.oraP) + weight;

                if (distanceThroughU < v.minDistance && distanceThroughU > 0) {
                    v.minDistance = distanceThroughU;	//nuova distanza
                    v.previousV = u;	//assegno il padre a v
                    v.previousE = e;	//assegno l'arco di attraversamento da u a v
                    v.oraAp = e.oraA;	//assegno a v l'orario di arrivo in stazione
                    pHeap.changePriority(v, v.minDistance);
                }
            }
        }
    }

    //restituisce l'itinerario del percorso minimo
    public Itinerary getShortestPathTo(Vertex target) {
        Itinerary output = new Itinerary();
        for (Vertex vertex = target; vertex.previousE != null; vertex = vertex.previousV) {
            output.addItinerary(vertex.previousE.nTrain, vertex.previousV.name, vertex.previousE.oraP, vertex.name, vertex.previousE.oraA, vertex.minDistance);
        }
        return output;
    }
}
