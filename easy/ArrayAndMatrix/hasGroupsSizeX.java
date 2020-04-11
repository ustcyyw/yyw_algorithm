package easy.ArrayAndMatrix;

/**
 * @Time : 2020年3月27日00:12:36
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一副牌，每张牌上都写着一个整数。
 *
 * 此时，你需要选定一个数字 X，使我们可以将整副牌按下述规则分成 1 组或更多组：
 *
 * 每组都有 X 张牌。
 * 组内所有的牌上都写着相同的整数。
 * 仅当你可选的 X >= 2 时返回 true。
 *
 * 示例 1：
 * 输入：[1,2,3,4,4,3,2,1]
 * 输出：true
 * 解释：可行的分组是 [1,1]，[2,2]，[3,3]，[4,4]
 *
 * 示例 2：
 * 输入：[1,1,1,2,2,2,3,3]
 * 输出：false
 * 解释：没有满足要求的分组。
 *
 * 示例 3：
 * 输入：[1]
 * 输出：false
 * 解释：没有满足要求的分组。
 *
 * 示例 4：
 * 输入：[1,1]
 * 输出：true
 * 解释：可行的分组是 [1,1]
 *
 * 示例 5：
 * 输入：[1,1,2,2,2,2]
 * 输出：true
 * 解释：可行的分组是 [1,1]，[2,2]，[2,2]
 *
 * 提示：
 *
 * 1 <= deck.length <= 10000
 * 0 <= deck[i] < 10000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/x-of-a-kind-in-a-deck-of-cards
 */
public class hasGroupsSizeX {
    /**
     * 执行用时 :13 ms, 在所有 Java 提交中击败了45.99%的用户
     * 内存消耗 :41.7 MB, 在所有 Java 提交中击败了5.51%的用户
     * 题目有设置 0 <= deck[i] < 10000，所以不用map也可以，用数组来做映射也可以，索引为元素的值；值则为该元素出现的次数
     */
    public boolean hasGroupsSizeX(int[] deck) {
        Map<Integer, Integer> count = new HashMap<>();
        for(int i : deck)
            count.put(i, count.getOrDefault(i, 0) + 1);
        int min = Integer.MAX_VALUE;
        for(int key : count.keySet())
            min = Math.min(min, count.get(key));
        for(int i = 2; i <= min; i++){
            boolean flag = true;
            for(int key : count.keySet())
                if(count.get(key) % i != 0){ // 如果有一组相同数字无法整除 每组分i就不可以
                    flag = false;
                    break;
                }
            if(flag) return true;
        }
        return false;
    }

    private int maxDivisor(int p, int q){
        if(p % q == 0) return q;
        else return maxDivisor(q, p % q);
    }

    /**
     * 执行用时 :13 ms, 在所有 Java 提交中击败了45.99%的用户
     * 内存消耗 :41.7 MB, 在所有 Java 提交中击败了5.51%的用户
     */
    public boolean hasGroupsSizeX2(int[] deck) {
        Map<Integer, Integer> count = new HashMap<>();
        for(int i : deck)
            count.put(i, count.getOrDefault(i, 0) + 1);
        int pre = count.get(deck[0]);
        for(int key : count.keySet()){
            int temp = count.get(key);
            pre = pre > temp ? maxDivisor(pre, temp) : maxDivisor(temp, pre);
            if(pre == 1)
                return false;
        }
        return pre != 1;
    }
}
