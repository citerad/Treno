package project.graph;

/**
 *
 * @author domenicocitera
 */
import java.util.Comparator;
import java.util.LinkedList;

//contiene la lista dei viaggi di un itinerario
public class Itinerary {

    public Integer distance;
    public int nStop = 0;
    public LinkedList<SingleTravel> list;	//lista viaggi

    public Itinerary() {
        list = new LinkedList<SingleTravel>();
    }

    public class SingleTravel {

        public String ntreno;
        public String nStazioneP;
        public String nStazioneA;
        public int oraP;
        public int oraA;
        public int weight;

        public SingleTravel(String tr, String stP, int oraP, String stA, int oraA, int weight) {
            this.ntreno = tr;
            this.nStazioneP = stP;
            this.oraP = oraP;
            this.nStazioneA = stA;
            this.oraA = oraA;
            this.weight = weight;
        }
    }

    //aggiunge un singolo viaggio all'itinerario
    public void addItinerary(String tr, String stP, int oraP, String stA, int oraA, int weight) {
        list.addFirst(new SingleTravel(tr, stP, oraP, stA, oraA, weight));
        nStop = list.size();
        distance = list.getLast().weight;

    }

    //confronta per distanza
    public static Comparator<Itinerary> COMPARE_BY_DISTANCE = new Comparator<Itinerary>() {
        public int compare(Itinerary one, Itinerary other) {
            return one.distance.compareTo(other.distance);
        }
    };
}
