package org.chance;

import java.util.ArrayList;

public class CollectionStudy {
	
	private static class ArrayListStudy{
		public static void main(String[] args) {
			ArrayList<Integer> src = new ArrayList<>(2);
			src.add(1);
			src.add(2);
			src.add(3);
			System.out.println(src.size());
			src.trimToSize();
			src.add(4);
			src.add(5);
			System.out.println(src.size());
			System.out.println(src.clone()==src);
			
			
			
		}
	}

}
