package easy.String;

/**
 * @Time : 2020年2月15日13:11:00
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
 * 异位词的定义：构成字符及每个字符出现的次数都相等。
 * <p>
 * 示例 1:
 * 输入: s = "anagram", t = "nagaram"
 * 输出: true
 * <p>
 * 示例 2:
 * 输入: s = "rat", t = "car"
 * 输出: false
 * <p>
 * 说明:
 * 你可以假设字符串只包含小写字母。
 * <p>
 * 进阶:
 * 如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-anagram
 */
public class isAnagram {
    /**
     * 执行用时 :28 ms, 在所有 Java 提交中击败了16.24%的用户
     * 内存消耗 :45.6 MB, 在所有 Java 提交中击败了5.08%的用户
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length())
            return false;
        Map<Character, Integer> charMap1 = new HashMap<>((int) (s.length() / 0.75F + 1.0F));
        Map<Character, Integer> charMap2 = new HashMap<>((int) (s.length() / 0.75F + 1.0F));
        for (char c : s.toCharArray())
            charMap1.put(c, charMap1.getOrDefault(c, 0) + 1);
        for (char c : t.toCharArray())
            charMap2.put(c, charMap2.getOrDefault(c, 0) + 1);
        if (charMap1.size() != charMap2.size())
            return false;
        for (char c : s.toCharArray()) {
            if (!charMap1.get(c).equals(charMap2.getOrDefault(c, 0))) {
                return false;
            }
        }
        return true;
    }

    /**
     执行用时 :2 ms, 在所有 Java 提交中击败了99.86%的用户
     内存消耗 :43.4 MB, 在所有 Java 提交中击败了5.08%的用户
     */
    public boolean isAnagram2(String s, String t) {
        int[] count1 = new int[26];
        for(char c : s.toCharArray())
            count1[c - 97] += 1;
        for(char c : t.toCharArray())
            count1[c - 97] -= 1;
        for(int i = 0; i < 26; i++){
            if(count1[i] != 0)
                return false;
        }
        return true;
    }
}
