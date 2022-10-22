package Structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {

    private Stack<Integer> stack;

    public void setup1() {
        stack = new Stack<>();
    }

    public void setup2() {
        stack = new Stack<>();
        stack.push(23);
        stack.push(10);
        stack.push(40);
        stack.push(25);
        stack.push(61);
    }

    @Test
    public void topTestStackSetup2() {
        setup2();
        assertEquals(61, stack.top());
    }

    @Test
    public void topTestStackEmpty() {
        setup1();
        boolean thoEx = false;
        try {
            stack.top();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            thoEx = true;
        }
        assertTrue(thoEx);
    }

    @Test
    public void topTestStackSetup2DeletingElements() {
        setup2();
        stack.pop();
        stack.pop();
        assertEquals(40, stack.top());
    }

    @Test
    public void emptyStackCheck() {
        setup1();
        assertTrue(stack.isEmpty());
    }

    @Test
    public void emptyStackCheckWithNoEmptyStack() {
        setup2();
        assertFalse(stack.isEmpty());
    }

    @Test
    public void NoEmptyStackCheck() {
        setup2();
        assertFalse(stack.isEmpty());
    }

    @Test
    public void popTestMethodWhenNull() {
        setup1();
        boolean aux = false;
        try {
            stack.pop();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            aux = true;
        }
        assertTrue(aux);
    }

    @Test
    public void popTestMethodSetup2() {
        setup2();
        assertEquals(61, stack.pop());
    }

    @Test
    public void popTestMethodClearStack() {
        setup2();
        boolean thoEx = false;
        try {
            stack.pop();
            stack.pop();
            stack.pop();
            stack.pop();
            stack.pop();
            stack.pop();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            thoEx = true;
        }
        assertTrue(thoEx);
    }

    @Test
    public void pushTestMethodWhenNull() {
        setup1();
        stack.push(null);
        System.out.println(stack);
        assertNull(stack.top());
    }

    @Test
    public void pushTestMethodSetup2AddingElement() {
        setup2();
        stack.push(50);
        stack.push(45);
        assertEquals(45, stack.top());
    }

    @Test
    public void pushTestMethodSetup2DeletingElementsThenAddNewOnes() {
        setup2();
        stack.pop();
        stack.pop();
        stack.pop();
        stack.push(50);
        assertEquals(50, stack.top());
    }

}