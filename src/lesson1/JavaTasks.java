package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        //        throw new NotImplementedError();
        try {
            BufferedReader inputFileReader = new BufferedReader(new FileReader(inputName)); //создаем буфер для считывания входного файла
            String line;  //сюда будет помещаться считываемая строка
            List<String> timeArrayList = new ArrayList<>();  //сюда будут помещаться строки из файла
            List<Integer> translatedTimeList = new ArrayList<>(); //список для отсортированных секунд
            //считываем строки в файле пока они есть
            while (inputFileReader.ready()) {
                line = inputFileReader.readLine();
                timeArrayList.add(line);
            }

            //переводим время в каждой строке файла в секунды
            for (String time : timeArrayList) translatedTimeList.add(translateTimeToSeconds(time));

            //сортируем секунды с помощью insertion sort
            for (int j = 1; j < translatedTimeList.size(); j++) {
                int comparableTime = translatedTimeList.get(j);
                int i = j - 1;
                while (i >= 0 && translatedTimeList.get(i) > comparableTime) {
                    translatedTimeList.set(i + 1, translatedTimeList.get(i));
                    i--;
                }
                translatedTimeList.set(i + 1, comparableTime);
            }

            //очищаем старый списсок и помещаем туда отсортированные секунды, переведенные в 12-часовой формат
            timeArrayList.clear();
            for (Integer time : translatedTimeList) timeArrayList.add(translateSecondsToTime(time));

            //создаем файл
            createFileFromStringArray(timeArrayList, outputName);
        }
        catch (IOException exception) {
            exception.printStackTrace();
            //        throw new NotImplementedError();
        }
    }

    //----------------------------------вспомогательные функции для sortTimes()-----------------------------------------
    private static int translateTimeToSeconds(String time) {
        int answerSeconds = 0;

        int hour = Integer.parseInt(time.split(" ")[0].split(":")[0]);
        int minutes = Integer.parseInt(time.split(" ")[0].split(":")[1]);
        int seconds = Integer.parseInt(time.split(" ")[0].split(":")[2]);
        String moon = time.split(" ")[1];

        if (moon.equalsIgnoreCase("pm")) answerSeconds += 12 * 3600;
        if (hour == 12) hour = 0;
        answerSeconds =  answerSeconds +
                        hour * 3600 +
                        minutes * 60 +
                        seconds;

        return answerSeconds;
    }

    private static String translateSecondsToTime(int time) {
        StringBuilder answerTime = new StringBuilder();
        String answerMoon = "AM";
        int hour = Math.round(time / 3600);
        int minutes = Math.round((time - (hour * 3600)) / 60);
        int seconds = Math.round(time - (hour * 3600) - (minutes * 60));
        if (hour >= 12) {
            answerMoon = "PM";
            hour -= 12;
        }

        String answerHour = Integer.toString(hour);
        String answerMinutes = Integer.toString(minutes);
        String answerSeconds = Integer.toString(seconds);

        if (hour == 0) answerHour = "12";
        else if (hour < 10) answerHour = "0" + hour;
        if (minutes < 10) answerMinutes = "0" + minutes;
        if (seconds < 10) answerSeconds = "0" + seconds;


        answerTime.append(answerHour);
        answerTime.append(":");
        answerTime.append(answerMinutes);
        answerTime.append(":");
        answerTime.append(answerSeconds);
        answerTime.append(" ");
        answerTime.append(answerMoon);

        return answerTime.toString();
    }

    //------------------------------------------------------------------------------------------------------------------

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
//        throw new NotImplementedError();

        //суть:
        //переводим 1ые буквы адресов в int и сравниваем
        //если числовые коды сравниваемых букв совпадают, то переходим к сравнению вторых букв в словах

        try {
            BufferedReader inputFileReader = new BufferedReader(new FileReader(inputName)); //создаем буфер для считывания входного файла
            String fileLine;  //сюда будет помещаться считываемая строка
            List<String> fileLinesArrayList = new ArrayList<>();//сюда будут помещаться строки из файла
            List<String> addressesArrayList = new ArrayList<>();
            List<String> namesArrayList = new ArrayList<>();

            while (inputFileReader.ready()) {
                fileLine = inputFileReader.readLine();
                fileLinesArrayList.add(fileLine);
            }

            for (String line : fileLinesArrayList) {
                namesArrayList.add(line.split("-")[0].trim());
                addressesArrayList.add(line.split("-")[1].trim());
            }

            //юзаем quick sort
            quickSortString(addressesArrayList, 0, addressesArrayList.size() - 1);

            //создаем файл
            createFileFromStringArray(addressesArrayList, outputName);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    //----------------------------------вспомогательные функции для sortAddresses()-------------------------------------
    public static void quickSortString(List<String> list, int indexFrom, int indexTo) {
        //addressesList - массив, который будем перебирать
        //indexFrom - индекс элемента с которого начнется перебор
        //indexTo - индекс элемента на котором закончится перебор

        //проверка индексов. условие не сработает когда поступит массив из 1 элемента, который уже не надо сортировать
        if (indexFrom < indexTo) {
            int partitionIndex = partitionString(list, indexFrom, indexTo);

            //сортировка для 1ой части массива (слева от опорного элемента)
            quickSortString(list, indexFrom, partitionIndex - 1);

            //сортировка для 2ой части массива (справа от опорного элемента)
            quickSortString(list, partitionIndex, indexTo);
        }
    }

    //функция выбора опорного элемента. возвращает индекс элемента, по которому массив будет разделяться на две части (на два подмассива)
    public static int partitionString(List<String> list, int indexFrom, int indexTo) {
        int startIndex = indexFrom;
        int endIndex = indexTo;

        String pivot = list.get(indexFrom);

        while (startIndex <= endIndex) {
            int i = 0; //индекс для букв в слове

            //ищем элемент, который будет больше опорного (в левой части массива)
            while ((startIndex < list.size()) && (int) list.get(startIndex).charAt(i) <= (int) pivot.charAt(i)) {
                if ((int) list.get(startIndex).charAt(i) == (int) pivot.charAt(i) &&
                        i + 1 < list.get(startIndex).length() &&
                        i + 1 < pivot.length()) {
                    i++;
                }
                else {
                    startIndex++;
                    i = 0;
                }
            }
            i = 0;

            //как только нашли элемент больше опорного, ищем элемент, который будет меньше опорного (уже в правой части массива)
            while(endIndex > -1 && (int) list.get(endIndex).charAt(i) >= (int) pivot.charAt(i)) {
                if (    (int) list.get(endIndex).charAt(i) == (int) pivot.charAt(i) &&
                        i + 1 < list.get(endIndex).length() &&
                        i + 1 < pivot.length()) {
                    i++;
                }
                else {
                    endIndex--;
                    i = 0;
                }
            }
            i = 0;


            //после того, как нашли элемент больший опорного и элемент меньший опорного, то меняем их местами
            if (startIndex <= endIndex) {
                swap(list, endIndex, startIndex);
                startIndex++;
                endIndex--;
            }
        }
        return startIndex;
    }

    public static void swap(List<String> list, int index1, int index2) {
       String temp = list.get(index1);
       list.set(index1, list.get(index2));
       list.set(index2, temp);
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) {
//        throw new NotImplementedError();
        try {
            BufferedReader inputFileReader = new BufferedReader(new FileReader(inputName)); //создаем буфер для считывания входного файла
            String fileLine;  //сюда будет помещаться считываемая строка
            List<String> fileLinesArrayList = new ArrayList<>();//сюда будут помещаться строки из файла

            while (inputFileReader.ready()) {
                fileLine = inputFileReader.readLine();
                fileLinesArrayList.add(fileLine);
            }

            quickSortDouble(fileLinesArrayList, 0, fileLinesArrayList.size() - 1);

            createFileFromStringArray(fileLinesArrayList, outputName);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    //------------------------------вспомогательная функция для sortTemperature-----------------------------------------
    public static void quickSortDouble(List<String> list, int indexFrom, int indexTo) {
        if (indexFrom < indexTo) {
            int partitionIndex = partitionDouble(list, indexFrom, indexTo);

            //сортировка для 1ой части массива (слева от опорного элемента)
            quickSortDouble(list, indexFrom, partitionIndex - 1);

            //сортировка для 2ой части массива (справа от опорного элемента)
            quickSortDouble(list, partitionIndex, indexTo);
        }
    }

    private static final Random random = new Random(Calendar.getInstance().getTimeInMillis());

    public static int partitionDouble(List<String> list, int indexFrom, int indexTo) {
        int leftIndex = indexFrom;
        int rightIndex = indexTo;

        String pivot = list.get(leftIndex + random.nextInt(rightIndex - leftIndex + 1));

        while (leftIndex <= rightIndex) {

            //ищем элемент, который будет больше опорного (в левой части массива)
            while (Double.parseDouble(list.get(leftIndex)) <= Double.parseDouble(pivot)) { leftIndex++; }

            //как только нашли элемент больше опорного, ищем элемент, который будет меньше опорного (уже в правой части массива)
            while(Double.parseDouble(list.get(rightIndex)) >= Double.parseDouble(pivot)) { rightIndex--; }

            //после того, как нашли элемент больший опорного и элемент меньший опорного, то меняем их местами
            if (leftIndex <= rightIndex) {
                swap(list, rightIndex, leftIndex);
                leftIndex++;
                rightIndex--;
            }
        }
        return rightIndex;
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }


    public static void createFileFromStringArray(List<String> list, String fileName) throws IOException{
        //создаём файл
        File outputFile = new File(fileName);
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        //записываем в него отсортированный массив
        FileWriter outputFileWriter = new FileWriter(outputFile);
        for (String address : list) {
            outputFileWriter.append(address);
            outputFileWriter.append("\n");
            outputFileWriter.flush();
        }
    }
}


