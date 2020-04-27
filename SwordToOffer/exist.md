# 79. 单词搜索
### 原题
给定一个二维网格和一个单词，找出该单词是否存在于网格中。
单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。

示例:
board =
[
  ['A','B','C','E'],
  ['S','F','C','S'],
  ['A','D','E','E']
]
给定 word = "ABCCED", 返回 true
给定 word = "SEE", 返回 true
给定 word = "ABCB", 返回 false


提示：

```
board 和 word 中只包含大写和小写英文字母。
1 <= board.length <= 200
1 <= board[i].length <= 200
1 <= word.length <= 10^3
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/word-search)：https://leetcode-cn.com/problems/word-search

### 解法
```java
public class exist {
    private static int[][] dires = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private int row, col;
    private boolean hasFind;
    private boolean[][] visited;
    public boolean exist(char[][] board, String word) {
        row = board.length;
        col = board[0].length;
        hasFind = false;
        if(row * col < word.length())
            return false;
        visited = new boolean[row][col];
        char[] chars = word.toCharArray();
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                if(board[i][j] == chars[0]){
                    backTrack(board, chars, 1, i, j);
                    if(hasFind)
                        return true;
                }
            }
        }
        return false;
    }

    private void backTrack(char[][] board, char[] word, int curIndex, int x, int y){
        if(hasFind)
            return;
        if(curIndex == word.length){
            hasFind = true;
            return;
        }
        visited[x][y] = true;
        for(int[] dire : dires){
            int newX = x + dire[0], newY = y + dire[1];
            if(isIn(newX, newY) && !visited[newX][newY] && board[newX][newY] == word[curIndex])
                backTrack(board, word, curIndex + 1, newX, newY);
        }
        visited[x][y] = false;
    }

    private boolean isIn(int x, int y){
        return x >= 0 && x < row && y >= 0 && y < col;
    }
}
```
思路分析：
* 翻译一下题目就是从某个格子开始行走，只能走上下左右四个方向，并且已经走过的格子不能再走进去。要判断是否能走出一条有顺序的路径，刚好字母构成了给定的字符串`word`。
* 二维平面上每个位置相当于都有四个岔路，如果某条路径发现上下左右四个方向都无法找到`word`中下一个对应的字符，那么这个走法不对，但是不必要从头来走。比如在寻找`abcde`的时候，如下：
    * 从a不断向右走到第一行第四列的d时，发现右边与下边都不是e（上面越界，左边已经走过不能再去），这条路径不对。
    * 但是没必要从头开始找，只需要折返到c，然后向下走即可。所以这显然应该使用回溯。

```
a b c d d
h e d f f
```

* 关于成员变量的说明（为了避免回溯函数中传参麻烦）：
    * 题目中说同一个单元格内的字母不允许被重复使用，所以需要使用`boolean[][] visited`来表示某一个坐标处的字母有没有被使用。
    * 二维格子中使用到坐标，需要防止越界，所以我们也需要知道二维格子的行列`int row, col;`
    * 并且为了方便表示上下左右四个方向，我们使用`static int[][] dires = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};`（这个是类变量）。
    * 如何表示在二维格子中找到了`word`，我们可以用`hasFind`来表示是否找到。
* 二维格子中使用到坐标，需要防止越界，还需要辅助函数`boolean isIn(int x, int y)`判断`x, y`坐标是否在区域内。
* 然后先来看回溯函数`void backTrack(char[][] board, char[] word, int curIndex, int x, int y)`。
    * 第三个参数表示**下一步**要找`word`中索引为`curIndex`的字符，第四第五个参数分别表示当前行列坐标。
    * 如果`curIndex == word.length`，就代表着所有字符都已经按顺序找到，此时就将`hasFind`设置为true，并且返回。
    * 另外，调用回溯函数时，如果`hasFind`为真，说明已经已经找到了该单词在网格中，就不需要在别的路径进行查找，直接返回。
    * 回溯函数主体:
        * 就是首先现在访问的字符已经被使用，则`visited[x][y] = true;`。
        * 然后对上下左右的下一个坐标满足在网格内`isIn(newX, newY)`，并且没有使用过该字母`!visited[newX][newY]`，并且下一个坐标对应的字幕是`word`中对应索引的字母`board[newX][newY] == word[curIndex]`的进行递归`backTrack(board, word, curIndex + 1, newX, newY);`。
        * 回溯时（也就是折返路径时），当前坐标要回退，所以恢复没有使用状态`visited[x][y] = false;`
* 在主函数中：
    * 首先给多个成员变量赋值。
    * 然后判断如果网格的数量都没有要找的字符数多`row * col < word.length()`，就直接返回`false`。
    * 由于路径可以从网格中任意一处`chars[0]`开始，所以需要一个二重循环来遍历所以可能的路径起点，然后调用`backTrack(board, chars, 1, i, j);`注意这里第三个参数是1，因为已经找到开头第一个字母，下一个要找的就是索引为1的字母。当然，如果某一个起点已经在网格中找到了`word`，就直接返回。`if(hasFind) return true`
* 最后，如果没有在遍历中返回，说明每一次路径的尝试都失败了，就返回`false`。

运行结果：

  * 执行用时 :6 ms, 在所有 Java 提交中击败了83.83%的用户
  * 内存消耗 :41.3 MB, 在所有 Java 提交中击败了100.00%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹