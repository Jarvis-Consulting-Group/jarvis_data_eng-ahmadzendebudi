package ca.jrvs.apps.utils;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface LambdaStreamExc {

  /**
   * Create a String stream from a String array
   * @param strings
   * @return
   */
  Stream<String> createStrStream(String... strings);

  /**
   * Convert all strings to uppercase
   * @param strings
   * @return
   */
  Stream<String> toUpperCase(String... strings);

  /**
   * Filter the String stream according to a regex pattern
   * @param stringStream
   * @param pattern
   * @return
   */
  Stream<String> filter(Stream<String> stringStream, String pattern);

  /**
   * Create IntStream from Int array
   * @param arry
   * @return
   */
  IntStream createIntStream(int... arry);

  /**
   * Collect values in the stream into a List
   * @param <E>
   * @param stream
   * @return
   */
  <E> List<E> toList(Stream<E> stream);

  /**
   * Collect values in the stream into a List
   * @param intStream
   * @return
   */
  List<Integer> toList(IntStream intStream);

  /**
   * Create an IntStream including integer numbers from start to end
   * @param intStream
   * @return
   */
  IntStream createIntStream(int start, int end);

  /**
   * Map each value to its squere root
   * @param intStream
   * @return
   */
  DoubleStream squareRootIntStream(IntStream intStream);

  /**
   * Filter out even values and return a stream of odd values
   * @param intStream
   * @return
   */
  IntStream getOdd(IntStream intStream);

  /**
   * Return a Consumer<String> that adds a prefix and a suffix to the input string and prints the content to the default logger
   * @param prefix
   * @param suffix
   * @return
   */
  Consumer<String> getLambdaPrinter(String prefix, String suffix);

  /**
   * Print each message with the given printer
   * @param messages
   * @param printer
   */
  void printMessages(String[] messages, Consumer<String> printer);

  /**
   * Print all odd numbers from the stream using the given printer
   * @param intStream
   * @param printer
   */
  void printOdd(IntStream intStream, Consumer<String> printer);

  /**
   * Flatten the nested Integer list into a stream
   * @param ints
   * @return
   */
  Stream<Integer> flatNestedInt(Stream<List<Integer>> ints);
}
