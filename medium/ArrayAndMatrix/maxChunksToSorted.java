package medium.ArrayAndMatrix;

/**
 * @Time : 2020年2月14日15:51:08
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 数组arr是[0, 1, ..., arr.length - 1]的一种排列，我们将这个数组分割成几个“块”，并将这些块分别进行排序。
 * 之后再连接起来，使得连接的结果和按升序排序后的原数组相同。
 * 我们最多能将数组分成多少块？
 * <p>
 * 示例 1:
 * 输入: arr = [4,3,2,1,0]
 * 输出: 1
 * 解释:
 * 将数组分成2块或者更多块，都无法得到所需的结果。
 * 例如，分成 [4, 3], [2, 1, 0] 的结果是 [3, 4, 0, 1, 2]，这不是有序的数组。
 * <p>
 * 示例 2:
 * 输入: arr = [1,0,2,3,4]
 * 输出: 4
 * 解释:
 * 我们可以把它分成两块，例如 [1, 0], [2, 3, 4]。
 * 然而，分成 [1, 0], [2], [3], [4] 可以得到最多的块数。
 * <p>
 * 注意:
 * arr 的长度在 [1, 10] 之间。
 * arr[i]是 [0, 1, ..., arr.length - 1]的一种排列。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/max-chunks-to-make-sorted
 */
public class maxChunksToSorted {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :36.8 MB, 在所有 Java 提交中击败了33.33%的用户
     */
    public int maxChunksToSorted(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length; ) {
            int j = i + 1;
            for (; j <= arr[i]; j++) {
                if (arr[j] > arr[i]) { // 在arr[i]到其正确位置的区间内，有比它大的数，不能分为一个块
                    break;
                }
            }
            if (j <= arr[i]) { // j <= arr[i]意味着：在arr[i]在其正确位置区间内，有比它大的数
                i = j; // 指针i移动到j，arr[i]成为当前最大的数字，下一次循环要去判断[j + 1,arr[i]]内有没有比它大的数
            } else {
                count++;
                i = arr[i] + 1;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        maxChunksToSorted t = new maxChunksToSorted();
        System.out.println(t.maxChunksToSorted(new int[]{1, 0, 2, 5, 3, 4, 6}));
    }
}
