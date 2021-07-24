import static org.junit.Assert.*;
import org.junit.Test;

import java.lang.reflect.Array;

public class ArrayDequeTest {
    @Test
    public void testAdd(){
        //  starts from empty list
        ArrayDeque emptyDeque = new ArrayDeque();
        emptyDeque.addFirst(2);
        emptyDeque.addLast(3);
        emptyDeque.addFirst(1);
        emptyDeque.addLast(4);
        for(int i=0; i<4; i+=1){
            assertEquals(i+1, emptyDeque.get(i));
        }
        assertEquals(4, emptyDeque.size());

        // starts from has one element list
        ArrayDeque dq2 = new ArrayDeque(2);
        dq2.addFirst(1);
        dq2.addLast(3);
        dq2.addLast(4);
        for(int i=0; i<4; i+=1){
            assertEquals(i+1, dq2.get(i));
        }
        assertEquals(4, dq2.size());
    }

    @Test
    public void testRemove(){
        ArrayDeque ad1 = new ArrayDeque();
        for(int i=0; i<4; i+=1){
            ad1.addLast(i);
        }
        ad1.removeFirst();
        assertEquals(1, ad1.get(0));

        ad1.removeLast();
        assertEquals(2, ad1.get(1));

        assertEquals(2, ad1.size());
    }

    @Test
    public void testIncSize() {
        ArrayDeque ad = new ArrayDeque();
        for(int i=0; i<15; i+=1){
            ad.addLast(i);
        }
        ad.addLast(15);
        assertEquals(32, ad.items.length);
        assertEquals(16, ad.size());
        for(int i=0; i<16; i+=1){
            assertEquals(i, ad.get(i));
        }

        for(int i=0; i<15; i+=1){
            ad.addLast(i+16);
        }
        ad.addFirst(31);
        assertEquals(64, ad.items.length);
        assertEquals(32, ad.size());
        assertEquals(31, ad.get(0));
        for(int i=1; i<32; i+=1){
            assertEquals(i-1, ad.get(i));
        }
    }

    @Test
    public void testDecSize() {
        ArrayDeque ad = new ArrayDeque();
        for(int i=0; i<64; i+=1){
            ad.addLast(i);
        }
        for(int i=0; i<32; i+=1){
            ad.removeLast();
        }
        ad.removeLast();
        assertEquals(64, ad.items.length);
        assertEquals(31, ad.size());
        for(int i=0; i<31; i+=1){
            assertEquals(i, ad.get(i));
        }

        for(int i=0; i<15; i+=1){
            ad.removeLast();
        }
        ad.removeFirst();
        assertEquals(32, ad.items.length);
        assertEquals(15, ad.size());
        for(int i=0; i<15; i+=1){
            assertEquals(i+1, ad.get(i));
        }
    }
}
