# 131. 分割回文串
给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串。
返回 s 所有可能的分割方案。

示例:
输入: `"aab"`
输出:
[
  ["aa","b"],
  ["a","a","b"]
]

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/palindrome-partitioning

### 两种解法

##### 1.回溯

```java
private List<List<String>> res;
    private char[] chars;
    private int n;
    private boolean[][] isP;
	public List<List<String>> partition2(String s) {
        res = new ArrayList<>();
        if (s == null || s.length() == 0)
            return res;
        chars = s.toCharArray();
        n = chars.length;
        markP();
        backTrack2(new Stack<String>(), 0);
        return res;
    }

    private void backTrack2(Stack<String> stack, int curIndex) {
        if (curIndex == n) {
            List<String> tempRes = new ArrayList<>(stack);
            res.add(tempRes);
            return;
        }
        for (int i = curIndex; i < n; i++){
            if(isP[curIndex][i]){
                stack.push(String.valueOf(chars,curIndex, i - curIndex + 1));
                backTrack2(stack, i + 1);
                stack.pop();
            }
        }
    }

    private void markP() {
        isP = new boolean[n][n];
        for (int i = 0; i < n; i++)
            isP[i][i] = true;
        for (int i = 0; i < n; i++) {
            for (int j = 1; i - j >= 0 && i + j < n; j++) { // 以字符为中心的子串的判断
                if (chars[i - j] != chars[i + j]) break;
                isP[i - j][i + j] = true;
            }
            for (int j = 0; i - j >= 0 && i + j + 1 < n; j++) {
                if (chars[i - j] != chars[i + j + 1]) break;
                isP[i - j][i + j + 1] = true;
            }
        }
    }
```

思路分析：

* 本题要求的结果，是一个子字符串的集合。集合，组合问题一般都会想到回溯。
* 假如我们现在要选择第一个拆分的出的子字符串，这个字符串的长度可能为1，可能为2...可能为n。如果第一个字符串的长度为1，那么后续要在字串`[1, n - 1]`上进行拆分；如果第一个字符串的长度为2，那么后续要在子串`[2, n - 1]`上进行拆分...知道所有字符都被拆分。这就是问题的递归结构。
* 递归函数`private void backTrack2(Stack<String> stack, int curIndex)`，第一个参数是用于存放拆分出来的子字符串的，很方便在回溯的时候，将当前字符串从结果集中弹出。第二个参数表示，现在拆分的字符串的起始索引。
    * 当`curIndex == n`时，已经没有可以拆分的字符，所以要将结果放入`res`中，然后返回。
    * 递归的时候，下一个子字符串的起始位置为`curIndex`，结束位置的索引用`i`表示，可取范围`[curIndex, n - 1]`。在递归之前首先要保证`[curIndex, i]`这个子串是回文子串，否则结果是不正确的。
    * 如果`[curIndex, i]`这个子串是回文子串，那么将子串入栈，然后递归调用`backTrack2(stack, i + 1);`。`i+1`就是下一次拆分字符串的起始索引。
* 问题的关键就成了如何判断回文，由于本题可能涉及到重复的子串的使用，所以我们可以先将所有回文子串找出来，避免重复计算。参考 [5.最长回文子串](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/String/longestPalindrome5.md)里的做法：
    * 保留状态类似动态规划方法的状态定义`isP[i][j]`为真，表示子字符串`[i, j]`为回文字符串。
    * 计算`isP[][]`则通过中心扩展的方式。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了99.86%的用户
* 内存消耗 :42.1 MB, 在所有 Java 提交中击败了7.66%的用户
##### 2.回溯 但是很麻烦（略）

```java
private List<List<String>> res;
    private char[] chars;
    public List<List<String>> partition(String s) {
        res = new ArrayList<>();
        if(s == null || s.length() == 0)
            return res;
        chars = s.toCharArray();
        for(int i = 1; i <= s.length(); i++) // i 表示拆分成多少个子串
            backTrack(new String[i], i, 0, -1);
        return res;
    }

    /**
     *
     * @param k 拆分成多少个子串
     * @param curIndex 当前在找第几个子串 从0开始
     * @param pre 上一个子串最后一个字符在chars中的索引
     * @param temp 存放子串的数组
     */
    private void backTrack(String[] temp, int k, int curIndex, int pre){
        if(curIndex == k){
            if(pre == chars.length - 1){
                List<String> tempRes = new ArrayList<>();
                for(String i : temp)
                    tempRes.add(i);
                res.add(tempRes);
            }
            return;
        }
        for(int length = 1; length <= chars.length - pre - k + curIndex; length++){
            if(isValid(pre + 1, pre + length)){
                temp[curIndex] = String.valueOf(chars, pre + 1, length);
                backTrack(temp, k, curIndex + 1, pre + length);
            }
        }
    }

    private boolean isValid(int lo, int hi){
        while(lo < hi){
            if(chars[lo++] != chars[hi--])
                return false;
        }
        return true;
    }
```

思路分析：

运行结果：

* 执行用时 :17 ms, 在所有 Java 提交中击败了6.46%的用户
* 内存消耗 :42.7 MB, 在所有 Java 提交中击败了6.22%的用户

----

* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹