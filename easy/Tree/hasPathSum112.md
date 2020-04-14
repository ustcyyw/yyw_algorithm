# 112. 路径总和

### 原题
给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
说明: 叶子节点是指没有子节点的节点。

示例: 
给定如下二叉树，以及目标和 sum = 22，

```
              5
             / \
            4   8
           /   / \
          11  13  4
         /  \      \
        7    2      1
```

返回 true, 因为存在目标和为 22 的根节点到叶子节点的路径 5->4->11->2。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/path-sum)：https://leetcode-cn.com/problems/path-sum

### 三种解法

##### 1.递归（我的第一解）

```java
public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) return false;
        return pathSum(root, sum, 0);
    }

    private boolean pathSum(TreeNode root, int sum, int curSum) {
        curSum += root.val;
        boolean result;
        if (root.left == null && root.right == null) // 没有左右子结点，才是叶子结点，此时才判断路径上值之和与目标sum是否相等
            result = sum == curSum;
        else if (root.left != null && root.right != null) // 左右子结点都有，左右子树中有一条路径添加上目前路径可以得到正确结果即可
            result = pathSum(root.left, sum, curSum) || pathSum(root.right, sum, curSum);
        else if (root.left != null) // 只有左子树，路径继续往左走
            result = pathSum(root.left, sum, curSum);
        else
            result = pathSum(root.right, sum, curSum);
        return result;
    }
```

思路分析：

* 题目中没有规定结点值为正，所以在从根走到叶子结点的过程中，如果路径和已经大于指定的`sum`，也要继续往下走，因为后续的结点值可能为负。所以我们需要遍历每一条从根到叶子结点的路径，除非某一条路径和为`sum`，那么可以返回`true`，否则必须遍历每一条从根到叶子结点的路径都没有找到满足要求的路径才能下结论为`false`
* 这是一个需要对树进行dfs的问题，这里先使用递归完成，定义辅助函数`boolean pathSum(TreeNode root, int sum, int curSum)`。
    * 返回值表示，从根结点经过当前结点到达叶子结点是否存在路径和为`sum`。
    * 第三个参数`curSum`表示到达当前结点的父节点的路径和，所以`curSum += root.val;`就是从根结点到当前结点的路径和。
    * 如果当前结点是叶子结点`root.left == null && root.right == null`，就判断`curSum == sum`是否成立，并返回判断结果（已经到路径最深处了哦，该回头啦😁）
    * 否则，如果当前结点有左子树和右子树都存在，则往哪边走最终能找到一条路径和为`sum`均代表从根结点经过当前结点到达叶子结点存在路径和为`sum`。结合参数定义和函数定义，这种情况下返回`result = pathSum(root.left, sum, curSum) || pathSum(root.right, sum, curSum);`
    * 如果只有左子树或者只有右子树，往存在的子树继续向下走即可`result = pathSum(root.left, sum, curSum); / pathSum(root.right, sum, curSum);`。
* 由于辅助函数没有关于`x == null`的检查，所以主函数先检查根是否为空，然后调用辅助函数返回结果即可。
* 时间复杂度为$O(n)$，因为遍历了每一个结点。空间复杂度为树高成正比。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :39.3 MB, 在所有 Java 提交中击败了8.60%的用户

##### 2.迭代（我的第二解）

```java
public boolean hasPathSum2(TreeNode root, int sum) {
        if (root == null) return false;
        Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
        stack.push(new Pair<>(root, root.val));
        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> curPair = stack.pop();
            TreeNode curNode = curPair.getKey();
            int curSum = curPair.getValue();
            if (curNode.left == null && curNode.right == null) {
                if (curSum == sum) return true;
            }
            if (curNode.left != null)
                stack.add(new Pair<>(curNode.left, curSum + curNode.left.val));
            if (curNode.right != null)
                stack.add(new Pair<>(curNode.right, curSum + curNode.right.val));
        }
        return false;
    }
```

思路分析：

* 方法一中已经解释过，这个问题需要dfs，这里用自己创建的栈代替函数调用栈，但是要传递截止当前结点的路径和信息，所以栈的元素为`Pair<TreeNode, Integer>`，`Pair`的值为截止此结点的路径和。
* 接下来就是标准的dfs写法，不过注意当从栈弹出的当前结点是叶子结点`curNode.left == null && curNode.right == null`，时如果`curSum == sum`，已经找到一条满足题意的路径，这个时候直接返回`true`就不需要继续遍历了。
* 时间复杂度为$O(n)$，因为遍历了每一个结点。空间复杂度与栈中元素个数成正比。

运行结果：
* 执行用时 :3 ms, 在所有 Java 提交中击败了6.90%的用户
* 内存消耗 :39.2 MB, 在所有 Java 提交中击败了13.39%的用户

##### 3.递归（官方解）

```java
public boolean hasPathSum3(TreeNode root, int sum) {
        if (root == null)
            return false;

        sum -= root.val; // 函数内部的sum指的是 剩下的结点还需要加和到多少
        if ((root.left == null) && (root.right == null))
            return sum == 0;
        return hasPathSum(root.left, sum) || hasPathSum(root.right, sum);
    }
```

思路分析：

* 官方解的思路只需要弄明白第二个参数的意义即可，第二个参数表示路径到达此结点（不包括此结点）还需要多少才能加和为制定的目标值。
* 所以，`sum -= root.val`后，变量`sum`表示，路径到达此结点（包括此结点）还需要多少才能加和为制定的目标值
    * 如果当前结点是叶子结点，判断`sum`是否为0（剩余需要加和为0，说明到叶子结点刚好加和为制定的目标值）
    * 否则分别往左右子树继续寻找路径`return hasPathSum(root.left, sum) || hasPathSum(root.right, sum);`。这里不需要判断非空，函数开头已经进行了处理。

