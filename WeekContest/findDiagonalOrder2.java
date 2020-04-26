
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Time : 2020年4月26日10:52:46
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给你一个列表 nums ，里面每一个元素都是一个整数列表。请你依照下面各图的规则，按顺序返回 nums 中对角线上的整数。
 *
 * 示例 1：
 * 输入：nums = [[1,2,3],[4,5,6],[7,8,9]]
 * 输出：[1,4,2,7,5,3,8,6,9]
 *
 * 示例 2：
 * 输入：nums = [[1,2,3,4,5],[6,7],[8],[9,10,11],[12,13,14,15,16]]
 * 输出：[1,6,2,8,7,3,9,4,12,10,5,13,11,14,15,16]
 *
 * 示例 3：
 * 输入：nums = [[1,2,3],[4],[5,6,7],[8],[9,10,11]]
 * 输出：[1,4,2,5,3,8,6,9,7,10,11]
 *
 * 示例 4：
 * 输入：nums = [[1,2,3,4,5,6]]
 * 输出：[1,2,3,4,5,6]
 *  
 *
 * 提示：
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i].length <= 10^5
 * 1 <= nums[i][j] <= 10^9
 * nums 中最多有 10^5 个数字。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/diagonal-traverse-ii
 */
public class findDiagonalOrder {
    /**
     * 42ms
     */
    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        int count = 0;
        for(List l : nums)
            count += l.size();
        int[] res = new int[count];
        count = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0,0, nums.get(0).get(0)});
        while (!queue.isEmpty()){
            int[] item = queue.remove();
            int x = item[0], y = item[1];
            res[count++] = item[2];
            add(queue, x + 1, y , nums);
            add(queue, x, y + 1, nums);
        }
        return res;
    }

    private void add(Queue<int[]> queue, int x, int y, List<List<Integer>> nums){
        if(nums.size() > x && nums.get(x).size() > y && nums.get(x).get(y) > 0){
            queue.add(new int[]{x, y, nums.get(x).get(y)});
            nums.get(x).set(y, -1);
        }
    }
}
