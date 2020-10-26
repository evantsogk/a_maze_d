/**
 * Single-link list. Uses {@link MyNode} for list nodes.
 */
public class MyList<T> {
	public MyNode<T> firstNode;
	public MyNode<T> lastNode;
	
	/** 
	 * Constructor creates empty list.
	 */
	public MyList() {
		firstNode=lastNode=null;
	}
	
	/** 
	 * Returns true if the list is empty.
	 * @return true if the list is empty
	 */
	public boolean isEmpty() {
		return (firstNode==null);
	}
	
    /**
	 * Inserts a node at the head of the list.
	 * @param data node's data
	 */
	public void insertAtFront(T data) {
		if (isEmpty()) 
			firstNode=lastNode=new MyNode(data);
		else 
			firstNode=new MyNode(data, firstNode);
	}
	
	/**
	 * Inserts a node at the tail of the list.
	 * @param data node's data
	 */
	public void insertAtBack(T data) {
		MyNode<T> node=new MyNode(data);
		if (isEmpty()) 
			firstNode=lastNode=node;
		else {
			lastNode.nextNode=node;
			lastNode=node;
		}
	}
	
	/**
	 * Removes the node from the list's head.
     */
	public void removeFromFront() {
		if (firstNode==lastNode) 
			firstNode=lastNode=null;
		else if (!isEmpty()) 
			firstNode=firstNode.nextNode;
	}
	
	/**
	 * Removes the node from the list's tail.
     */
	public void removeFromBack() {
		if (firstNode==lastNode) 
			firstNode=lastNode=null;
		else if (!isEmpty()) {
			MyNode<T> current=firstNode;
			
			while (current.nextNode!=lastNode)
				current=current.nextNode;
			lastNode=current;
			current.nextNode=null;
		}
	}
	
	
	/**
	 * Returns the number of the list's nodes.
	 * @return the number of the list's nodes
	 */
	public int size() {
		int elements=0;
		MyNode current=firstNode;
			
		while (current!=null) {
			elements++;
			current=current.nextNode;
		}
		
		return elements;
	}
}





