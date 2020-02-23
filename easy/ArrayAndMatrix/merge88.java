package easy.ArrayAndMatrix;

/**
 * @Time : 2020年2月23日14:08:00
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给定两个有序整数数组 nums1 和 nums2，将 nums2 合并到 nums1 中，使得 num1 成为一个有序数组。
 * 说明:
 * 初始化 nums1 和 nums2 的元素数量分别为 m 和 n。
 * 你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
 * 示例:
 * <p>
 * 输入:
 * nums1 = [1,2,3,0,0,0], m = 3
 * nums2 = [2,5,6],       n = 3
 * <p>
 * 输出: [1,2,2,3,5,6]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-sorted-array
 */
public class merge88 {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :38.3 MB, 在所有 Java 提交中击败了5.25%的用户
     * 就是归并排序的 merge 的过程
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] temp = new int[m];
        System.arraycopy(nums1, 0, temp, 0, m);

        int i = 0, j = 0;
        for(int k = 0; k < m + n; k++){
            if(i >= m) nums1[k] = nums2[j++];
            else if (j >= n) nums1[k] = temp[i++];
            else if(temp[i] > nums2[j]) nums1[k] = nums2[j++];
            else nums1[k] = temp[i++];
        }
    }
}
