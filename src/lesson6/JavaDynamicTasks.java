package lesson6;

import kotlin.NotImplementedError;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     *
     * Сложность
     * Время: O(n^2)    n - длина последовательности
     * Память: O(n)     n - длина последовательности
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if (list.isEmpty()) return list;
        int[] maxSubSequencesLengths = new int[list.size()];
        List<Integer> answer = new ArrayList<>();

        int maxLength = -1;
        int maxElementIndex = 0;

        for (int i = list.size() - 1; i > -1; i--) {
            int localMax = -1;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) < list.get(j) && maxSubSequencesLengths[j] > localMax) {
                    localMax = maxSubSequencesLengths[j];
                }
            }
            maxSubSequencesLengths[i] = localMax + 1;
            if (maxSubSequencesLengths[i] >= maxLength) {
                maxLength = maxSubSequencesLengths[i];
                maxElementIndex = i;
            }
        }

        while (maxLength > -1) {
            for (int i = maxElementIndex; i < list.size(); i++) {
                if (maxSubSequencesLengths[i] == maxLength && list.get(maxElementIndex) <= list.get(i)) {
                    answer.add(list.get(i));
                    maxLength--;
                    maxElementIndex = i;
                    break;
                }
            }
        }

        return answer;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     *
     * Сложность:
     * Время: O(n^2) n - количество элементов в строке
     * Память: O(n)  n - количество элементов в строке
     */
    public static int shortestPathOnField(String inputName) {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(new File(inputName)));
            String[] line = bf.readLine().split(" ");
            int[] arrPathLengths = new int[line.length];
            for (int i = 0; i < line.length; i++) arrPathLengths[i] = Integer.parseInt(line[i]);
            int lineCounter = 0;
            while (bf.ready() || lineCounter == 0) {
                if (lineCounter != 0) line = bf.readLine().split(" ");

                int previousLength = arrPathLengths[0];
                arrPathLengths[0] = Integer.parseInt(line[0]) + arrPathLengths[0];
                int temp;

                for (int i = 1; i < line.length; i++) {

                    temp = arrPathLengths[i];
                    if (lineCounter == 0)  {
                        arrPathLengths[i] = Integer.parseInt(line[i]) + arrPathLengths[i - 1];
                    }
                    else {
                        arrPathLengths[i] = Integer.parseInt(line[i]) + min(arrPathLengths[i - 1],
                                                                            arrPathLengths[i],
                                                                            previousLength);
                    }
                    previousLength = temp;
                }
                lineCounter++;
            }
            return arrPathLengths[arrPathLengths.length - 1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int min(int numb1, int numb2, int numb3) {
        int[] arr = new int[] {numb1, numb2, numb3};
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) min = arr[i];
        }
        return min;
    }
    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
