package medium.Backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2020年2月22日21:10:35
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */
public class letterCombinations17 {
    private static String[] map = {
            "",
            "",
            "abc",
            "def",
            "ghi",
            "jkl",
            "mno",
            "pqrs",
            "tuv",
            "wxyz"
    };

    private List<String> combinations = null;

    /**
     * 执行用时 :9 ms, 在所有 Java 提交中击败了5.09%的用户
     * 内存消耗 :38.2 MB, 在所有 Java 提交中击败了5.22%的用户
     * 执行用时这么长的原因，主要是因为大量的字符串的拼接 每次都需要创建新的字符串对象。
     * 如果改用StringBuilder对象 或者直接使用char[]转化为字符串会快非常多。
     */
    public List<String> letterCombinations(String digits) {
        if(digits == null || digits.equals(""))
            return new ArrayList<String>();

        combinations = new ArrayList<>();
        combinateAt("", digits, 0);
        return combinations;
    }

    private void combinateAt(String pre, String digits, int curIndex){
        if(curIndex == digits.length()){
            combinations.add(pre);
            return;
        }
        int curDigit = digits.charAt(curIndex) - '0';
        for(char c : map[curDigit].toCharArray()){
            combinateAt(pre + c, digits, curIndex + 1);
        }
    }
}
