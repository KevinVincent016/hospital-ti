package Structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapMaxTest {

    private HeapMax<String> heapMax;

    public void setup1() {
        heapMax = new HeapMax<>();
    }

    public void setup2() {
        heapMax = new HeapMax<>();
        heapMax.insert(2, "Paciente 1");
        heapMax.insert(3, "Paciente 2");
        heapMax.insert(1, "Paciente 3");
        heapMax.insert(5, "Paciente 4");
        heapMax.insert(2, "Paciente 5");
    }

    @Test
    public void testInsertMethodCheckInsertValueDifferentKey() {
        setup1();
        heapMax.insert(1, "Paciente1");
        heapMax.insert(2, "Paciente2");
        assertEquals("Paciente2 2  Paciente1 1  ", heapMax.printPQ());
    }

    @Test
    public void testInsertMethodCheckInsertValueSameKey() {
        setup1();
        heapMax.insert(1, "Paciente1");
        heapMax.insert(1, "Paciente2");
        assertEquals("Paciente1 1  Paciente2 1  ", heapMax.printPQ());
    }

    @Test
    public void testInsertMethodCheckInsertValueWithSetup2() {
        setup2();
        heapMax.insert(4, "PacienteTest");
        assertEquals("Paciente 4 5  Paciente 2 3  PacienteTest 4  Paciente 1 2  Paciente 5 2  Paciente 3 1  ", heapMax.printPQ());
    }

    @Test
    public void testMaximunMethodCheckWhenPQNull() {
        setup1();
        boolean thoEx = false;
        try {
            heapMax.maximun();
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            thoEx = true;
        }
        assertTrue(thoEx);
    }

    @Test
    public void testMaximunMethodCheckMaximunValueSetup2() {
        setup2();
        assertEquals("Paciente 4", heapMax.maximun());
    }

    @Test
    public void testMaximunMethodCheckMaximunValueSetup2AddingElements() {
        setup2();
        heapMax.insert(5, "PacienteTest");
        heapMax.insert(6, "PacienteTest2");
        assertEquals("PacienteTest2", heapMax.maximun());
    }

    @Test
    public void testExtractMaxMethodWithPQNull() {
        setup1();
        boolean thoEx = false;
        try {
            heapMax.extractMax();
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            thoEx = true;
        }
        assertTrue(thoEx);
    }

    @Test
    public void testExtractMaxMethodWithSetup2ShowingValueMax() {
        setup2();
        assertEquals("Paciente 4", heapMax.extractMax());
    }

    @Test
    public void testExtractMaxMethodWithSetup2CheckValueMaxBeingDeleted() {
        setup2();
        heapMax.extractMax();
        assertEquals("Paciente 2 3  Paciente 5 2  Paciente 3 1  Paciente 1 2  ", heapMax.printPQ());
    }

    @Test
    public void testIncreaseKeyMethodWithPQNull() {
        setup1();
        boolean thoEx = false;
        try {
            heapMax.increaseKey(5, "Paciente 1");
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            thoEx = true;
        }
        assertTrue(thoEx);
    }

    @Test
    public void testIncreaseKeyMethodWithElementInSetup2() {
        setup2();
        heapMax.increaseKey(6, "Paciente 3");
        //Ahora Paciente 3 tiene la key mas grande de los demas datos, si se cambio debe mostrarse como el maximo
        assertEquals("Paciente 3", heapMax.maximun());
    }

    @Test
    public void testIncreaseKeyMethodErrorKeySmallerThanActual() {
        setup2();
        heapMax.increaseKey(2, "Paciente 4");
        //La key es menor entonces no se modifica y Paciente 4 sigue siendo el maximo con key 5
        assertEquals("Paciente 4", heapMax.maximun());
    }

}