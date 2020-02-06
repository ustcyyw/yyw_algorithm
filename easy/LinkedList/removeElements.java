package easy.linkedList;

/**
 * @Time : 2020年2月4日16:13:49
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 删除链表中等于给定值 val 的所有节点。
 * <p>
 * 示例:
 * <p>
 * 输入: 1->2->6->3->4->5->6, val = 6
 * 输出: 1->2->3->4->5
 */
public class removeElements {
    /**
     * 递归的做法很像在单链表中删去重复元素
     * <p>
     * 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :40.3 MB, 在所有 Java 提交中击败了5.09%的用户
     */
    public ListNode removeElements(ListNode head, int val) {
        if (head == null)
            return null;
        if (head.val == val) {
            head.next = removeElements(head.next, val);
            return head.next;
        } else {
            head.next = removeElements(head.next, val);
            return head;
        }
    }

    /**
     *执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :39.5 MB, 在所有 Java 提交中击败了51.36%的用户
     */
    public ListNode removeElements2(ListNode head, int val) {
        while (head != null && head.val == val) // 与删除重复元素不一样的地方！！第一个元素 开头几个元素都是指定值的情况，需要先把这个情况排除
            head = head.next;
        if (head == null)
            return null;
        ListNode temp = head;
        while (temp.next != null) {
            if (temp.next.val != val)
                temp = temp.next;
            else {
                ListNode cursor = temp.next;
                while (cursor.next != null && cursor.next.val == val)
                    cursor = cursor.next;
                temp.next = cursor.next;
            }
        }
        return head;
    }
    /**
     * 注意：当要删除的一个或多个节点位于链表的头部时，事情会变得复杂。
     * 可以通过哨兵节点去解决它，哨兵节点广泛应用于树和链表中，如伪头、伪尾、标记等，
     * 它们是纯功能的，通常不保存任何数据，其主要目的是使链表标准化，如使链表永不为空、永不无头、简化插入和删除。
     *
     * 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :40.2 MB, 在所有 Java 提交中击败了8.14%的用户
     */
    public ListNode removeElements3(ListNode head, int val){
        if(head == null)
            return null;
        ListNode fakeHead = new ListNode(-1);
        fakeHead.next = head;
        ListNode temp = fakeHead;
        while(temp.next != null){
            if(temp.next.val != val)
                temp = temp.next;
            else {
                ListNode cursor = temp.next;
                while(cursor.next != null && cursor.next.val == val)
                    cursor = cursor.next;
                temp.next = cursor.next;
            }
        }
        return fakeHead.next;
    }
}
