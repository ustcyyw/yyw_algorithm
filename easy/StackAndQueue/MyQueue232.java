package easy.StackAndQueue;

/**
 * @Time : 2020年2月8日13:05:35
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.Stack;

/**
 * 使用栈实现队列的下列操作：
 * <p>
 * push(x) -- 将一个元素放入队列的尾部。
 * pop() -- 从队列首部移除元素。
 * peek() -- 返回队列首部的元素。
 * empty() -- 返回队列是否为空。
 * 示例:
 * <p>
 * MyQueue queue = new MyQueue();
 * <p>
 * queue.push(1);
 * queue.push(2);
 * queue.peek();  // 返回 1
 * queue.pop();   // 返回 1
 * queue.empty(); // 返回 false
 * 说明:
 * <p>
 * 你只能使用标准的栈操作 -- 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
 * 你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
 * 假设所有操作都是有效的 （例如，一个空的队列不会调用 pop 或者 peek 操作）。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/implement-queue-using-stacks。
 */

/**
 * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
 * 内存消耗 :33.9 MB, 在所有 Java 提交中击败了61.98%的用户
 */
public class MyQueue232 {
    private Stack<Integer> in = null;
    private Stack<Integer> out = null;

    /**
     * Initialize your data structure here.
     */
    public MyQueue232() {
        in = new Stack<>();
        out = new Stack<>();
    }

    /**
     * Push element x to the back of queue.
     */
    public void push(int x) {
        in.push(x);
    }

    /**
     * Removes the element from in front of queue and returns that element.
     */
    public int pop() {
        if (out.empty()) {
            int size = in.size();
            for (int i = 0; i < size; i++)
                out.push(in.pop());
        }
        return out.pop();
    }

    /**
     * Get the front element.
     */
    public int peek() {
        if (out.empty()) {
            int size = in.size();
            for (int i = 0; i < size; i++)
                out.push(in.pop());
        }
        return out.peek();
    }

    /**
     * Returns whether the queue is empty.
     */
    public boolean empty() {
        return in.empty() && out.empty();
    }

    public static void main(String[] args) {
        MyQueue232 mq = new MyQueue232();
        mq.push(1);
        mq.push(2);
        System.out.println(mq.peek());
        System.out.println(mq.pop());
    }
}
