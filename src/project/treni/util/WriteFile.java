package project.treni.util;

import java.io.*;

import project.graph.Itinerary;
import project.graph.Itinerary.SingleTravel;
import java.util.LinkedList;

/**
 *
 * @author domenicocitera
 */
public class WriteFile {

    //SCRIVE LA RIGA X NEL FILE txtFile
    public static void txtWrite(String x, String textFile) {
        FileOutputStream fop = null;
        try {
            fop = new FileOutputStream(textFile + ".txt", true);
            PrintStream write = new PrintStream(fop);

            write.println(x);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //SCRIVE I RISULTATI res in txtFile
    public static void writeResult(Itinerary res, String txtFile) {

        if (res == null) {	//se non ci sono risultati
            txtWrite("-2", txtFile);	//scrivi -1
        } else if (res.nStop == 0) {
            txtWrite("-1", txtFile);
        } else {
            txtWrite(res.distance + " " + res.nStop, txtFile);
            LinkedList<SingleTravel> travel = res.list;
            for (SingleTravel t : travel) {
                txtWrite(t.ntreno + " " + t.nStazioneP + " " + t.oraP + " " + t.nStazioneA + " " + t.oraA, txtFile);
            }
        }
    }
}
