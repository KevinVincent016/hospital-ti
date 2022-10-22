package Structures;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    HashTable<String,String> hashTable;

    public void setup1(){
        hashTable = new HashTable<>(3);
    }

    public void setup2(){
        setup1();
        hashTable.insert("A00389132", "PatientTest1");
        hashTable.insert("A00312341", "PatientTest2");
        hashTable.insert("A00351934", "PatientTest3");
    }

    @Test
    public void insertMethodCheckCorrectInsertOfNewPatient(){
        setup1();
        hashTable.insert("A00315812", "PatientTest0");
        assertEquals("PatientTest0", hashTable.search("A00315812"));
    }

    @Test
    public void insertMethodCheckCorrectInsertOfPatientEqualKeys(){
        setup2();
        hashTable.insert("A00351934", "PatientTest4");
        assertEquals("PatientTest3", hashTable.search("A00351934"));
    }

    @Test
    public void insertMethodCheckInsertOnFullHashTable(){
        setup2();
        hashTable.insert("A00314151", "PatientTest4");
        hashTable.insert("A00783535", "PatientTest5");
        hashTable.insert("A00245733", "PatientTest6");
        assertEquals("PatientTest6", hashTable.search("A00245733"));
    }

    @Test
    public void searchMethodCheckCorrectReturnOfSearchedPatient(){
        setup2();
        assertEquals("PatientTest2", hashTable.search("A00312341"));
    }

    @Test
    public void searchMethodCheckCorrectReturnOnNullHashTable(){
        setup1();
        assertNull(hashTable.search("A00312341"));
    }

    @Test
    public void searchMethodCheckCorrectReturnWhenSearchNoneRegisteredPatient(){
        setup2();
        assertNull(hashTable.search("A00512341"));
    }

    @Test
    public void deleteKeyMethodCheckCorrectRemovalOfPatient(){
        setup2();
        hashTable.deleteKey("A00389132");
        assertNull(hashTable.search("A00389132"));
    }

    @Test
    public void deleteKeyMethodCheckRemovePatientWithSameKey(){
        setup2();
        hashTable.insert("A00389132", "PatientTest0");
        hashTable.deleteKey("A00389132");
        assertNull(hashTable.search("A00389132"));
    }

}