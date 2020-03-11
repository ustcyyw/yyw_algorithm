package medium.Sort;

/**
 * @Time : 2020年3月10日12:08:25
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
 * 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
 * 注意:
 * 不能使用代码库中的排序函数来解决这道题。
 * <p>
 * 示例:
 * 输入: [2,0,2,1,1,0]
 * 输出: [0,0,1,1,2,2]
 * <p>
 * 进阶：
 * 一个直观的解决方案是使用计数排序的两趟扫描算法。
 * 首先，迭代计算出0、1 和 2 元素的个数，然后按照0、1、2的排序，重写当前数组。
 * 你能想出一个仅使用常数空间的一趟扫描算法吗？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sort-colors
 */
public class sortColors {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :38.5 MB, 在所有 Java 提交中击败了5.02%的用户
     * 三向切分的快速排序中切分的步骤
     */
    public void sortColors(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1){
                exch(nums, i, 0);
                break;
            }
        }
        int gt = nums.length - 1, lt = 0, i = 1;
        while(gt >= i){
            if(nums[i] > 1) exch(nums, gt--, i);
            else if (nums[i] < 1) exch(nums, lt++, i++);
            else i++;
        }
    }

    private void exch(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
