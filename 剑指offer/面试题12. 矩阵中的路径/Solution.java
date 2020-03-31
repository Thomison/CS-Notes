/*
请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。

路径可以从矩阵中的任意一格开始，每一步可以在矩阵中向左、右、上、下移动一格。

如果一条路径经过了矩阵的某一格，那么该路径不能再次进入该格子。

例如，在下面的3×4的矩阵中包含一条字符串“bfce”的路径（路径中的字母用加粗标出）。

[["a","b","c","e"],
["s","f","c","s"],
["a","d","e","e"]]

但矩阵中不包含字符串“abfb”的路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入这个格子。
*/
class Solution {
    final int[][] move = {{-1,0}, {1,0}, {0,-1}, {0,1}};
    
    public boolean exist(char[][] board, String word) {
        int row = board.length, col = board[0].length;
        boolean[][] visit = new boolean[row][col];
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                
                if (search(board, i, j, word, 0, visit))  //尝试搜索目标单词
                    return true;
            }
        }
        
        return false;
    }
    
    /* 在网格中搜索目标单词 dfs */
    private boolean search(char[][] board, int i, int j, String word, int cur, boolean[][] visit) {
        int row = board.length, col = board[0].length;
        
        if (board[i][j] != word.charAt(cur)) {   //不能形成子串的末尾字符
            return false;
        } else if (cur == word.length() - 1) {   //能形成字符串的末尾字符(搜索成功的标志)
            return true;
        }
            
        
        visit[i][j] = true;     
        for (int m = 0; m < move.length; m++) {     //尝试在四个方向上进行搜索
            int nexti = i + move[m][0], nextj = j + move[m][1];
            
            if (nexti < 0 || nexti >= row || nextj < 0 || nextj >= col) {   //超出边界
                continue;
            } else if (visit[nexti][nextj]) {       //被访问过的字符，即已作为目前搜索的子串字符
                continue;
            }
            
            if (search(board, nexti, nextj, word, cur + 1, visit)) 
                return true;
            
        }
        
        visit[i][j] = false;    //回溯 恢复访问状态 方便下次搜索
        return false;
    }
    
}
