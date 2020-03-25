package easy.Sort;

/**
 * @Time : 2020年3月23日19:52:11
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定两个数组，编写一个函数来计算它们的交集。
 *
 * 示例 1:
 *
 * 输入: nums1 = [1,2,2,1], nums2 = [2,2]
 * 输出: [2]
 * 示例 2:
 *
 * 输入: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * 输出: [9,4]
 * 说明:
 *
 * 输出结果中的每个元素一定是唯一的。
 * 我们可以不考虑输出结果的顺序。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/intersection-of-two-arrays
 */
public class intersection349 {
    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了99.17%的用户
     * 内存消耗 :39 MB, 在所有 Java 提交中击败了5.11%的用户
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        List<Integer> temp = new ArrayList<>();
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int pre = Integer.MIN_VALUE;
        for(int i = 0, j = 0; i < nums1.length && j < nums2.length; ){
            if(nums1[i] < nums2[j]) i++;
            else if(nums1[i] > nums2[j]) j++;
            else {
                if(pre != nums1[i]){
                    pre = nums1[i];
                    temp.add(nums1[i]);
                }
                i++;
                j++;
            }
        }
        int[] res = new int[temp.size()];
        for(int i = 0; i < res.length; i++)
            res[i] = temp.get(i);
        return res;
    }
}
