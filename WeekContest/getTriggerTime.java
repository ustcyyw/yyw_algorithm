import java.util.Arrays;

/**
 * @Time : 2020年4月18日15:44:18
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 在战略游戏中，玩家往往需要发展自己的势力来触发各种新的剧情。
 * 一个势力的主要属性有三种，分别是文明等级（C），资源储备（R）以及人口数量（H）。
 * 在游戏开始时（第 0 天），三种属性的值均为 0。
 * <p>
 * 随着游戏进程的进行，每一天玩家的三种属性都会对应增加，我们用一个二维数组 increase 来表示每天的增加情况。
 * 这个二维数组的每个元素是一个长度为 3 的一维数组，
 * 例如 [[1,2,1],[3,4,2]] 表示第一天三种属性分别增加 1,2,1 而第二天分别增加 3,4,2。
 * <p>
 * 所有剧情的触发条件也用一个二维数组 requirements 表示。
 * 这个二维数组的每个元素是一个长度为 3 的一维数组，对于某个剧情的触发条件 c[i], r[i], h[i]，
 * 如果当前 C >= c[i] 且 R >= r[i] 且 H >= h[i] ，则剧情会被触发。
 * <p>
 * 根据所给信息，请计算每个剧情的触发时间，并以一个数组返回。如果某个剧情不会被触发，则该剧情对应的触发时间为 -1 。
 * <p>
 * 示例 1：
 * 输入： increase = [[2,8,4],[2,5,0],[10,9,8]] requirements = [[2,11,3],[15,10,7],[9,17,12],[8,1,14]]
 * 输出: [2,-1,3,-1]
 * 解释：
 * 初始时，C = 0，R = 0，H = 0
 * 第 1 天，C = 2，R = 8，H = 4
 * 第 2 天，C = 4，R = 13，H = 4，此时触发剧情 0
 * 第 3 天，C = 14，R = 22，H = 12，此时触发剧情 2
 * 剧情 1 和 3 无法触发。
 * <p>
 * 示例 2：
 * 输入： increase = [[0,4,5],[4,8,8],[8,6,1],[10,10,0]] requirements = [[12,11,16],[20,2,6],[9,2,6],[10,18,3],[8,14,9]]
 * 输出: [-1,4,3,3,3]
 * <p>
 * 示例 3：
 * 输入： increase = [[1,1,1]] requirements = [[0,0,0]]
 * 输出: [0]
 * <p>
 * 限制：
 * 1 <= increase.length <= 10000
 * 1 <= requirements.length <= 100000
 * 0 <= increase[i] <= 10
 * 0 <= requirements[i] <= 100000
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/ju-qing-hong-fa-shi-jian
 */
public class getTriggerTime {
    /**
     * 执行用时 :53 ms, 在所有 Java 提交中击败了54.20%的用户
     * 内存消耗 :97.6 MB, 在所有 Java 提交中击败了100.00%的用户
     */
    public int[] getTriggerTime(int[][] increase, int[][] requirements) {
        int n = requirements.length;
        int m = increase.length;
        int[] cs = new int[m + 1];
        int[] rs = new int[m + 1];
        int[] hs = new int[m + 1];
        prepare(cs, rs, hs, increase);
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            doResult(res, i, cs, requirements[i][0], m + 1);
            doResult(res, i, rs, requirements[i][1], m + 1);
            doResult(res, i, hs, requirements[i][2], m + 1);
        }
        return res;
    }

    private void prepare(int[] cs, int[] rs, int[] hs, int[][] increase) {
        int c = 0, r = 0, h = 0;
        for (int i = 0; i < cs.length - 1; i++) {
            c += increase[i][0];
            r += increase[i][1];
            h += increase[i][2];
            cs[i + 1] = c;
            rs[i + 1] = r;
            hs[i + 1] = h;
        }
    }

    private void doResult(int[] res, int i, int[] s, int target, int arrLength) {
        if (res[i] == -1) return;
        int temp = search(s, target);
        if (temp == arrLength) {
            res[i] = -1;
            return;
        }
        res[i] = Math.max(res[i], temp);
    }

    private int search(int[] nums, int target) {
        int temp = Arrays.binarySearch(nums, target);
        if(temp < 0) return -temp - 1;
        if (temp == nums.length)
            return temp;
        for (int i = temp; i >= 0; i--) {
            if (nums[i] != nums[temp])
                return i + 1;
        }
        return 0;
    }

    private int mySearch(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = (hi - lo) / 2 + lo;
            if (nums[mid] < target) lo = mid + 1;
            else hi = mid - 1;
        }
        return lo;
    }
}
