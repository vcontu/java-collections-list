package com.endava.internship.collections;

import java.util.*;

public class StudentList implements List<Student> {
    private int capacity;
    private int size = 0;
    private Student[] array;

    public StudentList() {
        this.capacity = 10;
        this.array = new Student[this.capacity];
    }

    public StudentList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.capacity = initialCapacity;
            this.array = new Student[this.capacity];
        } else {
            if (initialCapacity != 0) {
                throw new IllegalArgumentException();
            }

            this.capacity = 10;
            this.array = new Student[this.capacity];
        }

    }

    public StudentList(Collection<? extends Student> collection) {
        if (collection == null) {
            throw new NullPointerException();
        } else {
            this.size = collection.size();
            this.capacity = this.size * 2;
            this.array = new Student[this.capacity];
            Iterator var2 = collection.iterator();

            while(var2.hasNext()) {
                Student t = (Student)var2.next();
                this.add(t);
            }

        }
    }
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size <= 0;
    }

    @Override
    public boolean contains(Object o) {
        boolean flag = false;

        for(int i = 0; i < this.size; i++) {
            if (o.equals(this.array[i])) {
                flag = true;
                break;
            }
        }

        return flag;
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
        return (T[])Arrays.copyOf(this.array,this.size);
    }

    @Override
    public boolean add(Student student) {
        if ((size + size/2)>=capacity) {
            this.capacity *= 2;
        }

        ++this.size;
        Student[] arr = new Student[this.capacity];

        for(int i = 0; i < this.size - 1; ++i) {
            arr[i] = this.array[i];
        }

        arr[this.size - 1] = student;
        this.array = arr;
        return false;
    }

    @Override
    public boolean remove(Object o) {
        boolean flag = false;
        if (o != null) {
            for(int i = 0; i < this.size; ++i) {
                if (o.equals(this.array[i])) {
                    flag = true;
                }

                if (flag) {
                    this.array[i] = this.array[i + 1];
                }
            }
        }

        --this.size;
        return flag;
    }

    @Override
    public void clear() {
        for(int i = 0; i < this.size; ++i) {
            this.array[i] = null;
        }

        this.size = 0;
    }

    @Override
    public Student get(int i) {
        if (i > this.size && i < 0) {
            throw new IndexOutOfBoundsException();
        } else {
            return this.array[i];
        }
    }

    @Override
    public Student set(int i, Student student) {
        if (i >= 0 && i < this.size) {
            Student previous = this.array[i];
            this.array[i] = student;
            return previous;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void add(int index, Student student) {
        if (index >= 0 && index < this.size) {
            for(int i = this.size; i > index; --i) {
                this.array[i] = this.array[i - 1];
            }

            this.array[index] = student;
            ++this.size;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public Student remove(int index) {
        if (index >= 0 && index < this.size) {
            Student previous = this.array[index];

            for(int i = index; i < this.size; ++i) {
                this.array[i] = this.array[i + 1];
            }

            --this.size;
            return previous;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public int indexOf(Object o) {
        int index = -1;
        if (this.contains(o)) {
            for(int i = 0; i < this.size; ++i) {
                if (o.equals(this.array[i])) {
                    index = i;
                    break;
                }
            }
        }

        return index;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = -1;
        if (this.contains(o)) {
            for(int i = 0; i < this.size; ++i) {
                if (o.equals(this.array[i])) {
                    index = i;
                }
            }
        }

        return index;
    }

    @Override
    public ListIterator<Student> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<Student> listIterator(int i) {
        return new ListIterator<Student>() {
            int cursor = i;
            int lastReturnedElem = -1;
            @Override
            public boolean hasNext() {
                return cursor<size;
            }

            @Override
            public Student next() {
                return array[cursor++];
            }

            @Override
            public boolean hasPrevious() {
                return cursor>0;
            }

            @Override
            public Student previous() {
                lastReturnedElem = --cursor;
                return array[cursor];
            }

            @Override
            public int nextIndex() {
                return cursor+1;
            }

            @Override
            public int previousIndex() {
                return cursor+1;
            }

            @Override
            public void remove() {
                StudentList.this.remove(cursor);
            }

            @Override
            public void set(Student student) {
                StudentList.this.set(cursor,student);
            }

            @Override
            public void add(Student student) {
                StudentList.this.add(student);
            }
        };
    }

    @Override
    public List<Student> subList(int i, int i1) {
        if(i<0 || i>size || i1<0 || i1>size)throw new IndexOutOfBoundsException();
        else if(i>i1)throw new IllegalArgumentException();
        ArrayList<Student> students = new ArrayList<>();
        for(int j=i;j<i1;j++){
            students.add(array[j]);
        }
        return students;
    }

    @Override
    public boolean addAll(Collection<? extends Student> c) {
        if (c == null) {
            throw new NullPointerException();
        } else {
            Student[] obs = (Student[]) c.toArray();
            if (c.size() + this.size() < this.capacity) {
                for(int i = this.size(); i < this.size() + c.size(); ++i) {
                    this.array[i] = obs[i];
                }
            } else {
                this.capacity *= 2;
                Student[] arr = new Student[this.capacity];

                int i;
                for(i = 0; i < this.size; ++i) {
                    arr[i] = this.array[i];
                }

                for(this.size += c.size(); i < this.size; ++i) {
                    arr[i] = obs[i];
                }

                this.array = arr;
            }

            return true;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<size;i++){
            stringBuilder.append(array[i] + " ");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        } else {
            if (c.size() < this.size()) {
                Iterator var2 = c.iterator();
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
    public boolean addAll(int index, Collection<? extends Student> c) {
        if (index >= 0 && index <= this.size) {
            if (c == null) {
                throw new NullPointerException();
            } else {
                Student[] objects = (Student[]) c.toArray();
                Student[] arr;
                if (this.size() + c.size() < this.capacity) {
                    arr = new Student[this.size() - index];
                    System.arraycopy(this.array, index, arr, 0, this.size() - index);
                    System.arraycopy(objects, 0, this.array, index, c.size() + index);
                    System.arraycopy(arr, 0, this.array, c.size() + index, this.size() + c.size());
                } else {
                    this.capacity *= 2;
                    arr = new Student[this.capacity];
                    System.arraycopy(this.array, 0, arr, 0, index);
                    System.arraycopy(objects, 0, arr, index, index + c.size());
                    System.arraycopy(this.array, index, arr, index + c.size(), this.size + c.size());
                }

                this.size += c.size();
                return true;
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException();
        } else {
            Object[] objects = c.toArray();
            for(Object ob:objects){
                if(ob==null)throw new NullPointerException();
                remove(ob);
            }
            return true;
        }
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }
}
