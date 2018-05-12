import java.io.*;
import java.util.*;

public class SortingTest2 {
	static Random randInt = new Random();
	static int size;

	public static void main(String args[]) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try	{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r') {
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			} else {
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true) {
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.
				String command = br.readLine();
				long t = System.currentTimeMillis();
				switch (command.charAt(0)) {
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom) {
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				} else {
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					for (int i = 0; i < newvalue.length; i++) {
						System.out.println(newvalue[i]);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value) {
		int temp; // temporary holder for swapping
		for(int i = 0; i < value.length; ++i) {
			for(int j = 0; j < value.length - i - 1; ++j) {
				if(value[j] > value[j + 1]) {
					temp = value[j + 1];
					value[j + 1] = value[j];
					value[j] = temp;
				}
			}
		}
		return value;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value) {
		int temp; // swapping
		for(int i = 1; i < value.length; ++i) {
			for(int j = i; j > 0; --j) {
				if(value[j] < value[j - 1]) {
					temp = value[j - 1];
					value[j - 1] = value[j];
					value[j] = temp;
				} else {
					break;
				}
			}
		}
		return value;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value) {
		size = value.length;
		// build max heap
		for(int i = size / 2; i >= 0; --i) {
			value = maxHeapify(value, i);
		}
		int temp, idx = value.length - 1;
		for(int i = 0; i < idx; ++i) { // sort
			temp = value[idx - i];
			value[idx - i] = value[0];
			value[0] = temp;
			--size;
			value = maxHeapify(value, 0);
		}
		return value;
	}

	private static int[] maxHeapify(int[] value, int idx) {
		int left = 2 * idx + 1, right = 2 * idx + 2;
		int temp;
		if(left < size)  { // left child exists
			if(right < size && value[left] < value[right]) { // right child exists and right child is greater
				left = right; // larger child is stored
			}
			if(value[idx] < value[left]) { // compare with current child and swap if necessary
				temp = value[idx];
				value[idx] = value[left];
				value[left] = temp;
				value = maxHeapify(value, left); // max heapify
			}
		}
		return value;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value) {
		return MergeSort(value, 0, value.length - 1);
	}

	private static int[] MergeSort(int[] value, int start, int end) {
		if(start >= end) {
			return value;
		} else {
			int mid = (start + end) / 2;
			int[] left = MergeSort(value, start, mid); // sort left
			int[] right = MergeSort(value, mid + 1, end); // sort right
			// Merge
			int[] tmp = new int[end - start + 1];
			int ptrLeft = start, ptrRight = mid + 1, ptrNew = 0; // pointers to left and right arrays
			while(ptrLeft <= mid && ptrRight <= end) { // compare and merge
				if(left[ptrLeft] <= right[ptrRight]) {
					tmp[ptrNew] = left[ptrLeft];
					ptrLeft++;
				} else {
					tmp[ptrNew] = right[ptrRight];
					ptrRight++;
				}
				ptrNew++;
			}
			while(ptrLeft <= mid) {
				tmp[ptrNew] = left[ptrLeft];
				ptrNew++;
				ptrLeft++;
			}
			while(ptrRight <= end) {
				tmp[ptrNew] = right[ptrRight];
				ptrNew++;
				ptrRight++;
			}
			for(int i = start; i <= end; ++i) { // place it into the original array
				value[i] = tmp[i - start];
			}
			return value;
		}

	}


	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value) {
		value = QuickSort(value, 0, value.length - 1);
		return value;
	}

	private static int[] QuickSort(int[] value, int start, int end) {
		if(start < end) {
			// partition the Array
			int pivot = value[end]; // last element as pivot
			int i = start - 1; // i counts: (the number of elements smaller than pivot) - 1 + low
			// thus i + 1 will be the correct place of the pivot element in the sorted array
			int temp;
			for(int j = start; j < end; ++j) {
				if(value[j] < pivot) { // elements smaller than pivot are moved to the left partition
					++i;
					temp = value[i];
					value[i] = value[j];
					value[j] = temp;
				}
			}
			temp = value[i + 1];
			value[i + 1] = pivot; // place the pivot at i + 1
			value[end] = temp;
			int partIndex = i + 1; // partition index
			int[] res = QuickSort(value, start, partIndex - 1); // sort left
			res = QuickSort(res, partIndex + 1, end); // sort right
			return res;
		} else {
			return value;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value) {
		int maximum = getMax(value); // maximum digit
		for(int digit = 1; maximum / digit > 0; digit *= 10) { // sort for each digit
			value = countSort(value, digit);
		}
		return value;
	}

	private static int[] countSort(int[] value, int digit) {
		int[] res = new int[value.length];
		int[] count = new int[20]; // number of occurrences of each digit from -9 to 9
		for(int i = 0; i < value.length; ++i) {
			count[(value[i] / digit) % 10 + 9]++; // occurrence of each digit
		}
		for(int i = 1; i < 20; ++i) {
			count[i] += count[i - 1]; // accumulate occurrences to get the correct index
		}
		for(int i = value.length - 1; i >= 0; --i) { // iterate backwards for stable sorting
			int loc = (value[i] / digit) % 10 + 9; //digit of current element
			res[count[loc] - 1] = value[i];
			count[loc]--;
		}
		return res;
	}

	private static int getMax(int[] arr) {
		// returns the element with largest absolute value
		// to figure out the largest exponent
		int max = Math.abs(arr[0]);
		for(int i = 1; i < arr.length; i++) {
			if(Math.abs(arr[i]) > max) {
				max = Math.abs(arr[i]);
			}
		}
		return max;
	}
}
