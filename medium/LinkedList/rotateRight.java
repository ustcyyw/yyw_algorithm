package medium.LinkedList;

/**
 * @Time : 2020年4月9日12:33:10
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.ListNode;

/**
 * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
 *
 * 示例 1:
 * 输入: 1->2->3->4->5->NULL, k = 2
 * 输出: 4->5->1->2->3->NULL
 * 解释:
 * 向右旋转 1 步: 5->1->2->3->4->NULL
 * 向右旋转 2 步: 4->5->1->2->3->NULL
 *
 * 示例 2:
 * 输入: 0->1->2->NULL, k = 4
 * 输出: 2->0->1->NULL
 * 解释:
 * 向右旋转 1 步: 2->0->1->NULL
 * 向右旋转 2 步: 1->2->0->NULL
 * 向右旋转 3 步: 0->1->2->NULL
 * 向右旋转 4 步: 2->0->1->NULL
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/rotate-list
 */
public class rotateRight {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :39.4 MB, 在所有 Java 提交中击败了5.13%的用户
     */
    public ListNode rotateRight(ListNode head, int k) {
        if(k == 0 || head == null)
            return head;
        int length = 1;
        ListNode tail = head;
        for (; tail.next != null; tail = tail.next)
            length++;
        int temp = k % length;
        if(temp == 0) return head;
        ListNode pre = head;
        for(int i = 0; i < length - temp - 1; i++)
            pre = pre.next;
        ListNode res = pre.next;
        pre.next = null;
        tail.next = head;
        return res;
    }
}
