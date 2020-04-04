package easy.String;

/**
 * @Time : 2020年3月8日00:09:19
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个赎金信 (ransom) 字符串和一个杂志(magazine)字符串，判断第一个字符串ransom能不能由第二个字符串magazines里面的字符构成。
 * 如果可以构成，返回 true ；否则返回 false。
 * (题目说明：为了不暴露赎金信字迹，要从杂志上搜索各个需要的字母，组成单词来表达意思。)
 *
 * 注意：
 * 你可以假设两个字符串均只含有小写字母。
 *
 * canConstruct("a", "b") -> false
 * canConstruct("aa", "ab") -> false
 * canConstruct("aa", "aab") -> true
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/ransom-note。
 */
public class canConstruct {
    /**
     * 执行用时 :8 ms, 在所有 Java 提交中击败了45.37%的用户
     * 内存消耗 :42 MB, 在所有 Java 提交中击败了5.04%的用户
     */
    public boolean canConstruct(String ransomNote, String magazine) {
        char[] r = ransomNote.toCharArray();
        char[] m = magazine.toCharArray();
        Arrays.sort(r);
        Arrays.sort(m);
        int i = 0, j = 0;
        while(i < r.length && j < m.length){
            if(r[i] > m[j]) j++;
            else if (r[i] < m[j]) return false;
            else { // 一个字母只能用一次，所以当相等时，两个指针都需要移动
                i++;
                j++;
            }
        }
        return i == r.length;
    }

    /**
     * 执行用时 :16 ms, 在所有 Java 提交中击败了32.57%的用户
     * 内存消耗 :42.4 MB, 在所有 Java 提交中击败了5.04%的用户
     */
    public boolean canConstruct2(String ransomNote, String magazine) {
        Map<Character, Integer> rMap = new HashMap<>((int)(ransomNote.length() / 0.75F + 1.0F));
        Map<Character, Integer> mMap = new HashMap<>((int)(ransomNote.length() / 0.75F + 1.0F));
        for(char c : ransomNote.toCharArray()){
            rMap.put(c, rMap.getOrDefault(c, 0) + 1);
        }
        for(char c : magazine.toCharArray()){
            mMap.put(c, mMap.getOrDefault(c, 0) + 1);
        }
        for(char key : rMap.keySet()){ // 要细心啊！可能第二个字符串中没有出现过第一个字符串中的字符，所以得用getOrDefault()
            if(rMap.get(key) > mMap.getOrDefault(key, 0)) // 第二个字符串可以有剩余
                return false;
        }
        return true;
    }

    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了81.69%的用户
     * 内存消耗 :41.4 MB, 在所有 Java 提交中击败了5.04%的用户
     */
    public boolean canConstruct3(String ransomNote, String magazine) {
        int[] countR = new int[26];
        int[] countM = new int[26];
        for(char c : ransomNote.toCharArray())
            countR[c - 'a'] += 1;
        for(char c : magazine.toCharArray())
            countM[c - 'a'] += 1;
        for(int i = 0; i < 26; i++){
            if(countR[i] > countM[i])
                return false;
        }
        return true;
    }

    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了81.69%的用户
     * 内存消耗 :41 MB, 在所有 Java 提交中击败了5.04%的用户
     */
    public boolean canConstruct4(String ransomNote, String magazine) {
        int[] count = new int[26];
        for(char c : magazine.toCharArray())
            count[c - 'a'] += 1;
        for(char c : ransomNote.toCharArray()){
            count[c - 'a'] -= 1;
            if(count[c - 'a'] < 0)
                return false;
        }
        return true;
    }
}
