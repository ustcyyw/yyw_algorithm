package easy.ArrayAndMatrix;

/**
 * @Time : 2020年2月12日15:07:00
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import java.util.Arrays;

/**
 * 集合 S 包含从1到 n 的整数。
 * 不幸的是，因为数据错误，导致集合里面某一个元素复制了成了集合里面的另外一个元素的值，导致集合丢失了一个整数并且有一个元素重复。
 * 给定一个数组 nums 代表了集合 S 发生错误后的结果。你的任务是首先寻找到重复出现的整数，再找到丢失的整数，将它们以数组的形式返回。
 * <p>
 * 示例 1:
 * 输入: nums = [1,2,2,4]
 * 输出: [2,3]
 * 注意:
 * 给定数组的长度范围是 [2, 10000]。
 * 给定的数组是无序的。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/set-mismatch
 */
public class findErrorNums {
    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了77.70%的用户
     * 内存消耗 :48.8 MB, 在所有 Java 提交中击败了5.62%的用户
     */
    public int[] findErrorNums(int[] nums) {
        int[] result = new int[2];
        int[] temp = new int[nums.length + 1]; // temp[i] 代表数字i出现的次数, 注意集合中元素是1~n，所以temp的长度为n+1
        for (int i = 0; i < nums.length; i++) {
            temp[nums[i]]++;
        }
        for (int i = 1; i < temp.length; i++) {
            if (temp[i] == 0)
                result[1] = i;
            if (temp[i] == 2)
                result[0] = i;
        }
        return result;
    }

    /**
     * 执行用时 :17 ms, 在所有 Java 提交中击败了28.63%的用户
     * 内存消耗 :49.3 MB, 在所有 Java 提交中击败了5.62%的用户
     * 其实排序的时间复杂度是 n*logn，必然不可能比方法1快
     */
    public int[] findErrorNums2(int[] nums) {
        Arrays.sort(nums);
        int[] result = new int[2];
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i + 1] - nums[i] == 0)
                result[0] = nums[i];
            if (nums[i + 1] - nums[i] == 2)
                result[1] = nums[i] + 1;
        }
        if (result[1] == 0) { // 要特别注意缺失第一个元素或者最后一个元素的情况， 这两种情况都会使得result[1]为初始值0
            if (nums[0] != 1)
                result[1] = 1;
            else result[1] = nums.length;
        }
        return result;
    }
}
