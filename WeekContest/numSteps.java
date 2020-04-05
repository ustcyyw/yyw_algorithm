package WeekContest;

/**
 * @Time : 2020年4月5日10:37:23
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */
public class numSteps {
    public int numSteps(String s) {
        char[] num = s.toCharArray();
        int count = 0;
        for(int i = num.length - 1; i >= 0; i--){
            if(i == 0 && num[i] == '1')
                return count;
            if(num[i] == '1'){
                count++;
                for(int j = i - 1; j >= 0; j--){
                    if(num[j] == '0'){
                        num[j] = '1';
                        break;
                    } else {
                        num[j] = '0';
                    }
                }
            }
            count++;
        }
        return count;
    }
}
