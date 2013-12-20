package project.treni.util;

import project.graph.*;
import java.util.*;

/**
 *
 * @author domenicocitera
 */
public class Utility {

    /**
     * RIORDINA LA MAPPA TEMPORALMENTE input: la mappa da riordinare
     */
    public static void mapOrder(Map<String, ArrayList<Treno>> map) {

        for (String nTreno : map.keySet()) {
            //per ogni treno nella mappa
            ArrayList<Treno> valuestops = map.get(nTreno);	//estraggo la lista fermate
            valuestops = stopsOrder(valuestops);	//ordino le fermate temporalmente
            map.put(nTreno, valuestops);	//oggiorno lista ordinata
        }
    }

    /**
     * ORDINA LISTA DELLE FERMATE input: una lista qualsiasi output: nuova lista
     * ordinata temporalmente
     */
    public static ArrayList<Treno> stopsOrder(ArrayList<Treno> stops) {
        Collections.sort(stops, Treno.COMPARE_BY_TIME);	//ordino crescentemente arrivi
        ArrayList<Treno> temp = new ArrayList<Treno>();	//creo la lista che sar� ordinata
        temp.add(stops.remove(0));	//aggiungo il primo elemento (la partenza) rimuovendolo dalle fermate
        int end = 0;	//conterr� indice seconda partenza

        for (int i = 0; i < stops.size(); i++) {
            //finch� non trovo la partenza successiva
            if (stops.get(i).getOraA() > temp.get(0).getOraP()) {	//se la partenza i � > della prima
                end = i;
                break;	//l'ho trovata
            }
        }
        for (int i = end; i < stops.size(); i++) //aggiungo alla lista le partenze successive
        {
            temp.add(stops.get(i));
        }
        for (int i = 0; i < end; i++) //aggiungo quelle dopo la mezzanotte
        {
            temp.add(stops.get(i));
        }
        return temp;

    }

    /**
     * INTEPRETA L'OPERAZIONE RICHIESTA input: UN OPERAZIONE CON ARGOMENTI, UN
     * GRAFO output: RISULTATO result, o null
     */
    public static Itinerary execRequest(String[] operation, Graph G) {

        Itinerary result = null; //conterr� risultati
        if (operation[0].equals("MINTEMPO")) {

            Vertex source = G.getVertex(operation[1]);		//cerchiamo tutte le partenze
            ArrayList<Itinerary> results = new ArrayList<Itinerary>();	//nella stazione di partenza
            for (Edge e : source.getAdjaciencies()) {	//e per ognuna partenza stabiliamo il
                G.computePaths(source, e.oraP);			//cammino minimo
                results.add(G.getShortestPathTo(G.getVertex(operation[2])));	//aggiungiamo il cammino minimo
            }																//per ogni partenza ai risultati
            result = Collections.min(results, Itinerary.COMPARE_BY_DISTANCE);	//scegliamo il cammino di distanza (tempo) minore
        }

        if (operation[0].equals("MINORARIO")) {

            Vertex source = G.getVertex(operation[1]);	//cerchiamo nodo partenza
            G.computePaths(source, Integer.parseInt((operation[3])));	//calcoliamo distanze da un orario
            result = G.getShortestPathTo(G.getVertex(operation[2]));	//salviamo risultato
        }
        return result;
    }

}
