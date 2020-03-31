/*
请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
*/

class Solution {
    public String replaceSpace(String s) {
        StringBuilder builder = new StringBuilder(s);
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == ' ') {
                builder.replace(i, i+1, "%20");
            }
        }
        return builder.toString();
    }
}

/*
改进：从后往前移动字符，时间复杂度为O(n)，字符串中的每个字符只用移动一次
*/
