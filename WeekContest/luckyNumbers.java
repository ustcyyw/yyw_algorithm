package WeekContest;

import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2020年3月15日10:32:30
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给你一个 m * n 的矩阵，矩阵中的数字 各不相同 。请你按 任意 顺序返回矩阵中的所有幸运数。
 * 幸运数是指矩阵中满足同时下列两个条件的元素：
 * 在同一行的所有元素中最小
 * 在同一列的所有元素中最大
 *  
 * 示例 1：
 * 输入：matrix = [[3,7,8],[9,11,13],[15,16,17]]
 * 输出：[15]
 * 解释：15 是唯一的幸运数，因为它是其所在行中的最小值，也是所在列中的最大值。
 *
 * 示例 2：
 * 输入：matrix = [[1,10,4,2],[9,3,8,7],[15,16,17,12]]
 * 输出：[12]
 * 解释：12 是唯一的幸运数，因为它是其所在行中的最小值，也是所在列中的最大值
 * 示例 3：
 * 输入：matrix = [[7,8],[1,2]]
 * 输出：[7]
 *
 * 提示：
 * m == mat.length
 * n == mat[i].length
 * 1 <= n, m <= 50
 * 1 <= matrix[i][j] <= 10^5
 * 矩阵中的所有元素都是不同的
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/lucky-numbers-in-a-matrix
 */
public class luckyNumbers {
    /**
     * 难度 medium 2ms
     */
    public List<Integer> luckyNumbers (int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if(matrix.length == 0 || matrix[0].length == 0)
            return res;
        int row = matrix.length, col = matrix[0].length;
        for(int i = 0; i < row; i++){
            int colMinIndex = 0; // 找到该行最小的元素的列索引
            for(int j = 1; j < col; j++){
                if(matrix[i][j] < matrix[i][colMinIndex])
                    colMinIndex = j;
            }
            int rowMaxIndex = i;
            for(int j = 0; j < row; j++){
                if(matrix[j][colMinIndex] > matrix[i][colMinIndex]){
                    rowMaxIndex = j;
                    break;
                }
            }
            if(rowMaxIndex == i)
                res.add(matrix[i][colMinIndex]);
        }
        return res;
    }
}
