package medium.LinkedList;

/**
 * @Time : 2020年2月6日14:27:58
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.ListNode;

import java.util.Stack;

/**
 * 给定两个非空链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储单个数字。将这两数相加会返回一个新的链表。
 * 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
 * <p>
 * 进阶:
 * 如果输入链表不能修改该如何处理？换句话说，你不能对列表中的节点进行翻转。
 * <p>
 * 示例:
 * 输入: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出: 7 -> 8 -> 0 -> 7
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers-ii
 */
public class addTwoNumbers {
    /**
     * 执行用时 :7 ms, 在所有 Java 提交中击败了56.85%的用户
     * 内存消耗 :45.7 MB, 在所有 Java 提交中击败了9.49%的用户
     * 不改变原列表 不翻转列表
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int flag = 0; // 用来表示是否进位
        Stack<Integer> s1 = new Stack<>();
        Stack<Integer> s2 = new Stack<>();
        for (ListNode temp = l1; temp != null; temp = temp.next) // 通过使用栈 使得链表元素表示个数字按个位十位百位的顺序取出
            s1.push(temp.val);
        for (ListNode temp = l2; temp != null; temp = temp.next)
            s2.push(temp.val);
        ListNode result = null;
        while (!s1.empty() || !s2.empty()) {
            int a1 = !s1.empty() ? s1.pop() : 0; // 取出当前位的数字
            int a2 = !s2.empty() ? s2.pop() : 0;
            int cur = a1 + a2 + flag;
            int single; // 表示相加之和进位后该位的数字
            if (cur < 10) {
                single = cur;
                flag = 0;
            } else {
                single = cur - 10;
                flag = 1;
            }
            ListNode temp = new ListNode(single);
            temp.next = result;
            result = temp;
        }
        if (flag == 1) {
            ListNode temp = new ListNode(1);
            temp.next = result;
            return temp;
        }
        return result;
    }

    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了85.27%的用户
     * 内存消耗 :44.3 MB, 在所有 Java 提交中击败了65.78%的用户
     */
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode result = null;
        ListNode tailA = reverse(l1);
        ListNode tailB = reverse(l2);
        int flag = 0;
        while (tailA != null || tailB != null || flag == 1) { // 一直相加到没有加数而且没有进位
            int single = 0;
            int a1 = tailA != null ? tailA.val : 0;
            int a2 = tailB != null ? tailB.val : 0;
            int sum = a1 + a2 + flag;
            if (sum >= 10) {
                flag = 1;
                single = sum - 10;
            } else {
                flag = 0;
                single = sum;
            }
            ListNode temp = new ListNode(single);
            temp.next = result;
            result = temp;
            tailA = tailA != null ? tailA.next : null;
            tailB = tailB != null ? tailB.next : null;
        }
        return result;
    }

    private ListNode reverse(ListNode node) {
        if (node == null || node.next == null)
            return node;
        ListNode p = reverse(node.next);
        node.next.next = node;
        node.next = null;
        return p;
    }
}
