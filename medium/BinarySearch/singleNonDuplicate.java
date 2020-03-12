package medium.BinarySearch;

/**
 * @Time : 2020年3月12日13:28:30
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给定一个只包含整数的有序数组，每个元素都会出现两次，唯有一个数只会出现一次，找出这个数。
 *
 * 示例 1:
 * 输入: [1,1,2,3,3,4,4,8,8]
 * 输出: 2
 *
 * 示例 2:
 * 输入: [3,3,7,7,10,11,11]
 * 输出: 10
 * 注意: 您的方案应该在 O(log n)时间复杂度和 O(1)空间复杂度中运行。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/single-element-in-a-sorted-array
 */
public class singleNonDuplicate {
    /**
     * 有序数组 并且时间复杂度是 logn 进行一个查找任务。很像是二分查找。
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :41.6 MB, 在所有 Java 提交中击败了5.10%的用户
     * 注意这个搜索区间的变化 要保证剔除中间元素时，两两放在区间外，否则通过区间内元素个数的奇数偶数判断下一次的查找区间会失败
     */
    public int singleNonDuplicate(int[] nums) {
        if(nums.length == 1)
            return nums[0];
        if(nums[0] != nums[1]) // 这两处的处理是为了防止递归中的 mid + 1与mid - 1越界
            return nums[0];
        int hi = nums.length - 1;
        if(nums[hi - 1] != nums[hi])
            return nums[hi];
        return find(nums, 2, hi - 2);
    }

    private int find(int[] nums, int lo, int hi){
        if(lo > hi) return nums[lo];
        int mid = (hi - lo) / 2 + lo;
        int count = mid - lo + 1;
        if(nums[mid] == nums[mid - 1]){
            if(count % 2 == 0)
                return find(nums, mid + 1, hi);
            else
                return find(nums, lo, mid - 2); // 这里一定要注意 要将作为中间点相等的两个元素放在下一次查找的区间外
        }
        else if(nums[mid] == nums[mid + 1]){
            if(count % 2 == 1)
                return find(nums, mid + 2, hi);
            else
                return find(nums, lo, mid - 1);
        }
        else return nums[mid];
    }

    public static void main(String[] args) {
        int[] test = new int[19999];
        int i = 0, j = 1;
        for(;i < 8648; i++, j++){
            test[i++] = j;
            test[i] = j;
        }
        test[i++] = j++;
        for(; i < 19999; i++, j++){
            test[i++] = j;
            test[i] = j;
        }
        singleNonDuplicate s = new singleNonDuplicate();
            System.out.println(s.singleNonDuplicate(test));
    }
}
