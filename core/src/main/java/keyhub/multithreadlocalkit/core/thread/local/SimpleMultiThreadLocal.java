package keyhub.multithreadlocalkit.core.thread.local;

import keyhub.multithreadlocalkit.core.context.MultiThreadTracer;
import keyhub.multithreadlocalkit.core.etc.ObjectRecord;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class SimpleMultiThreadLocal implements MultiThreadLocal {
    private final ThreadId threadId;
    private final Map<String, ObjectRecord<?>> statusMap = new ConcurrentHashMap<>();

    static SimpleMultiThreadLocal getInstance() {
        return new SimpleMultiThreadLocal(ThreadId.getInstance());
    }

    protected SimpleMultiThreadLocal(ThreadId threadId) {
        this.threadId = threadId;
        MultiThreadTracer.setThreadId(threadId);
    }

    @Override
    public ThreadId getThreadId() {
        return threadId;
    }

    @Override
    public <T> void set(String key, T value) {
        this.statusMap.put(key, ObjectRecord.from(value));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String key) {
        if (!this.statusMap.containsKey(key)) {
            return null;
        }
        ObjectRecord<?> record = this.statusMap.get(key);
        try {
            return (T) record.getObjectType().cast(record.get());
        } catch (ClassCastException e) {
            throw new ClassCastException("Cannot cast " + record.getObjectType().getName() + " to inferred type.");
        }
    }

    @Override
    public <T, R> R act(String key, Function<T, R> action) {
        if (!this.statusMap.containsKey(key)) {
            throw new NoSuchElementException();
        }
        return doAction(key, action);
    }

    private synchronized <T, R> R doAction(String key, Function<T, R> action) {
        T object = get(key);
        return action.apply(object);
    }

    @Override
    public <T> void act(String key, Consumer<T> action) {
        if (!this.statusMap.containsKey(key)) {
            throw new NoSuchElementException();
        }
        doAction(key, action);
    }

    private synchronized <T> void doAction(String key, Consumer<T> action) {
        T object = get(key);
        action.accept(object);
    }
}
