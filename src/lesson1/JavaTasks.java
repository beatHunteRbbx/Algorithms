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

    /**
     * Сложность
     * Время: О(n^2)
     * Память: O(n)
     */
    static public void sortTimes(String inputName, String outputName) {
        //        throw new NotImplementedError();
        try (BufferedReader inputFileReader = new BufferedReader(new FileReader(inputName))){;
             //создаем буфер для считывания входного файла
            String line;  //сюда будет помещаться считываемая строка

            //Память: O(n)  n - количество элементов списка
            List<String> timeArrayList = new ArrayList<>();  //сюда будут помещаться строки из файла

            //Память: O(n)  n - количество элементов списка
            List<Integer> translatedTimeList = new ArrayList<>(); //список для отсортированных секунд

            //считываем строки в файле пока они есть
            //Время: O(time)  time - количество строк во входящем файле
            while (inputFileReader.ready()) {
                line = inputFileReader.readLine();
                timeArrayList.add(line);
            }

            //переводим время в каждой строке файла в секунды
            //Время: O(time)   time - количество строк во входящем файле
            for (String time : timeArrayList) translatedTimeList.add(translateTimeToSeconds(time));

            //сортируем секунды с помощью insertion sort
            //Время: O(n^2)    n - количество элементов в списке translatedTimeList
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
            //Время: O(n)
            for (Integer time : translatedTimeList) timeArrayList.add(translateSecondsToTime(time));

            //создаем файл
            //Время: O(n)
            createFileFromStringArray(timeArrayList, outputName);
        }
        catch (IOException exception) {
            throw new NotImplementedError();
        }
    }

    //----------------------------------вспомогательные функции для sortTimes()-----------------------------------------
    //Время: O(1)
    private static int translateTimeToSeconds(String time) {
        int answerSeconds = 0;


        String[] arrTime = time.split(" ")[0].split(":");
        int hour = Integer.parseInt(arrTime[0]);
        int minutes = Integer.parseInt(arrTime[1]);
        int seconds = Integer.parseInt(arrTime[2]);
        String moon = time.split(" ")[1];

        if (moon.equalsIgnoreCase("pm")) answerSeconds += 12 * 3600;
        if (hour == 12) hour = 0;
        answerSeconds =  answerSeconds +
                        hour * 3600 +
                        minutes * 60 +
                        seconds;

        return answerSeconds;
    }

    //Время O(1)
    private static String translateSecondsToTime(int time) {
        StringBuilder answerTime = new StringBuilder();
        String answerMoon = "AM";
        int hour = time / 3600;
        int minutes = time % 3600 / 60;
        int seconds = time % 3600 % 60;
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

    /**
     * Сложность
     * Время: O(m*n*k)   ( O(n^3) )
     * Память: O(m*n*k)  ( O(n^3) )
     */
    static public void sortAddresses(String inputName, String outputName) {
        try {
            BufferedReader inputFileReader = new BufferedReader(new FileReader(inputName)); //создаем буфер для считывания входного файла
            String fileLine;  //сюда будет помещаться считываемая строка

            //Память: O(n)   n - количество элементов списка
            List<String> fileLinesArrayList = new ArrayList<>();//сюда будут помещаться строки из файла

            //Память: O(n*m*k)
            //n - число ключей главной мапы
            //m - число ключей вложенной мапы
            //k - число элементов в списке каждого ключа вложенной мапы
            Map<String, Map<Integer, List<String>>> addressesMap = new HashMap<>();

            //Время: O(n)   n - число строк во входящем файле
            while (inputFileReader.ready()) {
                fileLine = inputFileReader.readLine();
                String address = fileLine.split(" - ")[1].split(" ")[0];
                int houseNumber = Integer.parseInt(fileLine.split(" - ")[1].split(" ")[1]);
                String name = fileLine.split("-")[0].trim();
                if (addressesMap.containsKey(address)) {
                    if (addressesMap.get(address).containsKey(houseNumber)) {
                        addressesMap.get(address).get(houseNumber).add(name);
                    }
                    else {
                        addressesMap.get(address).put(houseNumber, new LinkedList<>());
                        addressesMap.get(address).get(houseNumber).add(name);
                    }
                }
                else {
                    addressesMap.put(address, new HashMap<>());
                    addressesMap.get(address).put(houseNumber, new LinkedList<>());
                    addressesMap.get(address).get(houseNumber).add(name);
                }
                fileLinesArrayList.add(fileLine);
            }

            //Память: O(n)  n - число элементов списка
            List<String> streetsList = new LinkedList<>();
            streetsList.addAll(addressesMap.keySet());

            //Время: O(n*log(n)) n - число элементов streetsList
            streetsList.sort(String::compareTo);

            //Время: O(m*n*k)   ( O(n^3) )
            //n - число элементов streetList
            //m - число элементов houseNumbers
            //k - число элементов names
            List<String> sortedAddressesList = new LinkedList<>();
            for (String street : streetsList) {
                StringBuilder fullAddress = new StringBuilder();
                fullAddress.append(street).append(" ");
                Map<Integer, List<String>> housesAndNamesMap = addressesMap.get(street);
                List<Integer> houseNumbers = new LinkedList<>();
                houseNumbers.addAll(addressesMap.get(street).keySet());
                for (Integer houseNumber : houseNumbers) {
                    fullAddress.append(houseNumber).append(" - ");
                    List<String> names = new ArrayList<>();
                    names.addAll(addressesMap.get(street).get(houseNumber));
                    names.sort(String::compareTo);
                    for (String name : names) {
                        fullAddress.append(name).append(", ");
                    }
                    sortedAddressesList.add(fullAddress.deleteCharAt(fullAddress.length() - 2).toString().trim());
                    fullAddress.setLength(0);
                    fullAddress.append(street).append(" ");
                }
            }

            //создаем файл
            //Время: O(n)
            createFileFromStringArray(sortedAddressesList, outputName);
        }
        catch (IOException exception) {
            throw new NotImplementedError();
        }
    }

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

    /**
     * Сложность
     * Время: O(n^2)
     * Память: O(1)
     */
    static public void sortTemperatures(String inputName, String outputName) {

        int minTemperature = 273;
        int maxTemperature = 500;
        int arraySize = (minTemperature + maxTemperature) * 10 + 1;
        //Память: O(1), так как исходя из условия задания можно точно расчитать размерность массива
        int[] arrTemperature = new int[arraySize];
        try (BufferedReader inputFileReader = new BufferedReader(new FileReader(inputName))) {
            //Время: O(n)   n - число строк во входящем файле
            while (inputFileReader.ready()) {
                int currentTemperature = ((int) (Double.parseDouble(inputFileReader.readLine()) * 10)) + 2730;
                arrTemperature[currentTemperature]++;
            }
            File outputFile = new File(outputName);
            if (!outputFile.exists()) outputFile.createNewFile();
            BufferedWriter outputFileWriter = new BufferedWriter(new FileWriter(outputFile));
            //Время: O(n)   n - число повторений текущей температуры (значение в ячейке массива)
            for (int i = 0; i < arrTemperature.length; i++) {
                for (int j = 0; j < arrTemperature[i]; j++) {
                    String outputFileLine = Double.toString((i - 2730) / 10d);
                    outputFileWriter.write(outputFileLine + "\n");
                    outputFileWriter.flush();
                }
            }
        }
        catch (IOException exception) {
            throw new NotImplementedError();
        }
    }

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


