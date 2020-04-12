package WeekContest;

import java.util.HashMap;
import java.util.Map;

/**
 * @Time : 2020年4月12日11:20:12
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 *「HTML 实体解析器」 是一种特殊的解析器，它将 HTML 代码作为输入，并用字符本身替换掉所有这些特殊的字符实体。
 * HTML 里这些特殊字符和它们对应的字符实体包括：
 *
 * 双引号：字符实体为 &quot; ，对应的字符是 " 。
 * 单引号：字符实体为 &apos; ，对应的字符是 ' 。
 * 与符号：字符实体为 &amp; ，对应对的字符是 & 。
 * 大于号：字符实体为 &gt; ，对应的字符是 > 。
 * 小于号：字符实体为 &lt; ，对应的字符是 < 。
 * 斜线号：字符实体为 &frasl; ，对应的字符是 / 。
 * 给你输入字符串 text ，请你实现一个 HTML 实体解析器，返回解析器解析后的结果。
 *
 * 示例 1：
 * 输入：text = "&amp; is an HTML entity but &ambassador; is not."
 * 输出："& is an HTML entity but &ambassador; is not."
 * 解释：解析器把字符实体 &amp; 用 & 替换
 *
 * 示例 2：
 * 输入：text = "and I quote: &quot;...&quot;"
 * 输出："and I quote: \"...\""
 *
 * 示例 3：
 * 输入：text = "Stay home! Practice on Leetcode :)"
 * 输出："Stay home! Practice on Leetcode :)"
 *
 * 示例 4：
 * 输入：text = "x &gt; y &amp;&amp; x &lt; y is always false"
 * 输出："x > y && x < y is always false"
 *
 * 示例 5：
 * 输入：text = "leetcode.com&frasl;problemset&frasl;all"
 * 输出："leetcode.com/problemset/all"
 *  
 *
 * 提示：
 * 1 <= text.length <= 10^5
 * 字符串可能包含 256 个ASCII 字符中的任意字符。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/html-entity-parser
 */
public class entityParser {
    private static Map<String, String> map;

    static {
        map = new HashMap<>();
        map.put("&quot;", "\"");
        map.put("&apos;", "'");
        map.put("&amp;", "&");
        map.put("&gt;", ">");
        map.put("&lt;", "<");
        map.put("&frasl;", "/");
    }

    public String entityParser(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            sb.append(text.charAt(i));
            if (text.charAt(i) == '&') {
                for (int j = i + 1; j < text.length(); j++) {
                    sb.append(text.charAt(j));
                    if (text.charAt(j) == ';') {
                        String temp = text.substring(i, j + 1);
                        if (map.containsKey(temp)) {
                            sb.delete(sb.length() - (j - i) - 1, sb.length() + 1);
                            sb.append(map.get(temp));
                        }
                        i = j;
                        break;
                    }
                }
            }
        }
        return sb.toString();
    }
}
