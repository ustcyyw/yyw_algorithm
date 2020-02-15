package medium.StackAndQueue;

/**
 * @Time : 2020年2月9日13:13:11
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.Stack;

/**
 * 根据每日 气温 列表，请重新生成一个列表，对应位置的输入是你需要再等待多久温度才会升高超过该日的天数。
 * 如果之后都不会升高，请在该位置用 0 来代替。
 * <p>
 * 例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的输出应该是 [1, 1, 4, 2, 1, 1, 0, 0]。
 * 提示：气温 列表长度的范围是 [1, 30000]。每个气温的值的均为华氏度，都是在 [30, 100] 范围内的整数。
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/daily-temperatures
 */
public class dailyTemperatures739 {
    /**
     * 执行用时 :243 ms, 在所有 Java 提交中击败了19.25%的用户
     * 内存消耗 :42.4 MB, 在所有 Java 提交中击败了57.45%的用户
     */
    public int[] dailyTemperatures(int[] T) {
        int[] result = new int[T.length];
        for (int i = 0; i < T.length; i++) {
            for (int j = i + 1; j < T.length; j++) {
                if (T[j] > T[i]) {
                    result[i] = j - i;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 官方标答
     * 执行用时 :75 ms, 在所有 Java 提交中击败了49.43%的用户
     * 内存消耗 :43.7 MB, 在所有 Java 提交中击败了14.47%的用户
     */
    public int[] dailyTemperatures2(int[] T) {
        int[] result = new int[T.length];
        Stack<Integer> stack = new Stack<>(); // 用来存放有效温度的索引
        for (int i = T.length - 1; i >= 0; --i) {
            while (!stack.isEmpty() && T[i] >= T[stack.peek()]) // 将冗余信息删去，如果靠后的日子温度比当前低，他完全没有意义记录
                stack.pop();
            result[i] = stack.isEmpty() ? 0 : stack.peek() - i; // 栈中无元素说明当日之后温度没有比其高的！
            stack.push(i);
        }
        return result;
    }

    /**
     * 别人标答
     * 执行用时 :7 ms, 在所有 Java 提交中击败了86.58%的用户
     * 内存消耗 :42.2 MB, 在所有 Java 提交中击败了67.20%的用户
     */
    public int[] dailyTemperatures3(int[] T) {
        int[] result = new int[T.length];
        for (int i = T.length - 2; i >= 0; i--) {
            // result[j]的意义是 j这一天之后多少天的温度比j这天高 result[j]+j即为比j温度高的最邻近的一天。这样就省略了比较比j这一天温度低的日子
            for (int j = i + 1; j < T.length; j += result[j]) { // j的值代表温度可能比i这天温度高的 天数
                if (T[i] < T[j]) {
                    result[i] = j - i;
                    break;
                } else if (result[j] == 0) { // 在i这一天温度>=j这一天温度时，如果result[j]==0意味着在j这天之后没有哪天温度比其更高
                    result[i] = 0; // 所以i这一天之后也没有任何温度比其高了 result[i]置为0
                    break;
                }
            }
        }
        return result;
    }
}
