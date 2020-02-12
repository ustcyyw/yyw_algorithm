package easy.StackAndQueue;

/**
 * @Time : 2020年2月12日16:11:42
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

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
 * 0 <= k <= arr.length <= 1000
 * 0 <= arr[i] <= 1000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/zui-xiao-de-kge-shu-lcof
 */
public class getLeastNumbers {
    /**
     * 执行用时 :25 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :48.8 MB, 在所有 Java 提交中击败了100.00%的用户
     * 优先队列的插入与删除元素都是logk 时间复杂度 n * logk
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        int[] result = new int[k];
        PriorityQueue<Integer> MaxPQ = new PriorityQueue<>(new NumReverseComparator());
        for(int i : arr){
            MaxPQ.add(i);
            if(MaxPQ.size() > k)
                MaxPQ.remove();
        }
        int i = 0;
        while(!MaxPQ.isEmpty())
            result[i++] = MaxPQ.remove();
        return result;
    }
    static class NumReverseComparator implements Comparator<Integer>{
        public int compare(Integer i, Integer j){
            return -(i - j);
        }
    }
}
