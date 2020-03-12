package medium.BinarySearch;

/**
 * @Time : 2020年2月12日16:42:21
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个包含 n + 1 个整数的数组 nums，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。
 * 假设只有一个重复的整数，找出这个重复的数。
 *
 * 示例 1:
 * 输入: [1,3,4,2,2]
 * 输出: 2
 *
 * 示例 2:
 * 输入: [3,1,3,4,2]
 * 输出: 3
 * 说明：
 * 不能更改原数组（假设数组是只读的）。
 * 只能使用额外的 O(1) 的空间。
 * 时间复杂度小于 O(n2) 。
 * 数组中只有一个重复的数字，但它可能不止重复出现一次。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-the-duplicate-number
 */

/**
 * 不能改变数组，所以不能排序。
 * 额外空间O(1),不能复制数组，也不能使用HashSet
 * 时间复杂度O(n平方)，不能用暴力方法
 */
public class findDuplicate {
    /**
     * 别人想出来的解法
     * 类似于在矩阵中找到第k小的元素的思路，使用二分查找。
     * 执行用时 :4 ms, 在所有 Java 提交中击败了59.72%的用户
     * 内存消耗 :38.4 MB, 在所有 Java 提交中击败了10.64%的用户
     * 二分法时间复杂度 log(n), 每次遍历n+1个元素 所以总的时间复杂度为(n+1)logn
     */
    public int findDuplicate(int[] nums) {
        int right = nums.length - 1;
        int left = 1;
        while (left < right) {
            int mid = (left + right) / 2;
            int count = 0;
            for (int num : nums) {
                if (num <= mid)
                    count++;
            }
            if (count > mid) { // mid是在没有重复元素时，小于等于mid的数的个数
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}
