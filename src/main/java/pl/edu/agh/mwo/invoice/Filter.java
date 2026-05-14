package pl.edu.agh.mwo.invoice;

import java.util.List;

public class Filter {

    public static void main(String[] args) {

    }

    public List<Integer> myFilter(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) % 2 == 0) {
                list.add(i);
            }
        }
        return list;
    }
}