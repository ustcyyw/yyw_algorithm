package easy.ArrayAndMatrix;

/**
 * @Time : 2020年2月14日18:27:57
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.Arrays;
import java.util.HashSet;

/**
 * 给定一个整数数组，判断是否存在重复元素。
 * 如果任何值在数组中出现至少两次，函数返回 true。如果数组中每个元素都不相同，则返回 false。
 * <p>
 * 示例 1:
 * 输入: [1,2,3,1]
 * 输出: true
 * <p>
 * 示例 2:
 * 输入: [1,2,3,4]
 * 输出: false
 * <p>
 * 示例 3:
 * 输入: [1,1,1,3,3,4,3,2,4,2]
 * 输出: true
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/contains-duplicate
 */
public class containsDuplicate {
    /**
     * 执行用时 :12 ms, 在所有 Java 提交中击败了63.45%的用户
     * 内存消耗 :53.6 MB, 在所有 Java 提交中击败了5.11%的用户
     */
    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> numsSet = new HashSet<>((int) (nums.length / 0.75F + 1.0F));
        for(int num: nums){
            if(!numsSet.contains(num))
                numsSet.add(num);
            else return true;
        }
        return false;
    }

    /**
     * 执行用时 :6 ms, 在所有 Java 提交中击败了85.44%的用户
     * 内存消耗 :51.1 MB, 在所有 Java 提交中击败了5.11%的用户
     */
    public boolean containsDuplicate2(int[] nums) {
        Arrays.sort(nums);
        for(int i = 0; i < nums.length - 1; i++){
            if(nums[i] == nums[i + 1])
                return true;
        }
        return false;
    }
}
