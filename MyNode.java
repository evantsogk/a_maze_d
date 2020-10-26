/**
 * MyNode objects are nodes used to create a single-link list.
 */
public class MyNode<T> {
	public T nodeData;
	public MyNode<T> nextNode;
	
	/** Constructor creates node with passed data and sets next node to null.
	 * @param nodeData node's data
	 */
	public MyNode(T nodeData) {
		this.nodeData=nodeData;
		nextNode=null;
	}
	/**
	 * Constructor creates node with passed data and next node.
	 * @param nodeData node's data
	 * @param nextNode the next node in the list
	 */
	public MyNode(T nodeData, MyNode<T> nextNode) {
		this.nodeData=nodeData;
		this.nextNode=nextNode;
	}
}


