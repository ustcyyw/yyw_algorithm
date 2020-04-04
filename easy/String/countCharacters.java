package easy.String;

/**
 * @Time : 2020年3月17日09:25:08
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给你一份『词汇表』（字符串数组） words 和一张『字母表』（字符串） chars。
 * 假如你可以用 chars 中的『字母』（字符）拼写出 words 中的某个『单词』（字符串），那么我们就认为你掌握了这个单词。
 * 注意：每次拼写时，chars 中的每个字母都只能用一次。
 * 返回词汇表 words 中你掌握的所有单词的 长度之和。
 *
 * 示例 1：
 * 输入：words = ["cat","bt","hat","tree"], chars = "atach"
 * 输出：6
 * 解释：
 * 可以形成字符串 "cat" 和 "hat"，所以答案是 3 + 3 = 6。
 *
 * 示例 2：
 * 输入：words = ["hello","world","leetcode"], chars = "welldonehoneyr"
 * 输出：10
 * 解释：
 * 可以形成字符串 "hello" 和 "world"，所以答案是 5 + 5 = 10。
 *  
 *
 * 提示：
 * 1 <= words.length <= 1000
 * 1 <= words[i].length, chars.length <= 100
 * 所有字符串中都仅包含小写英文字母
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-words-that-can-be-formed-by-characters
 */
public class countCharacters {
    /**
     * 执行用时 :5 ms, 在所有 Java 提交中击败了93.25%的用户
     * 内存消耗 :42.2 MB, 在所有 Java 提交中击败了5.08%的用户
     */
    public int countCharacters(String[] words, String chars) {
        int res = 0;
        int[] countChars = getCount(chars);
        for(int i = 0; i < words.length; i++){
            int n = words[i].length();
            if(n > chars.length())
                continue;
            if(isValid(words[i],n , getCount(words[i]), countChars))
                res += n;
        }
        return res;
    }

    private int[] getCount(String s){
        int[] count = new int[26];
        for(int i = 0; i < s.length(); i++)
            count[s.charAt(i) - 'a'] += 1;
        return count;
    }

    private boolean isValid(String wordStr, int n, int[] word, int[] chars){
        for(int i = 0; i < n; i++){
            int index = wordStr.charAt(i) - 'a';
            if(word[index] > chars[index])
                return false;
        }
        return true;
    }
}
