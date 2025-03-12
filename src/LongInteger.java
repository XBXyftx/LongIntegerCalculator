/**
 * 长整数类
 * @author XBX
 * 用于包装用户输入的长整数，将长整数转化为链表进行存储
 */
public class LongInteger {
    /**
     * IntegerHeadNode: 长整数的头节点
     * IntegerTailNode: 长整数的尾节点
     * IntegerSize: 长整数的长度
     */
    IntegerNode<String> IntegerHeadNode;
    IntegerNode<String> IntegerTailNode;
    int IntegerSize;

    /**
     * 空参构造函数
     * 初始化长整数的头节点、尾节点为null
     * 长度为0
     */
    public LongInteger(){
        IntegerHeadNode = null;
        IntegerTailNode = null;
        IntegerSize = 0;
    }

    /**
     * 有参构造函数
     * @param str 输入的长整数
     */
    public LongInteger(String str){

    }

    public LongInteger add(LongInteger num){
        return null;
    }

}
