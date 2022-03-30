package com.endava.internship.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StudentListTest {
    private StudentList students;

    private static final Student VALIK = new Student("Valik", LocalDate.now(), "DetailsValik");

    private static final Student VOVA = new Student("Vova", LocalDate.now().minusWeeks(1423), "DetailsVova");

    private static final Student PETEA = new Student("Petea", LocalDate.now().plusDays(1502), "DetailsPetea");


    @BeforeEach
    void setUp() {
        students = new StudentList();
    }

    @Test
    void testSizeShouldReturnZero() {
        //when
        final int size = students.size();

        //then
        assertEquals(0, size, "Size should be equal to 0");
    }

    @Test
    void testSizeShouldNotReturnZero() {
        //given
        students.add(VOVA);

        //when
        final int size = students.size();

        //then
        assertEquals(1, size, "Size should be equal to 1");
    }

    @Test
    void isEmptyShouldReturnTrue() {
        //when
        final boolean isEmpty = students.isEmpty();

        //then
        assertTrue(isEmpty, "If ArrayList has no elements, it is empty ad this test must return false");
    }

    @Test
    void isEmptyShouldReturnFalse() {
        //given
        students.add(VALIK);
        students.add(VOVA);

        //when
        final boolean isEmpty = students.isEmpty();

        //then
        assertFalse(isEmpty, "Because of I added 2 elements,ArrayList is not empty");
    }

    @Test
    void containsShouldFailureBecauseOfIllegalArg() {
        //given
        students.add(VALIK);

        //when
        final boolean ifContains = students.contains(1);

        //then
        assertFalse(ifContains, "Because there is illegal arg,it returns false");
    }

    @Test
    void containsShouldPassBecauseOfLegalArg() {
        //given
        students.add(null);
        students.add(VALIK);
        students.add(VOVA);

        //when
        final boolean ifContainsVova = students.contains(VOVA);
        final boolean ifContainsNull = students.contains(null);
        final boolean ifContainsValik = students.contains(VALIK);

        //then
        assertAll(
                () -> assertTrue(ifContainsNull),
                () -> assertTrue(ifContainsValik),
                () -> assertTrue(ifContainsVova)
        );
    }


    @Test
    void testNoArgtoArrayShouldPass() {
        //given
        students.add(null);
        students.add(PETEA);
        students.add(VALIK);

        //when
        final Object[] arrayOfStudents = students.toArray();

        //then
        assertArrayEquals(new Object[]{null, PETEA, VALIK}, arrayOfStudents, "These arrays should be same, return true");
    }

    @Test
    void testToArrayWithArgsShouldBePassed() {
        //given
        students.add(null);
        students.add(PETEA);
        students.add(VALIK);

        //when
        final Object[] arrayOfStudents = students.toArray(new Student[0]);

        //then
        assertArrayEquals(new Student[]{null, PETEA, VALIK}, arrayOfStudents,
                "These arrays should be same type,size and,content return true");
    }

    @Test
    void addStudentShouldReturnTrue() {
        //when
        final boolean ifAdded = students.add(VALIK);

        //then
        assertTrue(ifAdded, "Because of I added element to StudentList, result is true");
    }

    @Test
    void addNullReturnTrue() {
        //when
        final boolean ifAdded = students.add(null);

        //then
        assertTrue(ifAdded, "Because of I added element to StudentList, result is true");
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, 0, 1, 2, 7, 89, 100})
    void removeByIndexFromEmptyListShouldFail(int index) {
        //when
        final IndexOutOfBoundsException exception =
                assertThrows(IndexOutOfBoundsException.class, () -> students.remove(index), "Exception should be thrown");

        //then
        assertEquals("The index passed by you is invalid:index = " + index, exception.getMessage(), "With message");
    }

    @Test
    void removeByIndexListShouldBeOk() {
        //when
        students.add(VALIK);
        final Student deleted = students.remove(0);

        //then
        assertEquals(VALIK, deleted, "Because of students are same, test succeeds");
    }

    @Test
    void removeByIndexListNull() {
        //when
        students.add(null);
        students.add(null);
        final Student deleted = students.remove(0);

        //then
        assertNull(deleted, "Because of list can work with nulls, test succeeds");
    }

    @Test
    void tesGetShouldBeOk() {
        //when
        students.add(VALIK);
        final Student returnedStudent = students.get(0);

        //then
        assertEquals(VALIK, returnedStudent, "Because of objects are same,test succeeds");
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, 1, 2, 7, 89, 100})
    void tesGetShouldThrowAnException(int index) {
        //when
        students.add(VALIK);
        final IndexOutOfBoundsException exception =
                assertThrows(IndexOutOfBoundsException.class,
                        () -> students.get(index), "Exception should be thrown");

        //then
        assertEquals("Index is out of bounds", exception.getMessage(), "With message");
    }

    @Test
    void tesGetShouldBeOkWithNulls() {
        //when
        students.add(null);
        final Student returnedStudent = students.get(0);

        //then
        Assertions.assertNull(returnedStudent,
                "Because of Get method can work with nulls, I can get from list null reference");
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, 1, 2, 7, 89, 100})
    void setThrowsExceptionInvalidIndex(int index) {
        //when
        students.add(PETEA);
        final IndexOutOfBoundsException exception =
                assertThrows(IndexOutOfBoundsException.class,
                        () -> students.set(index, VALIK), "Exception should be thrown");

        //then
        assertEquals("Index is greater than size of ArrayList or smaller than 0: index = " + index,
                exception.getMessage(), "With message");
    }

    @Test
    void setSucceeds() {
        //when
        students.add(VALIK);
        final Student student = students.set(0, PETEA);

        //then
        assertEquals( VALIK,student, "Because I set Petea in place of Valik,in result I got previous element,should succeed");
    }

    @Test
    void testAddByIndexWithoutFail() {
        //when
        students.add(0, VALIK);
        students.add(0, PETEA);

        //then
        assertFalse(students.get(0).equals(VALIK), "Because Petea became first,Valik is second and equals returns false");
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, 1, 2, 7, 89, 100})
    void testAddByIndexShouldThrowAnException(int index) {
        //when
        final IndexOutOfBoundsException exception =
                assertThrows(IndexOutOfBoundsException.class, () -> students.add(index, VALIK), "Exception should be thrown");

        //then
        assertTrue(exception.getMessage().equals("The index passed by you is invalid:index = " + index));
    }

    @Test
    void testAddByIndexNulls() {
        //when
        students.add(0, null);
        students.add(0, null);
        final Student returnedObject = students.get(0);

        //then
        Assertions.assertNull(returnedObject,"I put nulls to list, therefore I get null");
    }

    @Test
    void testRemoveShouldBeOk() {
        //when
        students.add(0, PETEA);
        final Student returnedObject = students.remove(0);

        //then
        assertEquals(PETEA,returnedObject,  "Students should be same");
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, 1, 2, 7, 89, 100})
    void testRemoveShouldBeOk(int index) {
        //when
        students.add(0, PETEA);
        final IndexOutOfBoundsException exception =
                assertThrows(IndexOutOfBoundsException.class, () -> students.remove(index), "Exception should be thrown");

        //then
        assertEquals( "The index passed by you is invalid:index = " + index,exception.getMessage(), "Students should be same");
    }

    @Test
    void indexOfWorksNice() {
        //when
        students.add(0, PETEA);
        final int indexOfAddedStudent = students.indexOf(PETEA);

        //then
        assertEquals(0,indexOfAddedStudent,  "Because of Petea is single in list, it's index = 0");
    }

    @Test
    void indexOfWorksWithNullsNice() {
        //when
        students.add(0, PETEA);
        students.add(1, null);
        students.add(2, VALIK);
        students.add(3, null);
        final int indexOfAddedStudent = students.indexOf(PETEA);

        //then
        assertEquals(0,indexOfAddedStudent,
                "In case when I have more nulls in list, i return indexOf first of them,in this case index = 0");
    }

    @Test
    void indexOfFails() {
        //when
        students.add(0, PETEA);
        final int indexOfStudent = students.indexOf(VALIK);

        //then
        assertEquals(-1,indexOfStudent,  "Because list doesn't contains Valik, indexOf returns -1");
    }

    @Test
    void lastIndexOfSucceeds() {
        //when
        students.add(PETEA);
        students.add(PETEA);
        students.add(PETEA);
        students.add(PETEA);
        final  int lastIndexOfPetea = students.lastIndexOf(PETEA);

        //then
        assertEquals( 3,lastIndexOfPetea,"Last index of PETEA is 3,so test succeeds");
    }

    @Test
    void lastIndexOfNonExistingElementSucceeds() {
        //when
        students.add(PETEA);
        students.add(PETEA);
        students.add(PETEA);
        students.add(PETEA);
        final int lastIndexOfValik = students.lastIndexOf(VALIK);

        //then
        assertEquals(-1,lastIndexOfValik, "Because of Valik is not in list,output is -1");
    }

    @Test
    void subList() {
        //when
        students.add(VOVA);
        students.add(VALIK);
        students.add(PETEA);
        students.add(null);
        final StudentList subList = (StudentList) students.subList(0, 2);
        final int valikIndexInSubList = subList.indexOf(VALIK);
        final int valikIndexInList = students.indexOf(VALIK);

        //then
        assertAll(
                () -> assertEquals(0,subList.indexOf(VOVA)),
                () -> assertEquals(valikIndexInSubList, valikIndexInList)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, 1, 2, 7, 89, 100})
    void subListWithInvalidIndexes(int firstIndex) {
        //when
        students.add(PETEA);
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> students.subList(firstIndex, firstIndex + 2), "Exception should be thrown");

        //then
        assertEquals("Illegal arguments, at least one index is out of bounds",exception.getMessage(),
                "Size of list is less than subList needs");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 7, 5, 3})
    void subListWithInvalidIndexesCaseTwo(int firstIndex) {
        //when
        students.add(PETEA);
        students.add(PETEA);
        students.add(PETEA);
        students.add(PETEA);
        students.add(PETEA);
        students.add(PETEA);
        students.add(PETEA);
        students.add(PETEA);
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> students.subList(firstIndex, firstIndex - 1),
                        "Exception should be thrown");

        //then
        assertEquals( "Invalid arguments, i1 must ne greater than i",exception.getMessage(),
                "First index should be greater than last");
    }

    @Test
    void addAllThrowsExceptionBecauseOfNullArgument() {
        //when
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> students.addAll(null),
                        "Exception should be thrown");

        //then
        assertEquals( "Argument, passed to the method is null",exception.getMessage(),
                "First index should be greater than last");
    }

    @Test
    void addAllWorksNice() {
        //given
        final StudentList studentList = new StudentList();
        studentList.add(VALIK);
        studentList.add(VOVA);
        studentList.add(null);
        studentList.add(PETEA);
        studentList.add(null);

        //when
        final boolean result = students.addAll(studentList);

        //then
        assertAll(
                () -> assertTrue(result, "Returned true because of operation succeeds"),
                () -> assertEquals(studentList.get(0), students.get(0), "The order should be like in sourse list"),
                () -> assertEquals(studentList.get(1), students.get(1), "The order should be like in sourse list"),
                () -> assertEquals(studentList.get(2), students.get(2), "The order should be like in sourse list")
        );
    }

    @Test
    void containsAllFails() {
        //given
        students.add(VALIK);
        students.add(VOVA);
        final StudentList studentList = new StudentList();
        studentList.add(VALIK);
        studentList.add(VOVA);
        studentList.add(PETEA);

        //when
        final boolean ifContainsAll = students.containsAll(studentList);

        //then
        assertFalse(ifContainsAll, "Because of students does not contains all StudentList,result is false");
    }

    @Test
    void containsAllSucceeds() {
        //given
        students.add(VALIK);
        students.add(VOVA);
        final StudentList studentList = new StudentList();
        studentList.add(VALIK);
        studentList.add(VOVA);

        //when
        boolean ifContainsAll = students.containsAll(studentList);

        //then
        assertTrue(ifContainsAll, "Because of students contains all StudentList,result is true");
    }

    @Test
    void containsAllThrowsBecauseOfNullInArg() {
        //given
        students.add(VALIK);
        students.add(VOVA);

        //when
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> students.containsAll(null),
                        "Exception should be thrown");

        //then
        assertEquals( "Argument, passed to the method is null",exception.getMessage(),
                "Can't compare nulls");
    }

    @Test
    void containsAllWhenSecondListIsGreaterFails() {
        //given
        students.add(VALIK);
        final StudentList studentList = new StudentList();
        studentList.add(VALIK);
        studentList.add(VOVA);

        //when
        final boolean result = students.containsAll(studentList);

        //then
        assertFalse(result, "List cannot contain other list,that is greater than it");
    }

    @Test
    void testAddAllByIndex() {
        //given
        final StudentList studentList = new StudentList();
        studentList.add(VALIK);
        studentList.add(VOVA);
        students.add(PETEA);

        //when
        final boolean result = students.addAll(0, studentList);

        //then
        assertEquals(studentList.get(0),(students.get(0)),
                "StudentList added to Students and replaced elements in list to the right up two places");
    }

    @Test
    void removeAllShouldWorkNice() {
        //given
        final StudentList studentList = new StudentList();
        studentList.add(VALIK);
        studentList.add(VOVA);
        students.add(VALIK);
        students.add(VOVA);
        students.add(null);
        students.add(PETEA);
        students.add(null);

        //when
        final boolean result = students.removeAll(studentList);

        //then
        assertTrue(result,"The result is true because if all elements were deleted from student list");
        assertFalse(students.contains(VALIK), "Because it is removed, students does not contains this object");
        assertFalse(students.contains(VOVA), "Because it is removed, students does not contains this object");
    }

    @Test
    void removeAllWithNullArgumentWorksOk() {
        //given
        final StudentList studentList = null;
        students.add(VALIK);
        students.add(VOVA);
        students.add(null);
        students.add(PETEA);
        students.add(null);

        //when
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> students.removeAll(studentList),
                        "Exception should be thrown");

        //then
        assertEquals( "Argument, passed to the method is null",exception.getMessage(),
                "Argument, passed to the method is null");
    }
    @Test
    void removeAllWithNullReferenceInArgumentWorksOk() {
        //given
        final StudentList studentList = new StudentList();
        studentList.add(VALIK);
        studentList.add(null);
        studentList.add(PETEA);
        students.add(VALIK);
        students.add(VOVA);
        students.add(null);
        students.add(PETEA);
        students.add(null);

        //when
        final IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> students.removeAll(studentList),
                        "Exception should be thrown");

        //then
        assertEquals( "Your collection contains a null reference",exception.getMessage(),
                "I have not possibility to compare nulls correctly,if I meet it, throw exception");
    }
}