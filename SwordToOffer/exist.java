package SwordToOffer;

/**
 * @Time : 2020年4月14日12:59:49
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。
 * 路径可以从矩阵中的任意一格开始，每一步可以在矩阵中向左、右、上、下移动一格。
 * 如果一条路径经过了矩阵的某一格，那么该路径不能再次进入该格子。9
 * 例如，在下面的3×4的矩阵中包含一条字符串“bfce”的路径（路径中的字母用加粗标出）。
 *
 * [["a","b","c","e"],
 * ["s","f","c","s"],
 * ["a","d","e","e"]]
 *
 * 但矩阵中不包含字符串“abfb”的路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入这个格子。
 *  
 *
 * 示例 1：
 * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
 * 输出：true
 *
 * 示例 2：
 * 输入：board = [["a","b"],["c","d"]], word = "abcd"
 * 输出：false
 *
 * 提示：
 * 1 <= board.length <= 200
 * 1 <= board[i].length <= 200
 * 注意：本题与主站 79 题相同：https://leetcode-cn.com/problems/word-search/
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/ju-zhen-zhong-de-lu-jing-lcof
 */
public class exist {
    /**
     * 执行用时 :6 ms, 在所有 Java 提交中击败了83.83%的用户
     * 内存消耗 :41.3 MB, 在所有 Java 提交中击败了100.00%的用户
     */
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
