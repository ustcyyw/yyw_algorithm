package easy.ArrayAndMatrix;

/**
 * @Time : 2020年4月9日12:52:47
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
 * <p>
 * 示例 1:
 * 输入: [1,2,3,4,5,6,7] 和 k = 3
 * 输出: [5,6,7,1,2,3,4]
 * 解释:
 * 向右旋转 1 步: [7,1,2,3,4,5,6]
 * 向右旋转 2 步: [6,7,1,2,3,4,5]
 * 向右旋转 3 步: [5,6,7,1,2,3,4
 * 示例 2:
 * 输入: [-1,-100,3,99] 和 k = 2
 * 输出: [3,99,-1,-100]
 * 解释:
 * 向右旋转 1 步: [99,-1,-100,3]
 * 向右旋转 2 步: [3,99,-1,-100]
 * <p>
 * 说明:
 * 尽可能想出更多的解决方案，至少有三种不同的方法可以解决这个问题。
 * 要求使用空间复杂度为 O(1) 的 原地 算法。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/rotate-array
 */
public class rotate {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :39.8 MB, 在所有 Java 提交中击败了5.19%的用户
     */
    public void rotate(int[] nums, int k) {
        int temp = k % nums.length;
        if (temp == 0) return;
        int[] tempArr = new int[temp];
        System.arraycopy(nums, nums.length - temp, tempArr, 0, temp);
        System.arraycopy(nums, 0, nums, temp, nums.length - temp);
        System.arraycopy(tempArr, 0, nums, 0, temp);
    }

    /**
     * 官方做法2 翻转三次数组
     * 1 2 3 4 5 6 7
     * 整个数组翻转为 7 6 5 4 3 2 1
     * 将前k个进行翻转 5 6 7 4 3 2 1
     * 再将剩余元素翻转 5 6 7 1 2 3 4
     */
    public void rotate2(int[] nums, int k) {
        k = k % nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    private void reverse(int[] nums, int lo, int hi) {
        while (lo < hi) {
            exch(nums, lo++, hi--);
        }
    }

    private void exch(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
