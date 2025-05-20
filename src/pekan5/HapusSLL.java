package pekan5;

public class HapusSLL {
	public static NodeSLL deleteHead(NodeSLL head) {
		if(head == null)
			return null;
		head = head.next;
		return head;
	}
	
	public static NodeSLL removeLastNode(NodeSLL head) {
		if(head == null) {
			return null;
		}
		
		if(head.next == null) {
			return null;
		}
		
		NodeSLL secondLast = head;
		while(secondLast.next.next != null) {
			secondLast = secondLast.next;
		}
		secondLast.next = null;
		return head;
	}
	
	public static NodeSLL deleteNode(NodeSLL head, int position) {
		NodeSLL temp = head;
		NodeSLL prev = null;
		if(temp == null) {
			return head;
		}
		if(position == 1) {
			head = temp.next;
			return head;
		}
		for(int i = 1; temp != null && i < position; i++) {
			prev = temp;
			temp = temp.next;
		}
		if(temp != null) {
			prev.next = temp.next;
		} else {
			System.out.println("Data tidak ada.");
		}
		return head;
	}
	
	public static void printList(NodeSLL head) {
		NodeSLL curr = head;
		while(curr.next != null) {
			System.out.print(curr.data + " --> ");
			curr = curr.next;
		}
		if(curr.next == null) {
			System.out.print(curr.data);
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		NodeSLL head = new NodeSLL(1);
		head.next = new NodeSLL(2);
		head.next.next = new NodeSLL(3);
		head.next.next.next = new NodeSLL(4);
		head.next.next.next.next = new NodeSLL(5);
		head.next.next.next.next.next = new NodeSLL(6);
		
		System.out.println("List awal: ");
		printList(head);
		
		head = deleteHead(head);
		System.out.println("List setelah head dihapus: ");
		printList(head);
		
		head = removeLastNode(head);
		System.out.println("List setelah simpul terakhir dihapus: ");
		printList(head);
		
		int position = 2;
		head = deleteNode(head, position);
		
		System.out.println("List setelah posisi 2 dihapus: ");
		printList(head);
	}
}