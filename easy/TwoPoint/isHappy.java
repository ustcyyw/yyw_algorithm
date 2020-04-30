package easy.TwoPoint;

/**
 * @Time : 2020年4月30日00:28:29
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import java.util.HashSet;
import java.util.Set;

/**
 * 编写一个算法来判断一个数 n 是不是快乐数。
 * 「快乐数」定义为：对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和，
 * 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。如果 可以变为  1，那么这个数就是快乐数。
 * 如果 n 是快乐数就返回 True ；不是，则返回 False 。
 * <p>
 * 示例：
 * 输入：19
 * 输出：true
 * 解释：
 * 12 + 92 = 82
 * 82 + 22 = 68
 * 62 + 82 = 100
 * 12 + 02 + 02 = 1
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/happy-number
 */
public class isHappy {
    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了51.98%的用户
     * 内存消耗 :36.6 MB, 在所有 Java 提交中击败了8.33%的用户
     */
    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        set.add(n);
        while (true){
            n = create(n);
            if(n == 1)
                return true;
            if(set.contains(n))
                return false;
            set.add(n);
        }
    }

    private int create(int n) {
        int sum = 0;
        while (n != 0){
            sum += n % 10 * (n % 10);
            n /= 10;
        }
        return sum;
    }

    /**
     * 判断循环使用快慢指针指针，如同链表成环的判断。
     * 执行用时 :1 ms, 在所有 Java 提交中击败了99.89%的用户
     * 内存消耗 :36.4 MB, 在所有 Java 提交中击败了8.33%的用户
     */
    public boolean isHappy2(int n){
        int slow = n, fast = create(n);
        while(fast != 1){
            if(fast == slow) return false; // 快指针等于慢指针，这个说明在计算过程中循环了，数字之间构成了环。
            fast = create(create(fast));
            slow = create(slow);
        }
        return true;
    }
}
