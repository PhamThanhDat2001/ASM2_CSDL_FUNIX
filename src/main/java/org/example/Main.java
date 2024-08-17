package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);
    private static int[] arr; // Lưu trữ mảng đã nhập từ manualInput
    private static final String OUTPUT_FILE_Bubble = "C:\\Users\\dell\\Desktop\\Bubble_sort.txt";
    private static final String OUTPUT_FILE_Selection = "C:\\Users\\dell\\Desktop\\Selection_sort.txt";
    private static final String OUTPUT_FILE_Insertion = "C:\\Users\\dell\\Desktop\\Insertion_sort.txt";
    private static final String OUTPUT_FILE_Search = "C:\\Users\\dell\\Desktop\\OUTPUT4.TXT";
    private static final String OUTPUT_FILE_SearchEqual = "C:\\Users\\dell\\Desktop\\OUTPUT5.TXT";

    public static void menu() {
        System.out.println("+---------------------------------+");
        System.out.println("| MENU | FX38455@v3.0.0           |");
        System.out.println("+---------------------------------+");

        // In menu
        System.out.println("| 1. Manual Input                 |");
        System.out.println("| 2. File input                   |");
        System.out.println("| 3. Bubble sort                  |");
        System.out.println("| 4. Selection sort               |");
        System.out.println("| 5. Insertion sort               |");
        System.out.println("| 6. Search > value               |");
        System.out.println("| 7. Search = value               |");
        System.out.println("| 0. Exit                         |");

        System.out.println("+---------------------------------+");
    }

    public static void Choice() {
        int choice;
        do {
            menu();
            System.out.print("Chuc nang: ");
            choice = getValidChoose();
            switch (choice) {
                case 1:
                    manualInput();
                    break;
                case 2:
                    fileInput();
                    break;
                case 3:
                    bubbleSort();
                    break;
                case 4:
                    selectionSort();
                    break;
                case 5:
                    insertionSort();
                    break;
                case 6:
                    searchBig();
                    break;
                case 7:
                    searchEqual();
                    break;
                case 0:
                    System.out.println("Thoát chương trình...");
                    break;
            }
        } while (choice != 0);
    }

    public static int getValidChoose() {
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.next());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Yêu cầu nhập số hợp lệ");
                System.out.print("Nhập lại: ");
            }
        }
        return choice;
    }

    // Chức năng 1
    public static void manualInput() {
        int n;

        // Nhập số lượng phần tử
        while (true) {
            System.out.print("Please enter input number of elements: ");
            n = getValidChoose(); // Số lượng phần tử

            if (n > 0) {
                break; // Thoát vòng lặp nếu số lượng phần tử hợp lệ
            } else {
                System.out.println("The number of elements must be greater than 0. Please enter again.");
            }
        }

        arr = new int[n];
        int count = 0;

        // Nhập các phần tử
        while (count < n) {
            System.out.println("Please enter " + (n - count) + " more elements: ");
            int[] tempArray = new int[n];
            int index = 0;

            // Nhập phần tử vào mảng tạm thời
            //có thể dễ dàng yêu cầu nhập lại mà không làm thay đổi dữ liệu đã lưu.
            while (index < n) {
                System.out.print("Enter element " + (index + 1) + ": ");
                int element = getValidChoose();
                tempArray[index++] = element;
            }

            // Xác nhận số lượng phần tử nhập vào
            System.out.print("You have entered: ");
            for (int num : tempArray) {
                System.out.print(num + " ");
            }
            System.out.println();

            System.out.print("Is this correct? (yes/no): ");
            String response = scanner.next().trim().toLowerCase();

            if (response.equals("yes")) {
                arr = tempArray; // Lưu mảng vào arr nếu người dùng xác nhận đúng
                break;
            } else {
                System.out.println("Please enter the elements again.");
                count = 0; // Reset đếm để nhập lại
            }
        }

        // Lưu mảng vào file sau khi nhập
        System.out.print("Please enter the file path to save the data: ");
        String filePath = scanner.next();
        writeArrayToFile(filePath, arr);

        System.out.println("Data has been saved to " + filePath);
    }

    // Chức năng 2
    public static void fileInput() {
        System.out.println("Choice 2: File input");

        // Yêu cầu người dùng nhập đường dẫn tệp
        System.out.print("Please enter the file path to read the data: ");
        String filePath = scanner.next();

        // Đọc dữ liệu từ tệp và lưu vào mảng 'arr'
        arr = readArrayFromFile(filePath);

        if (arr != null) {
            if (arr.length == 0) {
                System.out.println("The file is empty.");
            } else {
                System.out.print("Data from file: ");
                for (int num : arr) {
                    System.out.print(num + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("Failed to read data from the file. Please check if the file exists and try again.");
        }

        System.out.println("Mời bạn chọn tiếp chức năng: ");
    }

    // Hàm này chịu trách nhiệm đọc dữ liệu từ tệp và
    // chuyển dữ liệu thành một mảng số nguyên (int[]).
    public static int[] readArrayFromFile(String filePath) {
        ArrayList<Integer> list = new ArrayList<>();
        // fileScanner.hasNextInt() sẽ kiểm tra xem tệp
        // có số nguyên tiếp theo để đọc hay không. Nếu có, phần tử đó sẽ được thêm vào list
        try (Scanner fileScanner = new Scanner(new File(filePath))) {
            while (fileScanner.hasNextInt()) {
                list.add(fileScanner.nextInt());
            }
            // Chuyển ArrayList thành mảng
            return list.stream().mapToInt(i -> i).toArray();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            e.printStackTrace();
            return null;
        }
    }

    // ghi các phần tử của một mảng số nguyên (int[] array) vào một tệp
    public static void writeArrayToFile(String filePath, int[] array) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (int num : array) {
                writer.write(num + " ");
            }
            System.out.println("Data has been written to " + filePath);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    // Chức năng 3 Sắp xếp nổi bọt (Bubble Sort)
    public static void bubbleSort() {
        System.out.println("Choice 3: Bubble sort");

        if (arr == null || arr.length == 0) {
            System.out.println("No input data available. Please use Manual Input first.");
            return;
        }

        int[] b = arr.clone(); // Sao chép mảng arr sang mảng b
        try (FileWriter writer = new FileWriter(OUTPUT_FILE_Bubble)) {
            boolean swapped;
            for (int i = 0; i < b.length - 1; i++) {
                swapped = false;
                for (int j = 0; j < b.length - 1 - i; j++) {
                    if (b[j] > b[j + 1]) {
                        // Hoán đổi b[j] và b[j+1]
                        int temp = b[j];
                        b[j] = b[j + 1];
                        b[j + 1] = temp;
                        swapped = true;
                    }
                }
                // In kết quả hiện tại ra màn hình và ghi vào tệp
                System.out.print("Step " + (i + 1) + ": ");
                for (int num : b) {
                    System.out.print(num + " ");
                }
                System.out.println();
                writer.write("Step " + (i + 1) + ": ");
                for (int num : b) {
                    writer.write(num + " ");
                }
                writer.write("\n");
                if (!swapped) break; // Nếu không có sự hoán đổi, dừng lại
            }
            System.out.println("Bubble sort completed. Steps are saved in " + OUTPUT_FILE_Bubble);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }

        System.out.println("Mời bạn chọn tiếp chức năng: ");
    }

    // Chức năng 4 Thuật toán sắp xếp chọn (Selection Sort)
    public static void selectionSort() {
        System.out.println("Choice 4: Selection sort");

        if (arr == null || arr.length == 0) {
            System.out.println("No input data available. Please use Manual Input first.");
            return;
        }

        int[] b = arr.clone(); // Sao chép mảng arr sang mảng b
        try (FileWriter writer = new FileWriter(OUTPUT_FILE_Selection)) {
            for (int i = 0; i < b.length - 1; i++) {
                int minIndex = i;
                for (int j = i + 1; j < b.length; j++) {
                    if (b[j] < b[minIndex]) {
                        minIndex = j;
                    }
                }
                // Hoán đổi b[i] và b[minIndex]
                int temp = b[i];
                b[i] = b[minIndex];
                b[minIndex] = temp;

                // In kết quả hiện tại ra màn hình và ghi vào tệp
                System.out.print("Step " + (i + 1) + ": ");
                for (int num : b) {
                    System.out.print(num + " ");
                }
                System.out.println();
                writer.write("Step " + (i + 1) + ": ");
                for (int num : b) {
                    writer.write(num + " ");
                }
                writer.write("\n");
            }
            System.out.println("Selection sort completed. Steps are saved in " + OUTPUT_FILE_Selection);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }

        System.out.println("Mời bạn chọn tiếp chức năng: ");
    }

    // Chức năng 5 thuật toán sắp xếp chèn (Insertion Sort)
    public static void insertionSort() {
        System.out.println("Choice 5: Insertion sort");

        if (arr == null || arr.length == 0) {
            System.out.println("No input data available. Please use Manual Input first.");
            return;
        }

        int[] b = arr.clone(); // Sao chép mảng arr sang mảng b

        try (FileWriter writer = new FileWriter(OUTPUT_FILE_Insertion)) {
            for (int i = 1; i < b.length; i++) {
                int key = b[i];
                int j = i - 1;

                // Di chuyển các phần tử của mảng b[0..i-1] mà lớn hơn key lên một vị trí về phía sau
                while (j >= 0 && b[j] > key) {
                    b[j + 1] = b[j];
                    j--;
                }
                b[j + 1] = key;

                // In kết quả hiện tại ra màn hình và ghi vào tệp
                System.out.print("Step " + i + ": ");
                for (int num : b) {
                    System.out.print(num + " ");
                }
                System.out.println();

                writer.write("Step " + i + ": ");
                for (int num : b) {
                    writer.write(num + " ");
                }
                writer.write("\n");
            }

            System.out.println("Insertion sort completed. Steps are saved in " + OUTPUT_FILE_Insertion);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }

        System.out.println("Mời bạn chọn tiếp chức năng: ");
    }

    // Chức năng 6 Sử dụng phương pháp tìm kiếm tuần tự
    public static void searchBig() {
        System.out.println("Choice 6: Linear Search");

        // Hướng dẫn nhập vào giá trị tìm kiếm
        System.out.print("Please enter searched input value: ");
        double value = scanner.nextDouble(); // Nhập giá trị thực từ bàn phím

        // Tìm kiếm các phần tử có giá trị lớn hơn value
        StringBuilder positions = new StringBuilder();
        boolean found = false;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > value) {
                if (positions.length() > 0) {
                    positions.append(" ");
                }
                positions.append(i);
                found = true;
            }
        }

        // In kết quả ra màn hình
        if (found) {
            System.out.println("Larger position: " + positions.toString());
        } else {
            System.out.println("There is no value that satisfies the condition.");
        }

        // Lưu kết quả vào tệp
        try (FileWriter writer = new FileWriter(OUTPUT_FILE_Search)) {
            if (found) {
                writer.write("Larger position: " + positions.toString() + "\n");
            } else {
                writer.write("There is no value that satisfies the condition.\n");
            }
            System.out.println("Search results have been saved in " + OUTPUT_FILE_Search);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }

        System.out.println("Mời bạn chọn tiếp chức năng: ");
    }

    // Chức năng 7 Sử dụng phương pháp tìm kiếm nhị phân
    public static void searchEqual() {
        System.out.println("Choice 7: Binary Search");

        if (arr == null || arr.length == 0) {
            System.out.println("No input data available. Please use Manual Input first.");
            return;
        }

        // Tạo mảng mới để lưu mảng đã sắp xếp
        int[] sortedArray = arr.clone();

        // Đảm bảo rằng mảng mới đã được sắp xếp
        if (!isSorted(sortedArray)) {
            System.out.println("The array is not sorted. Sorting the array...");
            bubbleSort(sortedArray); // Sử dụng mảng mới để sắp xếp
        }

        System.out.print("Please enter searched input value: ");
        double value = scanner.nextDouble();

        int left = 0;
        int right = sortedArray.length - 1;
        int position = -1;

        // Tìm kiếm nhị phân trên mảng đã sắp xếp
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (sortedArray[mid] == value) {
                position = mid;
                break;
            } else if (sortedArray[mid] < value) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // Ghi kết quả tìm kiếm vào tệp
        try (FileWriter writer = new FileWriter(OUTPUT_FILE_SearchEqual)) {
            if (position != -1) {
                System.out.println("The right position: " + position);
                writer.write("The right position: " + position + "\n");
            } else {
                System.out.println("The value does not exist!");
                writer.write("The value does not exist!\n");
            }
            System.out.println("Search results have been saved in " + OUTPUT_FILE_SearchEqual);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }

        System.out.println("Mời bạn chọn tiếp chức năng: ");
    }

    // Phương pháp sắp xếp để sắp xếp mảng được truyền vào
    public static void bubbleSort(int[] array) {
        int n = array.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (array[i - 1] > array[i]) {
                    int temp = array[i - 1];
                    array[i - 1] = array[i];
                    array[i] = temp;
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
    }

    // Hàm kiểm tra xem mảng đã được sắp xếp hay chưa
    public static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Choice();
    }
}
