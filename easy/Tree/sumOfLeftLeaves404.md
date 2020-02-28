# 404.左叶子之和

### 原题
计算给定二叉树的所有左叶子之和。

示例：

```
    3
   / \
  9  20
    /  \
   15   7
```

在这个二叉树中，有两个左叶子，分别是 9 和 15，所以返回 24

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/sum-of-left-leaves)：https://leetcode-cn.com/problems/sum-of-left-leaves

### 两种解法

##### 1.递归（我的第一解）

```java
public int sumOfLeftLeaves(TreeNode root) {
        return sumOfLeftLeaves(root, false); // 只有一个根结点 则根结点不算左子叶
    }

    private int sumOfLeftLeaves(TreeNode root, boolean isLeftLeave) {
        if (root == null) return 0;
        if (root.left == null && root.right == null && isLeftLeave) return root.val;
        return sumOfLeftLeaves(root.left, true) + sumOfLeftLeaves(root.right, false);
    }
```

思路分析：

* 本题中指定了求和的为左叶子结点而不是所有的子页结点。在递归中，我们只知道当前节点的信息（其有没有左右子结点及其本身的值），我们并不能知道它是它爸爸结点的左子结点还是右子结点。
* 为了解决这个问题，我们可以在递归的时候，传入一个布尔值，`true`表示递归的下一个结点是左子结点。对于根结点，不算是左子结点，所以在对根结点调用时为`sumOfLeftLeaves(root, false)`。
* 递归的终止条件：
    * 当前结点为空，返回0（空结点当然值为0）
    * 当前结点是个子叶结点且是个左子叶`root.left == null && root.right == null && isLeftLeave`，返回其本身的值参与相加。
* 递归结构，`root`为根的树的所有左子叶结点的和为其左子树与右子树左子叶结点的和相加。
* 时间复杂度，由于每个结点只访问了一次，所以$O(n)$。空间复杂度，就是函数调用栈的空间，其$O(h)$,h为树的高度。对于平衡二叉树为logn，完全不平衡二叉树则是n。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :37.4 MB, 在所有 Java 提交中击败了5.10%的用户
##### 2.迭代（我的第二解）

```java
private int sumOfLeftLeaves2(TreeNode root){
        if(root == null) return 0;
        int res = 0;
        Stack<Pair<TreeNode, Boolean>> stack = new Stack<>();
        stack.add(new Pair<>(root, false));
        while (!stack.isEmpty()){
            Pair<TreeNode, Boolean> pair = stack.pop();
            TreeNode node = pair.getKey();
            if(node.left != null)
                stack.add(new Pair<>(node.left, true));
            if(node.right != null)
                stack.add(new Pair<>(node.right, false));
            if(node.left == null && node.right == null && pair.getValue())
                res += node.val;
        }
        return res;
    }
```

思路分析：

* 迭代不过是自己的栈来维持递归调用，本质上是一样的。我们同样要解决一个结点是否为左叶子结点这个问题，毕竟当一个结点从栈中取出时已经无法知道这个信息。
* 我们同样可以添加额外信息，所以使用`Pair<TreeNode, Boolean>`，使得这个类的`getValue()`返回值是布尔值，为`true`表示这个结点为左子结点。

代码解释：

* 遍历结点的方式模式化，第9第11行都表示这不是个叶子结点，所以只继续搜索，将其子结点及左右子结点的信息放入栈中。
* 第13行，三个条件说明这是左叶子结点，所以`res += node.val;`。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了14.25%的用户
* 内存消耗 :37.4 MB, 在所有 Java 提交中击败了5.10%的用户