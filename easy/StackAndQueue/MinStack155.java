package easy.StackAndQueue;

/**
 * @Time : 时间
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。
 *
 * push(x) -- 将元素 x 推入栈中。
 * pop() -- 删除栈顶的元素。
 * top() -- 获取栈顶元素。
 * getMin() -- 检索栈中的最小元素。
 * 示例:
 *
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin();   --> 返回 -3.
 * minStack.pop();
 * minStack.top();      --> 返回 0.
 * minStack.getMin();   --> 返回 -2.
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/min-stack
 */

/**
 * 执行用时 :7 ms, 在所有 Java 提交中击败了86.67%的用户
 * 内存消耗 :40.3 MB, 在所有 Java 提交中击败了49.98%的用户
 */
public class MinStack155 {
    private int[] arr = null;
    private int min;
    private int n = 0;
    /** initialize your data structure here. */
    public MinStack155() {
        arr = new int[4];
        min = Integer.MAX_VALUE;
    }

    public void push(int x) {
        if(n == arr.length)
            resize(2 * n);
        arr[n++] = x;
        if(x < min)
            min = x;
    }

    public void pop() {
        if(arr.length > 4 && n <= arr.length / 4)
            resize(arr.length / 2);
        if(arr[n - 1] == min){ // 如果要删除的元素是最小元素 要更改记录的min
            int temp = Integer.MAX_VALUE;
            for(int i = 0; i < n - 1; i++){
                if(arr[i] < temp)
                    temp = arr[i];
            }
            min = temp;
        }
        n--;
    }

    public int top() {
        return arr[n - 1];
    }

    public int getMin() {
        return min;
    }

    private void resize(int newSize){
        int[] temp = new int[newSize];
        for(int i = 0; i < arr.length; i++)
            temp[i] = arr[i];
        this.arr = temp;
    }
}

/**
 * 我的第二解
 * 执行用时 :5 ms, 在所有 Java 提交中击败了99.97%的用户
 * 内存消耗 :40.3 MB, 在所有 Java 提交中击败了51.98%的用户
 */
class MinStack {
    private Node head;
    private int min;
    /** initialize your data structure here. */
    public MinStack() {
        min = Integer.MAX_VALUE;
    }

    public void push(int x) {
        Node temp = new Node(x);
        temp.next = head;
        head = temp;
        if(x < min)
            min = x;
    }

    public void pop() {
        if(head.val == min){
            int temp = Integer.MAX_VALUE;
            Node tempNode = head.next;
            while(tempNode != null){
                if(tempNode.val < temp)
                    temp = tempNode.val;
                tempNode = tempNode.next;
            }
            min = temp;
        }
        head = head.next;
    }

    public int top() {
        return head.val;
    }

    public int getMin() {
        return min;
    }

    class Node{
        public int val;
        public Node next;
        public Node(int val){
            this.val = val;
        }
    }
}