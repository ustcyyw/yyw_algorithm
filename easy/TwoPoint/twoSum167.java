package easy.TwoPoint;

/**
 * @Time : 2020年3月2日11:49:03
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给定一个已按照升序排列 的有序数组，找到两个数使得它们相加之和等于目标数。
 * 函数应该返回这两个下标值 index1 和 index2，其中 index1 必须小于 index2。
 *
 * 说明:
 * 返回的下标值（index1 和 index2）不是从零开始的。
 * 你可以假设每个输入只对应唯一的答案，而且你不可以重复使用相同的元素。
 *
 * 示例:
 * 输入: numbers = [2, 7, 11, 15], target = 9
 * 输出: [1,2]
 * 解释: 2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted
 */
public class twoSum167 {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了98.37%的用户
     * 内存消耗 :42.3 MB, 在所有 Java 提交中击败了5.06%的用户
     */
    public int[] twoSum(int[] numbers, int target) {
        int[] res = new int[2];
        for(int i = 0, j = numbers.length - 1; i < j; ){
            int sum = numbers[i] + numbers[j];
            if(sum > target) j--;
            else if(sum < target) i++;
            else {
                res[0] = i + 1;
                res[1] = j + 1;
                break;
            }
        }
        return res;
    }
}
