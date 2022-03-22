package com.endava.internship.collections;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Arrays;
import java.util.ListIterator;


public class StudentList implements List<Student> {

    private int capacity;

    private int size = 0;

    private Student[] array;


    public StudentList() {
        this.capacity = 10;
        this.array = new Student[this.capacity];
    }

    public StudentList(int initialCapacity) {
        if (initialCapacity >= 0) {
            this.capacity = initialCapacity;
            this.array = new Student[this.capacity];
        }
        else {
            throw new IllegalArgumentException("Initial capacity must be greater than 0 : initialCapacity = " + initialCapacity);
            }
    }

    public StudentList(Collection<? extends Student> collection) {
        if (Objects.isNull(collection)) {
            throw new IllegalArgumentException("Collection does not exists");
        } else {
            final Student[] students = collection.toArray(new Student[0]);
            this.size = collection.size();
            this.capacity = this.size * 2;
            this.array = new Student[this.capacity];
            System.arraycopy(students,0,array,0,size);
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object object) {
        for (int i = 0; i < this.size; i++) {
            if (array[i].equals(object)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Student> iterator() {
        return listIterator(0);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.array, this.size);
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return (T[]) Arrays.copyOf(this.array, this.size);
    }

    @Override
    public boolean add(Student student) {
        if (capacity == 0) {
            capacity = 2;
            array = new Student[capacity];
            array[0] = student;
            size++;
        } else {
            if ((size + size / 2) >= capacity) {
                this.capacity *= 2;
            }

            ++this.size;
            final Student[] students = new Student[this.capacity];

            for (int i = 0; i < this.size - 1; ++i) {
                students[i] = this.array[i];
            }

            students[this.size - 1] = student;
            this.array = students;
        }
        return true;
    }

    @Override
    public boolean remove(Object object) {
        boolean flag = false;
        if (Objects.nonNull(object)) {
            for (int i = 0; i < this.size; ++i) {
                if (object.equals(this.array[i])) {
                    flag = true;
                }

                if (flag) {
                    this.array[i] = this.array[i + 1];
                }
            }
            --this.size;
        }
        return flag;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.size; ++i) {
            this.array[i] = null;
        }

        this.size = 0;
    }

    @Override
    public Student get(int index) {
        if (index > this.size || index < 0) {
            throw new IndexOutOfBoundsException(" Index is out of bounds");
        } else {
            return this.array[index];
        }
    }

    @Override
    public Student set(int index, Student student) {
        if (index >= 0 && index < this.size) {
            final Student previous = this.array[index];
            this.array[index] = student;
            return previous;
        } else {
            throw new IndexOutOfBoundsException("Index is greater than size of ArrayList or smaller than 0: index = " + index);
        }
    }

    @Override
    public void add(int index, Student student) {
        if (index >= 0 && index < size) {
            for (int i = this.size; i > index; --i) {
                this.array[i] = this.array[i - 1];
            }

            this.array[index] = student;
            ++this.size;
        } else {
            throw new IndexOutOfBoundsException("The index passed by you is invalid:index = " + index);
        }
    }

    @Override
    public Student remove(int index) {
        if (index >= 0 && index < this.size) {
            final Student previous = this.array[index];

            for (int i = index; i < this.size; ++i) {
                this.array[i] = this.array[i + 1];
            }

            --this.size;
            return previous;
        } else {
            throw new IndexOutOfBoundsException("The index passed by you is invalid:index = " + index);
        }
    }

    @Override
    public int indexOf(Object object) {
        for (int i = 0; i < this.size; ++i) {
            if (array[i].equals(object)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        int index = -1;
        for (int i = 0; i < this.size; ++i) {
            if (array[i].equals(object)) {
                index = i;
            }
        }
        return index;
    }

    @Override
    public ListIterator<Student> listIterator() {
        return new MyListaiterator<>();
    }

    @Override
    public ListIterator<Student> listIterator(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index is out of bounds: index = " + index);
        }
        return new MyListaiterator<>(index);
    }

    @Override
    public List<Student> subList(int index, int index1) {
        if (index < 0 || index > size || index1 < 0 || index1 > size)
            throw new IllegalArgumentException(" Illegal arguments, at least one index is out of bounds");
        else if (index > index1)
            throw new IllegalArgumentException("Invalid arguments, i1 must ne greater than i");
        final StudentList students = new StudentList();
        for (int j = index; j < index1; j++) {
            students.add(array[j]);
        }
        return students;
    }

    @Override
    public boolean addAll(Collection<? extends Student> collection) {
        if (Objects.isNull(collection)) {
            throw new IllegalArgumentException("Argument, passed to the method is null");
        } else {
            final Student[] students = (Student[]) collection.toArray();
            if (collection.size() + this.size() < this.capacity) {
                for (int i = this.size(); i < this.size() + collection.size(); ++i) {
                    this.array[i] = students[i];
                }
            } else {
                this.capacity *= 2;
                Student[] arr = new Student[this.capacity];

                int i;
                for (i = 0; i < this.size; ++i) {
                    arr[i] = this.array[i];
                }

                for (this.size += collection.size(); i < this.size; ++i) {
                    arr[i] = students[i];
                }

                this.array = arr;
            }

            return true;
        }
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append(array[i] + " ");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        if (Objects.isNull(collection)) {
            throw new IllegalArgumentException("Argument, passed to the method is null");
        } else {
            if (collection.size() < this.size()) {
                final Iterator var2 = collection.iterator();
                if (var2.hasNext()) {
                    Object ob = var2.next();
                    if (!this.contains(ob)) {
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends Student> collection) {
        if (index >= 0 && index <= this.size) {
            if (Objects.isNull(collection)) {
                throw new IllegalArgumentException("Argument, passed to the method is null");
            } else {
                final Student[] objects = (Student[]) collection.toArray();
                Student[] students;
                if (this.size() + collection.size() < this.capacity) {
                    students = new Student[this.size() - index];
                    System.arraycopy(this.array, index, students, 0, this.size() - index);
                    System.arraycopy(objects, 0, this.array, index, collection.size() + index);
                    System.arraycopy(students, 0, this.array, collection.size() + index, this.size() + collection.size());
                } else {
                    this.capacity *= 2;
                    students = new Student[this.capacity];
                    System.arraycopy(this.array, 0, students, 0, index);
                    System.arraycopy(objects, 0, students, index, index + collection.size());
                    System.arraycopy(this.array, index, students, index + collection.size(), this.size + collection.size());
                }

                this.size += collection.size();
                return true;
            }
        } else {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (Objects.isNull(collection)) {
            throw new IllegalArgumentException("Argument, passed to the method is null");
        } else {
            final Object[] objects = collection.toArray();
            for (Object object : objects) {
                if (Objects.isNull(object)) {
                    throw new IllegalArgumentException("Your collection contains a null reference");
                }
                remove(object);
            }
            return true;
        }
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }
    private class MyListaiterator<Student> implements ListIterator<com.endava.internship.collections.Student> {
        int cursor;
        int lastReturnedElem = -1;

        MyListaiterator() {
            cursor = 0;
        }

        MyListaiterator(int index) {
            cursor = index;
        }

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public com.endava.internship.collections.Student next() {
            return array[cursor++];
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public com.endava.internship.collections.Student previous() {
            lastReturnedElem = --cursor;
            return array[cursor];
        }

        @Override
        public int nextIndex() {
            return cursor + 1;
        }

        @Override
        public int previousIndex() {
            return cursor + 1;
        }

        @Override
        public void remove() {
            StudentList.this.remove(cursor);
        }

        @Override
        public void set(com.endava.internship.collections.Student student) {
            StudentList.this.set(cursor, student);
        }

        @Override
        public void add(com.endava.internship.collections.Student student) {
            StudentList.this.add(student);
        }
    }
}
