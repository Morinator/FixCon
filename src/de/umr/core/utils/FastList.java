package de.umr.core.utils;

import java.util.*;

/**
 * This class implements, but operations like contains(x) are done in constant time, because it stores the
 * frequency of stored elements in a hash-based map.
 * @param <E>
 */
public final class FastList<E> extends AbstractSequentialList<E> implements List<E> {

    private final ArrayList<E> list = new ArrayList<>();
    private final HashMap<E, Integer> freq = new HashMap<>();

    public FastList() {}

    FastList(Collection<E> c) {
        this();
        addAll(c);
    }

    @Override
    public boolean add(E elem) {
        freq.putIfAbsent(elem, 0);
        freq.merge(elem, 1, Integer::sum);
        list.add(elem);
        return true;
    }

    @Override
    public boolean contains(Object o) {
        return freq.containsKey(o) && freq.get(o) > 0;
    }

    @Override
    public void clear() {
        list.clear();
        freq.clear();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E elem) {
        E prev_value = list.get(index);
        freq.merge(prev_value, -1, Integer::sum);
        freq.putIfAbsent(elem, 0);
        freq.merge(elem, 1, Integer::sum);
        list.set(index, elem);
        return prev_value;
    }

    @Override
    public E remove(int index) {
        E prev_value = list.get(index);
        freq.merge(prev_value, -1, Integer::sum);
        list.remove(index);
        return prev_value;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean has_changed = false;
        for (E e : c) {
            has_changed = add(e) || has_changed;
        }
        return has_changed;
    }
    
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }
}