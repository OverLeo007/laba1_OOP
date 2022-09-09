package com.lab1;

import java.util.Random;

/**
 * Класс реализующий матрицу
 */
public class Matrix {

  /**
   * Размер матрицы
   */
  private final int size;
  /**
   * Массив, хранящий саму матрицу
   */
  private final int[][] mtrx;

  /**
   * Конструктор класса, генерирующий матрицу рандомно
   *
   * @param size размер генерируемой матрицы
   */
  public Matrix(int size) {
    Random rand = new Random();
    this.size = size;
    this.mtrx = new int[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        this.mtrx[i][j] = rand.nextInt(100) * ((int) Math.pow(-1, rand.nextInt(2)));
      }
    }

  }

  /**
   * Конструктор класса, создающий матрицу из массива int[][]
   */
  public Matrix(int[][] matrix) {
    this.mtrx = matrix;
    this.size = matrix[0].length;
  }

  /**
   * Метод, возвращающий минор матрицы по одному из элеметнов первой строки
   *
   * @param mtrx массив матрицы, минор которой получаем
   * @param elJ  индекс элемента первой строки матрицы
   * @return матрицу, представляющую собой минор матрицы
   */
  private static Matrix getMinor(int[][] mtrx, int elJ) {
    int size = mtrx.length;
    int curRow = 0, curCol;
    int[][] minor = new int[size - 1][size - 1];
    for (int i = 0; i < size; i++) {
      curCol = 0;
      if (i == 0) {
        continue;
      }
      for (int j = 0; j < size; j++) {
        if (j == elJ) {
          continue;
        }
        minor[curRow][curCol] = mtrx[i][j];
        curCol++;
      }
      curRow++;
    }
    return new Matrix(minor);
  }

  /**
   * Рекурсивный метод, реализующий вычисление определителя матрицы
   *
   * @param matrix матрица, определитель которой необходимо вычислить
   * @return значение определителя матрицы
   */
  private static int determinant(Matrix matrix) {
    int result = 0;
    if (matrix.size == 2) {
      return matrix.mtrx[0][0] * matrix.mtrx[1][1] - matrix.mtrx[0][1] * matrix.mtrx[1][0];
    } else if (matrix.size == 1) {
      return matrix.mtrx[0][0];
    } else {

      for (int i = 0; i < matrix.size; i++) {
        result += ((int) Math.pow((-1), i)) * matrix.mtrx[0][i]
            * determinant(getMinor(matrix.mtrx, i));
      }
    }

    return result;
  }

  /**
   * Метод, позволяющий вычислить определитель матрицы, текущего экземпляра Matrix
   *
   * @return значение определителя матрицы
   */
  public int determinant() {
    return determinant(this);
  }

  /**
   * Cтатический метод, позволяющий вычислить сумму двух матриц
   *
   * @param matrix1 первое слагаемое
   * @param matrix2 второе слагаемое
   * @return экземпляр Matrix, представляющий собой сумму двух матриц - аргументов метода
   */
  public static Matrix sumMatrix(Matrix matrix1, Matrix matrix2) {
    int size = matrix1.size;
    int[][] newMtrx = new int[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        newMtrx[i][j] = matrix1.mtrx[i][j] + matrix2.mtrx[i][j];
      }
    }
    return new Matrix(newMtrx);
  }

  /**
   * Метод вывода матрицы на экран
   */
  public void print() {
    var out = System.out;
    for (int[] line : this.mtrx) {
      for (int elem : line) {
        out.printf("%5d", elem);
      }
      out.print("\n");
    }

  }


}
