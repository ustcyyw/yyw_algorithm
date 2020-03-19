package easy.BinarySearch;

/**
 * @Time : 2020年3月11日15:07:47
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 实现 int sqrt(int x) 函数。
 * 计算并返回 x 的平方根，其中 x 是非负整数。
 * 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。
 *
 * 示例 1:
 * 输入: 4
 * 输出: 2
 *
 * 示例 2:
 * 输入: 8
 * 输出: 2
 * 说明: 8 的平方根是 2.82842...,
 *      由于返回类型是整数，小数部分将被舍去。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sqrtx
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class mySqrt {
    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了79.59%的用户
     * 内存消耗 :37.3 MB, 在所有 Java 提交中击败了5.09%的用户
     */
    public int mySqrt(int x) {
        long lo = 0, hi = x;
        while(lo <= hi){
            long mid = (hi - lo) / 2 + lo;
            if(mid * mid > x) hi = mid - 1;
            else if(mid * mid < x) lo = mid + 1;
            else return (int)mid;
        }
        return (int)hi;
    }
}
