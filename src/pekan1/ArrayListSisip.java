package pekan1;

import java.util.ArrayList;

public class ArrayListSisip {

	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(4);
		System.out.println(list);
		list.add(2,3);
		System.out.println(list);
	}

}
