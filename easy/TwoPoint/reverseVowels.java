package easy.TwoPoint;

/**
 * @Time : 2020年3月10日12:29:00
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 编写一个函数，以字符串作为输入，反转该字符串中的元音字母。
 *
 * 示例 1:
 * 输入: "hello"
 * 输出: "holle"
 *
 * 示例 2:
 * 输入: "leetcode"
 * 输出: "leotcede"
 * 说明:
 * 元音字母不包含字母"y"。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-vowels-of-a-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class reverseVowels {
    /**
     * 执行用时 :5 ms, 在所有 Java 提交中击败了74.79%的用户
     * 内存消耗 :42.1 MB, 在所有 Java 提交中击败了5.08%的用户
     */
    public static char[] vowels = {'a','A','e','E','i','I','o','O','u','U'};
    public String reverseVowels(String s) {
        int n = s.length();
        if(n == 0)
            return "";
        StringBuilder res = new StringBuilder(s);
        for(int i = -1, j = n; ;){
            while(!isVowel(res.charAt(++i))){
                if(i == n - 1)
                    break;
            }
            while(!isVowel(res.charAt(--j))){
                if(j == 0)
                    break;
            }
            if(i >= j)
                break;
            exch(res, i, j);
        }
        return res.toString();
    }

    private void exch(StringBuilder sb, int i, int j){
        char temp = sb.charAt(i);
        sb.setCharAt(i, sb.charAt(j));
        sb.setCharAt(j, temp);
    }

    private boolean isVowel(char c){
        for(char v : vowels)
            if(c == v)
                return true;
        return false;
    }

    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了96.67%的用户
     * 内存消耗 :41.9 MB, 在所有 Java 提交中击败了5.08%的用户
     */
    public String reverseVowels2(String s) {
        int n = s.length();
        if(n == 0)
            return "";
        char[] chars = s.toCharArray();
        for(int i = -1, j = s.length(); ;){
            while(!isVowel(chars[++i])){
                if(i == n - 1)
                    break;
            }
            while(!isVowel(chars[--j])){
                if(j == 0)
                    break;
            }
            if(i >= j)
                break;
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return String.valueOf(chars);
    }
}
