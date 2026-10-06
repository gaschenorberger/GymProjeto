package br.com.sistema.util;

public final class CpfValidator {

    private CpfValidator() {}

    public static boolean isValid(String cpf) {
        String digits = cpf == null ? "" : cpf.replaceAll("\\D", "");
        if (digits.length() != 11 || digits.matches("(\\d)\\1{10}")) return false;

        int first = calculateDigit(digits, 9, 10);
        int second = calculateDigit(digits, 10, 11);
        return first == Character.digit(digits.charAt(9), 10)
                && second == Character.digit(digits.charAt(10), 10);
    }

    private static int calculateDigit(String digits, int length, int weight) {
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += Character.digit(digits.charAt(i), 10) * (weight - i);
        }
        int result = 11 - (sum % 11);
        return result >= 10 ? 0 : result;
    }
}
