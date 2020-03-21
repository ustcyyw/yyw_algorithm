package easy.Sort;

/**
 * @Time : 2020年3月20日00:14:17
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。
 *
 * 示例 1：
 * 输入：arr = [3,2,1], k = 2
 * 输出：[1,2] 或者 [2,1]
 *
 * 示例 2：
 * 输入：arr = [0,1,2,1], k = 1
 * 输出：[0]
 *
 * 限制：
 * 0 <= k <= arr.length <= 10000
 * 0 <= arr[i] <= 10000
 */
public class getLeastNumbers {
    /**
     * 执行用时 :23 ms, 在所有 Java 提交中击败了30.42%的用户
     * 内存消耗 :43.3 MB, 在所有 Java 提交中击败了100.00%的用户
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        if(k == 0)
            return new int[0];
        PriorityQueue<Integer> maxPQ = new PriorityQueue<>(new Order());
        for(int i = 0; i < k; i++)
            maxPQ.add(arr[i]);
        for(int i = k; i < arr.length; i++){
            if(arr[i] < maxPQ.peek()){
                maxPQ.add(arr[k]);
                maxPQ.remove();
            }
        }
        int[] res = new int[k];
        for(int i = k - 1; i >= 0; i++)
            res[i] = maxPQ.remove();
        return res;
    }

    private class Order implements Comparator<Integer>{
        public int compare(Integer v, Integer w){
            return w - v;
        }
    }

    /**
     * 可以使用切分 找到索引为k-1的切分点
     * 执行用时 :2 ms, 在所有 Java 提交中击败了99.75%的用户
     * 内存消耗 :42.6 MB, 在所有 Java 提交中击败了100.00%的用户
     */
    public int[] getLeastNumbers2(int[] arr, int k) {
        if(k == 0 || arr.length == 0)
            return new int[0];
        int lo = 0, hi = arr.length - 1;
        while(lo < hi){
            int temp = partition(arr, lo, hi);
            if(temp > k - 1) hi = temp - 1;
            else if(temp < k - 1) lo = temp + 1;
            else break;
        }
        return Arrays.copyOf(arr, k);
    }

    private int partition(int[] arr, int lo, int hi){
        int v = arr[lo];
        int i = lo, j = hi + 1;
        while(true){
            while(arr[++i] < v) if(i == hi) break;
            while(arr[--j] > v) if(j == lo) break;
            if(i >= j)
                break;
            exch(arr, i, j);
        }
        exch(arr, lo, j);
        return j;
    }

    private void exch(int[] num, int i, int j) {
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }
}
