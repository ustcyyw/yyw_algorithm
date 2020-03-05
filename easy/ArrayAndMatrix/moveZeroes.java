package easy.ArrayAndMatrix;

/**
 * @Time : 2020年2月10日13:28:20
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 * <p>
 * 示例:
 * 输入: [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 * 说明:
 * 必须在原数组上操作，不能拷贝额外的数组。
 * 尽量减少操作次数。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/move-zeroes
 */
public class moveZeroes {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :41.8 MB, 在所有 Java 提交中击败了5.07%的用户
     */
    public void moveZeroes(int[] nums) {
        int start = 0;
        for (int i = 0; i < nums.length && nums[i] != 0; i++)
            start++;
        int d = 0; // 用来表示连续的0的个数
        for (int i = start; i < nums.length; i++) {
            int j = i + d;
            for (; j < nums.length && nums[j] == 0; j++)
                d++;
            if (j >= nums.length)
                return;
            exchange(nums, i, j);
        }
    }

    private void exchange(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * 官方标答
     * 使用两个指针，一个指针总在找第一个0出现的位置，另外一个指针按顺序找非0元素
     */
    public void moveZeroes3(int[] nums) {
        int zeroIndex = 0;
        for(int nonZeroIndex = 1; nonZeroIndex < nums.length; nonZeroIndex++){
            if(nums[zeroIndex] == 0){
                if(nums[nonZeroIndex] != 0){ // nonZeroIndex所指元素非0
                    exchange(nums, zeroIndex, nonZeroIndex);
                    zeroIndex++;
                }
                // 只是zeroIndex所指元素为0， 但nonZeroIndex所指的也是0，外循环使得nonZeroIndex++去找下一个非0元素
            } else { // (nums[zeroIndex] ！= 0的情况，不需要交换，此时zeroIndex++向右移动去找0元素，外循环使得nonZeroIndex++去找下一个与0交换的元素
                zeroIndex++;
            }
        }
    }
}
