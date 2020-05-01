/**
 * @Time : 2020年4月18日15:01:37
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 桌上有 n 堆力扣币，每堆的数量保存在数组 coins 中。
 * 我们每次可以选择任意一堆，拿走其中的一枚或者两枚，求拿完所有力扣币的最少次数。
 *
 * 示例 1：
 * 输入：[4,2,1]
 * 输出：4
 * 解释：第一堆力扣币最少需要拿 2 次，第二堆最少需要拿 1 次，第三堆最少需要拿 1 次，总共 4 次即可拿完。
 *
 * 示例 2：
 * 输入：[2,3,10]
 * 输出：8
 *
 * 限制：
 * 1 <= n <= 4
 * 1 <= coins[i] <= 10
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/na-ying-bi
 */
public class minCount {
    /**
     * 0 ms
     */
    public int minCount(int[] coins) {
        int res = 0;
        for(int i : coins)
            res += count(i);
        return res;
    }

    private int count(int num){
        int count = 0;
        while(num > 0){
            num -= 2;
            count++;
        }
        return count;
    }
}
