package easy.BinarySearch;

/**
 * @Time : 2020年3月8日22:16:56
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 * 你可以假设数组中无重复元素。
 *
 * 示例 1:
 * 输入: [1,3,5,6], 5
 * 输出: 2
 *
 * 示例 2:
 * 输入: [1,3,5,6], 2
 * 输出: 1
 *
 * 示例 3:
 * 输入: [1,3,5,6], 7
 * 输出: 4
 *
 * 示例 4:
 * 输入: [1,3,5,6], 0
 * 输出: 0
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-insert-position
 */
public class searchInsert {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :39 MB, 在所有 Java 提交中击败了27.54%的用户
     */
    public int searchInsert(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while(lo <= hi){
            int mid = (hi - lo) / 2 + lo;
            if(nums[mid] > target)
                hi = mid - 1;
            else if(nums[mid] < target)
                lo = mid + 1;
            else return mid;
        }
        return lo;
    }
}
