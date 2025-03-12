package utils.IntegerListPack;

/**
 * 节点类
 * @author XBX
 * 用于存储长整数的每一位数字
 * @param <T> 节点的数据类型
 */
public class IntegerNode<T> {
    /**
     * data: 节点的数据
     * next: 节点的下一个节点
     * prev: 节点的上一个节点
     */
    T data;
    IntegerNode<T> next;
    IntegerNode<T> prev;

    public IntegerNode() {
        this(null, null);
    }

    public IntegerNode(T data) {
        this(data, null);
    }

    public IntegerNode(T data, IntegerNode<T> next) {
        this.data = data;
        this.next = next;
        if (next != null) {
            next.prev = this;
        }
        this.prev = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public IntegerNode<T> getNext() {
        return next;
    }

    public void setNext(IntegerNode<T> next) {
        this.next = next;
        if (next != null) {
            next.prev = this;
        }
    }

    public IntegerNode<T> getPrev() {
        return prev;
    }

    public void setPrev(IntegerNode<T> prev) {
        this.prev = prev;
        if (prev != null) {
            prev.next = this;
        }
    }
}
