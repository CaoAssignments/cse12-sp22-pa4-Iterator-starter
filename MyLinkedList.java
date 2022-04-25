
/**
 * Name: Christine Tang
 * ID: A16207079
 * Email: cttang@ucsd.edu
 * Sources used: MyArrayList to format comments
 * 
 * This file contains the MyLinkedList class to allow the creation
 * of a Doubly LinkedList that can allow the user to add, remove, set, and
 * get Nodes with elements in the LinkedList as well as 
 * clear the MyLinkedList. 
 */

import java.util.AbstractList;
import java.util.NoSuchElementException;
import java.util.ListIterator;
import java.util.Iterator;

/**
 * This class holds the head, tail, and size for a MyLinkedList and allows
 * the user to manipulate the MyLinkedList.
 * 
 * Instance variables:
 * head - a dummy Node for beginning of the MyLinkedList
 * tail - a dummy Node for the end of the MyLinkedList
 * size - an integer that will hold the size of the MyLinkedList
 */
public class MyLinkedList<E> extends AbstractList<E> {

    // instance variables
    int size;
    Node head;
    Node tail;

    /**
     * A Node class that holds data and references to previous and next Nodes.
     */
    protected class Node {
        E data;
        Node next;
        Node prev;

        /**
         * Constructor to create singleton Node
         * 
         * @param element Element to add, can be null
         */
        public Node(E element) {
            this.data = element;
            this.next = null;
            this.prev = null;
        }

        /**
         * Set the parameter prev as the previous node
         * 
         * @param prev - new previous node
         */
        public void setPrev(Node prev) {
            this.prev = prev;
        }

        /**
         * Set the parameter next as the next node
         * 
         * @param next - new next node
         */
        public void setNext(Node next) {
            this.next = next;
        }

        /**
         * Set the parameter element as the node's data
         * 
         * @param element - new element
         */
        public void setElement(E element) {
            this.data = element;
        }

        /**
         * Accessor to get the next Node in the list
         * 
         * @return the next node
         */
        public Node getNext() {
            return this.next;
        }

        /**
         * Accessor to get the prev Node in the list
         * 
         * @return the previous node
         */
        public Node getPrev() {
            return this.prev;
        }

        /**
         * Accessor to get the Nodes Element
         * 
         * @return this node's data
         */
        public E getElement() {
            return this.data;
        }
    }

    // Implementation of the MyLinkedList Class
    /** Only 0-argument constructor is defined */
    /**
     * The constructor initializes size and data with head and tail dummy Nodes.
     */
    public MyLinkedList() {
        size = 0;
        head = new Node(null);
        tail = new Node(null);
        head.setNext(tail);
        tail.setPrev(head);
    }

    /**
     * Returns the size of the MyLinkedList.
     * 
     * @return The size of the MyLinkedList
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Gets the element within the node at the given index.
     * 
     * @param index The index at which to extract the element
     * @return The element from the Node at the given index
     */
    @Override
    public E get(int index) {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException();
        }
        Node nodeAtIndex = this.getNth(index);
        return (E) (nodeAtIndex.getElement());
    }

    /**
     * Add a Node with the given data as the element into the MyLinkedList
     * at the given index.
     * 
     * @param index The index to insert the Node at
     * @param data  The data to put in the Node to insert
     */
    @Override
    public void add(int index, E data) {
        // Check for exceptions
        if (data == null) {
            throw new NullPointerException();
        }
        if (index < 0 || index > this.size()) {
            throw new IndexOutOfBoundsException();
        }
        Node newNode = new Node(data);
        Node indexNode;
        if (index == size) {
            indexNode = tail;
        } else {
            indexNode = this.getNth(index);
        }
        newNode.setPrev(indexNode.getPrev());
        newNode.setNext(indexNode);
        indexNode.getPrev().setNext(newNode);
        indexNode.setPrev(newNode);
        size++;
    }

    /**
     * Add a Node with the given data as the element at the end of the
     * MyLinkedList and return true to indicate that the addition was
     * successful.
     * 
     * @param data The data to put in the Node
     * @return True to indicate the success of the addition
     */
    public boolean add(E data) {
        add(this.size(), data);
        return true;
    }

    /**
     * Sets the element of the Node at the given index to data and return
     * the element previously stored in the Node.
     * 
     * @param index The index at which to replace the element of the Node
     * @param data  The data to replace the Node's element with
     * @return The element previously stored in the Node at index
     */
    public E set(int index, E data) {
        if (data == null) {
            throw new NullPointerException();
        }
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException();
        }
        E elementAtIndex = this.get(index);
        Node nodeAtIndex = this.getNth(index);
        nodeAtIndex.setElement(data);
        return (E) elementAtIndex;
    }

    /**
     * Remove the Node at element at the given index from the MyLinkedList.
     * 
     * @param index The index at which to remove the Node
     * @return The element stored in the Node that was previously at index
     */
    public E remove(int index) {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException();
        }
        E dataAtIndex = this.get(index);
        Node nodeAtIndex = this.getNth(index);
        nodeAtIndex.getPrev().setNext(nodeAtIndex.getNext());
        nodeAtIndex.getNext().setPrev(nodeAtIndex.getPrev());
        nodeAtIndex.setPrev(null);
        nodeAtIndex.setNext(null);
        size--;
        return (E) dataAtIndex;
    }

    /**
     * Clear the MyLinkedList and reset the size.
     */
    public void clear() {
        if (size > 0) {
            head.getNext().setPrev(null);
            tail.getPrev().setNext(null);
        }
        head.setNext(tail);
        tail.setPrev(head);
        size = 0;
    }

    /**
     * Check whether the MyLinkedList is empty or not.
     * 
     * @return Whether the MyLinkedList is empty or not.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Return the Node at the given index.
     * 
     * @param index
     * @return The Node at the given index.
     */
    protected Node getNth(int index) {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException();
        }
        Node currNode = head.getNext();
        for (int i = 0; i < index; i++) {
            currNode = currNode.getNext();
        }
        return (Node) currNode;
    }

    /**
     * Constructs a MyListIterator for the MyLinkedList and returns it.
     * 
     * @return The MyListIterator for the MyLinkedList
     */
    public ListIterator<E> listIterator() {
        MyListIterator iterator = new MyListIterator();
        return iterator;
    }

    /**
     * Constructs a MyListIterator for the MyLinkedList and returns it.
     * 
     * @return The MyListIterator for the MyLinkedList
     */
    public Iterator<E> iterator() {
        MyListIterator iterator = new MyListIterator();
        return iterator;
    }

    /**
     * A MyListIterator class holds an index, references to left and right
     * Nodes, whether the MyListIterator is going forward or not, and whether
     * the Node from the last call to next or previous can be removed or set.
     */
    protected class MyListIterator implements ListIterator<E> {

        // class variables
        Node left, right;
        int idx;
        boolean forward;
        boolean canRemoveOrSet;

        // MyListIterator methods

        /**
         * Constructor to creator iterator for the MyLinkedList
         */
        public MyListIterator() {
            left = head;
            right = head.next;
            idx = 0;
            forward = true;
            canRemoveOrSet = false;
        }

        /**
         * Returns whether the iterator has a next elemental Node or not.
         * 
         * @return Whether the iterator has a next elemental Node
         */
        public boolean hasNext() {
            return (nextIndex() < size);
        }

        /**
         * Moves the iterator to the next Node and returns the element in the
         * next Node.
         * 
         * @return The element within the next node
         */
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No next element.");
            }
            canRemoveOrSet = true;
            left = right;
            right = right.getNext();
            idx++;
            forward = true;
            return left.getElement();
        }

        /**
         * Returns whether the iterator has a previous elemental Node or not.
         * 
         * @return Whether the iterator has a previous elemental Node
         */
        public boolean hasPrevious() {
            return (previousIndex() >= 0);
        }

        /**
         * Moves the iterator to the previous Node and returns the element in
         * the previous Node.
         * 
         * @return The element within the previous node
         */
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException("No previous element.");
            }
            canRemoveOrSet = true;
            right = left;
            left = left.getPrev();
            idx--;
            forward = false;
            return right.getElement();
        }

        /**
         * Returns the next index.
         * 
         * @return The index of the next Node
         */
        public int nextIndex() {
            return idx;
        }

        /**
         * Returns the previous index.
         * 
         * @return The index of the previous Node
         */
        public int previousIndex() {
            return idx - 1;
        }

        /**
         * Adds a Node with the given element into the MyLinkedList at the
         * current index and moves the Iterator to the next index.
         * 
         * @param element The element of the Node to be added
         */
        public void add(E element) {
            if (element == null) {
                throw new NullPointerException();
            }
            size++;
            idx++;
            Node toAdd = new Node(element);
            toAdd.setPrev(left);
            toAdd.setNext(right);
            left.setNext(toAdd);
            right.setPrev(toAdd);
            left = toAdd;
            canRemoveOrSet = false;
        }

        /**
         * Sets the element of the Node last called in previous or next to the
         * given element.
         * 
         * @param element The element to set the Node's element to
         */
        public void set(E element) {
            if (element == null) {
                throw new NullPointerException();
            }
            if (!canRemoveOrSet) {
                throw new IllegalStateException();
            }
            Node toSet;
            if (forward) {
                toSet = left;
            } else {
                toSet = right;
            }
            toSet.setElement(element);
            canRemoveOrSet = false;
        }

        /**
         * Removes the Node last called in next or previous and changes the
         * index accordingly.
         */
        public void remove() {
            if (!canRemoveOrSet) {
                throw new IllegalStateException();
            }
            size--;
            Node toRemove;
            if (forward) {
                toRemove = left;
                left = left.getPrev();
                idx--;
            } else {
                toRemove = right;
                right = right.getNext();
            }
            toRemove.getPrev().setNext(toRemove.getNext());
            toRemove.getNext().setPrev(toRemove.getPrev());
            toRemove.setNext(null);
            toRemove.setPrev(null);
            canRemoveOrSet = false;
        }

    }

}