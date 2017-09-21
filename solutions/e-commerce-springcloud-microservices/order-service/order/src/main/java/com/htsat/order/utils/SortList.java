package com.htsat.order.utils;

import java.lang.reflect.Method;
import java.util.Comparator;

public class SortList<T> implements Comparator<T> {

    private String propertyName;
    private boolean isAsc;

    public SortList(String propertyname, boolean isasc) {
        this.propertyName = propertyname;
        this.isAsc = isasc;
    }

    /**
     * 需要的是：根据类中的字段对对象进行排序
     *
     * @return
     */

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public int compare(T b1, T b2) {

        Class<?> clz = b1.getClass();
        Method mth = getPropertyMethod(clz, propertyName);

        try {
            Object o1 = mth.invoke(b1);
            Object o2 = mth.invoke(b2);

            if (o1 == null || o2 == null) return 0;
            Comparable value1 = (Comparable) o1;
            Comparable value2 = (Comparable) o2;

            if (isAsc) {
                return value1.compareTo(value2);
            } else {
                return value2.compareTo(value1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    // 获取类名
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Method getPropertyMethod(Class clz, String propertyName) {
        Method mth = null;
        try {
            mth = clz.getMethod("get" + propertyName);
        } catch (Exception e) {
            System.out.println("获取类名发生错误！");
        }
        return mth;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public boolean isAsc() {
        return isAsc;
    }

    public void setAsc(boolean isAsc) {
        this.isAsc = isAsc;
    }

}
