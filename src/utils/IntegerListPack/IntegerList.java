package utils.IntegerListPack;

/**
 * 存储长整数的双向非循环链表类
 * @author XBX
 * @param <T> 泛型参数，用于存储任意类型的整数
 */
public class IntegerList<T> {
    public IntegerNode<T> head;
    public IntegerNode<T> tail; // 新增尾节点指针
    private int size;

    /**
     * 空参构造函数
     * 初始化链表的头节点和尾节点为 null，链表大小为 0
     */
    public IntegerList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * 有参构造函数
     * @param data 链表的头节点数据
     */
    public IntegerList(T data) {
        head = new IntegerNode<>(data);
        tail = head;
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
     * @return 如果链表为空，返回 true；否则返回 false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 添加元素到链表尾部
     * @param data 节点数据
     */
    public void add(T data) {
        IntegerNode<T> newNode = new IntegerNode<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
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
        if (isEmpty()) { // 如果链表为空，直接将新节点作为头节点和尾节点
            head = newNode;
            tail = newNode;
        } else if (index == 0) { // 在头部插入元素
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else { // 在指定位置插入元素
            IntegerNode<T> current = head;
            for (int i = 0; i < index; i++) { // 找到要插入位置的节点
                current = current.next;
            }
            IntegerNode<T> prevNode = current.prev;
            prevNode.next = newNode;
            newNode.prev = prevNode;
            newNode.next = current;
            current.prev = newNode;
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
            tail = null;
        } else if (index == 0) { // 移除头节点
            head = current.next;
            head.prev = null;
        } else if (index == size - 1) { // 移除尾节点
            tail = current.prev;
            tail.next = null;
        } else {
            IntegerNode<T> prevNode = current.prev;
            IntegerNode<T> nextNode = current.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
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
    public String getListTotalValue() {
        IntegerNode<T> current = head;
        StringBuilder sb = new StringBuilder();
        while (current != null) {
            sb.append(current.data);
            current = current.next;
        }
        return sb.toString();
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

    /**
     * 在链表头部添加元素
     * @param data 元素值
     */
    public void addFirst(T data) {
        insert(0, data);
    }
}

