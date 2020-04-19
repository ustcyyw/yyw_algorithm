
import java.util.HashMap;
import java.util.Map;

/**
 * @Time : 2020年4月19日11:08:10
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */
public class minNumberOfFrogs {
    private static Map<Character, Integer> map = new HashMap<>(); // 某字符在count[]数组中的索引
    static {
        map.put('r', 1);
        map.put('o', 2);
        map.put('a', 3);
    }

    public int minNumberOfFrogs(String croakOfFrogs) {
        int res = 0, cur = 0;
        int[] count = new int[4]; // 分别记数 c r o a 结尾的字符串的个数
        for (char c : croakOfFrogs.toCharArray()) {
            if (c == 'c'){
                count[0] += 1;
                cur++;
                res = Math.max(cur, res);
            }
            else if (c == 'k'){
                count[3] -= 1;
                cur--;
            }
            else {
                int index = map.get(c);
                count[index] += 1;
                count[index - 1] -= 1;
            }
            for(int i : count)
                if(i < 0)
                    return -1;
        }
        return cur > 0 ? -1 : res;
    }
}
