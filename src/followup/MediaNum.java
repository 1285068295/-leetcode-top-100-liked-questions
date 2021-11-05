package followup;

/**
 * @author ：Lisp
 * @date： 2021/11/4
 * @version: V1.0
 * @slogan:
 * @description : 电商面试 大数据资源限制问题
 * 【题目】 商品sku共有 20亿  价格区间再1 ~ 10000 之间  在内存有限的条件下
 *         1 求商品的价格的中位数
 *         2 求商品价格去重情况下的中位数
 *         3 求每个商品种类下的top3
 *
 * 【解题】
 *   1 long[] prices = new long[10001] 来统计价格频率  从小到大的累加价格频率 直到频率和到10亿时 就找到中位数了
 *   2 long[] prices = new long[10001] 先找出prices数组上不为空的价格 把数量加起来  如有20个  找中位数就是找第10个数
 *   3 使用堆结构来找top3
 */
public class MediaNum {
}
