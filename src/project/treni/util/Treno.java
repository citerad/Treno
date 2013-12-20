package project.treni.util;

/**
 *
 * @author domenicocitera
 */
import java.util.Comparator;

public class Treno {

    private String treno, stazione;
    private int oraA, oraP;

    //costruttore
    public Treno(String t, String s, int OraA, int OraP) {
        this.treno = t;
        this.oraA = OraA;
        this.oraP = OraP;
        this.stazione = s;
    }

    //Confronta per tempo di arrivo
    public static Comparator<Treno> COMPARE_BY_TIME = new Comparator<Treno>() {
        public int compare(Treno one, Treno other) {
            return one.getOraA().compareTo(other.getOraA());
        }
    };

    public String getTrain() {
        return treno;
    }

    public String getStation() {
        return stazione;
    }

    public Integer getOraA() {
        return oraA;
    }

    public Integer getOraP() {
        return oraP;
    }
}
