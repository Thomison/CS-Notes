/*
请实现一个函数用来匹配包含'. '和'*'的正则表达式。

模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（含0次）。

在本题中，匹配是指字符串的所有字符匹配整个模式。

例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但与"aa.a"和"ab*a"均不匹配。
*/
class Solution {
    
    public boolean isMatch(String s, String p) {
        
        /* initial */
        int s_len = s.length(), p_len = p.length();
        boolean[][] dp = new boolean[s_len + 1][p_len + 1];
        char[] s_chs = s.toCharArray(), p_chs = p.toCharArray();
        
        dp[0][0] = true;
        for (int j = 1; j <= p_len; j++) {
            char p_ch = p_chs[j - 1];
            if (p_ch == '*') {          //因为要匹配空字符串，*肯定匹配0个上一个字符
                dp[0][j] = dp[0][j - 2];
            }
        }
        
        /* construct */
        for (int i = 1; i <= s_len; i++) {            
            char s_ch = s_chs[i - 1];       //s的当前字符
            
            for (int j = 1; j <= p_len; j++) {               
                char p_ch = p_chs[j - 1];       //p的当前字符
                
                if (s_ch == p_ch || p_ch == '.') {  //p和s当前字符匹配 结果取决于剩余部分是否匹配  
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (p_ch == '*'){
                    char p_ch_pre = p_chs[j - 2];   //p的上一个字符

                    if (s_ch != p_ch_pre && p_ch_pre != '.') {   //p的上一个字符不能匹配s的当前字符，如匹配#a和#b*
                        dp[i][j] = dp[i][j - 2];
                    } else {                            //如匹配#b和#b*：b*可以匹配0个或多个b
                        dp[i][j] = dp[i - 1][j] || dp[i][j - 1] || dp[i][j - 2];
                    }
                    
                } else {            //p和s当前字符不匹配 结果必然不匹配
                    dp[i][j] = false;
                }
            }
        }
        
        return dp[s_len][p_len];
    }
    
}
