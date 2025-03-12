package utils.IntegerListPack;

/**
 * 存储长整数的双向循环链表类
 * @author XBX
 * @param <T> 泛型参数，用于存储任意类型的整数
 */
public class IntegerList<T> {
    private IntegerNode<T> head;
    private int size;

    /**
     * 空参构造函数
     * 初始化链表的头节点为null，链表大小为0
     */
    public IntegerList() {
        head = null;
        size = 0;
    }

    /**
     * 有参构造函数
     * @param data 链表的头节点数据
     */
    public IntegerList(T data) {
        head = new IntegerNode<>(data);
        head.next = head;
        head.prev = head;
        size = 1;
    }

    /**
     * 获取链表的大小
     * @return 链表的大小
     */
    public int size() {
        return size;
    }

    /**
     * 判断链表是否为空
     * @return 如果链表为空，返回true；否则返回false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 清空链表
     * @param data 节点数据
     */
    public void add(T data) {
        IntegerNode<T> newNode = new IntegerNode<>(data);
        if (isEmpty()) {
            head = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
        } else {
            IntegerNode<T> tail = head.prev;
            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = head;
            head.prev = newNode;
        }
        size++;
    }

    /**
     * 在指定位置插入元素
     * @param index 插入位置
     * @param data 插入元素
     */
    public void insert(int index, T data) {
        if (index < 0 || index > size) { // 检查索引是否合法
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == size) { // 在末尾插入元素
            add(data);
            return;
        }
        IntegerNode<T> newNode = new IntegerNode<>(data);
        if (isEmpty()) { // 如果链表为空，直接将新节点作为头节点
            head = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
        } else { // 在指定位置插入元素
            IntegerNode<T> current = head;
            for (int i = 0; i < index; i++) {// 找到要插入位置的节点
                current = current.next;
            }
            IntegerNode<T> prevNode = current.prev;
            prevNode.next = newNode;
            newNode.prev = prevNode;
            newNode.next = current;
            current.prev = newNode;
            if (index == 0) {
                head = newNode;
            }
        }
        size++;
    }

    /**
     * 移除指定位置的元素
     * @param index 移除位置
     * @return 移除的元素
     */
    public T remove(int index) {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        IntegerNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        T removedData = current.data;
        if (size == 1) {
            head = null;
        } else {
            IntegerNode<T> prevNode = current.prev;
            IntegerNode<T> nextNode = current.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            if (index == 0) {
                head = nextNode;
            }
        }
        size--;
        return removedData;
    }

    /**
     * 获取指定位置的元素
     * @param index 位置
     * @return 指定位置的元素
     */
    public T get(int index) {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        IntegerNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * 打印链表
     */
    public void printList() {
        IntegerNode<T> current = head;
        for (int i = 0; i < size; i++) {
            System.out.print(current.data);
            current = current.next;
        }
    }

    /**
     * 设置指定位置的元素
     * @param index 位置
     * @param data 新元素值
     */
    public void set(int index, T data) {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        IntegerNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.data = data;
    }
}
