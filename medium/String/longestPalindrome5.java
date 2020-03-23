package medium.String;

/**
 * @Time : 2020年3月3日23:49:40
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */
public class longestPalindrome5 {
    /**
     * 执行用时 :14 ms, 在所有 Java 提交中击败了79.97%的用户
     * 内存消耗 :37.9 MB, 在所有 Java 提交中击败了25.93%的用户
     * 中心扩散法
     * 每个字符串能当做对策中心的地方一共有2n-1个，每一个字母及每两个字母的中间
     */
    public String longestPalindrome(String s) {
        int n = s.length();
        if (n < 2) return s;
        char[] chars = s.toCharArray();
        int start = 0, maxL = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 1; i + j < n && i - j >= 0; j++) { // 以字符为中心
                if (chars[i - j] != chars[i + j]) break;
                else {
                    if (2 * j + 1 > maxL) {
                        maxL = 2 * j + 1;
                        start = i - j;
                    }
                }
            }
            for (int j = 0; i - j >= 0 && i + 1 + j < n; j++) { // 以两个字符的中间为中心
                if (chars[i - j] != chars[i + 1 + j]) break;
                else {
                    if(2 * j + 2 > maxL){
                        maxL = 2 * j + 2;
                        start = i - j;
                    }
                }
            }
        }
        return s.substring(start, start + maxL);
    }

    /**
     * 采用DP 想法都是从字符串的中心向外扩 进行判断 这样做多一个好处就是可以记录各个子串是否是回文串
     * 执行用时 :76 ms, 在所有 Java 提交中击败了42.10%的用户
     * 内存消耗 :42.8 MB, 在所有 Java 提交中击败了12.50%的用户
     */
    public String longestPalindrome2(String s) {
        int n = s.length();
        if (n < 2) return s;
        char[] chars = s.toCharArray();
        int start = 0, maxL = 1;
        boolean[][] p = new boolean[n][n]; // p[i][j] 表示子串[i,j]是否为一个回文串
        for(int i = 1; i < n; i++){ // i指定回文串的右边界
            p[i][i] = true;
            if(chars[i] == chars[i - 1]){
                p[i - 1][i] =true;
                if(maxL < 2){
                    maxL = 2;
                    start = i - 1;
                }
            }
            for(int j = i - 2; j >= 0; j--){ // j不断向左更新左边界。 本质上这两层循环 也是从中心往外扩地判断是不是回文串
                if(p[j + 1][i - 1] && chars[i] == chars[j]){
                    p[j][i] = true;
                    if(maxL < i - j + 1){
                        maxL = j - i + 1;
                        start = j;
                    }
                }
            }
        }
        return s.substring(start, start + maxL);
    }
}
