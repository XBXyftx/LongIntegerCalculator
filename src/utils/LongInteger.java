package utils;

import utils.IntegerListPack.IntegerList;

/**
 * 长整数类
 * @author XBX
 * 用于包装用户输入的长整数，将长整数转化为链表进行存储
 */
public class LongInteger {
    /**
     * list: 长整数的链表
     * integerSize: 长整数的长度
     */
    IntegerList<Integer> list;
    int integerSize;

    /**
     * 空参构造函数
     * 初始化长整数的头节点、尾节点为null
     * 长度为0
     */
    public LongInteger(){
        list = new IntegerList<>();
        integerSize = 0;
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
