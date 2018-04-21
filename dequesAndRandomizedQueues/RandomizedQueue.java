import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] random;
    private int size;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        random = (Item[]) new Object[2];
        size = 0;
    }
    
    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }
    
    // return the number of items on the randomized queue
    public int size() {
        return size;
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument cannot be null.");
        }
        if (size == random.length) {
            resize(random.length * 2);
        }
        random[size++] = item;
    }
    
    // remove and return a random item
    public Item dequeue() {
        checkEmpty();
        int remove = StdRandom.uniform(size);
        Item item = random[remove];
        random[remove] = random[--size];
        random[size] = null;
        if (size > 0 && size == random.length / 4) {
            resize(random.length / 2);
        }
        return item;
    }
    
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = random[i];
        }
        random = copy;
    }
    
    // return a random item (but do not remove it)
    public Item sample() {
        checkEmpty();
        int sample = StdRandom.uniform(size);
        return random[sample];
    }
    
    private void checkEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {
        private final Item[] copy;
        private int curr;
        
        public RandomizedQueueIterator() {
            copy = (Item[]) new Object[size]; 
            for (int i = 0; i < size; i++) {
                copy[i] = random[i];
            }
            StdRandom.shuffle(copy);
            curr = 0;
        }
        
        public boolean hasNext() {
            return curr < size;
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items.");
            }
            return copy[curr++];
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        for (int i = 0; i < 20; i++) {
            queue.enqueue(i);
        }
        for (int i = 0; i < 20; i++) {
            System.out.println(queue.dequeue());
        }
    }
}