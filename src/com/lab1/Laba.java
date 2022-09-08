package com.lab1;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


/**
 * Класс точки входа
 */
public class Laba {

  public static void main(String[] args) {
    UI myUI = new UI();
    myUI.startMenu();
  }
}

/**
 * Интерфейс, реализующий нумерацию вариантов выбора для switch()
 */
interface menuEnum {
   int MANUAL_MATRIX = 1,
       AUTO_MATRIX = 2;

   int PRINT_MATRIX1 = 1,
       PRINT_MATRIX2 = 2,
       PRINT_SUM_MATRIX = 3,
       PRINT_DET = 4,
       RESET = 5,
       EXIT = 6;

}

/**
 * Класс реализующий интерфейс программы
 */
class UI implements menuEnum {

  PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
  Scanner sc = new Scanner(System.in);
  int size = -1;
  Matrix matrix1 = null, matrix2 = null, sumMatrix = null;

  /**
   * Метод сбора данных для работы алгоритма
   */
  public void startMenu() {

    while (this.size == -1) {
      out.println("Введите размер матриц:");
      this.size = getInt();
      if (this.size == -1) {
        this.out.println("Некорректный ввод!");
      }
      if (this.size > 11) {
        this.out.println("Слишком большая матрица, рекурсия не справится :(");
        this.size = -1;
      }
    }
    do {
      this.out.println("""
          1. Ввести матрицы вручную
          2. Сгенерировать матрицы автоматически
          """);
      int menuVariant = getInt();
      switch (menuVariant) {
        case MANUAL_MATRIX -> {
          out.println("Вводите матрицы построчно, числа через пробел");
          out.println("Первая матрица:");
          this.matrix1 = getMatrixFromKeyboard(this.size);
          this.matrix1.print();
          out.println("Вторая матрица:");
          this.matrix2 = getMatrixFromKeyboard(this.size);
          this.matrix2.print();
        }
        case AUTO_MATRIX -> {
          this.matrix1 = new Matrix(this.size);
          this.matrix2 = new Matrix(this.size);
        }
        default -> out.println("Некорректный ввод!");
      }
    } while (this.matrix1 == null & this.matrix2 == null);
    sumMatrix = Matrix.sumMatrix(matrix1, matrix2);

    mainMenu();

  }

  /**
   * Метод выводящй меню основной части алгоритма и реализующий его варианты
   */
  private void mainMenu() {
    int menuVariant;
    do {
      this.out.println("""
          1. Вывести первую матрицу
          2. Вывести вторую матрицу
          3. Вывести сумму матриц
          4. Вывести определитель суммы матриц
          5. Заполнить матрицы заново
          6. Выйти из программы
          """);
      menuVariant = getInt();
      switch (menuVariant) {
        case PRINT_MATRIX1 -> this.matrix1.print();
        case PRINT_MATRIX2 -> this.matrix2.print();
        case PRINT_SUM_MATRIX -> this.sumMatrix.print();
        case PRINT_DET ->
            this.out.printf("Определитель суммы матриц: %d\n", this.sumMatrix.determinant());
        case RESET -> {
          this.size = -1;
          this.matrix1 = null;
          this.matrix2 = null;
          this.sumMatrix = null;
          this.startMenu();
        }
        case EXIT -> {
          this.out.println("До связи...");
          this.sc.close();
        }
        default -> this.out.println("Некорректный ввод!");
      }

    } while (menuVariant != 6);
  }

  /**
   * Метод получения значения int из консоли
   */
  private int getInt() {
    int res;
    try {
      res = this.sc.nextInt();
    } catch (Exception e) {
      res = -1;
    }
    return res;
  }

  /**
   * Метод получения строки, содержащей int значения из консоли
   *
   * @return список int[] содержащий int значения введенные через пробел
   */
  private int[] getIntLine() {

    String scString = this.sc.nextLine();
    String[] scStrings = scString.split(" ");
    int[] scNumbers = new int[scStrings.length];

    try {
      for (int i = 0; i < scStrings.length; i++) {
        scNumbers[i] = Integer.parseInt(scStrings[i]);
      }
    } catch (Throwable t) {
      return new int[]{-1};
    }
    return scNumbers;
  }

  /**
   * Метод, заполняющий матрицу из консоли
   *
   * @param size размер получаемой матрицы
   * @return матрицу, полученную с консоли
   */
  private Matrix getMatrixFromKeyboard(int size) {

    int[][] new_matrix = new int[size][size];
    for (int i = 0; i < size; i++) {
      int[] line;
      do {
        line = getIntLine();
        if ((line.length == 1 & line[0] == -1) | line.length != size) {
          this.out.println("Некорректный ввод!");
        }
      } while (line.length != size);
      new_matrix[i] = line;

    }
    return new Matrix(new_matrix);
  }
}