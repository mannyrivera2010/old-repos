package org.sorting;

public class QuickSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QuickSort QSort = new QuickSort();
		int[] intArray = {1,12,5,26,7,14,3,7,2 };
		QSort.quickSort(intArray, 0, intArray.length - 1);
	}

	int partition(int arr[], int left, int right){
		int i = left, j = right;
		int tmp;
		int pivot = arr[(left + right) / 2];

		while (i <= j) {
			while (arr[i] < pivot)
				i++;
			while (arr[j] > pivot)
				j--;
			if (i <= j) {
				tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
				i++;
				j--;
			}
		}
		return i;
	}

	void quickSort(int arr[], int left, int right) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + ",");
		}
		System.out.println("");

		int index = partition(arr, left, right);

		if (left < index - 1) {
			quickSort(arr, left, index - 1);
		}

		if (index < right) {
			quickSort(arr, index, right);
		}
	}

}
