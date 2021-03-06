package es.ucm.fdi.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;

/**
 * Created by mfreire on 12/01/18.
 */
public class MultiTreeMapTest {

  @Test
  public void add() throws Exception {

  }

  @Test
  public void innerValuesAscending() throws Exception {
    MultiTreeMap<Integer, T> ts = new MultiTreeMap<>();
    T[] array = new T[]{
        new T(3, "3"),
        new T(1, "1"),
        new T(2, "2"),
        new T(3, "3.1"),
        new T(1, "1.1"),
        new T(2, "2.1"),
        new T(3, "3.2"),
        new T(0, "0"),
        new T(2, "2.2")};

    for (T t : array) ts.putValue(t.i, t);
    Arrays.sort(array, new AscendingPlusAge());

    int i = 0;
    for (T t : ts.innerValues()) {
      System.err.println(t + " vs " + array[i]);
      assertEquals("correct order at position " + i, array[i], t);
      i++;
    }
    assertEquals("all elements iterated", array.length, i);
  }

  @Test
  public void innerValuesDescending() throws Exception {
    MultiTreeMap<Integer, T> ts = new MultiTreeMap<>((Integer a, Integer b) -> b.compareTo(a));
    T[] array = new T[]{
        new T(3, "3"),
        new T(1, "1"),
        new T(2, "2"),
        new T(3, "3.1"),
        new T(1, "1.1"),
        new T(2, "2.1"),
        new T(3, "3.2"),
        new T(0, "0"),
        new T(2, "2.2")};

    for (T t : array) ts.putValue(t.i, t);
    Arrays.sort(array, new DescendingPlusAge());

    int i = 0;
    for (T t : ts.innerValues()) {
      System.err.println(t + " vs " + array[i]);
      assertEquals("correct order at position " + i, array[i], t);
      i++;
    }
    assertEquals("all elements iterated", array.length, i);
  }

  /**
   * A simple test class with 2 fields, only the first of which is
   * used to provide ordering
   */
  private static class T implements Comparable<T> {
    private int i;
    private String s;

    public T(int i, String s) {
      this.i = i;
      this.s = s;
    }

    @Override
    public int compareTo(T o) {
      return o.i - i;
    }

    public String toString() {
      return "{" + i + ":" + s + "}";
    }

    @Override
    public boolean equals(Object o) {
      return o instanceof T && ((T) o).i == i && ((T) o).s.equals(s);
    }
  }

  private static class AscendingPlusAge implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
      int rc = o1.i - o2.i;
      return (rc == 0) ? o1.s.compareTo(o2.s) : rc;
    }
  }

  private static class DescendingPlusAge implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
      int rc = o2.i - o1.i;
      return (rc == 0) ? o1.s.compareTo(o2.s) : rc;
    }
  }
}

