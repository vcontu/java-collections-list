package com.endava.internship.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudentListTest {
    private StudentList students= new StudentList();;

    private static final String[] names = {"Valik","Vova","Petea","Alex"};

    private static final String[] details = {"details1","details2","details3","details4"};

    private static final LocalDate[] birthdays = {LocalDate.now(),LocalDate.now().minusWeeks(1000),LocalDate.now().plusDays(12590)};

    @Test
    void testSizeShouldReturnZero() {
        //given

        //when
        int size = students.size();
        //then
        assertTrue(size==0,"Size should be equal to 0");
    }

    @Test
    void testSizeShouldNotReturnZero() {
        //given
        Student valik = new Student(names[0],birthdays[0],details[0]);
        students.add(valik);
        //when
        int size = students.size();
        //then
        assertTrue(size==1,"Size should be equal to 1");
    }
    @Test
    void isEmptyShouldReturnTrue() {
        //given

        //when
        boolean isEmpty = students.isEmpty();
        //then
        assertTrue(isEmpty,"If ArrayList has no elements, it is empty ad this test must return false");
    }

    @Test
    void isEmptyShouldReturnFalse() {
        //given
        Student valik = new Student(names[0],birthdays[0],details[0]);
        Student vova = new Student(names[1],birthdays[1],details[1]);
        students.add(valik);
        students.add(vova);
        //when
        boolean isEmpty = students.isEmpty();
        //then
        assertFalse(isEmpty,"Because of I added 2 elements,ArrayList is not empty");
    }

    @Test
    void containsShouldFailureBecauseOfIllegalArg() {
        //given
        Student valik = new Student(names[0],birthdays[0],details[0]);
        students.add(valik);
        //when
        boolean ifContains= students.contains(1);
        //then
        assertFalse(ifContains,"Because there is illegal arg,it returns false");
    }

    @Test
    void containsShouldPassBecauseOfLegalArg() {
        //given
        Student valik = new Student(names[0],birthdays[0],details[0]);
        Student vova = new Student(names[1],birthdays[1],details[1]);
        students.add(null);
        students.add(valik);
        students.add(vova);
        //when
        boolean ifContainsVova= students.contains(new Student(names[0],birthdays[0],details[0]));
        boolean ifContainsNull = students.contains(null);
        boolean ifContainsValik = students.contains(valik);
        //then
        assertAll(
                ()->assertTrue(ifContainsNull),
                ()->assertTrue(ifContainsValik),
                ()->assertTrue(ifContainsVova)
        );
    }


    @Test
    void testNoArgtoArrayShouldPass() {
        //given
        Student valik = new Student(names[0],birthdays[0],details[0]);
        Student petea =new Student(names[3],birthdays[2],details[3] );
        students.add(null);
        students.add(petea);
        students.add(valik);
        //when
        Object [] arrayOfStudents =students.toArray();
        //then
        assertArrayEquals(new Object[]{null,petea,valik},arrayOfStudents,"These arrays should be same, return true");
    }

    @Test
    void testToArrayWithArgsShouldBePassed() {
        //given
        Student valik = new Student(names[0],birthdays[0],details[0]);
        Student petea =new Student(names[3],birthdays[2],details[3] );
        students.add(null);
        students.add(petea);
        students.add(valik);
        //when
        Object [] arrayOfStudents =students.toArray(new Student[0]);
        //then
        assertArrayEquals(new Student[]{null,petea,valik},arrayOfStudents,
                "These arrays should be same type,size and,content return true");
    }

    @Test
    void addStudentShouldReturnTrue() {
        //given
        Student valik = new Student(names[0],birthdays[0],details[0]);
        //when
        boolean ifAdded= students.add(valik);
        //then
        assertTrue(ifAdded,"Because of I added element to StudentList, result is true");
    }
    @Test
    void addNullReturnTrue() {
        //given
        Student valik = null;
        //when
        boolean ifAdded= students.add(valik);
        //then
        assertTrue(ifAdded,"Because of I added element to StudentList, result is true");
    }

    @ParameterizedTest
    @ValueSource(ints = {-10,0,1,2,7,89,100})
    void removeByIndexFromEmptyListShouldFail(int index) {
        //given

        //when
        IndexOutOfBoundsException exception =
                assertThrows(IndexOutOfBoundsException.class,()->students.remove(index),"Exception should be thrown");
        //then
        assertEquals("The index passed by you is invalid:index = " + index,exception.getMessage(),"With message");
    }
    @Test
    void removeByIndexListShouldBeOk() {
        //given
        Student valik = new Student(names[0],birthdays[0],details[0]);
        //when
        students.add(valik);
        Student deleted = students.remove(0);
        //then
        assertEquals(valik,deleted,"Because of students are same, test succeeds");
    }
    @Test
    void removeByIndexListNull() {
        //given

        //when
        students.add(null);
        students.add(null);
        Student deleted = students.remove(0);
        //then
        assertEquals(null,deleted,"Because of list can work with nulls, test succeeds");
    }

    @Test
    void tesGetShouldBeOk() {
        //given
        Student valik = new Student(names[0],birthdays[0],details[0]);
        //when
        students.add(valik);
        Student returnedStudent = students.get(0);
        //then
        assertEquals(new Student(names[0],birthdays[0],details[0]),returnedStudent,"Because of objects are same,test succeeds");
    }
    @ParameterizedTest
    @ValueSource(ints = {-10,1,2,7,89,100})
    void tesGetShouldThrowAnException(int index) {
        //given
        Student valik = new Student(names[0],birthdays[0],details[0]);
        //when
        students.add(valik);
        IndexOutOfBoundsException exception =
                assertThrows(IndexOutOfBoundsException.class,()->students.get(index),"Exception should be thrown");
        //then
        assertEquals("Index is out of bounds",exception.getMessage(),"With message");
    }
    @Test
    void tesGetShouldBeOkWithNulls() {
        //given
        Student valik = null;
        //when
        students.add(valik);
        Student returnedStudent = students.get(0);
        //then
        assertEquals(null,returnedStudent);
    }
    @Test
    void set() {

    }

    @Test
    void testAddByIndexWithoutFail() {
        //given
        Student valik = new Student(names[0],birthdays[0],details[0]);
        Student petea =new Student(names[3],birthdays[2],details[3] );
        //when
        students.add(0,valik);
        students.add(0,petea);
        //then
        assertFalse(students.get(0).equals(valik),"Because Petea became first,Valik is second and equals returns false");
    }
    @ParameterizedTest
    @ValueSource(ints = {-10,1,2,7,89,100})
    void testAddByIndexShouldThrowAnException(int index) {
        //given
        Student valik = new Student(names[0],birthdays[0],details[0]);
        //when
        IndexOutOfBoundsException exception =
                assertThrows(IndexOutOfBoundsException.class,()->students.add(index,valik),"Exception should be thrown");
        //then
        assertTrue(exception.getMessage().equals("The index passed by you is invalid:index = " + index));
    }
    @Test
    void testAddByIndexNulls() {
        //given
        Student valik = null;
        Student petea =null;
        //when
        students.add(0,null);
        students.add(0,null);
        Student returnedObject = students.get(0);
        //then
        assertEquals(returnedObject,valik);
    }

    @Test
    void testRemoveShouldBeOk() {
        //given
        Student petea = new Student(names[0],birthdays[0],details[0]);
        //when
        students.add(0,petea);
        Student returnedObject = students.remove(0);
        //then
        assertEquals(returnedObject,petea,"Students should be same");
    }
    @ParameterizedTest
    @ValueSource(ints = {-10,1,2,7,89,100})
    void testRemoveShouldBeOk(int index) {
        //given
        Student petea = new Student(names[0],birthdays[0],details[0]);
        //when
        students.add(0,petea);
        IndexOutOfBoundsException exception =
                assertThrows(IndexOutOfBoundsException.class,()->students.remove(index),"Exception should be thrown");
        //then
        assertEquals(exception.getMessage(),"The index passed by you is invalid:index = " + index,"Students should be same");
    }

    @Test
    void indexOfWorksNice() {
        //given
        Student petea = new Student(names[0],birthdays[0],details[0]);
        //when
        students.add(0,petea);
        int indexOfAddedStudent = students.indexOf(petea);
        //then
        assertEquals(indexOfAddedStudent,0,"Because of Petea is single in list, it's index = 0");
    }
    @Test
    void indexOfWorksWithNullsNice() {
        //given
        Student petea = null;
        Student valik = new Student(names[0],birthdays[0],details[0]);
        //when
        students.add(0,petea);
        students.add(1,null);
        students.add(2,valik);
        students.add(3,null);
        int indexOfAddedStudent = students.indexOf(petea);
        //then
        assertEquals(indexOfAddedStudent,0,
                "In case when I have more nulls in list, i return indexOf first of them,in this case index = 0");
    }

    @Test
    void indexOfFails() {
        //given
        Student petea = new Student(names[0],birthdays[0],details[0]);
        Student valik = new Student(names[1],birthdays[2],details[3]);
        //when
        students.add(0,petea);
        int indexOfStudent = students.indexOf(valik);
        //then
        assertEquals(indexOfStudent,-1,"Because list doesn't contains Valik, indexOf returns -1");
    }
    @Test
    void lastIndexOfSucceeds() {
        //given
        Student petea = new Student(names[0],birthdays[0],details[0]);
        //when
        students.add(petea);
        students.add(petea);
        students.add(petea);
        students.add(petea);
        int lastIndexOfPetea= students.lastIndexOf(petea);
        //then
        assertEquals(lastIndexOfPetea,3);
    }
    @Test
    void lastIndexOfNonExistingElementSucceeds() {
        //given
        Student petea = new Student(names[0],birthdays[0],details[0]);
        Student valik = new Student(names[1],birthdays[2],details[3]);
        //when
        students.add(petea);
        students.add(petea);
        students.add(petea);
        students.add(petea);
        int lastIndexOfValik= students.lastIndexOf(valik);
        //then
        assertTrue(lastIndexOfValik==-1,"Because of Valik is not in list,output is -1");
    }
    @Test
    void listIterator() {
    }

    @Test
    void testListIterator() {
    }

    @Test
    void subList() {
        //given
        Student petea = new Student(names[0],birthdays[0],details[0]);
        Student valik = new Student(names[1],birthdays[2],details[3]);
        Student vova = new Student(names[1],birthdays[1],details[1]);
        //when
        students.add(vova);
        students.add(valik);
        students.add(petea);
        students.add(null);
        StudentList subList = (StudentList) students.subList(0,2);
        int valikIndexInSubList = subList.indexOf(valik);
        int valikIndexInList = students.indexOf(valik);
        //then
        assertAll(
                ()->assertTrue(subList.indexOf(vova)==0),
                ()->assertEquals(valikIndexInSubList,valikIndexInList)
        );
    }
    @ParameterizedTest
    @ValueSource(ints = {-10,1,2,7,89,100})
    void subListWithInvalidIndexes(int firstIndex) {
        //given
        Student petea = new Student(names[0],birthdays[0],details[0]);
        //when
        students.add(petea);
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,()->students.subList(firstIndex,firstIndex+2),"Exception should be thrown");
        //then
        assertEquals(exception.getMessage(),"Illegal arguments, at least one index is out of bounds",
                "Size of list is less than subList needs");
    }
    @ParameterizedTest
    @ValueSource(ints = {1,2,7,5,3})
    void subListWithInvalidIndexesCaseTwo(int firstIndex) {
        //given
        Student petea = new Student(names[0],birthdays[0],details[0]);
        //when
        students.add(petea);
        students.add(petea);
        students.add(petea);
        students.add(petea);
        students.add(petea);
        students.add(petea);
        students.add(petea);
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,()->students.subList(firstIndex,firstIndex-1),
                        "Exception should be thrown");
        //then
        assertEquals(exception.getMessage(),"Invalid arguments, i1 must ne greater than i",
                "First index should be greater than last");
    }

    @Test
    void addAll() {
    }

    @Test
    void testToString() {
    }

    @Test
    void containsAll() {
    }

    @Test
    void testAddAll() {
    }

    @Test
    void removeAll() {
    }

    @Test
    void retainAll() {
    }
}