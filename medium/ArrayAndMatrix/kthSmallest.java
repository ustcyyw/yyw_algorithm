package medium.ArrayAndMatrix;

/**
 * @Time : 2020年2月11日17:23:45
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 给定一个 n x n 矩阵，其中每行和每列元素均按升序排序，找到矩阵中第k小的元素。
 * 请注意，它是排序后的第k小元素，而不是第k个元素。
 * <p>
 * 示例:
 * matrix = [
 * [ 1,  5,  9],
 * [10, 11, 13],
 * [12, 13, 15]
 * ],
 * k = 8,
 * 返回 13。
 * 说明:
 * 你可以假设 k 的值永远是有效的, 1 ≤ k ≤ n2 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/kth-smallest-element-in-a-sorted-matrix
 */
public class kthSmallest {
    /**
     * 执行用时 :21 ms, 在所有 Java 提交中击败了34.35%的用户
     * 内存消耗 :53.3 MB, 在所有 Java 提交中击败了5.02%的用户
     */
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<Integer> MaxPQ = new PriorityQueue<>(Collections.reverseOrder());
        for (int[] row : matrix) {
            for (int num : row) {
                if (MaxPQ.size() == k && num > MaxPQ.peek())
                    break;
                MaxPQ.add(num);
                if (MaxPQ.size() > k)
                    MaxPQ.remove();
            }
        }
        return MaxPQ.remove();
    }

    /**
     * 1.找出二维矩阵中最小的数left，最大的数right，那么第k小的数必定在left~right之间
     * 2.mid = left + (right - left) / 2; 在二维矩阵中寻找小于等于mid的元素个数count
     * 3.若这个count小于k，表明第k小的数在右半部分且不包含mid，即left=mid+1, right=right，又保证了第k小的数在left~right之间
     * 4.若这个count大于等于k，表明第k小的数在左半部分且可能包含mid，即left=left, right=mid，又保证了第k小的数在left~right之间
     * 5.因为每次循环中都保证了第k小的数在left~right之间，当left==right时，第k小的数即被找出，等于right
     *
     * 注意：这里的left mid right是数值，不是索引位置。
     * 时间复杂度 使用二分法，外循环需要log(max - min)次，内层查找小于等于mid的数的个数时，最坏需要移动2n次 所以 2n*log(max - min)
     */
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :45.4 MB, 在所有 Java 提交中击败了7.69%的用户
     */
    public int kthSmallest2(int[][] matrix, int k) {
        int n = matrix.length - 1;
        int left = matrix[0][0], right = matrix[n][n];
        while (left < right) {
            int mid = left + (right - left) / 2;
            int count = countNotMoreThanMid(matrix, mid, n);
            if (count < k)
                left = mid + 1;
            else
                right = mid;
        }
        return left;
    }

    private int countNotMoreThanMid(int[][] matrix, int mid, int n) {
        int count = 0;
        int x = 0, y = n;
        while (x <= n && y >= 0) {
            if (matrix[y][x] <= mid) {
                count += y + 1;
                x++;
            } else {
                y--;
            }
        }
        return count;
    }
}


