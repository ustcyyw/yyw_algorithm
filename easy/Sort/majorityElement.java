package easy.Sort;

/**
 * @Time : 2020年3月13日10:57:17
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 *
 * 示例 1:
 * 输入: [3,2,3]
 * 输出: 3
 *
 * 示例 2:
 * 输入: [2,2,1,1,1,2,2]
 * 输出: 2
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/majority-element
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class majorityElement {
    /**
     * 执行用时 :14 ms, 在所有 Java 提交中击败了36.78%的用户
     * 内存消耗 :47.1 MB, 在所有 Java 提交中击败了5.07%的用户
     */
    public int majorityElement(int[] nums) {
        int n = nums.length / 2;
        Map<Integer, Integer> map = new HashMap<>();
        for(int i : nums)
            map.put(i, map.getOrDefault(i, 0) + 1);
        for(int key : map.keySet()){
            if(map.get(key) > n)
                return key;
        }
        return -1;
    }

    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了85.34%的用户
     * 内存消耗 :42 MB, 在所有 Java 提交中击败了35.52%的用户
     */
    private int[] num;
    private int n;
    public int majorityElement2(int[] nums) {
        num = nums;
        n = nums.length / 2;
        return partition(0, nums.length - 1);
    }

    private int partition(int lo, int hi){
        int i = lo + 1, lt = lo, gt = hi;
        int v = num[lo];
        while(i <= gt){
            if(num[i] > v) exch(i, gt--);
            else if(num[i] < v) exch(i++, lt++);
            else i++;
        }
        if(lt - lo > n) return partition(lo, lt - 1);
        else if (hi - gt > n) return partition(gt + 1, hi);
        else return num[lt];
    }

    private void exch(int i, int j) {
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }

    public static void main(String[] args) {
        majorityElement t = new majorityElement();
        int[] test = {6, 5, 5};
        System.out.println(t.majorityElement2(test));
    }
}
