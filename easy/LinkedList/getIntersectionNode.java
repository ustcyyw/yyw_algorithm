package easy.linkedList;

/**
 * @Time : 2020年2月4日14:12:28
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.HashSet;

/**
 * 编写一个程序，找到两个单链表相交的起始节点。
 * 示例 1：
 * 输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3
 * 输出：Reference of the node with value = 8
 * 输入解释：相交节点的值为 8 （注意，如果两个列表相交则不能为 0）。从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,0,1,8,4,5]。在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
 *  
 * <p>
 * 示例 2：
 * 输入：intersectVal = 2, listA = [0,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
 * 输出：Reference of the node with value = 2
 * 输入解释：相交节点的值为 2 （注意，如果两个列表相交则不能为 0）。从各自的表头开始算起，链表 A 为 [0,9,1,2,4]，链表 B 为 [3,2,4]。在 A 中，相交节点前有 3 个节点；在 B 中，相交节点前有 1 个节点。
 *  
 * <p>
 * 示例 3：
 * 输入：intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
 * 输出：null
 * 输入解释：从各自的表头开始算起，链表 A 为 [2,6,4]，链表 B 为 [1,5]。由于这两个链表不相交，所以 intersectVal 必须为 0，而 skipA 和 skipB 可以是任意值。
 * 解释：这两个链表不相交，因此返回 null。
 * <p>
 * 注意：
 * <p>
 * 如果两个链表没有交点，返回 null.
 * 在返回结果后，两个链表仍须保持原有的结构。
 * 可假定整个链表结构中没有循环。
 * 程序尽量满足 O(n) 时间复杂度，且仅用 O(1) 内存。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/intersection-of-two-linked-lists
 */
public class getIntersectionNode {
    /**
     * 执行用时 :15 ms, 在所有 Java 提交中击败了9.82%的用户
     * 内存消耗 :43.2 MB, 在所有 Java 提交中击败了14.09%的用户
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null)
            return null;
        HashSet<ListNode> nodes = new HashSet<>();
        while (headA != null || headB != null) {
            if (headA != null) {
                if (!nodes.contains(headA)) {
                    nodes.add(headA);
                    headA = headA.next;
                } else return headA;
            } else {
                if (!nodes.contains(headB)) {
                    nodes.add(headB);
                    headB = headB.next;
                } else return headB;
            }
        }
        return null;
    }

    /**
     *执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :41.4 MB, 在所有 Java 提交中击败了17.447%的用户
     */
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        if (headA == null || headB == null)
            return null;
        int lengthA = 0, lengthB = 0;
        ListNode tempA = headA, tempB = headB; // 为了保持原表结果 需要temp
        while (tempA.next != null) { // 这里保证了temp 最后都是该链的最后一个结点
            lengthA++;
            tempA = tempA.next;
        }
        while (tempB.next != null) {
            lengthB++;
            tempB = tempB.next;
        }
        if (tempA != tempB) // 如果两个链表有交叉点 他们的链表尾一定是同一个结点
            return null;
        tempA = headA; // temp 复位
        tempB = headB;
        if (lengthA > lengthB) { // A链更长 所以让tempA先移动 确保移动之后两个temp在同一个起跑线
            for (int i = 0; i < lengthA - lengthB; i++)
                tempA = tempA.next;
        } else {
            for (int i = 0; i < lengthB - lengthA; i++)
                tempB = tempB.next;
        }
        while (true) {
            if (tempA == tempB)
                return tempA;
            tempA = tempA.next;
            tempB = tempB.next;
        }
    }
}
