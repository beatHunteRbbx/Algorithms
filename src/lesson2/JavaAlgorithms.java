package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.*;

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

    /**
     * Сложность
     * Время: O(n^2)
     * Память: O(n*m)
     */
    static public String longestCommonSubstring(String firstString, String secondString) {
        //throw new NotImplementedError();
        StringBuilder answerString = new StringBuilder();

        //создаём двумерный массив символов, чтобы разбить firstString и secondString на буквы
        //Память: O(n*m)
        //n - число строк
        //m - число стоблцов
        int[][] concurrenceMatrix = new int[firstString.length() + 1][secondString.length() + 1];
        ArrayList<String> firstStringSegmentsArray = new ArrayList<>();

        //составляем карту пересечений firstString и secondString
        //Время: O(n^2)
        for (int i = 1; i < firstString.length(); i++) {
            for (int j = 1; j < secondString.length(); j++) {
                if (firstString.charAt(i - 1) == secondString.charAt(j - 1)) {
                    concurrenceMatrix[i][j] = concurrenceMatrix[i - 1][j - 1] + 1;
                }
            }
        }

        StringBuilder maxSegment = new StringBuilder();
        StringBuilder currentSegment = new StringBuilder();
        //Время: o(n^2)
        for (int i = 1; i < firstString.length(); i++) {
            for (int j = 1; j < secondString.length(); j++) {
                if (concurrenceMatrix[i][j] == 1) {
                    int iTemp = i;
                    int jTemp = j;
                    currentSegment.append(secondString.charAt(jTemp - 1));
                    while (concurrenceMatrix[iTemp+1][jTemp+1] == concurrenceMatrix[iTemp][jTemp] + 1) {
                        iTemp++;
                        jTemp++;
                        currentSegment.append(secondString.charAt(jTemp - 1));
                    }
                    if (currentSegment.length() > maxSegment.length()) {
                        maxSegment.setLength(0);
                        maxSegment.append(currentSegment);
                    }
                    currentSegment.setLength(0);
                }
            }
        }

        answerString.append(maxSegment);
        return answerString.toString();
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

    /**
     * Сложность
     * Время: O(n*log(log(n)))  n - количество чисел в интервале
     * Память: O(n)   n - количество чисел в интервале
     */
    static public int calcPrimesNumber(int limit) {
//       throw new NotImplementedError();
        //Метод решения: Алгоритм "Решето Эратосфена"
        if(limit > 1) {
            List<Integer> numbersList = new ArrayList<>();
            for (int i = 0; i <= limit; i++) numbersList.add(i);
            numbersList.set(1, 0);

            for (int i = 2; i <= limit; i++) {
                if (numbersList.get(i) != 0) {
                    int j = i + i;
                    while (j <= limit) {
                        numbersList.set(j, 0);
                        j += i;
                    }
                }
            }
            Set<Integer> primeNumbersSet = new HashSet<>(numbersList);
            primeNumbersSet.remove(0);
            return primeNumbersSet.size();
        }
        else return 0;
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

    /**
     * Сложность
     * Время: O(n*m*k)
     * Память: O(n*m)
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        try {
            BufferedReader inputFileReader = new BufferedReader(new FileReader(inputName));

            //Память: O(n)  n - число элементов списка
            List<String> fileLinesList = new ArrayList<>();

            //Время: O(n) n - число строк во входящем файле
            while (inputFileReader.ready()) fileLinesList.add(inputFileReader.readLine());

            int numberOfStrings = fileLinesList.size() + 1;
            int numberOfRows = fileLinesList.get(0).split(" ").length + 1;

            //Память: O(n*m)
            //n - число строк матрицы + 1
            //m - число столбцов матрицы + 1
            Letter[][] lettersMatrix = new Letter[numberOfStrings + 1][numberOfRows + 1];

            //Память: O(n)  n - число посещенных координат
            Set<String> foundWords = new HashSet<>();

            //Заполнение матрицы буквами
            // *по периметру матрица заполняется нулями, чтобы при дальнейшем сравнении не было IndexOutOfBoundException
            //Время: O(n*m)
            //n - число строк матрицы + 1
            //m - число столбцов матрицы + 1
            for (int i = 0; i <= numberOfStrings; i++) {
                for (int j = 0; j <= numberOfRows; j++){
                    if (i == 0) lettersMatrix[i][j] = new Letter('0');
                    else if (j == 0 && i != 0) lettersMatrix[i][j] = new Letter('0');
                    else if (j == numberOfRows) lettersMatrix[i][j] = new Letter('0');
                    else if (i == numberOfStrings) lettersMatrix[i][j] = new Letter('0');
                    else lettersMatrix[i][j] = new Letter(fileLinesList.get(i - 1).split(" ")[j - 1].charAt(0));
                }

            }


            StringBuilder wordBuilder = new StringBuilder();

            //Время: O(n*m*k)
            //n - число слов во входящем множестве
            //m - число строк в матрице + 1
            //k - число столбцов в матрице + 1
            matrixLoop: for (String word : words) {

                //Память: O(n)  n - число букв в слове
                char[] wordLetters = word.toCharArray();
                int letterIndex = 0;

                //обновляем посещаемость каждой буквы. для нового слова все буквы непосещены
                //Время: O(n^2)
                for (int i = 0; i < numberOfStrings; i++) {
                    for (int j = 0; j < numberOfRows; j++) {
                        lettersMatrix[i][j].turnOffVisited();
                    }
                }

                //ищем нужное слово
                 for (int i = 0; i <= numberOfStrings; i++) {
                    for (int j = 0; j <= numberOfRows; j++) {
                        if (lettersMatrix[i][j].letter == (word.charAt(letterIndex))) {
                            int iTemp = i;
                            int jTemp = j;

                            //Память: O(n)  n - число посещенных букв (ячеек матрицы)
                            LinkedList<CoordinatesPair> visitedLettersList = new LinkedList<>();

                            wordBuilder.append(word.charAt(letterIndex));
                            lettersMatrix[i][j].hasVisited = true;
                            while ((lettersMatrix[iTemp - 1][jTemp].letter == (word.charAt(letterIndex + 1)) && !lettersMatrix[iTemp - 1][jTemp].hasVisited) || //север
                                    (lettersMatrix[iTemp + 1][jTemp].letter == (word.charAt(letterIndex + 1)) && !lettersMatrix[iTemp + 1][jTemp].hasVisited) || //юг
                                    (lettersMatrix[iTemp][jTemp - 1].letter == (word.charAt(letterIndex + 1)) && !lettersMatrix[iTemp][jTemp - 1].hasVisited) || //запад
                                    (lettersMatrix[iTemp][jTemp + 1].letter == (word.charAt(letterIndex + 1)) && !lettersMatrix[iTemp][jTemp + 1].hasVisited)) {   //восток
                                if (lettersMatrix[iTemp - 1][jTemp].letter == (word.charAt(letterIndex + 1)) && !lettersMatrix[iTemp - 1][jTemp].hasVisited) {
                                    lettersMatrix[iTemp - 1][jTemp].hasVisited = true;
                                    CoordinatesPair pair = new CoordinatesPair(iTemp - 1, jTemp);
                                    visitedLettersList.add(pair);
                                    iTemp--;
                                } else if ( lettersMatrix[iTemp + 1][jTemp].letter == (word.charAt(letterIndex + 1)) &&
                                            !lettersMatrix[iTemp + 1][jTemp].hasVisited) {
                                    lettersMatrix[iTemp + 1][jTemp].hasVisited = true;
                                    CoordinatesPair pair = new CoordinatesPair(iTemp + 1, jTemp);
                                    visitedLettersList.add(pair);
                                    iTemp++;
                                } else if ( lettersMatrix[iTemp][jTemp - 1].letter == (word.charAt(letterIndex + 1)) &&
                                            !lettersMatrix[iTemp][jTemp - 1].hasVisited) {
                                    lettersMatrix[iTemp][jTemp - 1].hasVisited = true;
                                    CoordinatesPair pair = new CoordinatesPair(iTemp, jTemp - 1);
                                    visitedLettersList.add(pair);
                                    jTemp--;
                                } else if ( lettersMatrix[iTemp][jTemp + 1].letter == (word.charAt(letterIndex + 1)) &&
                                            !lettersMatrix[iTemp][jTemp + 1].hasVisited) {
                                    lettersMatrix[iTemp][jTemp + 1].hasVisited = true;
                                    CoordinatesPair pair = new CoordinatesPair(iTemp, jTemp + 1);
                                    visitedLettersList.add(pair);
                                    jTemp++;
                                }
                                letterIndex++;
                                wordBuilder.append(word.charAt(letterIndex));
                                if (letterIndex == word.length() - 1) break;
                            }
                            if (wordBuilder.toString().equalsIgnoreCase(word)) {
                                foundWords.add(wordBuilder.toString());
                                wordBuilder.setLength(0);
                                continue matrixLoop;
                            }
                            else if (iTemp == i && jTemp == j) {
                                lettersMatrix[i][j].turnOffVisited();
                                letterIndex = 0;
                            }
                            else if ((iTemp != i || jTemp != j) && wordBuilder.length() > 1) {
                                lettersMatrix[i][j].turnOffVisited();
                                j -= 1; //если слово состовлялось но ничего не получилось, возвращаемся на 1 позицю назад в матрице, чтобы при следующей итерации вернуться в букву, из которой начинали
                                visitedLettersList.removeLast(); //при этом удаляем из списка посещенных ячеек последнюю пару посещенных координат (последнюю посещенную букву)
                                for (CoordinatesPair pair : visitedLettersList) { //и затем обнуляем посещаемость всех букв кроме
                                    lettersMatrix[pair.i][pair.j].turnOffVisited(); // последней посещенной
                                }// (у которой не получилось найти соседей), чтобы при следующем обходе снова не наткнуться на неё
                                letterIndex = 0;
                            }

                            wordBuilder.setLength(0);
                        }
                    }
                }
            }
            return foundWords;
        }
        catch (IOException exception) {
            throw new NotImplementedError();
        }
    }

    static class Letter {
        char letter;
        boolean hasVisited = false;

        Letter(char letter) {
            this.letter = letter;
        }

        void turnOffVisited() {
            hasVisited = false;
        }
    }

    static class CoordinatesPair {
        int i;
        int j;

        CoordinatesPair(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
}
