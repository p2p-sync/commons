package org.rmatil.sync.commons.list;

import java.util.List;
import java.util.ArrayList;

/**
 * Utilities for lists
 */
public class Lists {

    /**
     * Checks whether the given list contains an instance of the
     * given class. If true, then a list containing the entries
     * of the given list is returned. Otherwise, the list will be empty
     *
     * @param list  The list to check for entries
     * @param clazz The class to check for occurrence
     *
     * @return A list containing all hits found for the given class
     */
    public static <E> List<E> getInstances(List<E> list, Class<? extends E> clazz) {
        List<E> hits = new ArrayList<E>();

        for (int i = 0; i < list.size(); i++) {
            if (clazz.isInstance(list.get(i))) {
                hits.add(list.get(i));
            }
        }

        return hits;
    }

}
