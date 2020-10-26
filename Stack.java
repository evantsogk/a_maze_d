import java.util.NoSuchElementException;

public class Stack<T> {
	private MyList<T> list;
	
	/** 
	 * Constructor creates an empty list for the stack.
	 */
	public Stack(){
		list=new MyList();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public void push(T item) {
		list.insertAtFront(item);
	}
	
	public T pop() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException();
		
		T removedItem=list.firstNode.nodeData;
		list.removeFromFront();
		
		return removedItem;
	}
	
	public T peek() throws NoSuchElementException {
		if (isEmpty())
			throw new NoSuchElementException();
		
		return list.firstNode.nodeData;
	}
	
	public int size() {
		return list.size();
	}
	
	public T get(int i) throws NoSuchElementException {
		if (isEmpty() || i<=0 || i>size()) throw new NoSuchElementException();
		MyNode<T> node=list.firstNode;
        for (int j=1; j<i; j++) {
			node=node.nextNode;
		}
		return node.nodeData;
    }
}


