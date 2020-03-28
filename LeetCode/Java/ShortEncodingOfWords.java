/*

给定一个单词列表，我们将这个列表编码成一个索引字符串 S 与一个索引列表 A。

例如，如果这个列表是 ["time", "me", "bell"]，我们就可以将其表示为 S = "time#bell#" 和 indexes = [0, 2, 5]。

对于每一个索引，我们可以通过从字符串 S 中索引的位置开始读取字符串，直到 "#" 结束，来恢复我们之前的单词列表。

那么成功对给定单词列表进行编码的最小字符串长度是多少呢？

*/

class ShortEncodingOfWords {

    /* 方法一：先反转所有字符串，再按照字典序排序，最后一次检查相邻项是否前缀关系 */
    public int method1(String[] words) {
        int ret = 0;
        int n = words.length;
        //逆转字符串
        for (int i = 0; i < n; i++) {
            StringBuilder builder = new StringBuilder(words[i]);
            words[i] = builder.reverse().toString();
        }
        //字典序列排序
        Arrays.sort(words);
        //检查相邻项的前缀关系
        for (int i = 0; i < n - 1; i++) {
             if ( !words[i + 1].startsWith(words[i]) ) {
                 ret += words[i].length() + 1;
             } 
        }
        return ret + words[n - 1].length() + 1;
    }

    /* 方法二：先反转所有字符串，再构建一个字典树，根据字典树构建索引字符串 */
    public int method2(String[] words) {

        int n = words.length;
        //逆转所有字符串
        for (int i = 0; i < n; i++) {
            StringBuilder builder = new StringBuilder(words[i]);
            words[i] = builder.reverse().toString();
        }
        //构造字典树
        Trie trie = new Trie(words);

        return trie.getSize();
    }
    
    /* 定义字典树 */
    private class Trie {
        private TrieNode root;
        private int size;
        
        public Trie(String[] words) {
            root = new TrieNode('/');
            size = 0;
            for (String word: words) {
                add(word);
            }
        }
        
        private void add(String s) {
            TrieNode node = root;
            int i = 0;
            for (char c: s.toCharArray()) {
                if (node.map.containsKey(c)) {
                    node = node.map.get(c);
                } else {
                    if (node.isLeaf == true) {
                        node.isLeaf = false;
                        size -= (i + 1);
                    }
                    TrieNode new_node = new TrieNode(c);
                    node.map.put(c, new_node);
                    node = new_node;
                }
                i++;
            }
            if (node.isLeaf == false && node.map.size() == 0) {
                node.isLeaf = true;
                size += (i + 1);
            }
        }

        private int getSize() {
            return size;
        }
        
        /* 定义字典树的结点 */
        private class TrieNode {
            private Character val;
            private Map<Character, TrieNode> map;
            private boolean isLeaf;
            
            public TrieNode(char val) {
                this.val = val;
                map = new HashMap<>();
                isLeaf = false;
            }
        }
        
    }
}
