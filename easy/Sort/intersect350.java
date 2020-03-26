package easy.Sort;

/**
 * @Time : 2020年3月23日20:06:27
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.*;

/**
 * 给定两个数组，编写一个函数来计算它们的交集。
 *
 * 示例 1:
 * 输入: nums1 = [1,2,2,1], nums2 = [2,2]
 * 输出: [2,2]
 *
 * 示例 2:
 * 输入: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * 输出: [4,9]
 * 说明：
 *
 * 输出结果中每个元素出现的次数，应与元素在两个数组中出现的次数一致。
 * 我们可以不考虑输出结果的顺序。
 *
 * 进阶:
 * 如果给定的数组已经排好序呢？你将如何优化你的算法？
 * 如果 nums1 的大小比 nums2 小很多，哪种方法更优？
 * 如果 nums2 的元素存储在磁盘上，磁盘内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/intersection-of-two-arrays-ii
 */
public class intersect350 {
    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了80.55%的用户
     * 内存消耗 :39.6 MB, 在所有 Java 提交中击败了5.04%的用户
     * 不考虑内存 和 磁盘的限制 像349题一样即可
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        List<Integer> temp = new ArrayList<>();
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        for(int i = 0, j = 0; i < nums1.length && j < nums2.length; ){
            if(nums1[i] > nums2[j]) j++;
            else if(nums1[i] < nums2[j]) i++;
            else {
                temp.add(nums1[i]);
                i++;
                j++;
            }
        }
        int[] res = new int[temp.size()];
        for(int i = 0; i < res.length; i++)
            res[i] = temp.get(i);
        return res;
    }

    /**
     * 无法一次读取到内存中，可以保留两个HashMap 统计出现的数字及出现了几次。
     * 执行用时 :6 ms, 在所有 Java 提交中击败了32.05%的用户
     * 内存消耗 :41.5 MB, 在所有 Java 提交中击败了5.04%的用户
     */
    public int[] intersect2(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map1 = new HashMap<>((int)(nums1.length / 0.75F + 1.0F));
        Map<Integer, Integer> map2 = new HashMap<>((int)(nums1.length / 0.75F + 1.0F));
        for(int i : nums1)
            map1.put(i, map1.getOrDefault(i, 0) + 1);
        for(int i : nums2)
            map2.put(i, map2.getOrDefault(i, 0) + 1);
        List<Integer> temp = new ArrayList<>();
        for(int key : map1.keySet()){
            if(map2.containsKey(key)){
                for(int i = 0; i < Math.min(map1.get(key), map2.get(key)); i++)
                    temp.add(key);
            }
        }
        int[] res = new int[temp.size()];
        for(int i = 0; i < res.length; i++)
            res[i] = temp.get(i);
        return res;
    }
}
