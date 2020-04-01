## 问题：

**实现函数 double Power(double base, int exponent)，求 base 的 exponent 次方。不得使用库函数，同时不需要考虑大数问题。**

## 目地

实现特定库函数的功能是常见的面试题（数值计算和字符串处理类）

## 思路

很容易想到`O(n)`的解法

但是否可以进一步优化时间复杂度呢？

可以参考二分的思路，将一个大的数分成两个小的相等的数。

但是否对于奇数偶数场景都是通用的呢？

答案是 no！

当`n`为奇数时，我们还需添加上一个`base`底数

最后总结公式如下：

$$ a^n=\left\{
\begin{aligned}
a^{n/2}\times a^{n/2}, even \\
a^{n/2}\times a^{n/2} \times a, odd
\end{aligned}
\right.
$$



可以通过递归实现上述求解幂值的过程，时间复杂度为`O(logn)`。
