package easy.String;

/**
 * @Time : 2020年2月15日14:29:58
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定两个字符串 s 和 t，判断它们是否是同构的。
 * 如果 s 中的字符可以被替换得到 t ，那么这两个字符串是同构的。
 * 所有出现的字符都必须用另一个字符替换，同时保留字符的顺序。两个字符不能映射到同一个字符上，但字符可以映射自己本身。
 *
 * 示例 1:
 * 输入: s = "egg", t = "add"
 * 输出: true
 *
 * 示例 2:
 * 输入: s = "foo", t = "bar"
 * 输出: false
 *
 * 示例 3:
 * 输入: s = "paper", t = "title"
 * 输出: true
 *
 * 说明:
 * 你可以假设 s 和 t 具有相同的长度。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/isomorphic-strings
 */
public class isIsomorphic {
    /**
     执行用时 :3 ms, 在所有 Java 提交中击败了97.81%的用户
     内存消耗 :43.3 MB, 在所有 Java 提交中击败了5.03%的用户
     */
    public boolean isIsomorphic(String s, String t) {
        return canMap(s, t) && canMap(t, s);
    }

    private boolean canMap(String s, String t){
        int[] map = new int[128]; // ascii 128个字符
        char[] chars1 = s.toCharArray();
        char[] chars2 = t.toCharArray();
        for(int i = 0; i < chars1.length; i++){
            if(map[chars1[i]] == 0)
                map[chars1[i]] = chars2[i];
            else {
                if(map[chars1[i]] != chars2[i])
                    return false;
            }
        }
        return true;
    }

    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了98.19%的用户
     * 内存消耗 :42 MB, 在所有 Java 提交中击败了5.03%的用户
     */
    public boolean isIsomorphic2(String s, String t) {
        int[] map1 = new int[128];
        int[] map2 = new int[128];
        char[] chars1 = s.toCharArray();
        char[] chars2 = t.toCharArray();
        for(int i = 0; i < chars1.length; i++){
            if(map1[chars1[i]] == 0 && map2[chars2[i]] == 0) { // 还没有建立相互的映射
                map1[chars1[i]] = chars2[i]; // 从1到2的映射 角标表示1中的字符，值表示隐射到的值
                map2[chars2[i]] = chars1[i];
            } else if (map1[chars1[i]] == chars2[i] && map2[chars2[i]] == chars1[i])
                continue;
            else return false;
        }
        return true;
    }
}
