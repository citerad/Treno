package project.treni;

import java.io.*;
import java.util.*;
import project.graph.*;
import project.treni.util.*;

/**
 * 
 * @author domenicocitera
 */
public class Main {

    static final String OUT_FILE = "output";
    static final String IN_FILE = "input";

    public static void main(String[] args) {

        int nTest, nTab, nStop;
        String nStation = null;
        String[] splitLine = null;
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(IN_FILE + ".txt"));
            nTest = Integer.parseInt(br.readLine());	//numero casi di test

            for (int i = 0; i < nTest; i++) {
                //per ogni caso di test
                Graph G = new Graph();	//inizializzo il grafo
                br.readLine(); //ntestcase
                nTab = Integer.parseInt(br.readLine());
                Map<String, ArrayList<Treno>> map = new HashMap<String, ArrayList<Treno>>();	//per ogni treno contiene le fermate in stazione

                for (int h = 0; h < nTab; h++) {
                    //per ogni tabella/stazione
                    splitLine = br.readLine().split("\\s+"); //divido stazione e fermate
                    nStation = splitLine[0];
                    nStop = Integer.parseInt(splitLine[1]);
                    G.addVertex(nStation, new Vertex(nStation));	//aggiungo stazione come vertice

                    for (int j = 0; j < nStop; j++) {
                        //per ogni fermata
                        splitLine = br.readLine().split("\\s+");	//divido treno e orari
                        Integer oraa = Integer.parseInt(splitLine[1]);	//ora arrivo
                        Integer orap = Integer.parseInt(splitLine[2]);	//ora partenza		
                        Treno train = new Treno(splitLine[0], nStation, oraa, orap);	//creo l'oggetto treno
                        ArrayList<Treno> valuestops = map.get(splitLine[0]);	//estraggo dalla mappa la lista fermate relativo al ntreno (se c'� gi�)
                        if (valuestops == null) {	//se non c'� creo la lista 
                            valuestops = new ArrayList<Treno>();
                            map.put(splitLine[0], valuestops);	//e la inserisco (vuota)
                        }
                        valuestops.add(train);	//aggiungo treno alla lista
                    }
                }
                //dopo aver aquisito tutti i treni
                Utility.mapOrder(map);	//riordino gli orari temporalmente

                //CERCO GLI ARCHI DA CREARE
                for (String nTreno : map.keySet()) {
                    //per ogni ntreno nella mappa
                    ArrayList<Treno> valuestops = map.get(nTreno);

                    for (int k = 0; k < valuestops.size() - 1; k++) {
                        //esamino le fermate
                        Treno t1 = valuestops.get(k);	//partenza n
                        Treno t2 = valuestops.get(k + 1);	//partenza n+1
                        int weight = Graph.weightCalcul(t1.getOraP(), t2.getOraA());	//calcola peso
                        G.addEdge(G.getVertex(t1.getStation()), G.getVertex(t2.getStation()), weight, t1.getTrain(), t1.getOraP(), t2.getOraA());
                    }
                }

                int nOperazioni = Integer.parseInt(br.readLine()); //n richieste
                WriteFile.txtWrite("TESTCASE " + (i + 1), OUT_FILE);

                for (int g = nOperazioni; g > 0; g--) {
                    //analizzo la richiesta
                    String txtLine = br.readLine();
                    splitLine = txtLine.split("\\s+");
                    Itinerary it = Utility.execRequest(splitLine, G);
                    WriteFile.txtWrite(txtLine, OUT_FILE);
                    WriteFile.writeResult(it, OUT_FILE);	//scriviamo risultato
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
