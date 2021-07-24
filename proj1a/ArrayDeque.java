public class ArrayDeque<T> {
    public T[] items;
    private int front;
    private int last;
    private int refactor = 2;
    private int maxSize = 16;

    public ArrayDeque() {
        items = (T[]) new Object[maxSize];
        front = 0;
        last = 0;
    }

    public ArrayDeque(T i) {
        items = (T[]) new Object[maxSize];
        items[0] = i;
        front = maxSize - 1;
        last = 0;
    }

    public boolean isEmpty() {
        return front - last == 0;
    }

    public void addFirst(T i) {
        // 如果满了再resize，会导致无法分辨满的size和空size的情况
        if(maxSize - size() == 1){
            resize(1);
        }
        items[front] = i;
        front = minusIndex(front);
    }

    public void addLast(T i) {
        if(maxSize - size() == 1){
            resize(1);
        }
        last = plusIndex(last);
        items[last] = i;
    }

    public T removeFirst() {
        if(needDecSize()) {
            resize(0);
        }
        front = plusIndex(front);
        return items[front];
    }


    public T removeLast() {
        if(needDecSize()) {
            resize(0);
        }
        T removeNum = items[last];
        last = minusIndex(last);
        return removeNum;
    }

    public int size() {
        if(front > last){
            return last + maxSize - front;
        }
        return last - front;
    }

    public T get(int index){
        return items[(index + front + 1) % maxSize];
    }

    public boolean needDecSize() {
        return (maxSize > 16) && ((4*size() - maxSize) <= 0);
    }

    public void resize(int type) {
        T[] itemsCopy = (T[]) new Object[maxSize];
        int oldSize = size();
        int oldMaxSize = maxSize;
        System.arraycopy(items, 0, itemsCopy, 0, maxSize);
        if(type == 0){
            maxSize /= 2;
            if(maxSize < 16) {
                maxSize = 16;
            }
        } else {
            maxSize *= refactor;
        }
        items = (T[]) new Object[maxSize];
        int i = (front+1) % oldMaxSize;
        int j = 0;
        while(j<oldSize) {
            items[j] = itemsCopy[i];
            j += 1;
            i = (i+1) % oldMaxSize;
        }
        front = maxSize - 1;
        last = oldSize - 1;
    }

    public int minusIndex(int index){
        if(index == 0) {
            return maxSize - 1;
        }
        return index - 1;
    }

    public int plusIndex(int index) {
        return (index + 1) % maxSize;
    }

}
