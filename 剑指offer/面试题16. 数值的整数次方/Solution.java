/*
实现函数`double Power(double base, int exponent)`，求`base`的`exponent`次方。

不得使用库函数，同时不需要考虑大数问题。
*/

class Solution {
    
    public double myPow(double x, int n) {
        if (x == 0) {     //底数为1的情况
            return 0;
        } 
        double base = x;
        long exponent = n;
        
        double ret = powerWithUnsignedExponent(base, Math.abs(exponent));
        
        if (exponent < 0) {         //指数为负 取倒数
            return 1 / ret;
        } else {
            return ret;
        }
    }
    
    /* 求解无符号指数的幂值 */
    private double powerWithUnsignedExponent(double base, long exponent) {
        if (exponent == 0) {
            return 1;
        } else if (exponent == 1) {
            return base;
        }
        
        double ret = powerWithUnsignedExponent(base, exponent >> 1);              //递归求解
        
        ret *= ret;
        if ((exponent & 1) == 1) {
            ret *= base;
        }
        
        return ret;
    }
    
}
