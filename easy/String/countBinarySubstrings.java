package easy.String;

/**
 * @Time : 2020年2月16日16:48:07
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个字符串 s，计算具有相同数量0和1的非空(连续)子字符串的数量，并且这些子字符串中的所有0和所有1都是组合在一起的。
 * 重复出现的子串要计算它们出现的次数。
 *
 * 示例 1 :
 * 输入: "00110011"
 * 输出: 6
 * 解释: 有6个子串具有相同数量的连续1和0：“0011”，“01”，“1100”，“10”，“0011” 和 “01”。
 * 请注意，一些重复出现的子串要计算它们出现的次数。
 * 另外，“00110011”不是有效的子串，因为所有的0（和1）没有组合在一起。
 *
 * 示例 2 :
 * 输入: "10101"
 * 输出: 4
 * 解释: 有4个子串：“10”，“01”，“10”，“01”，它们具有相同数量的连续1和0。
 *
 * 注意：
 * s.length 在1到50,000之间。
 * s 只包含“0”或“1”字符。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/count-binary-substrings
 */
public class countBinarySubstrings {
    /**
     * 执行用时 :11 ms, 在所有 Java 提交中击败了88.22%的用户
     * 内存消耗 :38 MB, 在所有 Java 提交中击败了61.89%的用户
     * 类似于回文字符串的做法
     */
    public int countBinarySubstrings(String s) {
        int result = 0;
        char[] chars = s.toCharArray();
        for(int i = 1; i < s.length(); i++){
            int left = i - 1, right = i;
            char leftChar = chars[left], rightChar = chars[right];
            if(leftChar == rightChar)
                continue;
            while(left >= 0 && right < s.length() && chars[left] == leftChar && chars[right] == rightChar){
                left--;
                right++;
                result++;
            }
        }
        return result;
    }

    /**
     *参考官方思路，自己实现
     * 执行用时 :18 ms, 在所有 Java 提交中击败了47.50%的用户
     * 内存消耗 :38 MB, 在所有 Java 提交中击败了61.89%的用户
     */
    public int countBinarySubstrings2(String s) {
        int result = 0;
        char[] chars = s.toCharArray();
        int preCount = 0, curCount = 1;
        for(int i = 1; i < chars.length; i++){
            if(chars[i - 1] == chars[i]) // 记录当前组的数量
                curCount++;
            else {
                result += Math.min(preCount, curCount); // 将连续的0,1分组后，相邻两组能满足题意的子串数量即为 Math.min(preCount, curCount)
                preCount = curCount;
                curCount = 1;
            }
        }
        return result + Math.min(preCount, curCount); // 循环结束后还有最后一组要和前一组进行计数
    }
}
