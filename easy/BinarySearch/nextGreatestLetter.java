package easy.BinarySearch;

/**
 * @Time : 2020年3月11日21:25:50
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个只包含小写字母的有序数组letters 和一个目标字母 target，寻找有序数组里面比目标字母大的最小字母。
 * 数组里字母的顺序是循环的。举个例子，如果目标字母target = 'z' 并且有序数组为 letters = ['a', 'b']，则答案返回 'a'。
 *
 * 示例:
 *
 * 输入:
 * letters = ["c", "f", "j"]
 * target = "a"
 * 输出: "c"
 *
 * 输入:
 * letters = ["c", "f", "j"]
 * target = "c"
 * 输出: "f"
 *
 * 输入:
 * letters = ["c", "f", "j"]
 * target = "d"
 * 输出: "f"
 *
 * 输入:
 * letters = ["c", "f", "j"]
 * target = "g"
 * 输出: "j"
 *
 * 输入:
 * letters = ["c", "f", "j"]
 * target = "j"
 * 输出: "c"
 *
 * 输入:
 * letters = ["c", "f", "j"]
 * target = "k"
 * 输出: "c"
 *
 *  注:
 * letters长度范围在[2, 10000]区间内。
 * letters 仅由小写字母组成，最少包含两个不同的字母。
 * 目标字母target 是一个小写字母。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-smallest-letter-greater-than-target
 */
public class nextGreatestLetter {
    /**
     * 题目中可能有多个重复的元素
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :40.9 MB, 在所有 Java 提交中击败了5.05%的用户
     */
    public char nextGreatestLetter(char[] letters, char target) {
        int n = letters.length;
        int lo = 0, hi = n - 1;
        while(lo <= hi){
            int mid = (hi - lo) / 2 + lo;
            if(letters[mid] <= target) lo = mid + 1;
            else if(letters[mid] > target) hi = mid - 1;
            else { // 这坑的会有多个重复的元素，一直要找到它后面的第一个不同元素
                for(int i = mid; ; i = (i + 1) % n){ // 如果i从mid + 1开始的话 mid刚好是最后一个索引 就会越界。
                    if(letters[i] != letters[mid])
                        return letters[i];
                }
            }
        }
        return letters[lo % n];
    }

    /**
     * 官方题解
     */
    public char nextGreatestLetter2(char[] letters, char target) {
        int n = letters.length;
        int lo = 0, hi = n - 1;
        while(lo <= hi){
            int mid = (hi - lo) / 2 + lo;
            if(letters[mid] <= target) lo = mid + 1; // 这个条件厉害 一定会使得lo落在target的下一个元素。
            else hi = mid - 1;
        }
        return letters[lo % n];
    }
}
