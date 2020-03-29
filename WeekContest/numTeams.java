package WeekContest;

/**
 * @Time : 2020年3月29日10:40:16
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 *  n 名士兵站成一排。每个士兵都有一个 独一无二 的评分 rating 。
 * 每 3 个士兵可以组成一个作战单位，分组规则如下：
 * 从队伍中选出下标分别为 i、j、k 的 3 名士兵，他们的评分分别为 rating[i]、rating[j]、rating[k]
 * 作战单位需满足： rating[i] < rating[j] < rating[k] 或者 rating[i] > rating[j] > rating[k] ，其中  0 <= i < j < k < n
 * 请你返回按上述条件可以组建的作战单位数量。每个士兵都可以是多个作战单位的一部分。
 * <p>
 * 示例 1：
 * 输入：rating = [2,5,3,4,1]
 * 输出：3
 * 解释：我们可以组建三个作战单位 (2,3,4)、(5,4,1)、(5,3,1) 。
 * <p>
 * 示例 2：
 * 输入：rating = [2,1,3]
 * 输出：0
 * 解释：根据题目条件，我们无法组建作战单位。
 * <p>
 * 示例 3：
 * 输入：rating = [1,2,3,4]
 * 输出：4 
 * <p>
 * 提示：
 * n == rating.length
 * 1 <= n <= 200
 * 1 <= rating[i] <= 10^5
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/count-number-of-teams
 */
public class numTeams {
    public int numTeams(int[] rating) {
        int n = rating.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (rating[i] > rating[j] && rating[j] > rating[k])
                        count++;
                    if (rating[i] < rating[j] && rating[j] < rating[k])
                        count++;
                }
            }
        }
        return count;
    }

    /**
     * 2ms 平方的时间复杂度
     * 评论区大佬的做法
     */
    public int numTeams2(int[] rating) {
        int res = 0;
        int n = rating.length;
        for(int i = 1; i < n - 1; i++){
            int[] countLeft = count(0, i - 1, rating, rating[i]);
            int[] countRight = count(i + 1, n - 1, rating, rating[i]);
            res += countLeft[0] * countRight[1]; // 以i为中间士兵，左边比他大的数量 * 右边比他小的数量
            res += countLeft[1] * countRight[0]; // 以i为中间士兵，左边比他小的数量 * 右边比他大的数量
        }
        return res;
    }

    private int[] count(int lo, int hi, int[] rating, int target){
        int[] res = new int[2];
        for(int i = lo; i <= hi; i++){
            if(rating[i] > target) // 计数区间[lo, hi]上比target大的元素的数量
                res[0]++;
            if(rating[i] < target)
                res[1]++;
        }
        return res;
    }
}
