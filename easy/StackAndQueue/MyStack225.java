package easy.StackAndQueue;

/**
 * @Time : 2020年2月8日13:45:04
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.LinkedList;
import java.util.Queue;

/**
 * 使用队列实现栈的下列操作：
 * <p>
 * push(x) -- 元素 x 入栈
 * pop() -- 移除栈顶元素
 * top() -- 获取栈顶元素
 * empty() -- 返回栈是否为空
 * 注意:
 * <p>
 * 你只能使用队列的基本操作-- 也就是 push to back, peek/pop from front, size, 和 is empty 这些操作是合法的。
 * 你所使用的语言也许不支持队列。 你可以使用 list 或者 deque（双端队列）来模拟一个队列 , 只要是标准的队列操作即可。
 * 你可以假设所有操作都是有效的（例如, 对一个空的栈不会调用 pop 或者 top 操作）。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/implement-stack-using-queues
 */


/**
 * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
 * 内存消耗 :33.7 MB, 在所有 Java 提交中击败了96.32%的用户
 */
public class MyStack225 {
    private int tail;
    private Queue<Integer> q;

    /**
     * Initialize your data structure here.
     */
    public MyStack225() {
        q = new LinkedList<Integer>();
    }

    /**
     * Push element x onto stack.
     */
    public void push(int x) {
        tail = x;
        q.add(x);
    }

    /**
     * Removes the element on top of the stack and returns that element.
     * 出栈的时间复杂度为 n
     */
    public int pop() {
        int size = q.size();
        for (int i = 0; i < size - 2; i++)
            q.add(q.remove());
        tail = q.remove(); // 堆顶第二个元素 让下次调用top时使用
        q.add(tail);
        return q.remove();
    }

    /**
     * Get the top element.
     */
    public int top() {
        return tail;
    }

    /**
     * Returns whether the stack is empty.
     */
    public boolean empty() {
        return q.isEmpty();
    }
}

/**
 * 官方标答 与我的解法均摊来看并没有优劣 只不过这里插入时，时间复杂度为 n，出栈时间复杂度 1
 */
class MyStack {
    Queue<Integer> queue;

    /**
     * Initialize your data structure here.
     */
    public MyStack() {
        queue = new LinkedList<>();
    }

    /**
     * Push element x onto stack.
     */
    public void push(int x) {
        queue.add(x);
        int size = queue.size();
        while (size-- > 1) {
            queue.add(queue.poll());
        }
    }

    /**
     * Removes the element on top of the stack and returns that element.
     */
    public int pop() {
        return queue.remove();
    }

    /**
     * Get the top element.
     */
    public int top() {
        return queue.peek();
    }

    /**
     * Returns whether the stack is empty.
     */
    public boolean empty() {
        return queue.isEmpty();
    }
}