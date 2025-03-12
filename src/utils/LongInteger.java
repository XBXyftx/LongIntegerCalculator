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
     * @param IntegerStr 输入的长整数
     */
    public LongInteger(String IntegerStr){
        IntegerStr = IntegerStr.trim();
        // 检查是否为负数
        if (IntegerStr.startsWith("-")) {
            isNegative = true;
            IntegerStr = IntegerStr.substring(1); // 去掉负号
        } else {
            isNegative = false;
        }
        list = new IntegerList<>();
        integerSize = IntegerStr.length();
        for(int i = 0; i < integerSize; i++){
            list.add(Integer.parseInt(IntegerStr.substring(i, i + 1)));
        }
        // 输出时考虑正负
        String sign = isNegative ? "-" : "+";
        logger.log(Level.INFO, LONG_INTEGER_LOG_TAG+"创建长整数链表完成，当前链表长度为:"+list.size()+"，长整数长度为:"+integerSize+"，长整数为:"+sign+list.getListTotalValue());
    }

    /**
     * 比较两个长整数的绝对值大小
     * @param num1 待比较的长整数
     * @param num2 待比较的长整数
     * @return 如果num1的绝对值大于num2的绝对值，返回1；如果num1的绝对值小于num2的绝对值，返回-1；如果num1的绝对值等于num2的绝对值，返回0
     */
    public static int compareAbs(LongInteger num1, LongInteger num2){
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
     * 然后将操作数封装为 LongInteger 对象，最后调用 dispatchCalculationBySign 方法进行计算。
     */
    public static void startServer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入算式（格式示例：(-100)*(+200)）：");
        String userInput = scanner.nextLine().trim();

        // 使用增强型正则表达式匹配带符号括号格式
        Pattern pattern = Pattern.compile(
                "^\\s*" +                // 允许前置空格
                        "\\(\\s*" +             // 左括号
                        "([+-]?\\d+)" +         // 操作数1（允许符号）
                        "\\s*\\)\\s*" +         // 右括号及周围空格
                        "([+*/-])" +            // 运算符
                        "\\s*" +                // 运算符周围空格
                        "\\(\\s*" +             // 左括号
                        "([+-]?\\d+)" +         // 操作数2（允许符号）
                        "\\s*\\)\\s*" +         // 右括号及周围空格
                        "$"                     // 行尾
        );
        Matcher matcher = pattern.matcher(userInput);

        if (matcher.find()) {
            String numStr1 = matcher.group(1);  // 提取第一个操作数
            String operator = matcher.group(2); // 提取运算符
            String numStr2 = matcher.group(3);  // 提取第二个操作数
            logger.log(Level.INFO, LONG_INTEGER_LOG_TAG + "numStr1:" + numStr1 + ",operator:" + operator + ",numStr2:" + numStr2);

            try {
                LongInteger num1 = new LongInteger(numStr1);
                LongInteger num2 = new LongInteger(numStr2);
                dispatchCalculationBySign(num1, num2, operator);
            } catch (NumberFormatException e) {
                System.err.println(LONG_INTEGER_LOG_TAG + "非法长整数格式：" + e.getMessage());
            }
        } else {
            System.err.println("输入格式错误！请使用括号包裹操作数，示例：(-123)*(+456)");
        }
    }



    /**
     * 根据运算符号进行相应的运算
     * @param num1 待运算的长整数
     * @param num2 待运算的长整数
     * @param sign 运算符号
     */
    public static void dispatchCalculationBySign(LongInteger num1, LongInteger num2,String sign){

    }

    /**
     * 加法运算
     * @param num1 待相加的长整数
     * @param num2 待相加的长整数
     * @return 相加后的长整数
     */
    public static LongInteger add(LongInteger num1, LongInteger num2){
        logger.log(Level.WARNING, LONG_INTEGER_LOG_TAG+"add:  开始相加处理");
        return null;
    }


    /**
     * 减法运算
     * @param num1 待相减的长整数
     * @param num2 待相减的长整数
     * @return 相减后的长整数
     */
    public static LongInteger subtract(LongInteger num1, LongInteger num2){
        return null;
    }

    /**
     * 乘法运算
     * @param num1 待相乘的长整数
     * @param num2 待相乘的长整数
     * @return 相乘后的长整数
     */
    public static LongInteger multiply(LongInteger num1, LongInteger num2){
        return null;
    }

    /**
     * 除法运算
     * @param num1 待相除的长整数
     * @param num2 待相除的长整数
     * @return 相除后的长整数
     */
    public static LongInteger divide(LongInteger num1, LongInteger num2){
        return null;
    }

}
