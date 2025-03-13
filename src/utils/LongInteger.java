package utils;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.IntegerListPack.IntegerList;
import utils.IntegerListPack.IntegerNode;

/**
 * 长整数类
 * @author XBX
 * 用于包装用户输入的长整数，将长整数转化为链表进行存储
 */
public class LongInteger {
    /**
     * list: 长整数的链表
     * integerSize: 长整数的长度
     * LONG_INTEGER_LOG_TAG: 长整数的日志标签
     * isNegative: 长整数的正负号
     */
    private static final Logger logger = Logger.getLogger(LongInteger.class.getName());
    private static final String LONG_INTEGER_LOG_TAG = "LongInteger:  ";
    IntegerList<Integer> list;
    int integerSize;
    boolean isNegative = false; // 默认正数

    /**
     * 空参构造函数
     * 初始化长整数的头节点、尾节点为null
     * 长度为0
     */
    public LongInteger(){
        list = new IntegerList<>();
        integerSize = 0;
        logger.log(Level.INFO, LONG_INTEGER_LOG_TAG+"创建长整数链表完成，当前链表长度为:"+list.size()+"，长整数长度为:"+integerSize+"，长整数为:0");
    }

    /**
     * 有参构造函数
     * @param numStr 输入的长整数
     */
    public LongInteger(String numStr) {
        // 1. 清理非法字符并处理符号
        numStr = numStr.replaceAll("[^0-9+-]", "");
        if (numStr.isEmpty()) {
            throw new NumberFormatException("输入为空");
        }

        // 2. 处理符号
        this.isNegative = numStr.startsWith("-");
        if (numStr.startsWith("+") || numStr.startsWith("-")) {
            numStr = numStr.substring(1);  // 去除符号
        }

        // 3. 校验剩余字符串是否为纯数字
        if (numStr.isEmpty() || !numStr.matches("\\d+")) {
            throw new NumberFormatException("非法字符: " + numStr);
        }

        // 4. 初始化链表并存储数字
        this.list = new IntegerList<>();  // 确保链表对象被创建
        this.integerSize = numStr.length();

        // 5. 将数字字符按高位到低位存入链表
        for (char c : numStr.toCharArray()) {
            int digit = Character.getNumericValue(c);
            this.list.add(digit);
        }

        // 6. 移除前导零（可选，根据需求）
        while (this.list.size() > 1 && this.list.get(0) == 0) {
            this.list.remove(0);
            this.integerSize--;
        }
    }

    /**
     * 比较两个长整数的绝对值大小
     * @param num1 待比较的长整数
     * @param num2 待比较的长整数
     * @return 如果num1的绝对值大于num2的绝对值，返回1；如果num1的绝对值小于num2的绝对值，返回-1；如果num1的绝对值等于num2的绝对值，返回0
     */
    public static int compareAbs(LongInteger num1, LongInteger num2){
        // 去掉前导零
        while (num1.integerSize > 1 && num1.list.head.getData() == 0) {
            num1.list.remove(0);
            num1.integerSize--;
        }
        while (num2.integerSize > 1 && num2.list.head.getData() == 0) {
            num2.list.remove(0);
            num2.integerSize--;
        }

        // 比较两个长整数的长度
        if(num1.integerSize > num2.integerSize){
            logger.log(Level.INFO, LONG_INTEGER_LOG_TAG+"compareAbs:  num1的绝对值大于num2的绝对值");
            return 1;
        }else if(num1.integerSize < num2.integerSize){
            logger.log(Level.INFO, LONG_INTEGER_LOG_TAG+"compareAbs:  num1的绝对值小于num2的绝对值");
            return -1;
        }

        IntegerNode<Integer> node1 = num1.list.head;
        IntegerNode<Integer> node2 = num2.list.head;

        // 比较两个长整数的每一位数字
        for(int i = 0; i<num1.integerSize; i++){
            if(node1.getData() > node2.getData()){
                logger.log(Level.INFO, LONG_INTEGER_LOG_TAG+"compareAbs:  num1的绝对值大于num2的绝对值");
                return 1;
            }else if(node1.getData() < node2.getData()){
                logger.log(Level.INFO, LONG_INTEGER_LOG_TAG+"compareAbs:  num1的绝对值小于num2的绝对值");
                return -1;
            }
            node1 = node1.getNext();
            node2 = node2.getNext();
        }
        return 0;
    }

    /**
     * 该方法用于让用户输入一个四则运算式子，并根据输入的式子确定运算符和操作数，
     * 然后将操作数封装为 LongInteger 对象，最后调用 dispatchCalculationBySign 方法进行计算任务的分配。
     */
    public static void startServer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入算式（示例：-20 - +10）：");
        String userInput = scanner.nextLine().trim();

        // 修正后的正则表达式（精确匹配操作数、运算符和空格）
        Pattern pattern = Pattern.compile(
                "^\\s*([+-]?\\d+)\\s+([+\\-*/])\\s+([+-]?\\d+)\\s*$"
        );
        Matcher matcher = pattern.matcher(userInput);

        if (matcher.matches()) {
            String numStr1 = matcher.group(1).replaceAll("[^0-9+-]", "");
            String operator = matcher.group(2);
            String numStr2 = matcher.group(3).replaceAll("[^0-9+-]", "");
            try {
                LongInteger num1 = new LongInteger(numStr1);
                logger.log(Level.INFO, LONG_INTEGER_LOG_TAG + "num1: " +(num1.isNegative? "-" : "+")+ num1.list.getListTotalValue());
                LongInteger num2 = new LongInteger(numStr2);
                logger.log(Level.INFO, LONG_INTEGER_LOG_TAG + "num2: " + (num2.isNegative? "-" : "+")+ num2.list.getListTotalValue());
                dispatchCalculationBySign(num1, num2, operator);
            } catch (NumberFormatException e) {
                System.err.println("非法格式: " + e.getMessage());
            }
        } else {
            System.err.println("格式错误！示例：-20 - +10");
        }
    }

    /**
     * 根据运算符号进行相应的运算
     * @param num1 待运算的长整数
     * @param num2 待运算的长整数
     * @param sign 运算符号
     */
    public static void dispatchCalculationBySign(LongInteger num1, LongInteger num2, String sign) {
        if (sign.equals("-")) {
            // 将减法转换为加法：num1 + (-num2)
            LongInteger newNum2 = new LongInteger(num2.list.getListTotalValue());
            //LongInteger newNum2 = new LongInteger(num2.toString());
            newNum2.isNegative = !newNum2.isNegative;
            dispatchCalculationBySign(num1, newNum2, "+");
            return;
        }

        boolean bothNegative = num1.isNegative && num2.isNegative;
        boolean bothPositive = !num1.isNegative && !num2.isNegative;
        boolean oppositeSign1 = num1.isNegative && !num2.isNegative;
        boolean oppositeSign2 = !num1.isNegative && num2.isNegative;

        if (sign.equals("+")) {
            if (bothNegative) {
                // 两负数相加，结果为负
                logger.log(Level.INFO, LONG_INTEGER_LOG_TAG + "加法运算，结果为负");
                LongInteger result = add(num1, num2, true);
                System.out.println("结果为：" +(result.isNegative?"-":"+")+ result.list.getListTotalValue());
            } else if (bothPositive) {
                // 两正数相加，结果为正
                logger.log(Level.INFO, LONG_INTEGER_LOG_TAG + "加法运算，结果为正");
                LongInteger result = add(num1, num2, false);
                System.out.println("结果为：" +(result.isNegative?"-":"+")+ result.list.getListTotalValue());
            } else if (oppositeSign1 || oppositeSign2) {
                // 异号相加，执行减法
                int cmp = compareAbs(num1, num2);
                if (cmp == 1) {
                    boolean resultIsNegative = num1.isNegative;
                    logger.log(Level.INFO, LONG_INTEGER_LOG_TAG + "减法运算，结果为" + (resultIsNegative ? "负" : "正"));
                    LongInteger result = subtract(num1, num2, resultIsNegative);
                    System.out.println("结果为：" +(result.isNegative?"-":"+")+ result.list.getListTotalValue());
                } else if (cmp == -1) {
                    boolean resultIsNegative = num2.isNegative;
                    logger.log(Level.INFO, LONG_INTEGER_LOG_TAG + "减法运算，结果为" + (resultIsNegative ? "负" : "正"));
                    LongInteger result = subtract(num2, num1, resultIsNegative);
                    System.out.println("结果为：" +(result.isNegative?"-":"+")+ result.list.getListTotalValue());
                } else {
                    // 绝对值相等，结果为0
                    logger.log(Level.INFO, LONG_INTEGER_LOG_TAG + "结果为0");
                    LongInteger result = new LongInteger("0");
                    System.out.println("结果为：" +(result.isNegative?"-":"+")+ result.list.getListTotalValue());
                }
            }
        }
    }

    /**
     * 加法运算
     * @param num1 待相加的长整数
     * @param num2 待相加的长整数
     * @param ResultIsNegative 结果是否为负数
     * @return 相加后的长整数
     */
    public static LongInteger add(LongInteger num1, LongInteger num2, boolean ResultIsNegative) {
        logger.log(Level.INFO, LONG_INTEGER_LOG_TAG + "进入加法运算" + (num1.isNegative? "-" : "+") +num1.list.getListTotalValue() + (num2.isNegative? "-" : "+")+num2.list.getListTotalValue());
        IntegerList<Integer> resultList = new IntegerList<>();
        IntegerNode<Integer> node1 = num1.list.tail; // 从个位开始遍历（链表尾部）
        IntegerNode<Integer> node2 = num2.list.tail;
        int carry = 0;

        // 遍历所有位数并处理进位
        while (node1 != null || node2 != null || carry != 0) {
            int digit1 = (node1 != null) ? node1.getData() : 0;
            int digit2 = (node2 != null) ? node2.getData() : 0;

            int sum = digit1 + digit2 + carry;
            carry = sum / 10;
            int currentDigit = sum % 10;

            // 将当前位插入结果链表头部（保证高位在前）
            resultList.addFirst(currentDigit);

            // 移动到前一位
            if (node1 != null) node1 = node1.getPrev();
            if (node2 != null) node2 = node2.getPrev();
        }

        // 去除前导零（例如 099 -> 99）
        while (resultList.size() > 1 && resultList.get(0) == 0) {
            resultList.remove(0);
        }

        LongInteger result = new LongInteger(resultList.getListTotalValue());
        result.isNegative = ResultIsNegative;
        return result;
    }

    /**
     * 减法运算
     * @param num1 待相减的长整数
     * @param num2 待相减的长整数
     * @param ResultIsNegative 结果是否为负数
     * @return 相减后的长整数
     */
    public static LongInteger subtract(LongInteger num1, LongInteger num2, boolean ResultIsNegative) {
        logger.log(Level.INFO, LONG_INTEGER_LOG_TAG + "进入减法运算" + (num1.isNegative? "-" : "+") +num1.list.getListTotalValue() + (num2.isNegative? "-" : "+")+num2.list.getListTotalValue());
        IntegerList<Integer> resultList = new IntegerList<>();
        IntegerNode<Integer> node1 = num1.list.tail; // 被减数（绝对值较大）
        IntegerNode<Integer> node2 = num2.list.tail;
        int borrow = 0;

        // 遍历被减数的所有位数
        while (node1 != null) {
            int digit1 = node1.getData() - borrow; // 减去借位
            int digit2 = (node2 != null) ? node2.getData() : 0;

            borrow = 0; // 重置借位

            // 处理当前位减法
            if (digit1 < digit2) {
                digit1 += 10;      // 借位
                borrow = 1;        // 标记需要向高位借位
            }

            int diff = digit1 - digit2;
            resultList.addFirst(diff); // 插入结果头部

            // 移动到前一位
            node1 = node1.getPrev();
            if (node2 != null) node2 = node2.getPrev();
        }
        // 去除前导零（例如 007 -> 7）
        while (resultList.size() > 1 && resultList.get(0) == 0) {
            resultList.remove(0);
        }

        LongInteger result = new LongInteger(resultList.getListTotalValue());
        result.isNegative = ResultIsNegative;
        return result;
    }

}
