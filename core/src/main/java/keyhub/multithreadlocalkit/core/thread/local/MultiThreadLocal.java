package keyhub.multithreadlocalkit.core.thread.local;

import java.util.function.Consumer;
import java.util.function.Function;

public interface MultiThreadLocal {

    static MultiThreadLocal getInstance() {
        return SimpleMultiThreadLocal.getInstance();
    }

    ThreadId getThreadId();

    <T> void set(String key, T value);

    <T> T get(String key);

    <T, R> R act(String key, Function<T, R> action);

    <T> void act(String key, Consumer<T> action);
}
