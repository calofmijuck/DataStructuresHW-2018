import java.io.*;
import java.util.*;

public class SortingTest {
	static int[] arr, tmp1, tmp2; // temporary arrays to save copying time for recursive calls
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
				tmp1 = new int[numsize];
				tmp2 = new int[numsize];
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			} else {
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				tmp1 = new int[numsize];
				tmp2 = new int[numsize];
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true) {
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.
				arr = (int[]) value.clone(); // copy
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
					for(int i = 0; i < newvalue.length; i++) {
						System.out.println(newvalue[i]);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value) {
		int tmp;
		for(int i = 0; i < value.length - 1; i++) {
			for(int j = 0; j < value.length - i - 1; j++) {
				if(value[j] > value[j + 1]) { // compare and swap
					tmp = value[j + 1];
					value[j + 1] = value[j];
					value[j] = tmp;
				}
			}
		}
		// value는 정렬안된 숫자들의 배열이며 value.length 는 배열의 크기가 된다.
		// 결과로 정렬된 배열은 리턴해 주어야 하며, 두가지 방법이 있으므로 잘 생각해서 사용할것.
		// 주어진 value 배열에서 안의 값만을 바꾸고 value를 다시 리턴하거나
		// 같은 크기의 새로운 배열을 만들어 그 배열을 리턴할 수도 있다.
		return value;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value) {
		int tmp;
		for(int i = 0; i < value.length; i++) {
			for(int j = i; j > 0; j--) {
				if(value[j] < value[j - 1]) { // Is this the right place to insert?
					tmp = value[j - 1];
					value[j - 1] = value[j];
					value[j] = tmp;
				} else { // stop insertion
					break;
				}
			}
		}
		return value;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{
		// TODO : Heap Sort 를 구현하라.
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value) {
		MergeSort(1, value.length);
		return arr;
	}

	private static void MergeSort(int start, int end) {
		if(start < end) {
			int mid = (int) Math.ceil((start + end) / 2);
			MergeSort(start, mid);
			MergeSort(mid + 1, end);
			Merge(start, mid, end);
		}
	}

	private static void Merge(int start, int mid, int end) {
		int len1 = mid - start + 1, len2 = end - mid;
		for(int i = 0; i < len1; i++) { // copy array
			tmp1[i] = arr[start + i - 1];
		}
		for(int j = 0; j < len2; j++) {
			tmp2[j] = arr[mid + j];
		}
		int i = 0, j = 0, k = start - 1; // pointer indices for tmp1, tmp2, result array
		while(i < len1 && j < len2) {
			if(tmp1[i] <= tmp2[j]) { // stable sort
				arr[k] = tmp1[i];
				i++;
			} else {
				arr[k] = tmp2[j];
				j++;
			}
			k++;
		}
		// copy the remaining elements
		for(; i < len1; i++, k++) {
			arr[k] = tmp1[i];
		}
		for(; j < len2; j++, k++) {
			arr[k] = tmp2[j];
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value) {
		QuickSort(0, value.length - 1);
		return arr;
	}

	private static int partition(int low, int high) { // partitions from index low to high
		int pivot = arr[high]; // selecting the last element as pivot
		int i = low - 1; // i counts: (the number of elements smaller than pivot) - 1 + low
		// thus i + 1 will be the correct place of the pivot element in the sorted array
		int tmp;
		for(int j = low; j < high; j++) {
			if(arr[j] < pivot) { // put all elements smaller than pivot on the left part of array
				i++;
				tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
			}
		}
		tmp = arr[i + 1];
		arr[i + 1] = pivot; // place the pivot at i + 1
		arr[high] = tmp;
		return i + 1; // correct location of pivot in the array
	}

	private static void QuickSort(int low, int high) {
		if(low < high) {
			int partIndex = partition(low, high); // partitioning index
			QuickSort(low, partIndex - 1); // Sort left
			QuickSort(partIndex + 1, high); // Sort right
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value) {
		int maximum = getMax(value);
		for(int digit = 1; maximum / digit > 0; digit *= 10) {
			value = countSort(value, digit);
		}
		return value;
	}

	private static int[] countSort(int[] arr, int digit) {
		int[] res = new int[arr.length];
		int[] count = new int[20]; // number of occurrences of each digit from -9 to 9
		for(int i = 0; i < arr.length; i++) {
			count[(arr[i] / digit) % 10 + 9]++; // occurrence of digit
		}
		for(int i = 1; i < 20; i++) {
			count[i] += count[i - 1]; // accumulate occurrences to get the correct index
		}
		for(int i = arr.length - 1; i >= 0; i--) { // iterate backwards for stable sorting
			int loc = (arr[i] / digit) % 10 + 9; // digit of current element
			res[count[loc] - 1] = arr[i]; // count[loc] - 1 is the correct place
			count[loc]--; // decrement
		}
		return res;
	}

	private static int getMax(int[] arr) {
		// returns the element with largest absolute value, to figure out the largest existing digit from the input
		int max = Math.abs(arr[0]);
		for(int i = 1; i < arr.length; i++) {
			if(Math.abs(arr[i]) > max) {
				max = Math.abs(arr[i]);
			}
		}
		return max;
	}
}
