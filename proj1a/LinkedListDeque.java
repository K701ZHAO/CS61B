public class LinkedListDeque<T> {
    private class Node {
        private T item;
        private Node next;
        private Node prev;

        public Node(T i, Node n, Node p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    private Node sentinel;
    private Node last;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        last = sentinel;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T i) {
        Node first = new Node(i, sentinel.next, sentinel);
        sentinel.next.prev = first;
        sentinel.next = first;
        size += 1;
        if (last == sentinel) {
            last = first;
        }
    }

    public void addLast(T i) {
        Node newLast = new Node(i, sentinel, last);
        last.next = newLast;
        sentinel.prev = newLast;
        last = newLast;
        size += 1;
    }

    public T removeFirst() {

        if (isEmpty()) {
            throwException();
        }
        Node first = sentinel.next;
        sentinel.next = first.next;
        first.next.prev = sentinel;
        size -= 1;
        if (size == 0) {
            last = sentinel;
        }
        return first.item;
    }

    public T removeLast() {
        if (isEmpty()) {
            throwException();
        }
        T item = last.item;
        last.prev.next = sentinel;
        sentinel.prev = last.prev;
        last = last.prev;
        size -= 1;
        return item;
    }

    public T get(int index) {
        if (index < 0 | index > (size - 1)) {
            throwException();
        }
        Node p = sentinel;
        for (int i = 0; i <= index; i += 1) {
            p = p.next;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        if (index < 0 | index > (size - 1)) {
            throwException();
        }
        return getRecursiveHelper(index, sentinel.next);
    }

    private T getRecursiveHelper(int index, Node p) {
        if (index == 0) {
            return p.item;
        }
        return getRecursiveHelper(index - 1, p.next);
    }

    public void printDeque() {
        int i = 0;
        Node p = sentinel;
        while (i < size) {
            p = p.next;
            System.out.print(p.item);
            System.out.print(' ');
            i += 1;
        }
    }

    private void throwException() {
        ArrayIndexOutOfBoundsException  exception = new ArrayIndexOutOfBoundsException();
        throw exception;
    }
}
