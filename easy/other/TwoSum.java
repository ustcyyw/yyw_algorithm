package easy.other;

/**
 * @Time : 2020年2月1日20:32:55
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 * 示例:
 * 给定 nums = [2, 7, 11, 15], target = 9
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class TwoSum {
    /**
     * 执行用时 :33 ms, 在所有 Java 提交中击败了23.51%的用户
     * 内存消耗 :37.6 MB, 在所有 Java 提交中击败,33.02%的用户
     */
    public static int[] towSum1(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了99.47%的用户
     * 内存消耗 :38.2 MB, 在所有 Java 提交中击败了7.17%的用户
     */
    public static int[] towSum2(int[] nums, int target) {
        // 要进行排序 先把原数组留一个备份
        int[] temp = new int[nums.length];
        System.arraycopy(nums, 0, temp, 0, nums.length);
        Arrays.sort(nums);
        int[] result = new int[2];

        // 有点类似于快排找切分点的做法 从最大最小向target靠近
        int m = 0, n = nums.length - 1;
        while (true) {
            int sum = nums[m] + nums[n];
            if (sum == target) {
                result[0] = m;
                result[1] = n;
                break;
            } else if (sum > target)
                n--;
            else m++;
        }

        // 由备份找到原来的索引在哪
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == nums[result[0]]) {
                result[0] = i;
                break;
            }
        }
        // 避免nums中有相等元素时候的错误 有一个下标要从右向左
        for (int i = temp.length - 1; i >= 0; i--) {
            if (temp[i] == nums[result[1]]) {
                result[1] = i;
                break;
            }
        }
        return null;
    }

    /**
     * 官方标答
     */
    public static int[] towSum3(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>((int) (nums.length / 0.75F + 1.0F));
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    public static void main(String[] args) {
    }
}
