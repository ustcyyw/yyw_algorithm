# 345. 反转字符串中的元音字母

### 原题
编写一个函数，以字符串作为输入，反转该字符串中的元音字母。

示例 1:
输入: "hello"
输出: "holle"

示例 2:
输入: "leetcode"
输出: "leotcede"

说明:
元音字母不包含字母"y"。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/reverse-vowels-of-a-string)：https://leetcode-cn.com/problems/reverse-vowels-of-a-string

### 两种解法

##### 1.双指针（StringBuild使用）

```java
public static char[] vowels = {'a','A','e','E','i','I','o','O','u','U'};
public String reverseVowels(String s) {
        int n = s.length();
        if(n == 0)
            return "";
        StringBuilder res = new StringBuilder(s);
        for(int i = -1, j = n; ;){
            while(!isVowel(res.charAt(++i))){
                if(i == n - 1)
                    break;
            }
            while(!isVowel(res.charAt(--j))){
                if(j == 0)
                    break;
            }
            if(i >= j)
                break;
            exch(res, i, j);
        }
        return res.toString();
    }

    private void exch(StringBuilder sb, int i, int j){
        char temp = sb.charAt(i);
        sb.setCharAt(i, sb.charAt(j));
        sb.setCharAt(j, temp);
    }

    private boolean isVowel(char c){
        for(char v : vowels)
            if(c == v)
                return true;
        return false;
    }
```

思路分析：

* 从给的示例来看，要完成的功能就是从字符串的左边找到一个元音字母，从字符串的右边找到一个元音字母，然后这两个字母交换。直到没有这样可以进行交换的一组元音字母。
* 这个过程很像快速排序中的某一次切分。写法类似。
* 使用双指针`i`，`j`分别从字符串的左右开始查找元音字符。在内循环中：
    * 左边找到一个元音字符（当前字符不是元音字母则指针`i`右移），右边找到一个元音字符，然后进行交换。（对应到切分中就是从数组左边找一个别切分点大或者相等的元素，从右边找一个比切分点小或者相等的元素，然后交换）
    * 内循环要防止越界，所以当`i == n - 1`时`break`；当`j == 0`时`break`。
    * 外循环停止条件当两个指针相交后即不再进行交换，直接结束。
* 进行交换的辅助函数为`void exch(StringBuilder sb, int i, int j)`，第二三个参数即要进行交换的字符的索引，函数中运用了`StringBuilder`的方法`setCharAt`
* 判断是否为元音字母，先在类变量`char[] vowels`中枚举出所有元音字母，然后由辅助函数`boolean isVowel(char c)`对参数`c`进行判断，线性遍历如果数组中有相等元素，就返回`true`。
* 时间复杂度为$O(n)$，空间复杂度为$O(n)$。

运行结果：
* 执行用时 :5 ms, 在所有 Java 提交中击败了74.79%的用户
* 内存消耗 :42.1 MB, 在所有 Java 提交中击败了5.08%的用户

##### 2.双指针（数组的使用）

```java
public static char[] vowels = {'a','A','e','E','i','I','o','O','u','U'};
public String reverseVowels2(String s) {
        int n = s.length();
        if(n == 0)
            return "";
        char[] chars = s.toCharArray();
        for(int i = -1, j = s.length(); ;){
            while(!isVowel(chars[++i])){
                if(i == n - 1)
                    break;
            }
            while(!isVowel(chars[--j])){
                if(j == 0)
                    break;
            }
            if(i >= j)
                break;
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return String.valueOf(chars);
    }

    private boolean isVowel(char c){
        for(char v : vowels)
            if(c == v)
                return true;
        return false;
    }
```

思路分析：

* 逻辑与方法一一致。这里唯一不同的就是改用字符数组来表示字符串，用数组元素的交换代替`StringBuilder`的`setCharAt`。最后返回时使用`String.valueOf(chars)`得到字符串。

运行结果：
* 执行用时 :3 ms, 在所有 Java 提交中击败了96.67%的用户
* 内存消耗 :41.9 MB, 在所有 Java 提交中击败了5.08%的用户