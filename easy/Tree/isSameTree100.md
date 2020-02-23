# 100.相同的树

### 原题目

给定两个二叉树，编写一个函数来检验它们是否相同。
如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。

示例 1:
输入:     

 		  1         1
          / \       / \
         2   3     2   3
[1,2,3],   [1,2,3]
输出: true

示例 2:
输入:    

  	    1          1
          /           \
         2             2
[1,2],     [1,null,2]
输出: false

示例 3:
输入:   

​	       1         1
​          / \       / \
​         2   1     1   2
[1,2,1],   [1,1,2]
输出: false

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/same-tree)：https://leetcode-cn.com/problems/same-tree

----

### 两种解法

##### 1.递归

```java
public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;

        if (p.val != q.val) return false;
        else return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
```

思路分析：

* 两棵树相同意味着，树的结构一模一样且对应位置的结点的值相等。所以，根节点值相同，且左右子树相同才能判断一颗树相同。这就是递归的关系。
* 递归结束的条件，当两颗树均是`null`时他们相等，当两颗树其中之一为`null`另外一颗非`null`时他们不相等。
* 其它情况下，先判断当前节点的值是否相等，如果相等再判断当前节点的左右子树是否均相同。
* 时间复杂度 : $O(n)$，其中 n 是树的结点数，因为每个结点都访问一次。空间复杂度 : 最优情况（完全平衡二叉树）时为 $O(log(n))$，最坏情况下（完全不平衡二叉树）时为 $O(n)$，用于维护递归栈。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :37.2 MB, 在所有 Java 提交中击败了5.02%的用户


##### 2.迭代

```java
public boolean isSameTree2(TreeNode p, TreeNode q) {
        Queue<TreeNode> queue1 = new LinkedList<>();
        Queue<TreeNode> queue2 = new LinkedList<>();
        queue1.add(q);
        queue2.add(p);
        while (!queue1.isEmpty()){
            TreeNode tempQ = queue1.remove();
            TreeNode tempP = queue2.remove();
            if (tempQ == null && tempP == null) continue;
            if (tempQ == null || tempP == null) return false;
            if(tempP.val != tempQ.val) return false;
            queue1.add(tempQ.left);
            queue1.add(tempQ.right);
            queue2.add(tempP.left);
            queue2.add(tempP.right);
        }
        return true;
    }
```

思路分析：

* 树的相同，意味着结点位置一一对应，且对应位置的结点的值是相等的。所以只需要遍历每个结点，一旦有结点值不同就可以返回`false`。
* 使用DFS或者BFS均可以，只要每次一一对应地将结点放入队列（栈），再一一对应地取出比较。当树的结构不相同时，两颗树每次都入队（栈）相同数量的结点，取出相同数量的结点，必然导致值不同。当树结构相同，但值不同时，也能比较值得到结果。所以这个思路正确。
* 迭代和递归等价，所以时间复杂度与空间复杂度同方法一。

代码解释：

* 4~5两行，根结点为`null`也可以放入队列中，并且这种情况会在while循环的9-10行得到处理，如果均为`null`直接循环结束，由17行返回结果为`true`。有其一为`null`即答案为`false`，第10行直接返回。
* 11行，比较当前结点的值。
* 7-8行及12-15行，即为每次一一对应地将结点放入队列（栈），再一一对应地取出比较。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了6.17%的用户
* 内存消耗 :37 MB, 在所有 Java 提交中击败了5.02%