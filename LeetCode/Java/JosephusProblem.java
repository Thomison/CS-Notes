class JosephusProblem {
/*
0,1,,n-1这n个数字排成一个圆圈，从数字0开始，每次从这个圆圈里删除第m个数字。求出这个圆圈里剩下的最后一个数字。

例如，0、1、2、3、4这5个数字组成一个圆圈，从数字0开始每次删除第3个数字，则删除的前4个数字依次是2、0、4、1，因此最后剩下的数字是3。
*/
     
    //arraylist 模拟  O(n^2) 1482ms
    public int method1(int n, int m) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(i);
        }
        int i = 0;
        while (n > 1) {
            i = (i + m - 1) % n;
            list.remove(i);
            n--;
        }
        return list.get(0);
    }

    //数学 公式推导 O(n) 8ms
    public int method2(int n, int m) {
        int ret = 0;
        for (int i = 2; i <= n; i++) {
            ret = (ret + m) % i;
        }
        return ret;
    }

}
```
