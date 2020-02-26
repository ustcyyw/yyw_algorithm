package easy.other;

/**
 * @Time : 2020年2月26日00:00:17
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.HashSet;

/**
 * 给定一个非负整数 c ，你要判断是否存在两个整数 a 和 b，使得 a2 + b2 = c。
 * <p>
 * 示例1:
 * <p>
 * 输入: 5
 * 输出: True
 * 解释: 1 * 1 + 2 * 2 = 5
 *  
 * <p>
 * 示例2:
 * <p>
 * 输入: 3
 * 输出: False
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sum-of-square-numbers
 */
public class judgeSquareSum633 {
    /**
     * 执行用时 :5 ms, 在所有 Java 提交中击败了36.36%的用户
     * 内存消耗 :36.3 MB, 在所有 Java 提交中击败了5.18%的用户
     */
    public boolean judgeSquareSum(int c) {
        int end = (int) Math.sqrt(c);
        for (int i = 0; i <= end; i++) {
            double temp = Math.sqrt(c - i * i);
            if (temp == (int) temp)
                return true;
        }
        return false;
    }

    /**
     * 其它人解法
     */
    public boolean judgeSquareSum2(int c){
        int end = (int) Math.sqrt(c);
        int start = 0;
        while(start <= end){
            int temp = start * start + end * end;
            if(temp == c)
                return true;
            else if(temp < c)
                start++;
            else end--;
        }
        return false;
    }
}
