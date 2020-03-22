package WeekContest;

/**
 * @Time : 2020年3月22日10:37:10
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给你一个整数数组 nums，请你返回该数组中恰有四个因数的这些整数的各因数之和。
 * 如果数组中不存在满足题意的整数，则返回 0 。
 *
 * 示例：
 * 输入：nums = [21,4,7]
 * 输出：32
 * 解释：
 * 21 有 4 个因数：1, 3, 7, 21
 * 4 有 3 个因数：1, 2, 4
 * 7 有 2 个因数：1, 7
 * 答案仅为 21 的所有因数的和。
 *  
 * 提示：
 *
 * 1 <= nums.length <= 10^4
 * 1 <= nums[i] <= 10^5
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/four-divisors
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class sumFourDivisors {
    public int sumFourDivisors(int[] nums) {
        int sum = 0;
        for (int i : nums)
            sum += getOne(i);
        return sum;
    }

    private int getOne(int num) {
        if((int)Math.sqrt(num) == Math.sqrt(num))
            return 0;
        int sum = 0;
        int count = 0;
        for (int i = 2; i <= (int)Math.sqrt(num); i++) {
            if (num % i == 0) {
                sum += i;
                sum += num / i;
                count++;
            }
            if (count > 1)
                return 0;
        }
        return count == 1 ? sum + 1 + num : 0;
    }
}
