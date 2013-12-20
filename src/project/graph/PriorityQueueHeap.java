package project.graph;

import java.util.Hashtable;

/**
 *
 * @author domenicocitera
 */
public class PriorityQueueHeap<E, K extends Comparable<K>> {

    private Object[] heap;
    private int nElem;
    private Hashtable<E, Integer> posizione = new Hashtable<E, Integer>();	//memorizza posizione

    //elemento che abbina ad un elemento di tipo E la sua priorityit�
    private class Elemento {

        E elem;
        K priority;

        //COSTRUTTORE ELEMENTO
        public Elemento(E e, K p) {
            elem = e;
            priority = p;
        }
    }

    //COSTRUTTORE CODA HEAP
    public PriorityQueueHeap() {
        //elementi memorizzati a partire dalla posizione 1`per semplicit� di calcolo.
        heap = new Object[1];
        nElem = 0;
    }

    //RITORNA VERO SE L'HEAP E' VUOTO
    public boolean isEmpty() {
        return nElem < 1;
    }

    /**
     * AGGIUNGE UN ELEMENTO ALLA CODA NELLA POSIZIONE CORRETTA INPUT: e oggetto
     * da inserire, p rappresenta la priorityit�
     */
    public void insert(E e, K p) {
        // Se nella tabella hash l'elemento non c'�, viene inserito
        if (posizione.get(e) == null) {
            // Se lo Heap � pieno, lo si rialloca con dimensione maggiore. */
            if (nElem >= heap.length - 1) {
                rialloca();
            }
			//viene aggiunto come ultimo elemento dello Heap e
            //fatto risalire al posto giusto con moveUp.
            heap[++nElem] = new Elemento(e, p);
            moveUp(nElem);
        }
    }

    /**
     * CAMBIA LA priorityITA' DELL'ELEMENTO e IN p RIPOSIZIONANDOLO INPUT:
     * elemento e, e priorityit� p
     */
    public void changePriority(E e, K p) {
        //Se l'elemento esiste
        if (posizione.get(e) != null) {
			//Si cerca la posizione nello Heap dell'elemento e si invoca il
            //metodo interno che cambia la priorityit�
            int i = posizione.get(e);
            changePriority(i, p);
        }
    }

    //CAMBIA LA PRIORITA' ALL'ELEMENTO DI INDICE i CON p
    private void changePriority(int i, K p) {
        // Memorizza la vecchia priorityit� a parte e aggiornala 
        K vecchiapriority = ((Elemento) heap[i]).priority;
        ((Elemento) heap[i]).priority = p;

        if (vecchiapriority.compareTo(p) > 0) {
            moveUp(i);	//priorityit� diminuita, elemento sale
        } else {
            moveDown(i);	//priorityit� aumentata, elemento scende
        }
    }

    //RITORNA PRIMO ELEMENTO DELLA CODA
    public E min() {
        return isEmpty() ? null : ((Elemento) heap[1]).elem;

    }

    //ESTRAE PRIMO ELEMENTO DALLA CODA E RIORDINA L'HEAP
    public E extraxtMin() {
        E primo = ((Elemento) heap[1]).elem;	//memorizza da parte primo
        Elemento ultimo = (Elemento) heap[nElem];	//e ultimo elemento
        heap[nElem--] = null;	//oggiorna n elementi
        posizione.remove(primo);	//rimuovi dalla tabella hash l'elemento

        if (nElem > 0) {	//se ci sono ancora elementi nella coda
            heap[1] = ultimo;	//posiziona l'ultimo in cima
            moveDown(1);	//posizionalo nella corretta locazione

        }
        return primo;

    }

    //RIPOSIZIONA SU
    private void moveUp(int i) {
        Elemento el = (Elemento) heap[i];
        for (; i > 1; i /= 2) {
            if (((Elemento) heap[i / 2]).priority.compareTo(el.priority) < 0) {
                break;
            }
            heap[i] = heap[i / 2];
            posizione.put(((Elemento) heap[i]).elem, i);
        }
        heap[i] = el;
        posizione.put(((Elemento) el).elem, i);
    }

    //RIPOSIZIONA GIU'
    private void moveDown(int i) {
        Elemento el = (Elemento) heap[i];
        int j;
        while ((j = 2 * i) <= nElem) {
            if (j + 1 <= nElem
                    && ((Elemento) heap[j + 1]).priority.compareTo(((Elemento) heap[j]).priority) < 0) {
                j++;
            }
            if (((Elemento) heap[j]).priority.compareTo(el.priority) > 0) {
                break;
            }
            heap[i] = heap[j];
            posizione.put(((Elemento) heap[i]).elem, i);
            i = j;
        }
        heap[i] = el;
        posizione.put(((Elemento) el).elem, i);
    }

    //RIALLOCA LA MEMORIA
    private void rialloca() {
        int nuovaLungh;
        if (nElem == 0) {
            nuovaLungh = 2;
        } else {
            nuovaLungh = nElem * 2 + 1;
        }
        Object[] nuovoHeap = new Object[nuovaLungh];
        for (int i = 1; i <= nElem; i++) {
            nuovoHeap[i] = heap[i];
        }
        heap = nuovoHeap;
    }
}
