package module_8;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import Log.Logger;
import javafx.util.Pair;
import module_8.util.Person;

//My hash map
public class Assignment_8_3_2 {

    private int M = 10;
    private int n; //number of inserted elements;

    //list containing pairs
    //list containing a list of references to pairs
    private LinkedList<Pair<Person, Integer>> pairs = new LinkedList<>();
    private ArrayList<LinkedList<Pair<Person, Integer>>> buckets = new ArrayList<>(M);

    public Assignment_8_3_2() {
        for (int i = 0; i < M; i++) {
            buckets.add(i, null);
        }
    }

    public void insert(Person key, int value) {
        int h = getPositiveHashCode(key) % M;
        Logger.log("insert, hash=%d", h);
        LinkedList<Pair<Person, Integer>> listByH = buckets.get(h);

        Pair<Person, Integer> pair = new Pair<>(key, value);

        if (listByH == null) {
            listByH = new LinkedList<>();

            listByH.addFirst(pair);
            buckets.set(h, listByH);

            pairs.addFirst(pair);
            n++;

            if (n >= 0.75 * M) {
                extendHashMap();
            }

            return;
        }

        if (listByH.isEmpty()) {
            listByH.addFirst(pair);

            buckets.set(h, listByH);

            pairs.addFirst(pair);
            n++;

            if (n >= 0.75 * M) {
                extendHashMap();
            }

            return;
        }

        ListIterator<Pair<Person, Integer>> it = listByH.listIterator();
        while (it.hasNext()) {
            Pair<Person, Integer> p = it.next();
            if (p.getKey().equals(key)) {
                it.set(pair);

                int index = pairs.indexOf(p);
                pairs.set(index, pair);
                return;
            }
        }

        listByH.addFirst(pair);
        buckets.set(h, listByH);

        pairs.addFirst(pair);
        n++;

        if (n >= 0.75 * M) {
            extendHashMap();
        }
    }

    public int get(Person key) {
        int h = getPositiveHashCode(key) % M;
        LinkedList<Pair<Person, Integer>> list = buckets.get(h);

        if (list == null || list.isEmpty()) {
            return 0;
        }

        Iterator<Pair<Person, Integer>> it = list.iterator();
        while (it.hasNext()) {
            Pair<Person, Integer> p = it.next();
            if (p.getKey().equals(key)) {
                return p.getValue();
            }
        }

        return 0;
    }

    public void remove(Person key) {
        int h = getPositiveHashCode(key) % M;
        LinkedList<Pair<Person, Integer>> list = buckets.get(h);

        if (list == null || list.isEmpty()) {
            return;
        }

        Iterator<Pair<Person, Integer>> it = list.iterator();
        while (it.hasNext()) {
            Pair<Person, Integer> p = it.next();
            if (p.getKey().equals(key)) {
                it.remove();

                pairs.remove(p);
                n--;
            }
        }
    }

    private void extendHashMap() {
        M = n * 2;

        ArrayList<LinkedList<Pair<Person, Integer>>> bucketsExtended = new ArrayList<>(M);
        for (int i = 0; i < M; i++) {
            bucketsExtended.add(i, null);
        }

        for (LinkedList<Pair<Person, Integer>> list : buckets) {
            if (list == null) {
                continue;
            }

            for (Pair<Person, Integer> pair : list) {
                int h = getPositiveHashCode(pair.getKey()) % M;

                LinkedList<Pair<Person, Integer>> listByH = bucketsExtended.get(h);
                if (listByH == null) {
                    listByH = new LinkedList<>();
                }

                listByH.addFirst(pair);
                bucketsExtended.set(h, listByH);
            }
        }

        buckets = bucketsExtended;
    }

    private int getPositiveHashCode(Person key) {
        int hashCode = key.hashCode();
        if (hashCode < 0) {
            return Math.abs(hashCode);
        }

        return hashCode;
    }
}

