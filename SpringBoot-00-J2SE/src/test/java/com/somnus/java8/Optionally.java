package com.somnus.java8;

import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * A container object which may or may not contain a non-null value.
 * If a value is present, {@code isPresent()} will return {@code true} and
 * {@code get()} will return the value.
 *
 * <p>Additional methods that depend on the presence or absence of a contained
 * (return a default value if value not present) and
 * of code if the value is present).
 *
 * <p>This is a <a href="../lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code Optional} may have unpredictable results and should be avoided.
 *
 * @since 1.8
 */
public final class Optionally<T> {
    /**
     * Common instance for {@code empty()}.
     */
    private static final Optionally<?> EMPTY = new Optionally<>();

    /**
     * If non-null, the value; if null, indicates no value is present
     */
    private final T value;

    /**
     * 初始化为 true  true &&其它布尔值时由其它布尔值决定真假
     */
    private Predicate<T> predicate = t -> true;

    /**
     * 添加一个校验策略，可以无限续杯😀
     *
     * @param predicate the predicate
     * @return the validator
     */
    public Optionally<T> with(Predicate<T> predicate) {
        this.predicate = this.predicate.and(predicate);
        return this;
    }

    /**
     * 执行校验
     *
     * @return the boolean
     */
    public boolean validate() {
        return predicate.test(value);
    }

    private Optionally() {
        this.value = null;
    }

    public static<T> Optionally<T> empty() {
        @SuppressWarnings("unchecked")
        Optionally<T> t = (Optionally<T>) EMPTY;
        return t;
    }

    /**
     * Constructs an instance with the value present.
     *
     * @param value the non-null value to be present
     * @throws NullPointerException if value is null
     */
    private Optionally(T value) {
        this.value = Objects.requireNonNull(value);
    }

    public static <T> Optionally<T> of(T value) {
        return new Optionally<>(value);
    }

    public static <T> Optionally<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    /**
     *  传入的值不为null，且经过布尔运算后为true，则执行lambda表达式语句
     * @param predicate
     * @param consumer
     */
    public void ifTrue(Predicate<? super T> predicate, Consumer<? super T> consumer) {
        Objects.requireNonNull(predicate);
        if (value != null && predicate.test(value)){
            consumer.accept(value);
        }
    }

    /**
     *  传入的值不为null，且经过布尔运算后为false，则执行lambda表达式语句
     * @param predicate
     * @param consumer
     */
    public void ifFalse(Predicate<? super T> predicate, Consumer<? super T> consumer) {
        Objects.requireNonNull(predicate);
        if (value != null && predicate.negate().test(value)){
            consumer.accept(value);
        }
    }


    public <X extends Throwable> void trueThrow(Predicate<? super T> predicate, Supplier<? extends X> exceptionSupplier) throws X {
        Objects.requireNonNull(predicate);
        if (value != null && predicate.test(value)) {
            throw exceptionSupplier.get();
        }
    }

    public <X extends Throwable> void falseThrow(Predicate<? super T> predicate, Supplier<? extends X> exceptionSupplier) throws X {
        Objects.requireNonNull(predicate);
        if (value != null && predicate.negate().test(value)) {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 传入的值为null，则执行语句
     * @param consumer
     */
    public void ifNull(Consumer<? super T> consumer) {
        if (value == null)
            consumer.accept(value);
    }

    /**
     * 传入的值对象类型为String Collection Map，且不为空，则执行lambda表达式语句
     * @param consumer
     */
    public void ifNotEmpty(Consumer<? super T> consumer) {
        if (value != null && value instanceof String && ((String) value).length() > 0) {
            consumer.accept(value);
        } else if (value != null && value instanceof Collection && ! ((Collection<?>) value).isEmpty()) {
            consumer.accept(value);
        } else if (value != null && value instanceof Map && ((Map<?,?>) value).size() > 0) {
            consumer.accept(value);
        }
    }

    /**
     * 传入的值对象类型为String Collection Map，且为空，则执行lambda表达式语句
     * @param consumer
     */
    public void ifEmpty(Consumer<? super T> consumer) {
        if(value == null){
            consumer.accept(value);
        } else if (value instanceof String && ((String) value).length() == 0) {
            consumer.accept(value);
        } else if (value instanceof Collection && ((Collection<?>) value).isEmpty()) {
            consumer.accept(value);
        } else if (value instanceof Map && ((Map<?,?>) value).size() == 0) {
            consumer.accept(value);
        }
    }

    public Optionally<T> emptyMap(UnaryOperator<T> mapper) {
        Objects.requireNonNull(mapper);
        if(value == null){
            return Optionally.ofNullable(mapper.apply(value));
        } else if (value instanceof String && ((String) value).length() == 0) {
            return Optionally.ofNullable(mapper.apply(value));
        } else if (value instanceof Collection && ((Collection<?>) value).isEmpty()) {
            return Optionally.ofNullable(mapper.apply(value));
        } else if (value instanceof Map && ((Map<?,?>) value).size() == 0) {
            return Optionally.ofNullable(mapper.apply(value));
        } else{
            return Optionally.ofNullable(value);
        }
    }

    public <X extends Throwable> void existThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            throw exceptionSupplier.get();
        }
    }

    public <X extends Throwable> Optionally<T> existThrow(Predicate<? super T> predicate, Supplier<? extends X> exceptionSupplier) throws X {
        Objects.requireNonNull(predicate);
        if (value != null && predicate.negate().test(value)) {
            throw exceptionSupplier.get();
        }else{
            return this;
        }
    }

    /**
     * 传入的值为null，则返回默认值，否则返回一个新值
     * @param supplier
     */
    public <R> R orElse(R r, Supplier<? extends R> supplier) {
        if (value != null)
            return supplier.get();

        return r;
    }

    /**
     * 传入的值为null，则返回默认值
     * 传入的值不为null并且为判断为true，返回supplier1
     * 传入的值不为null并且为判断为false，返回supplier2
     * @param <R>
     * @return
     */
    public <R> R orElse(Predicate<T> predicate, R r, Supplier<? extends R> supplier1, Supplier<? extends R> supplier2) {
        if (value != null && predicate.test(value)){
            return supplier1.get();
        } else if (value != null && predicate.negate().test(value)){
            return supplier2.get();
        }
        return r;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Optionally)) {
            return false;
        }

        Optionally<?> other = (Optionally<?>) obj;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value != null
            ? String.format("Optional[%s]", value)
            : "Optional.empty";
    }

}
