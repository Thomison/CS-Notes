/* 
在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。

请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
*/
class Solution {
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        int row = matrix.length;
        if (row == 0) 
            return false;
        int col = matrix[0].length;
        if (col == 0) 
            return false;

        int i = 0, j = col - 1;
        while (i < row && j >= 0) {
            int num = matrix[i][j];
            
            if (num == target) {  //查找成功
                return true;
            } else if (num < target) {  //删除行
                i++; 
            } else if (num > target) {  //删除列
                j--;   
            }
        }
        return false;   //查找失败
    }
}
