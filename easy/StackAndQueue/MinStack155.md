# 实现常规时间找到最小元素的栈

### 原题

设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。

```java
push(x) -- 将元素 x 推入栈中。
pop() -- 删除栈顶的元素。
top() -- 获取栈顶元素。
getMin() -- 检索栈中的最小元素。
```

示例:

```java
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin();   --> 返回 -3.
minStack.pop();
minStack.top();      --> 返回 0.
minStack.getMin();   --> 返回 -2.
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/min-stack)：https://leetcode-cn.com/problems/min-stack

----

### 两种实现方式

##### 1.使用数组实现（我的第一解）

```java
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
```

思路分析：

* 要保证在常数时间内获得最小元素，需要使用一个变量去存放最小元素`private int min;`。这样就需要再入栈和弹出元素时更新变量`min`：在入栈时如果新加入的元素更小，更新之；在弹出元素时如果弹出的是最小元素，则遍历栈中元素找到新的最小值更新之。
* 采用数组实现，因为数组本身长度固定，所以需要再需要的时候创建新数组，以避免栈满或者占有不必要的过多空间。所以栈满时再入栈元素，先将数组增长为原来两倍；在数组中元素只使用四分之一时，将其减半。
* 数组的实现，关键就是栈顶元素索引的表示。这里使用元素个数`n`去想办法表示，当栈中有一个元素时`n=1`。而栈顶元素为`arr[0]`，所以栈顶元素均用`arr[n-1]`表示。元素入栈时`arr[n]`是没有存放元素的，入栈后栈的元素数量需要加一，所以入栈可以写为` arr[n++] = x;`出栈时，`n--`就使得当前栈顶访问不到，达到删除效果。
* 删除元素时，刚好删除最小元素时，时间复杂度为$O(n)$，其余为$O(1)$。在插入元素及删除元素时候可能需要改变数组长度，构造新的数字的时间复杂度为$O(n)$，这一点在频繁需要改变数组大小时会比较占用时间，这也是比方法二要慢一些的原因。

代码分析：

* 第7行，我将数组默认设为长度4，所以在20行，数组减半时，要满足数组的长度大于4`if(arr.length > 4 && n <= arr.length / 4)`。
* 22~29行，如果弹出的元素是最小的，遍历更新`min`。
* 41~46行，更改数组长度的辅助函数，需要先将原有元素copy到辅助数组中。

运行结果：
* 执行用时 :7 ms, 在所有 Java 提交中击败了86.67%的用户
* 内存消耗 :40.3 MB, 在所有 Java 提交中击败了49.98%的用户
##### 2.使用链表实现（我的第二解）

```java
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
```

思路分析：

* 使用链表实现，同样对于`min`的处理是一致。
* 维持链表的头结点`head`，入栈就是在链表头插入新结点，出栈就删除头结点。
* 入栈和弹出的时间复杂度与方法一一样，只是这里不需要进行数组的扩容啦。

代码分析：没啥可分析。

运行结果：
* 执行用时 :5 ms, 在所有 Java 提交中击败了99.97%的用户
* 内存消耗 :40.3 MB, 在所有 Java 提交中击败了51.98%的用户
