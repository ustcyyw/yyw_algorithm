package easy.String;

/**
 * @Time : 2020年3月19日23:49:25
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给定一个仅包含大小写字母和空格 ' ' 的字符串 s，返回其最后一个单词的长度。如果字符串从左向右滚动显示，那么最后一个单词就是最后出现的单词。
 * 如果不存在最后一个单词，请返回 0 。
 * 说明：一个单词是指仅由字母组成、不包含任何空格字符的 最大子字符串。

 * 示例:
 * 输入: "Hello World"
 * 输出: 5
 *
 * https://leetcode-cn.com/problems/length-of-last-word/
 */
public class lengthOfLastWord {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :38.1 MB, 在所有 Java 提交中击败了5.43%的用户
     */
    public int lengthOfLastWord(String s) {
        char[] chars = s.toCharArray();
        if(chars.length == 0)
            return 0;
        int tail = chars.length - 1;
        for(;tail >= 0; tail--){ // 处理"a      "，先找到第一个非空的字符
            if(chars[tail] != ' ')
                break;
        }
        if(tail == -1) // "      "的情况
            return 0;
        int i = tail - 1;
        for(; i >= 0; i--){
            if(chars[i] == ' ')
                break;
        }
        return tail - i;
    }
}
