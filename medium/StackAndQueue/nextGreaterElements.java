package medium.StackAndQueue;

/**
 * @Time : 2020年2月9日23:42:00
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.Stack;

/**
 * 给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。
 * 数字 x 的下一个更大的元素是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。
 * <p>
 * 示例 1:
 * 输入: [1,2,1]
 * 输出: [2,-1,2]
 * 解释: 第一个 1 的下一个更大的数是 2；
 * 数字 2 找不到下一个更大的数；
 * 第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
 * 注意: 输入数组的长度不会超过 10000。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/next-greater-element-ii
 */
public class nextGreaterElements {
    /**
     * 执行用时 :56 ms, 在所有 Java 提交中击败了41.17%的用户
     * 内存消耗 :40.4 MB, 在所有 Java 提交中击败了48.94%的用户
     */
    public int[] nextGreaterElements(int[] nums) {
        int[] result = new int[nums.length];
        Stack<Integer> stack = new Stack<>();
        int loop = 2;
        while (loop > 0) { // 类比找距离下一次升温间隔天数的题目，但是本题的数组是个循环数组，所以需要两次循环才能得出结果
            for (int i = nums.length - 1; i >= 0; --i) {
                while (!stack.isEmpty() && nums[i] >= stack.peek())
                    stack.pop();
                if (!stack.empty())
                    result[i] = stack.peek();
                else
                    result[i] = -1;
                stack.push(nums[i]);
            }
            loop--;
        }
        return result;
    }

    /**
     * 执行用时 :4 ms, 在所有 Java 提交中击败了98.85%的用户
     * 内存消耗 :40.1 MB, 在所有 Java 提交中击败了61.49%的用户
     */
    public int[] nextGreaterElements2(int[] nums) {
        int[] temp = new int[nums.length * 2]; // 用来记录当前数字与下一个比它大的数组的距离（索引之差）
        // 按照方法一的思路，最多循环两次就能得到结果，这里直接将原数组加长，重复一遍也可以达到效果。
        int[] doubleNums = new int[nums.length * 2];
        System.arraycopy(nums, 0, doubleNums, 0, nums.length);
        System.arraycopy(nums, 0, doubleNums, nums.length, nums.length);

        for (int i = doubleNums.length; i >= 0; --i) {
            for (int j = i + 1; j < doubleNums.length; j += temp[j]) { // temp[j]表示j这个位置的数与下一个比它大的数的距离
                if (doubleNums[i] < doubleNums[j]) {
                    temp[i] = j - i;
                    break;
                } else if (temp[j] == 0) {
                    temp[i] = 0;
                    break;
                }
            }
        }
        int[] result = new int[nums.length];
        for (int i = 0; i < result.length; i++) {
            // temp[i]表示i这个位置的数与下一个比它大的数的距离 再加上i即为比它大的数的索引。但如果temp[i] == 0，意味着i这个位置的数最大
            result[i] = temp[i] == 0 ? -1 : doubleNums[i + temp[i]];
        }
        return result;
    }

    public static void main(String[] args) {
        int[] test = {1, 2, 1};
        nextGreaterElements nge = new nextGreaterElements();
        int[] result = nge.nextGreaterElements2(test);
    }
}
