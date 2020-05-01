# 130. 被围绕的区域
### 原题
给定一个二维的矩阵，包含 'X' 和 'O'（字母 O）。
找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。

示例:

X X X X
X O O X
X X O X
X O X X
运行你的函数后，矩阵变为：

X X X X
X X X X
X X X X
X O X X
解释:

被围绕的区间不会存在于边界上，换句话说，任何边界上的 'O' 都不会被填充为 'X'。 任何不在边界上，或不与边界上的 'O' 相连的 'O' 最终都会被填充为 'X'。如果两个元素在水平或垂直方向相邻，则称它们是“相连”的。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/surrounded-regions)：https://leetcode-cn.com/problems/surrounded-regions

### 2种解法
##### 1.正向思维：去找被X围绕的区域
```java
	private int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private Queue<Integer> pos = null;
    private boolean flag;
    private int row, col;
    private boolean[][] visited;
    public void solve(char[][] board) {
        if (board.length == 0 || board[0].length == 0)
            return;
        row = board.length;
        col = board[0].length;
        pos = new LinkedList<>();
        visited = new boolean[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++) {
                if (!visited[i][j] && board[i][j] == 'O') {
                    flag = false;
                    searchO(board, i, j);
                    if (flag)
                        pos.clear();
                    else {
                        while (!pos.isEmpty())
                            board[pos.remove()][pos.remove()] = 'X';
                    }
                }
            }
    }

    private void searchO(char[][] board, int x, int y) {
        visited[x][y] = true;
        pos.add(x);
        pos.add(y);
        flag = flag || isEdge(x, y);
        for (int i = 0; i < 4; i++) {
            int newX = x + direction[i][0];
            int newY = y + direction[i][1];
            if (inArea(newX, newY) && !visited[newX][newY] && board[newX][newY] == 'O')
                searchO(board, newX, newY);
        }
    }

    private boolean inArea(int x, int y) {
        return x >= 0 && x < row && y >= 0 && y < col;
    }

    private boolean isEdge(int x, int y) {
        return x == 0 || y == 0 || x == row - 1 || y == col - 1;
    }
```
思路分析：
* 正向从题目的角度出发，就是直接去找被X包围的O，然后将它变为X。
* 从某一个为O的点开始出发，去查找这一块O是否被X包围，使用dfs，函数定义为`private void searchO(char[][] board, int x, int y)`
    * 但是在查找过程中并不能直接将O变为X，因为这一片可以会连接到边界。所以需要先将坐标存放起来，查找完成后再根据是否被X包围来确定要不要改为X。`pos.add(x);, pos.add(y);`这里使用了成员变量`Queue<Integer> pos`
    * 使用成员变量`visited[x][y]`表示点`x,y`是否被访问过，所以调用开始时先`visited[x][y] = true;`。
    * 在查找过程中要关注这一片点是否连接到边界，使用成员变量`boolean flag`表示，访问每一个点都进行更新`flag = flag || isEdge(x, y);`。辅助函数`boolean isEdge(int x, int y)`判断某个点是否为边界上的点，逻辑 或 则保证有一个点在矩阵边界则这一块的点都是连接至边界的。
    * 然后就是二维平面上下左右四个方向去遍历的常规做法了，要遍历下一个坐标就需要满足：在矩阵范围内，没有访问过，且该点是O。`inArea(newX, newY) && !visited[newX][newY] && board[newX][newY] == 'O'`
* 在主函数中，初始化成员变量后，就遍历每一个点，如果没有范围过且是O，开始dfs：
    * 首先要将表示该块是否连接到边界的标识`flag = false`。
    * 调用`searchO(board, i, j);`后，如果`flag == true`，直接清空队列中的坐标，因为他们链接到边界没有被X包围。否则，每次弹出x，y坐标，并且转变` board[pos.remove()][pos.remove()] = 'X';`直到队列为空。

运行结果：
* 执行用时 :10 ms, 在所有 Java 提交中击败了15.20%的用户
* 内存消耗 :50.4 MB, 在所有 Java 提交中击败了5.03%的用户

##### 2.逆向思维：去找没有被X围绕的区域
```java
	private int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};	
	private int row, col;
	private boolean[][] linkedEdge = null;
    public void solve2(char[][] board) {
        if(board.length == 0 || board[0].length == 0)
            return;
        row = board.length;
        col = board[0].length;
        linkedEdge = new boolean[row][col];
        for(int i = 0; i < row; i++){
            if(board[i][0] == 'O' && !linkedEdge[i][0])
                search2(board, i, 0);
            if(board[i][col - 1] == 'O' && !linkedEdge[i][col - 1])
                search2(board, i, col - 1);
        }
        for(int j = 1; j < col - 1; j++){
            if(board[0][j] == 'O' && !linkedEdge[0][j])
                search2(board, 0, j);
            if(board[row - 1][j] == 'O' && !linkedEdge[row - 1][j])
                search2(board, row - 1, j);
        }

        for(int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                if(!linkedEdge[i][j])
                    board[i][j] = 'X';
    }

 	private boolean inArea(int x, int y){
        return x >= 0 && x < row && y >= 0 && y < col;
    }	

    private void search2(char[][] board, int x, int y){
        linkedEdge[x][y] = true;
        for(int i = 0; i < 4; i++){
            int newX = x + direction[i][0];
            int newY = y + direction[i][1];
            if(inArea(newX, newY) && !linkedEdge[newX][newY] && board[newX][newY] == 'O')
                search2(board, newX, newY);
        }
    }
```
思路分析：
* 区别于方法1，另外一种思路就是，从边界的O出发，去找到所有与边界的O相连的O，标记这些位置。那么当所有搜索完成后，没有被标记的地方就有两种可能：
    * 原本就是X
    * 原本是O，但是没有与边界的O相连，那么要被转化为X
    * 所以只要没有标记的地方，设置为X就可以。
* 接下来就是从边界的O出发，去查找与之相连的O，定义`void search2(char[][] board, int x, int y)`.
    * 成员变量`linkedEdge[x][y]`表示是否与边界上的O相连。
    * 首先将`linkedEdge[x][y] = true;`，然后上下左右四个方向去尝试。要遍历下一个坐标就需要满足：在矩阵范围内，没有访问过，且该点是O。并且这里没有访问过可以用`!linkedEdge[newX][newY]`来标识，因为访问过的都是与边界O相连的O。
* 主函数：
    * 初始化成员变量后。
    * 就是对矩阵上下左右四个边，找到边界上的O，且还没有被访问过的`!linkedEdge[i][0]`进行DFS。
    * 然后遍历每一个点，如果没有被标记，统统变为X即可。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了97.69%的用户
* 内存消耗 :42.1 MB, 在所有 Java 提交中击败了32.57%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹