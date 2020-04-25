package medium.ArrayAndMatrix;

/**
 * @Time : 2020年2月10日20:26:14
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.Arrays;

/**
 * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target。该矩阵具有以下特性：
 * 每行的元素从左到右升序排列。
 * 每列的元素从上到下升序排列。
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
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-a-2d-matrix-ii
 */
public class searchMatrix {
    /**
     * 执行用时 :9 ms, 在所有 Java 提交中击败了35.30%的用户
     * 内存消耗 :49.6 MB, 在所有 Java 提交中击败了5.03%的用户
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return false;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] > target)
                return false;
            if (Arrays.binarySearch(matrix[i], target) >= 0)
                return true;
        }
        return false;
    }

    /**
     * 标答一
     * 搜索空间的缩小,如果目标值比矩阵的左上角小或者比矩阵的右小角大，肯定无法不能在矩阵中找到该值
     * 以此为依据，可以不断将搜索区域划分小
     * 执行用时 :9 ms, 在所有 Java 提交中击败了35.30%的用户
     * 内存消耗 :50.8 MB, 在所有 Java 提交中击败了5.03%的用户
     * 这个方法同方法一 时间复杂度都是O（n*logn）
     */
    public boolean searchMatrix2(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0)
            return false;
        return search(matrix, target, 0, matrix[0].length - 1, 0, matrix.length - 1);
    }

    private boolean search(int[][] matrix, int target, int left, int right, int top, int bottom) {
        if (left > right || top > bottom) // 已无迭代区域
            return false;
        if (target < matrix[top][left] || target > matrix[bottom][right]) // 目标值比矩阵的左上角小或者比矩阵的右小角大，肯定无法不能在矩阵中找到该值
            return false;
        int mid = (left + right) / 2;
        int row = top;
        while (row <= bottom && matrix[row][mid] <= target) { // 搜索中间列是否能找到target，如果找不到就使row停在该行中间元素比target大的位置
            if (matrix[row][mid] == target)
                return true;
            row++;
        }
        return search(matrix, target, left, mid - 1, row, bottom) ||
                search(matrix, target, mid + 1, right, top, row - 1);
    }

    /**
     * 标答二：类似于行走
     * 起点位于矩阵左下角或者右上角都行，这两个点的特点是往一个方向走，矩阵元素减小，另一个方向走，矩阵元素增大
     * 执行用时 :7 ms, 在所有 Java 提交中击败了86.14%的用户
     * 内存消耗 :50.5 MB, 在所有 Java 提交中击败了5.03%的用户
     * 时间复杂度 O（m+n） 最多移动m+n次，位置就会超出矩阵。
     */
    public boolean searchMatrix3(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return false;
        int x = matrix[0].length - 1, y = 0;
        while(y < matrix.length && x >= 0){
            if(matrix[y][x] < target)
                y++;
            else if (matrix[y][x] > target)
                x--;
            else return true;
        }
        return false;
    }
}
