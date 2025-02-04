package keyhub.multithreadlocalkit.core.context;

import keyhub.multithreadlocalkit.core.thread.local.ThreadId;

import java.util.function.Consumer;
import java.util.function.Function;

public interface MultiThreadLocalManager {

    <T, R> R act(ThreadId threadId, String key, Function<T, R> action);

    <T> void act(ThreadId threadId, String key, Consumer<T> action);
}
