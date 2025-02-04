package keyhub.multithreadlocalkit.core.context;

import keyhub.multithreadlocalkit.core.thread.local.MultiThreadLocal;
import keyhub.multithreadlocalkit.core.thread.local.ThreadId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class SimpleMultiThreadLocalManager implements MultiThreadLocalManager {
    private final Map<ThreadId, MultiThreadLocal> multiThreadLocalMap = new ConcurrentHashMap<>();

    public MultiThreadLocal generateThreadLocal() {
        MultiThreadLocal threadLocal = MultiThreadLocal.getInstance();
        multiThreadLocalMap.put(threadLocal.getThreadId(), threadLocal);
        return threadLocal;
    }

    public void clear() {
        multiThreadLocalMap.clear();
    }

    public void close(ThreadId threadId) {
        remove(threadId);
    }

    public MultiThreadLocal remove(ThreadId threadId) {
        return multiThreadLocalMap.remove(threadId);
    }

    @Override
    public <T, R> R act(ThreadId threadId, String key, Function<T, R> action) {
        if(!this.multiThreadLocalMap.containsKey(threadId)) {
            throw new IllegalStateException("Multithread thread id " + threadId + " not exist");
        }
        return doAction(threadId, key, action);
    }

    private synchronized <T, R> R doAction(ThreadId threadId, String key, Function<T, R> action) {
        MultiThreadLocal threadLocal = this.multiThreadLocalMap.get(threadId);
        return threadLocal.act(key, action);
    }

    @Override
    public <T> void act(ThreadId threadId, String key, Consumer<T> action) {
        if(!this.multiThreadLocalMap.containsKey(threadId)) {
            throw new IllegalStateException("Multithread thread id " + threadId + " not exist");
        }
        doAction(threadId, key, action);
    }

    private synchronized <T> void doAction(ThreadId threadId, String key, Consumer<T> action) {
        MultiThreadLocal threadLocal = this.multiThreadLocalMap.get(threadId);
        threadLocal.act(key, action);
    }
}
