package com.test;

public class 快速排序 {
	public static void main(String[] args) {
		int[] array = {72, 6, 57, 88, 60, 42, 83, 73, 48, 85};
		sort(array, 0, array.length-1);
		for(int a : array) {
			System.out.print(a);
			System.out.print(",");
		}
	}
	
	public static void sort(int[] array, int start, int end) {
		if(start < end) {
			int left = start, right = end;
			int temp = array[left];
			while(left < right) {
				while(left < right && array[right] >= temp)
					right--;
				if(left < right) {
					array[left] = array[right];
					left++;
				}
				
				while(left < right && array[left] < temp)
					left++;
				if(left < right) {
					array[right] = array[left];
					right--;
				}
			}
			array[left] = temp;
			sort(array, start, left - 1);
			sort(array, left + 1, end);
		}
	}
}
