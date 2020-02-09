package medium.LinkedList;

import BaseClass.ListNode;

/**
 * @Time : 2020年2月7日20:30:09
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */
public class oddEvenList {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了29.82%的用户
     * 内存消耗 :37.6 MB, 在所有 Java 提交中击败了24.22%的用户
     */
    public ListNode oddEvenList(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode odd = new ListNode(-1), even = new ListNode(-1); // 为了将元素插入表尾，创建两链表尾。第一个元素的插入需要使用伪头
        ListNode oddHead = odd, evenHead = even;
        int count = 0;
        while(head != null){
            if(count % 2 == 0){
                ListNode temp = new ListNode(head.val);
                odd.next = temp;
                odd = temp;
            } else {
                ListNode temp = new ListNode(head.val);
                even.next = temp;
                even = temp;
            }
            count++;
            head = head.next;
        }
        odd.next = evenHead.next;
        return oddHead.next;
    }

    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :36 MB, 在所有 Java 提交中击败了76.96%的用户
     */
    public ListNode oddEvenList2(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode temp = null, oddTail = head, evenTail = head.next;
        while(evenTail != null && evenTail.next != null){ // 循环条件左边是对应有奇数个结点的链表，右边对应有偶数个结点的链表
            temp = evenTail.next; // 要交换到前面的奇数结点
            evenTail.next = temp.next;
            temp.next = oddTail.next;
            oddTail.next = temp;
            oddTail = oddTail.next; // 奇数偶数结点尾指针移动到尾部
            evenTail = evenTail.next;
        }
        return head;
    }

    /**
     * 标答，其实和我的答案2关键点是一样的：需要两个指针分别指奇数结点的尾和偶数结点的尾
     */
    public ListNode oddEvenList3(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }
        ListNode odd=head;
        ListNode even=head.next;
        ListNode tem=even;

        while(even!=null&&even.next!=null){
            odd.next=even.next;
            odd=odd.next;
            even.next=odd.next;
            even=even.next;
        }
        odd.next=tem;
        return head;
    }
}
