package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     *
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке firstString.
     */
    static public String longestCommonSubstring(String firstString, String secondString) {
        //throw new NotImplementedError();
        StringBuilder answerString = new StringBuilder();

        //создаём массив символов, чтобы разбить строку firstString на сегменты по два символа
        char[] firstStringLetters = firstString.toCharArray();

        ArrayList<String> firstStringSegmentsArray = new ArrayList<>();

        //разбиваем строку firstString на сегменты по два символа
        for (int i = 0; i < firstStringLetters.length - 1; i++) {
            String segment = String.valueOf(firstStringLetters[i]) + (firstStringLetters[i+1]);
            firstStringSegmentsArray.add(segment);
        }

        ArrayList<ArrayList<Integer>> segmentsMatrix_i = new ArrayList<>();

        //составляем проекцию сегментов firstString на secondString
        for (int i = 0; i < firstStringSegmentsArray.size(); i++) {
            ArrayList<Integer> segmentsMatrix_j = new ArrayList<>();
            for (int j = 0; j < secondString.length() - 1; j++) {
                if (firstStringSegmentsArray.get(i).charAt(0) == (secondString.charAt(j)) &&
                        firstStringSegmentsArray.get(i).charAt(1) == secondString.charAt(j + 1)) {
                    segmentsMatrix_j.add(j);
                    if (!segmentsMatrix_i.contains(segmentsMatrix_j)) segmentsMatrix_i.add(segmentsMatrix_j);
                }
            }
        }
        //Получившийся заполненный список списков можно назвать "картой пересечений"

        //вытаскиваем из карты пересечений нужную последовательность (ищем маскимальное подмножество)
        StringBuilder maxSegment = new StringBuilder();
        StringBuilder currentSegment = new StringBuilder();

//        boolean isArrayEmpty = false;
        int currentSegmentIndex = 0;
        int beginningSymbolIndex = 0;
        int currentMatrixString = 0;
        if (!segmentsMatrix_i.isEmpty()) {
            while (beginningSymbolIndex < segmentsMatrix_i.get(0).size()) {
                currentSegmentIndex = segmentsMatrix_i.get(0).get(beginningSymbolIndex);  //выбираем индекс для начальной буквы подмножества из 1ой строки матрицы
                currentSegment.setLength(0);
                currentSegment.append(secondString.charAt(currentSegmentIndex));//добавляем к "буферу" начальную букву подмножества
                for (int i = 0; i < segmentsMatrix_i.size(); i++) {
                    if (i < segmentsMatrix_i.size() - 1) {
                        ArrayList<Integer> segmentsMatrix_j = segmentsMatrix_i.get(i + 1); //создаем отдельный списко для индексов следующей буквы
                        int binarySearchResult = binarySearch(segmentsMatrix_j,
                                0,
                                segmentsMatrix_j.size() - 1,
                                currentSegmentIndex + 1); //ищем следующий индекс (предыдущий_индекс + 1) с помощью бинарного поиска
                        if (binarySearchResult > -1) { //если индекс существует
                            currentSegmentIndex++; //увеличиваем текущий индекс на 1
                            currentSegment.append(secondString.charAt(currentSegmentIndex)); //и длбавялем следующую букву в "буфер"
                        } else { //если такого индекса не существует
                            currentSegment.append(secondString.charAt(currentSegmentIndex + 1)); //добавляем последний символ в подмножество
                            maxSegment.setLength(0);
                            maxSegment.append(currentSegment);  //и заполняем хранитель максимального сегмента среди найденных
                            if (beginningSymbolIndex < segmentsMatrix_i.get(currentMatrixString).size() - 1) beginningSymbolIndex++;
                            else {
                                currentMatrixString++;
                                beginningSymbolIndex = 0;
                            }
                            if (currentMatrixString == segmentsMatrix_i.size() - 1) break;
                            i = 0;
                        }
                    } else {
                        currentSegment.append(secondString.charAt(currentSegmentIndex + 1));
                        maxSegment.setLength(0);
                        maxSegment.append(currentSegment);
                        beginningSymbolIndex++;
                        break;
                    }
                }
//                for (int j = 1; j < segmentsMatrix_j.size() - 1; j++) {
//                    int nextElementIndex = segmentsMatrix_j.get(binarySearch(segmentsMatrix_j,
//                            0,
//                            segmentsMatrix_j.size() - 1,
//                            currentSegmentIndex + 1));
//                    currentSegment.append(secondString.charAt(nextElementIndex));
//                    if (segmentsMatrix_j.get(j) == segmentsMatrix_j.get(nextElementIndex)) {
//                        currentSegmentIndex = segmentsMatrix_j.get(j);
//                        currentSegment.append(secondString.charAt(currentSegmentIndex));
//                    } else {
//                        if (maxSegment.length() < currentSegment.length()) {
//                            maxSegment.setLength(0);
//                            maxSegment.append(currentSegment);
//                        }
//                        break;
//                    }
//                }
//                if (beginningSymbolIndex < segmentsMatrix_i.get(0).size() - 1) beginningSymbolIndex++;
//            }
//            currentSegment.append(firstStringSegmentsArray.get(currentSegmentIndex).charAt(0));
//            if (maxSegment.length() < currentSegment.length()) {
//                maxSegment.setLength(0);
//                maxSegment.append(currentSegment);
//                currentSegment.setLength(0);
//                currentSegment.append(" ");
//            }
            }
//        if (maxSegment.length() != 0) {
//            ArrayList<Integer> segmentsMatrix_jLast = segmentsMatrix_i.get(segmentsMatrix_i.size() - 1);
//            maxSegment.append(secondString.charAt(segmentsMatrix_jLast.
//                    get(binarySearch(   segmentsMatrix_jLast,
//                                0,
//                                segmentsMatrix_jLast.size() - 1,
//                            currentSegmentIndex + 2)) + 1));
//        }
//        while (!isArrayEmpty) {
//            isArrayEmpty = true;
//
//            mainLoop:
//            for (int i = 0; i < firstStringSegmentsArray.size(); i++) {
//                for (int j = 0; j < secondString.length() - 1; j++) {
//                    if (segmentsMatrix[i][j] == 0) continue;
//                    else {
//                        if (currentSegmentIndex == 0) {
//                            isArrayEmpty = false;
//                            currentSegment.append(secondString.toCharArray()[segmentsMatrix[i][j]]);
//                            currentSegmentIndex = segmentsMatrix[i][j];
//                            segmentsMatrix[i][j] = 0;
//                            break mainLoop;
//                        }
//                    }
//                }
//            }
//
//            for (int i = 0; i < secondString.length() - 1; i++) {
//                if (segmentsMatrix[i][currentSegmentIndex + 1] != 0) {
//                    currentSegment.append(secondString.toCharArray()[segmentsMatrix[i][currentSegmentIndex + 1]]);
//                    currentSegmentIndex = segmentsMatrix[i][currentSegmentIndex + 1];
//                    segmentsMatrix[i][currentSegmentIndex] = 0;
//                } else {
//                    maxSegment.append(currentSegment);
//                    break;
//                }
//            }
//
//            if (currentSegment.toString().length() > maxSegment.toString().length()) {
//                maxSegment.setLength(0); //удаление прошлой максимальной последовательности (очистка StringBuilder'a)
//                maxSegment.append(currentSegment);
//            }
//
//            currentSegment.setLength(0);
//        }
        }
        answerString.append(maxSegment);
        return answerString.toString().trim();
    }

    private static int binarySearch(ArrayList<Integer> arr, int firstIndex, int lastIndex, int nextElementIndex) {
        int middleIndex = -1;
        if (arr.contains(nextElementIndex)) {
            //находим индекс среднего (центрального) элемента массива
            middleIndex = (firstIndex + lastIndex) / 2;

            while ((arr.get(middleIndex) != nextElementIndex) && (firstIndex <= lastIndex)) {
                if (arr.get(middleIndex) > nextElementIndex) {  //если центральный элемент больше искомого
                    lastIndex = middleIndex - 1;                               //то переходим в левую часть массива (делаем индекс последнего элемента массива равным индексу элемента слева от центрального)
                } else {                                                       //иначе
                    firstIndex = middleIndex + 1;                              //переходим в правую часть массива (делаем индекс первого элемента массива равным индексу элемента справа от центрального)
                }
                //в конце каждой итерации ищем новый центральный элемент подмассива, в который мы переместились
                middleIndex = (firstIndex + lastIndex) / 2;
            }
        }
        return middleIndex;
    }
    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    static public int calcPrimesNumber(int limit) {
        throw new NotImplementedError();
    }

    /**
     * Балда
     * Сложная
     *
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     *
     * И Т Ы Н
     * К Р А Н
     * А К В А
     *
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     *
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     *
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     *
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        throw new NotImplementedError();
    }
}
