# 1071. 字符串的最大公因子

### 原题
对于字符串 S 和 T，只有在 S = T + ... + T（T 与自身连接 1 次或多次）时，我们才认定 “T 能除尽 S”。
返回最长字符串 X，要求满足 X 能除尽 str1 且 X 能除尽 str2。

示例 1：
输入：str1 = "ABCABC", str2 = "ABC"
输出："ABC"

示例 2：
输入：str1 = "ABABAB", str2 = "ABAB"
输出："AB"

示例 3：
输入：str1 = "LEET", str2 = "CODE"
输出：""

提示：
1 <= str1.length <= 1000
1 <= str2.length <= 1000
`str1[i] 和 str2[i] `为大写英文字母

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/greatest-common-divisor-of-strings)：https://leetcode-cn.com/problems/greatest-common-divisor-of-strings

### 两种解法

##### 1.直接模拟

```java
public String gcdOfStrings(String str1, String str2) {
        int n1 = str1.length(), n2 = str2.length();
        char[] chars1 = str1.toCharArray(), chars2 = str2.toCharArray();
        int max = n1 > n2 ? maxProduct(n1, n2) : maxProduct(n2, n1);
        while(max >= 1){
            if(n1 % max == 0 && n2 % max == 0){
                if(isValid(chars1, chars1, n1, max) && isValid(chars2, chars1, n2, max))
                    return str1.substring(0, max);
            }
            max--;
        }
        return "";
    }

    private boolean isValid(char[] chars, char[] target, int n, int length){
        for(int i = 0, j = 0; i < n; i++, j = (j + 1) % length){
            if(chars[i] != target[j])
                return false;
        }
        return true;
    }

    private int maxProduct(int p, int q){
        int r = p % q;
        if(r == 0) return q;
        else return maxProduct(q, r);
    }
```

思路分析：

* 首先如何确定子串T可以整除S。对于S可以由多个T组成。假定T的长度为`length`，那么S中的子串`[0, length - 1]`，`[length, 2*length - 1]`……都与T相等。
    * 定义函数`boolean isValid(char[] chars, char[] target, int n, int length)`，第一个参数为要验证的字符串S，第四个参数表示T的长度，第二个参数索引0到`length - 1`的字符构成T。第三个参数为S的长度。
    * 内部比较字串的相等通过遍历完成。指针`j`指向T的每一个字符，`j`需要取模循环`j = (j + 1) % length`
    * 如果S中每个字符都与T中对应的字符相等，循环结束返回`true`，否则过程中只要出现不相等，就直接返回`false`。
* 主函数中，首先从两个字符串长度的最大公约数`max`找到。通过辗转相除法来完成。
* 然后判断长度为`max`的子串是否可以整除`str1`与`str2`。
    * 首先保证`n1 % max == 0 && n2 % max == 0`（`max`可以分别整除两个字符串的长度，否则当然不能整出字符串咯）
    * 然后调用辅助函数`isValid`进行判断。
    * 如果该长度的子串可以整出，则返回`str1.substring(0, max);`
    * 如果不可以就将长度减少1，知道长度为0，循环结束
* 循环结束都没有返回结果，说明长度为1的子串都没办法同时整除`str1`与`str2`。返回空字符串。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了93.98%的用户
* 内存消耗 :38.7 MB, 在所有 Java 提交中击败了8.80%的用户

##### 2.官方题解的超强技巧

```java
public String gcdOfStrings2(String str1, String str2) {
        String temp = str1 + str2;
        if(!temp.equals(str2 + str1))
            return "";
        int p = str1.length(), q = str2.length();
        if(p > q)
            return str1.substring(0, maxProduct(p, q));
        else
            return str1.substring(0, maxProduct(q, p));
    }

private int maxProduct(int p, int q){
        int r = p % q;
        if(r == 0) return q;
        else return maxProduct(q, r);
    }
```

思路分析：

* 本题技巧性很强，不看官方完全没有想到，而且证明有些复杂，这里简单描述后。当作结论稍微记下。
* 如果 str1 + str2 与 str2 + str1 相等 则两个字符串长度的最大公约数就是解。
* 必要性：
    * str1 = m * T, str2 = n * T
    * str1 + str2 = (m + n) * T, str2 + str1 = (n + m) * T。对于字符串来说相等。
* 充分性：
    * 比如一个长度为8，一个长度为4，那假设最优解是2，不是4，对于长度为8的可以理解成，要有4个这样的前缀，长度为4的要有2个这样的前缀。
    * 然后这时候4个这样的前缀，和2个这样的前缀，实际上又可以有共同的约数2来合并，而且不合并的话实际上就不是最优的长度。
    * 无论两个数有多少约数的情况都可以这样类推合并。因此最优解就是在最大公约数，当然，前提你要确定是有解的。
* 所以进行字符串拼接 str1 + str2与str2 + str1不相等说明没有可以整除的子串，直接返回空字符串。否则就找到两个字符串长度的最大公约数，然后返回`str1.substring(0, max)`

运行结果：

* 执行用时 :1 ms, 在所有 Java 提交中击败了93.98%的用户
* 内存消耗 :38.7 MB, 在所有 Java 提交中击败了8.80%的用户