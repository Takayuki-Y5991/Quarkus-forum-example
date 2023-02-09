package com.example.utility;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Random;
import java.util.stream.IntStream;

public class ValueGenerator {

    /**
     * 文字列 ランダム生成
     *
     * @param size 生成文字数
     * @return 文字列
     */
    public static String generateString(int size) {

        if (size >= 20) return generateContent();

        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();
        StringBuilder result = new StringBuilder();

        IntStream.range(1, size).boxed()
                .map(e -> {
                            return alphabet.charAt(random.nextInt(alphabet.length()));
                        }
                )
                .forEach(result::append);

        return result.toString();
    }

    /**
     * 数値 ランダム生成
     *
     * @param max    数値: MAX値
     * @param isPast 符号: true - [-], false - [+]
     * @return 数値
     */
    public static int generateInteger(int max, boolean isPast) {
        Random random = new Random();
        return isPast ? random.nextInt(max) * -1 : random.nextInt(max);
    }

    /**
     * 電話番号 生成
     *
     * @param number 1 - 携帯, 2 - 自宅
     * @return
     */
    public static String generateContactNumber(int number) {
        return number == 1 ? new Faker(Locale.JAPANESE).phoneNumber().phoneNumber() : new Faker().phoneNumber().cellPhone();
    }

    /**
     * E-mail 生成
     *
     * @return
     */
    public static String generateEmail() {
        return new Faker().internet().safeEmailAddress();
    }

    private static String generateContent() {
        return new Faker().lorem().sentence(new Random().nextInt(10));
    }

    public static String generateName() {
        return new Faker().name().firstName();
    }

    public static LocalDate generateBirthDay() {
        return new Faker().date().birthday().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
