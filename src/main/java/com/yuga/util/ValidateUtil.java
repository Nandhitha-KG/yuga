package com.yuga.util;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;

public class ValidateUtil {

    public static boolean isNullEmpty(Optional<Object> obj) {

        return obj.isEmpty();

    }

    public static boolean isPresent(Optional<?> optional) {

        return optional.isPresent();

    }

    public static boolean isSignumZero(BigDecimal number) {

        return number.signum() == 0;

    }

    public static boolean isGreaterThanZero(int number) {

        return number > 0;

    }

    public static boolean checkIfSizeLess(int rightNum, int leftNum) {
        return rightNum < leftNum;
    }

    public static boolean checkIfSizeGreater(int rightNum, int leftNum) {
        return rightNum > leftNum;
    }

    public static boolean checkIfSizeEqual(int rightNum, int leftNum) {
        return rightNum == leftNum;
    }

    public static boolean isEqualsZero(int number) {

        return number == 0;

    }

    public static boolean isGreaterThanOne(int number) {

        return number > 1;

    }

    public static boolean isSignumNegative(BigDecimal rightNumber) {

        return rightNumber.signum() == -1;

    }

    public static boolean isSignumPositive(BigDecimal rightNumber) {

        return rightNumber.signum() == 1;

    }

    public static boolean checkNumberGreaterThan(BigDecimal rightNumber, BigDecimal leftNumber) {

        return rightNumber.compareTo(leftNumber) == 1;

    }

    public static boolean checkNumberLessThan(BigDecimal rightNumber, BigDecimal leftNumber) {

        return rightNumber.compareTo(leftNumber) == -1;

    }

    public static boolean checkNumberEqual(BigDecimal rightNumber, BigDecimal leftNumber) {

        return rightNumber.compareTo(leftNumber) == 0;

    }

    public static boolean isNullEmpty(String str) {
        return str == null || str.isBlank();
    }

    public static boolean isNullEmpty(Long obj) {
        return obj == null;
    }

    public static boolean isNullEmpty(StringBuffer sb) {

        return sb == null || sb.toString().isBlank();

    }

    public static boolean isNullEmpty(StringBuilder sb) {

        return sb == null || sb.toString().isBlank();

    }

    public static boolean isNullEmpty(Collection c) {

        return c == null || c.isEmpty();

    }

    public static boolean isNullEmpty(Map m) {

        return m == null || m.isEmpty();

    }

    public static boolean isNullEmpty(BigDecimal value) {

        return value == null;

    }

    public static boolean isNullEmpty(Object obj) {

        return obj == null;

    }
    public static boolean isNullEmpty(MultipartFile file) {

        return file == null || file.isEmpty();

    }

    public static boolean twoStringEqual(String rightWord, String leftWord) {

        return rightWord.equals(leftWord);

    }
    public static boolean twoStringEqualIgnoreCase(String rightWord, String leftWord) {

        return rightWord.equalsIgnoreCase(leftWord);

    }

    public static boolean isNullEmpty(List<Object> obj) {

        return obj == null || obj.isEmpty();

    }

    public static boolean checkTwoLongEqual(Long rightNum, Long leftNum) {

        return rightNum.longValue() == leftNum.longValue();

    }

    public static boolean checkLongGreaterThan(Long rightNum, Long leftNum) {

        return rightNum.longValue() > leftNum.longValue();

    }

    public static boolean checkLongLessThan(Long rightNum, Long leftNum) {

        return rightNum.longValue() < leftNum.longValue();

    }

    public static boolean checkIfDateIsBefore(java.sql.Date rightDate, java.sql.Date leftDate) {

        return rightDate.before(leftDate);

    }
    public static boolean checkIfDateIsBeforeTimestamp(java.sql.Date rightDate, java.sql.Timestamp leftDate) {

        return rightDate.before(leftDate);

    }


    public static boolean checkIfDateIsEqual(java.sql.Date rightDate, java.sql.Date leftDate) {

        return rightDate.equals(leftDate);

    }
    public static boolean checkIfDateIsEqualTimestamp(java.sql.Date rightDate, java.sql.Timestamp leftDate) {

        return rightDate.equals(leftDate);

    }

    public static boolean checkIfDateIsAfter(java.sql.Date rightDate, java.sql.Date leftDate) {

        return rightDate.after(leftDate);

    }

    public static boolean checkIfDateIsAfterTimestamp(java.sql.Date rightDate, java.sql.Timestamp leftDate) {

        return rightDate.after(leftDate);

    }

    public static boolean checkIfTimestampIsBefore(java.sql.Timestamp rightDate, java.sql.Timestamp leftDate) {

        return rightDate.before(leftDate);

    }

    public static boolean checkIfTimestampIsEqual(java.sql.Timestamp rightDate, java.sql.Timestamp leftDate) {

        return rightDate.equals(leftDate);

    }

    public static boolean checkIfTimestampIsAfter(java.sql.Timestamp rightDate, java.sql.Timestamp leftDate) {

        return rightDate.after(leftDate);

    }

    public static boolean isNullEmpty(Object[] arr) {

        return arr == null || arr.length == 0;

    }

    public static boolean isPositive(int num) {
        return num > 0;
    }

    public static boolean isPositive(BigDecimal num) {
        int value = num.compareTo(BigDecimal.ZERO);

        return value == 1;
    }

    public static boolean isNegative(int num) {
        return num < 0;
    }

    public static boolean isNegative(BigDecimal num) {
        int value = num.compareTo(BigDecimal.ZERO);

        return value == -1;
    }

    public static boolean equalToZero(int num) {
        return num == 0;
    }

    public static boolean equalToZero(BigDecimal num) {
        int value = num.compareTo(BigDecimal.ZERO);

        return value == 0;
    }

    public static boolean longNullCheck(Long num) {
        return num == null;
    }

    public static boolean twoUUIDStringEqual(UUID leftWord, UUID rightWord) {
        return leftWord.equals(rightWord);
    }
}
