package medium.LinkedList;

/**
 * @Time : 2020年2月5日22:45:45
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.ListNode;

/**
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 * <p>
 * 示例：
 * 给定一个链表: 1->2->3->4->5, 和 n = 2.
 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 * 说明：
 * 给定的 n 保证是有效的。
 * <p>
 * 进阶：
 * 你能尝试使用一趟扫描实现吗？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list
 */
public class removeNthFromEnd {
    /**
     * 执行用时 :
     * 1 ms, 在所有 Java 提交中击败了64.53%的用户
     * 内存消耗 :35 MB, 在所有 Java 提交中击败了15.79%的用户
     * 这种方法最坏遍历了两次列表 时间复杂度 n， 只使用了一个int和一个伪头 空间复杂度 1
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 要特别注意 如果要删的结点是头结点这种情况， 可以使用伪头结点
        ListNode fakeHead = new ListNode(-1);
        fakeHead.next = head;
        int count = 0;
        for (; head != null; head = head.next) // 得到链表的长度
            count++;
        ListNode temp = fakeHead;
        for (int i = 0; i < count - n; i++)
            temp = temp.next;
        temp.next = temp.next.next;
        return fakeHead.next;
    }

    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了64.53%的用户
     * 内存消耗 :34.9 MB, 在所有 Java 提交中击败了43.16%的用户
     */
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode fakeNode = new ListNode(-1);
        fakeNode.next = head;
        ListNode fast = fakeNode; // 使用双指针 间隔为n 在前面的指针到达终结点的时候，后一个指针刚好距离终结点n，也就是倒数第n个结点的上一个结点
        for(int i = 0; i < n; i++)
            fast = fast.next;
        ListNode slow = fakeNode;
        while(fast.next != null){
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return fakeNode.next;
    }
}
