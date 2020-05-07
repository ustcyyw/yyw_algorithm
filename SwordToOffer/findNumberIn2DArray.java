package SwordToOffer;

/**
 * @Time : 2020年4月14日00:23:40
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
 * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 * <p>
 * 示例:
 * 现有矩阵 matrix 如下：
 * [
 * [1,   4,  7, 11, 15],
 * [2,   5,  8, 12, 19],
 * [3,   6,  9, 16, 22],
 * [10, 13, 14, 17, 24],
 * [18, 21, 23, 26, 30]
 * ]
 * 给定 target = 5，返回 true。
 * 给定 target = 20，返回 false。 
 * <p>
 * 限制：
 * 0 <= n <= 1000
 * 0 <= m <= 1000
 * <p>
 * 注意：本题与主站 240 题相同：https://leetcode-cn.com/problems/search-a-2d-matrix-ii/
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof
 */
public class findNumberIn2DArray {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :45.4 MB, 在所有 Java 提交中击败了100.00%的用户
     */
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        int row = matrix.length;
        if (row == 0)
            return false;
        int col = matrix[0].length;
        if (col == 0)
            return false;
        int i = row - 1, j = 0;
        while (i > -1 && j < col) {
            if (target > matrix[i][j]) // 右移
                j++;
            else if (target < matrix[i][j])
                i--;
            else return true;
        }
        return false;
    }
}
