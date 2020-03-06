package easy.TwoPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2020年3月6日08:58:05
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 输入一个正整数 target ，输出所有和为 target 的连续正整数序列（至少含有两个数）。
 * 序列内的数字由小到大排列，不同序列按照首个数字从小到大排列。
 *
 * 示例 1：
 * 输入：target = 9
 * 输出：[[2,3,4],[4,5]]
 *
 * 示例 2：
 * 输入：target = 15
 * 输出：[[1,2,3,4,5],[4,5,6],[7,8]]
 *  
 *
 * 限制：
 * 1 <= target <= 10^5
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/he-wei-sde-lian-xu-zheng-shu-xu-lie-lcof
 */
public class findContinuousSequence {
    /**
     * 执行用时 :4 ms, 在所有 Java 提交中击败了75.32%的用户
     * 内存消耗 :37.4 MB, 在所有 Java 提交中击败了100.00%的用户
     * 一个指针指向参与相加的最小值，另外一个指针指向参与相加的最大值
     */
    public int[][] findContinuousSequence(int target) {
        if(target == 2) return null;
        List<int[]> lists = new ArrayList<>();
        int min = 1, max = 2;
        int sum = 3;
        while(min <= target / 2){
            if(sum > target){
                sum -= min;
                min++;
            }
            else{
                if(sum == target)
                    lists.add(getOneArray(min, max));
                max++;
                sum += max;
            }
        }
        return lists.toArray(new int[0][]);
    }

    private int[] getOneArray(int lo, int hi){
        int[] res = new int[hi - lo + 1];
        for(int i = lo; i <= hi; i++){
            res[i - lo] = i;
        }
        return res;
    }
}
