package medium.Sort;

/**
 * @Time : 2020年3月10日11:36:42
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import java.util.ArrayList;
import java.util.List;

/**
 *给定一个字符串，请将字符串里的字符按照出现的频率降序排列。
 *
 * 示例 1:
 * 输入:
 * "tree"
 * 输出:
 * "eert"
 * 解释:
 * 'e'出现两次，'r'和't'都只出现一次。
 * 因此'e'必须出现在'r'和't'之前。此外，"eetr"也是一个有效的答案。
 *
 * 示例 2:
 * 输入:
 * "cccaaa"
 * 输出:
 * "cccaaa"
 * 解释:
 * 'c'和'a'都出现三次。此外，"aaaccc"也是有效的答案。
 * 注意"cacaca"是不正确的，因为相同的字母必须放在一起。
 *
 * 示例 3:
 * 输入:
 * "Aabb"
 * 输出:
 * "bbAa"
 * 解释:
 * 此外，"bbaA"也是一个有效的答案，但"Aabb"是不正确的。
 * 注意'A'和'a'被认为是两种不同的字符。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sort-characters-by-frequency
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class frequencySort {
    /**
     * 执行用时 :6 ms, 在所有 Java 提交中击败了95.71%的用户
     * 内存消耗 :42 MB, 在所有 Java 提交中击败了5.09%的用户
     * 桶排序的想法
     */
    public String frequencySort(String s) {
        int[] count = new int[128];
        for(int i = 0; i < s.length(); i++)
            count[s.charAt(i)] += 1;
        List<Character>[] charsSort = (List<Character>[]) new List[s.length() + 1];
        for(int i = 0; i < count.length; i++){
            if(charsSort[count[i]] == null)
                charsSort[count[i]] = new ArrayList<>();
            charsSort[count[i]].add((char) i);
        }

        StringBuilder res = new StringBuilder();
        for(int i = charsSort.length; i >= 0; i--){
            if(charsSort[i] != null){
                for(char c : charsSort[i]){
                    for(int j = 0; j < i; j++)
                        res.append(c);
                }
            }
        }
        return res.toString();
    }
}
