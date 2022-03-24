package com.endava.internship.collections;

import java.util.*;
import java.util.function.Consumer;

public class StudentList<T> implements List<T> {

    private static final int DEFAULT_CAPACITY = 10;

    private int size;

    private int capacity = 10;

    private Object[] elements;

    public StudentList() {
        this.elements = new Object[DEFAULT_CAPACITY];
    }

    public StudentList(int capacity) {
        this.capacity = capacity;
        this.elements = new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object t) {
        if (Objects.isNull(t)) {
            for (int i = 0; i < size; i++) {
                if (null == elements[i]) {
                    return true;
                }
            }
        }else{
            for (int i = 0; i < size; i++) {
                if (t.equals(elements[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new StudentList.Iter();
    }
    private class Iter implements Iterator<T> {
        int cursor;       // index of next element to return
        int lastElem = -1; // index of last element returned; -1 if no such


        // prevent creating a synthetic constructor
        Iter() {}

        public boolean hasNext() {
            return cursor != size;
        }

        public T next() {
            int i = cursor;
            if (i >= size) {
                throw new NoSuchElementException();
            }
            Object[] elements = StudentList.this.elements;

            if (i >= elements.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (T) elements[lastElem = i];
        }

        public void remove() {
            if (lastElem < 0) {
                throw new IllegalStateException();
            }
            try {
                StudentList.this.remove(lastElem);
                cursor = lastElem;
                lastElem = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            Objects.requireNonNull(action);
            final int size = StudentList.this.size;
            int i = cursor;

            if (i < size) {
                final Object[] es = elements;
                if (i >= es.length) {
                    throw new ConcurrentModificationException();
                }

                for (; i < size ; i++) {
                    action.accept(elementAt(es, i));
                }

                cursor = i;
                lastElem = i - 1;
            }
        }
        private <T> T elementAt(Object[] es, int index) {
            return (T) es[index];
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        if(Objects.isNull(ts)){
            throw new NullPointerException();
        }
        if(ts.length >= this.size()){
            for (int i = 0; i < this.size; i++) {
                ts[i] = (T) this.get(i);
            }
            return ts;
        }else{
            Object[] newResizedArray = new Object[ts.length];
            for (int i = 0; i < ts.length; i++) {
                newResizedArray[i] = (T) this.get(i);
            }
        return (T[]) newResizedArray;
        }
    }

    @Override
    public boolean add(T t) {
        if (size == elements.length) {
            resizeArray();
        }
        elements[size++] = t;
        return true;
    }

    @Override
    public boolean remove(Object t) {
        if (!this.contains(t)) {
            return false;
        }
        Object[] elements = decreasedArray(size);
        int indexOfDeletedObject = this.indexOf(t);
        copyArray(indexOfDeletedObject, elements);
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++)
            elements[i] = null;
        size = 0;
    }

    @Override
    public T get(int i) {
        if (i >= 0 && i <= size) {
            return (T) elements[i];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public T set(int i, T t) {
        if (i >= 0 && i < size) {
            return (T) (elements[i] = t);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void add(int i, T t) {
        if (i < 0 && i > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == elements.length) {
            resizeArray();
        }
        for (int start = size; start > i; start--) {
            elements[start] = elements[start - 1];
        }
        elements[i] = t;
    }

    @Override
    public T remove(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException();
        }
        final Object elementToDelete = elements[i];
        Object[] elements = decreasedArray(size);
        copyArray(i, elements);
        return (T) elementToDelete;
    }

    @Override
    public int indexOf(Object t) {
        if (Objects.isNull(t)) {
            for (int i = 0; i < size; i++) {
                if (null == elements[i]) {
                    return i;
                }
            }
        }
        for (int i = 0; i < size; i++) {
            if (t.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object t) {
        if (Objects.nonNull(t)) { // ???
            for (int i = size - 1; i >= 0; i--) {
                if (t.equals(elements[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIter(0);
    }

    @Override
    public ListIterator<T> listIterator(int i) {
        return new ListIter(i);
    }

    private class ListIter extends Iter implements ListIterator<T> {
        ListIter(int index) {
            super();
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public T previous() {
            int i = cursor - 1;
            if (i < 0){
                throw new NoSuchElementException();
            }
            Object[] elements = StudentList.this.elements;
            if (i >= elements.length) {
                throw new ConcurrentModificationException();
            }
            cursor = i;
            return (T) elements[lastElem = i];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void set(T t) {
            if (lastElem < 0) {
                throw new IllegalStateException();
            }
            try {
                StudentList.this.set(lastElem, t);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(T t) {

            try {
                int i = cursor;
                StudentList.this.add(i, t);
                cursor = i + 1;
                lastElem = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public List<T> subList(int i, int i1) {
        List<T> subList = new StudentList<>();
        for (int j = i; j < i1; j++) {
            subList.add((T) elements[j]);
        }
        return subList;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        resizeArray(calculateCapacity(collection.size()));
        collection.forEach(this::add);
        return true;
    }


    //Ignore this for homework
    @Override
    public boolean containsAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    //Ignore this for homework
    @Override
    public boolean addAll(int i, Collection<? extends T> collection) {
        throw new UnsupportedOperationException();
    }

    //Ignore this for homework
    @Override
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    //Ignore this for homework
    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    // Utility methods
    private void resizeArray() {
        this.capacity = elements.length * 2;
        elements = Arrays.copyOf(elements, capacity);
    }

    private void resizeArray(int capacity) {
        elements = Arrays.copyOf(elements, capacity);
        this.capacity = capacity;
    }

    private int calculateCapacity(int size) {
        final int sizeCombined = size + this.size();
        int currentCapacity = this.capacity;
        while (currentCapacity < sizeCombined) {
            currentCapacity *= 2;
        }
        return currentCapacity;
    }

    private T[] decreasedArray(int size){
        Object[] tempArr = new Object[size - 1];
        return (T[])tempArr;
    }

    private void copyArray(int i, Object[] tempArr) {
        for (int j = 0; j < size - 1; j++) {
            if(j >= i){
                tempArr[j] = elements[j + 1];
            }else{
                tempArr[j] = elements[j];
            }
        }
        size--;
        elements = tempArr;
    }
}

