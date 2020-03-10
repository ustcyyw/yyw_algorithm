package medium.Sort;

/**
 * @Time : 2020年3月9日18:46:16
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 *
 * 示例 1:
 * 输入: [3,2,1,5,6,4] 和 k = 2
 * 输出: 5
 *
 * 示例 2:
 * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
 * 输出: 4
 *
 * 说明:
 * 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/kth-largest-element-in-an-array
 */
public class findKthLargest {
    /**
     * 执行用时 :9 ms, 在所有 Java 提交中击败了49.97%的用户
     * 内存消耗 :41.5 MB, 在所有 Java 提交中击败了5.05%的用户
     */
    public int findKthLargest(int[] nums, int k) {
        int index = nums.length - k;
        int lo = 0, hi = nums.length - 1;
        while(lo < hi){
            int j = partition(nums, lo, hi);
            if(j < index)
                lo = j + 1;
            else if(j > index)
                hi = j - 1;
            else return nums[j];
        }
        return nums[index];
    }

    private int partition(int[] nums, int lo, int hi){
        int i = lo, j = hi + 1;
        while(i < j){
            while(nums[++i] < nums[lo])
                if(i == hi)
                    break;
            while(nums[--j] > nums[lo]);
            if(j <= i)
                break;
            exch(nums, i, j);
        }
        exch(nums, lo, j);
        return j;
    }

    private void exch(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 执行用时 :4 ms, 在所有 Java 提交中击败了70.18%的用户
     * 内存消耗 :41.5 MB, 在所有 Java 提交中击败了5.05%的用户
     */
    public int findKthLargest2(int[] nums, int k) {
        PriorityQueue<Integer> minPQ = new PriorityQueue<>();
        for(int i = 0; i < k; i++)
            minPQ.add(nums[i]);
        for(int i = k; i < nums.length; i++){
            if(nums[i] < minPQ.peek())
                continue;
            minPQ.add(nums[i]);
            minPQ.remove();
        }
        return minPQ.remove();
    }

    /**
     * 更慢了 要5s
     */
    public int findKthLargest3(int[] nums, int k) {
        int m = nums.length - k + 1;
        if(m < k){
            PriorityQueue<Integer> maxPQ = new PriorityQueue<>(new numOrder());
            for(int i = 0; i < m; i++)
                maxPQ.add(nums[i]);
            for(int i = m; i < nums.length; i++){
                if(nums[i] > maxPQ.peek())
                    continue;
                maxPQ.add(nums[i]);
                maxPQ.remove();
            }
            return maxPQ.remove();
        }
        else {
            PriorityQueue<Integer> minPQ = new PriorityQueue<>();
            for(int i = 0; i < k; i++)
                minPQ.add(nums[i]);
            for(int i = k; i < nums.length; i++){
                if(nums[i] < minPQ.peek())
                    continue;
                minPQ.add(nums[i]);
                minPQ.remove();
            }
            return minPQ.remove();
        }
    }

    private class numOrder implements Comparator<Integer>{
        public int compare(Integer v, Integer w){
            return -(v - w);
        }
    }
}
