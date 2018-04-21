import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node<Item> first;
    private Node<Item> last;
    
    private static class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> prev;
    }
    
    // construct an empty deque
    public Deque() {
        size = 0;
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }
    
    // return the number of items on the deque
    public int size() {
        return size;
    }
    
    // add the item to the front
    public void addFirst(Item item) {
        checkNull(item);
        Node<Item> add = new Node<>();
        add.item = item;
        if (isEmpty()) {
            first = add;
            last = add;
        } else {
            add.next = first;
            first.prev = add;
            first = add;
        }
        size++;
    }
    
    // add the item to the end
    public void addLast(Item item) {
        checkNull(item);
        Node<Item> add = new Node<>();
        add.item = item;
        if (isEmpty()) {
            first = add;
            last = add;
        } else {
            last.next = add;
            add.prev = last;
            last = add;
        }
        size++;
    }
    
    private void checkNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument cannot be null.");
        }
    }
    
    // remove and return the item from the front
    public Item removeFirst() {
        checkEmpty();
        Item item = first.item;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        size--;
        return item;
    }
    
    // remove and return the item from the end
    public Item removeLast() {
        checkEmpty();
        Item item = last.item;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next.prev = null;
            last.next = null;
        }
        size--;
        return item;
    }
    
    private void checkEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty.");
        }
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node<Item> curr = first;
        
        public boolean hasNext() {
            return curr != null;
        }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items.");
            }
            Item item = curr.item;
            curr = curr.next;
            return item;
        }
        
        public void remove() {
            throw new UnsupportedOperationException(
                "remove() is not supported.");
        }
    }
    
    // unit testing (optional)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        for (int i = 0; i < 20; i++) {
            deque.addFirst(i);
        }
        for (int i = 0; i < 20; i++) {
            System.out.println(deque.removeLast());
        }
    }
}