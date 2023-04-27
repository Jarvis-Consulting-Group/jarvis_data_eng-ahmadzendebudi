package ca.jrvs.apps.utils;

import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LambdaStreamExcImpl implements LambdaStreamExc {

  Logger logger = LoggerFactory.getLogger(LambdaStreamExcImpl.class);

  @Override
  public Stream<String> createStrStream(String... strings) {
    return Stream.of(strings);
  }

  @Override
  public Stream<String> toUpperCase(String... strings) {
    return Stream.of(strings).map(value -> value.toUpperCase());
  }

  @Override
  public Stream<String> filter(Stream<String> stringStream, String pattern) {
    Pattern _pattern = Pattern.compile(pattern);
    return stringStream.filter(value -> _pattern.matcher(value).find());
  }

  @Override
  public IntStream createIntStream(int... arry) {
    return IntStream.of(arry);
  }

  @Override
  public <E> List<E> toList(Stream<E> stream) {
    return stream.collect(Collectors.toList());
  }

  @Override
  public List<Integer> toList(IntStream intStream) {
    return intStream.boxed().collect(Collectors.toList());
  }

  @Override
  public IntStream createIntStream(int start, int end) {
    return IntStream.rangeClosed(start, end);
  }

  @Override
  public DoubleStream squareRootIntStream(IntStream intStream) {
    return intStream.mapToDouble(intVal -> Math.sqrt(intVal));
  }

  @Override
  public IntStream getOdd(IntStream intStream) {
    return intStream.filter(intVal -> intVal % 2 == 1);
  }

  @Override
  public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
    return val -> logger.info(prefix + val + suffix);
  }

  @Override
  public void printMessages(String[] messages, Consumer<String> printer) {
    Stream.of(messages).forEach(printer);
  }

  @Override
  public void printOdd(IntStream intStream, Consumer<String> printer) {
    intStream.filter(val -> val % 2 == 1).mapToObj(Integer::toString).forEach(printer);
  }

  @Override
  public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
    return ints.flatMap(val -> val.stream());
  }
  
}
