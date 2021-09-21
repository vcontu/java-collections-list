package com.endava.internship.collections;

import lombok.ToString;

import java.util.*;
import java.util.function.Consumer;

@ToString
public class StudentList extends AbstractList<Student> implements List<Student> {

    private int sizeOfArray = 0;
    private static int CAPACITY = 10;
    private static Student[] elements = new Student[CAPACITY];

    public StudentList() {
    }

    public StudentList(int initialCapacity) {
        CAPACITY = initialCapacity;
        updateCapacity(elements, initialCapacity);
    }

    public StudentList(Collection<? extends Student> studentsCollection) {
        addAll(studentsCollection);
    }

    @Override
    public int size() {
        return sizeOfArray;
    }

    @Override
    public boolean isEmpty() {
        return sizeOfArray == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1 ? true : false;
    }

    @Override
    public Iterator<Student> iterator() {
        return new Iterator<Student>() {
            private int initial = 0;

            @Override
            public boolean hasNext() {
                return elements.length > initial;
            }

            @Override
            public Student next() {
                return elements[initial++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, sizeOfArray);
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        if (ts.length < size()) {
            return (T[]) Arrays.copyOf(elements, size(), ts.getClass());
        }
        System.arraycopy(elements, 0, ts, 0, ts.length);
        if (ts.length > size()) {
            ts[size()] = null;
        }
        return ts;
    }

    @Override
    public boolean add(Student student) {
        checkCapacity();
        elements[sizeOfArray++] = student;
        return true;
    }

    private void checkCapacity() {
        if (elements.length == CAPACITY || elements.length > CAPACITY) {
            CAPACITY = elements.length;
            int capacity = CAPACITY + 10;
            updateCapacity(elements, capacity);
        }
    }

    private void updateCapacity(Student[] students, int newSize) {
        elements = Arrays.copyOf(students, newSize);
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index != -1) {
            System.arraycopy(elements, index + 1, elements, index, size() - index + 1);
            sizeOfArray--;
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size(); i++) {
            elements[i] = null;
        }
        CAPACITY = 10;
        sizeOfArray = 0;
    }

    @Override
    public Student get(int i) {
        checkTheExistanceOfIndex(i);
        return elements[i];
    }

    private boolean checkTheExistanceOfIndex(int index) {
        if (size() <= index) {
            throw new IndexOutOfBoundsException("Array size: " + size() + " is less or equal to index: " + index);
        }
        return true;
    }

    private boolean checkIndex(int index) {
        if (size() < index || index < 0) {
            throw new IndexOutOfBoundsException("Array size: " + size() + " less than index: " + index + " or index less than: 0");
        }
        return true;
    }

    @Override
    public Student set(int i, Student student) {
        checkTheExistanceOfIndex(i);
        Student previous = elements[i];
        elements[i] = student;
        return previous;
    }

    @Override
    public void add(int i, Student student) {
        if (checkIndex(i)) {
            Student[] beforeIndex = Arrays.copyOfRange(elements, 0, i + 1);
            Student[] afterIndex = Arrays.copyOfRange(elements, i, elements.length);
            beforeIndex[i] = student;
            sizeOfArray++;
            mergeTheArray(beforeIndex, afterIndex, beforeIndex.length, afterIndex.length);

        }
    }

    private void mergeTheArray(Student[] beforeIndex, Student[] afterIndex, int lengthOfBefore, int lengthOfAfter) {
        elements = new Student[lengthOfBefore + lengthOfAfter];
        System.arraycopy(beforeIndex, 0, elements, 0, lengthOfBefore);
        System.arraycopy(afterIndex, 0, elements, lengthOfBefore, lengthOfAfter);
    }

    @Override
    public Student remove(int i) {
        Student targerToRemove = null;
        if (checkTheExistanceOfIndex(i)) {
            targerToRemove = get(i);
            remove(get(i));
        }
        return targerToRemove;
    }

    @Override
    public int indexOf(Object o) {
        if (o != null) {
            for (int index = 0; index < size(); index++) {
                if (elements[index].equals(o)) {
                    return index;
                }
            }
        } else {
            for (int index = 0; index < size(); index++) {
                if (elements[index] == null) {
                    return index;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o != null) {
            for (int index = size() - 1; index >= 0; index--) {
                if (elements[index].equals(o)) {
                    return index;
                }
            }
        } else {
            for (int index = size() - 1; index >= 0; index--) {
                if (elements[index] == null) {
                    return index;
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator<Student> listIterator() {
        return new StudentItr();
    }

    @Override
    public ListIterator<Student> listIterator(int i) {
        checkIndex(i);
        return new StudentItr(i);
    }

    @Override
    public List<Student> subList(int i, int i1) {
        subListRangeCheck(i, i1, size());
        return Arrays.asList(Arrays.copyOfRange(elements, i, i1));
    }

    static void subListRangeCheck(int from, int to, int arraySize) {
        if (to > arraySize)
            throw new IndexOutOfBoundsException("to is: " + to);
        if (from > to)
            throw new IllegalArgumentException("from: " + from + " - to: " + to);
        if (from < 0)
            throw new IndexOutOfBoundsException("from: " + from);
    }

    @Override
    public boolean addAll(Collection<? extends Student> collection) {
        int sizeOfCollection = collection.size();
        System.arraycopy(collection.toArray(), 0, elements, size(), sizeOfCollection);
        sizeOfArray += sizeOfCollection;
        return sizeOfCollection > 0;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int i, Collection<? extends Student> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    private class StudentItr implements ListIterator<Student> {
        int cursor = 0;

        public StudentItr(int index) {
            cursor = index;
        }

        public StudentItr() {
        }

        @Override
        public boolean hasNext() {
            return checkIndex(cursor + 1);
        }

        @Override
        public Student next() {
            hasNext();
            return elements[cursor + 1];
        }

        @Override
        public boolean hasPrevious() {
            return checkIndex(cursor - 1);
        }

        @Override
        public Student previous() {
            hasPrevious();
            return elements[cursor - 1];
        }

        @Override
        public int nextIndex() {
            hasNext();
            return cursor + 1;
        }

        @Override
        public int previousIndex() {
            hasPrevious();
            return cursor - 1;
        }

        @Override
        public void remove() {
            StudentList.this.remove(cursor);
        }

        @Override
        public void forEachRemaining(Consumer<? super Student> action) {
            ListIterator.super.forEachRemaining(action);
        }

        @Override
        public void set(Student student) {
            StudentList.this.set(cursor, student);
        }

        @Override
        public void add(Student student) {
            StudentList.this.add(cursor, student);
        }
    }
}
