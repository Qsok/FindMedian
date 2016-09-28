/**
 * Created by NKrutovskoy on 23.10.2015.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FindMedian {
    static ArrayList<Long> firstArr = new ArrayList<Long>();
    static ArrayList<Long> arrY = new ArrayList<Long>();
    static String errMsg = new String();
    static boolean arrUploaded = false;

    //запускает меню,сортирует введенные массивы и запускает и выводит данные алгоритма
    public static void main(String[] args) throws FileNotFoundException, NumberFormatException {
        firstArr.add(0l);//добавляем ноль в начало массива,для корректного определения четности в алгоритме
        arrY.add(0l);
        boolean exit = false;
        Scanner sc = new Scanner(System.in);
        while (!exit || !arrUploaded) {
            try {
                System.out.println("Choose way to load arrays:\n1-Load from file\n2-User's input\n3-Exit");
                switch (Integer.valueOf(sc.nextLine())) {
                    case 1:
                        exit = readFromFile(sc);
                        break;
                    case 2:
                        exit = ManualEnter(sc);
                        break;
                    case 3:
                        exit = true;
                        sc.close();
                        System.exit(0);
                    default:
                        throw new NumberFormatException();
                }
            } catch (NumberFormatException f) {
                System.out.println("Wrong command");
            }

        }
        quickSort(firstArr, 0, firstArr.size() - 1);
        quickSort(arrY, 0, arrY.size() - 1);
        for (int x = 1; x < firstArr.size(); x++) {
            System.out.println(firstArr.get(x) + "    " + arrY.get(x));
        }
        System.out.println("Median equals "+findMedian(firstArr, arrY));
        sc.close();
    }



    //меню чтения из файла
    static boolean readFromFile(Scanner sc) throws FileNotFoundException {
        File file;
        String fileName = "";
        int menuChoice;
        System.out.println("Please enter the way to file");
        fileName = sc.nextLine();
        file = new File(fileName);
        if (!file.exists())
            errMsg = "Wrong adress " + fileName;
        while (!file.exists()) {
            System.out.println(errMsg + "\nYou can:\n" +
                    "1-Enter address\n" +
                    "2-Return to main menu");
            try {
                menuChoice = Integer.parseInt(sc.nextLine());
                switch (menuChoice) {
                    case 1:
                        System.out.println("Please enter the way to file");
                        fileName = sc.next();
                        fileName.trim();
                        errMsg = "Wrong adress" + fileName;
                        break;
                    case 2:
                        return false;
                }
            } catch (NumberFormatException n) {
                errMsg = "Wrong command";
            }
        }
        if (!read(file))
            return false;
        else
            return true;
    }

    //считывание из файла (считывает только лонги, другие символы пропускает)
    public static boolean read(File file) throws FileNotFoundException {
        try {
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try {
                String s;
                s = in.readLine();
                try {
                    for (String x : s.split(" "))
                        firstArr.add(Long.parseLong(x));
                } catch (NumberFormatException a) {
                }
                s = in.readLine();
                for (String x : s.split(" "))
                    try {
                        arrY.add(Long.parseLong(x));
                    } catch (NumberFormatException a) {
                    }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            firstArr.clear();
            arrY.clear();
            throw new RuntimeException(e);
        }
        if (firstArr.size() == arrY.size()) {
            arrUploaded = true;
            System.out.println("Succesfull read from file");
            return true;
        } else {
            System.out.println("Unsuccessful read");
            firstArr.clear();
            arrY.clear();
            return false;
        }

    }

    //функция ручного ввода массива
    //воспринимает
    static boolean ManualEnter(Scanner sc) {
        int size, i = 0;
        System.out.println("Please enter size of arrrays");
        try {
            size = sc.nextInt();
            System.out.println("Please enter first arrray");
            while (firstArr.size() < size + 1)
                firstArr.add(sc.nextLong());
            System.out.println("Please enter second arrray");
            while (arrY.size() < size + 1)
                arrY.add(sc.nextLong());
        }catch(NumberFormatException e){
            System.out.println("Wrong input.");
            return false;
        }catch(InputMismatchException e){
            System.out.println("Wrong input.");
            return false;
        }
        arrUploaded = true;
        return true;
    }

    //первая часть алгоритма - сокращающает оба массива до размера 2, далее из этихъ четырех чисел создается массив и находится там медиана
    //описание алгоритма см.Документацию
    //возвращает медиану 2х массивов в double
    static double findMedian() {
        int rightBorderFirstArr = firstArr.size() - 1;
        int leftBorderFirstArr = 1;//правая и левая граница массива Х
        int medianFirstArr, medianSecondArr;//медианы обрезаемых массивов
        long temp;
        double tempMedian, median = 0;
        //проверка на слишком маленький массив , длинной в один или ноль элементов
        if (firstArr.size() == 2) {
            median = (double) (firstArr.get(1) + arrY.get(1)) / 2;
            return median;
        }
        else if(firstArr.size()==1)
            return 0;
        while ((rightBorderFirstArr - leftBorderFirstArr) != 1) {
            medianFirstArr = (rightBorderFirstArr + leftBorderFirstArr) / 2;
            medianSecondArr = arrY.size() - medianFirstArr;
            temp = firstArr.get(medianFirstArr) + arrY.get(medianSecondArr);
            tempMedian = temp / 2;
            if (firstArr.get(medianFirstArr) >= arrY.get(medianSecondArr)) {
                rightBorderFirstArr = medianFirstArr;
                if (arrY.get(medianSecondArr + 1) >= tempMedian && firstArr.get(medianFirstArr - 1) <= tempMedian) {
                    return AccurateMedianChoice(firstArr.get(medianFirstArr - 1), firstArr.get(medianFirstArr), arrY.get(medianSecondArr + 1), arrY.get(medianSecondArr));
                }
            } else if (firstArr.get(medianFirstArr) <= arrY.get(medianSecondArr)) {
                leftBorderFirstArr = medianFirstArr;
                if (arrY.get(medianSecondArr - 1) <= tempMedian && firstArr.get(medianFirstArr + 1) >= tempMedian) {
                    return AccurateMedianChoice(firstArr.get(medianFirstArr + 1), firstArr.get(medianFirstArr), arrY.get(medianSecondArr - 1), arrY.get(medianSecondArr));
                }
            }
        }
        return AccurateMedianChoice(firstArr.get(leftBorderFirstArr), firstArr.get(rightBorderFirstArr), arrY.get(arrY.size() - leftBorderFirstArr), arrY.get(arrY.size() - rightBorderFirstArr));
    }

    static boolean checkIfArrTooSmall(){

    }

    //вторая часть алгоритма , точно выбирающая медиану объединенных массивов
    //в параметры получает элементы обрезанных массивов
    static double AccurateMedianChoice(long first, long second, long third, long forth) {
        double mediana;
        ArrayList<Long> list = new ArrayList<Long>();
        list.add(first);
        list.add(second);
        list.add(third);
        list.add(forth);
        quickSort(list, 0, 3);
        mediana = (double)(list.get(1) + list.get(2))/2;
        return mediana;
    }

    //стандартная быстрая сортировка ArrayList
    public static void quickSort(ArrayList<Long> arr, int low, int high) {
        if (arr == null || arr.size() == 0)
            return;
        if (low >= high)
            return;
        int middle = low + (high - low) / 2;
        long pivot = arr.get(middle);
        int i = low, j = high;
        while (i <= j) {
            while (arr.get(i) < pivot) {
                i++;
            }
            while (arr.get(j) > pivot) {
                j--;
            }
            if (i <= j) {
                long temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
                i++;
                j--;
            }
        }
        if (low < j)
            quickSort(arr, low, j);

        if (high > i)
            quickSort(arr, i, high);
    }

}
