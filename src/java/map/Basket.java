package map;

import java.util.ArrayList;
import java.util.Arrays;

class Basket<T> {
    private ArrayList<T> set;

    Basket(T[] set) {
        this.set = new ArrayList<T>(Arrays.asList(set));
    }

    T pickRandomItem() {
        int length = set.size();

        if (length > 0) {
            int buf = (int)(Math.random() * length);
            T res = set.get(buf);
            set.remove(buf);

            return res;
        }
        return null;
    }
}
