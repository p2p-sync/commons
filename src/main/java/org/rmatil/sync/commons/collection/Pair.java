package org.rmatil.sync.commons.collection;

/**
 * A container having a pair of values
 *
 * @param <K> The type of the first element in the pair
 * @param <V> The type of the second element in the pair
 */
public class Pair<K, V> {

    /**
     * The first element in the pair
     */
    protected K first;

    /**
     * The second element in the pair
     */
    protected V second;

    /**
     * @param first  The first element in the pair
     * @param second The second element in the pair
     */
    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns the first element in the pair
     *
     * @return The first element in the pair
     */
    public K getFirst() {
        return first;
    }

    /**
     * Sets the first element in the pair
     *
     * @param first The first elemnt in the pair to set
     */
    public void setFirst(K first) {
        this.first = first;
    }

    /**
     * Returns the second element in the pair
     *
     * @return The second element in the pair
     */
    public V getSecond() {
        return second;
    }

    /**
     * Sets the second element in the pair
     *
     * @param second The second element in the pair to set
     */
    public void setSecond(V second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o2) {
        if (! (o2 instanceof Pair)) {
            return false;
        }

        Pair<?, ?> p = (Pair<?, ?>) o2;
        return p.first == first || (p.first != null && p.first.equals(first)) &&
                p.second == second || (p.second != null && p.second.equals(second));
    }

    @Override
    public int hashCode() {
        return (this.first == null ? 0 : this.first.hashCode()) ^ (this.second == null ? 0 : this.second.hashCode());
    }
}
