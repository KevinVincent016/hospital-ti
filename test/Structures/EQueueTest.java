package Structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EQueueTest {

    EQueue<String> queue;

    public void setup1(){
        queue = new EQueue<>();
    }

    public void setup2(){
        setup1();
        queue.enqueue("Patient1");
        queue.enqueue("Patient2");
        queue.enqueue("Patient3");
    }

    @Test
    public void enqueueMethodCheckCorrectAdditionOfPatient(){
        setup1();
        queue.enqueue("Patient1");
        assertEquals("Patient1", queue.peek());
    }

    @Test
    public void enqueueMethodCheckCorrectAdditionOfPatientAndCorrectManageOfFIFOLogic(){
        setup2();
        assertEquals("Patient1", queue.peek());
    }

    @Test
    public void enqueueMethodCheckAdditionOfDifferentPatients(){
        setup2();
        queue.enqueue("Patient4");
        queue.enqueue("Patient5");
        //Ensure than all the patient had been added to the queue
        assertEquals("Patient1", queue.dequeue());
        assertEquals("Patient2", queue.dequeue());
        assertEquals("Patient3", queue.dequeue());
        assertEquals("Patient4", queue.dequeue());
        assertEquals("Patient5", queue.dequeue());
    }

    @Test
    public void dequeueMethodCheckCorrectRemovalOfPatient(){
        setup2();
        queue.dequeue();
        assertEquals("Patient2", queue.peek());
    }

    @Test
    public void dequeueMethodCheckCorrectRemovalOfPatientAndCorrectReturnOfDeletedPatient(){
        setup2();
        assertEquals("Patient1", queue.dequeue());
        assertEquals("Patient2", queue.peek());
    }

    @Test
    public void dequeueMethodCheckCorrectRemovalOfVariousPatients(){
        setup2();
        assertEquals("Patient1", queue.dequeue());
        assertEquals("Patient2", queue.dequeue());
        assertEquals("Patient3", queue.dequeue());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void peekMethodCheckCorrectReturnOfFirstPatientInQueue(){
        setup2();
        assertEquals("Patient1", queue.peek());
    }

    @Test
    public void peekMethodCheckCorrectReturnOfFirstPatientAfterAddingAPatient(){
        setup2();
        queue.enqueue("Patient0");
        assertEquals("Patient1", queue.peek());
    }

    @Test
    public void peekMethodCheckCorrectReturnOfFirstPatientAfterRemovePatients(){
        setup2();
        queue.dequeue();
        assertEquals("Patient2", queue.peek());
    }

    @Test
    public void isEmptyMethodCheckCorrectReturnInAEmptyQueue(){
        setup1();
        assertTrue(queue.isEmpty());
    }

    @Test
    public void isEmptyMethodCheckCorrectReturnInANoneEmptyQueue(){
        setup2();
        assertFalse(queue.isEmpty());
    }

    @Test
    public void isEmptyMethodCheckCorrectReturnAfterRemoveAllPatientsInQueue(){
        setup2();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

}