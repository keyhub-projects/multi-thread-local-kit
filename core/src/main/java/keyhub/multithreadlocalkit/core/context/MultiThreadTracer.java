package keyhub.multithreadlocalkit.core.context;

import keyhub.multithreadlocalkit.core.thread.local.ThreadId;
import reactor.util.context.Context;
import java.lang.ScopedValue;

public class MultiThreadTracer {
    private static final ThreadLocal<ThreadId> THREAD_LOCAL_THREAD_ID = new ThreadLocal<>();
    private static final ScopedValue<ThreadId> SCOPED_THREAD_ID = ScopedValue.newInstance();

    public static void setThreadId(ThreadId threadId) {
        THREAD_LOCAL_THREAD_ID.set(threadId);
    }

    public static ThreadId getThreadId() {
        return THREAD_LOCAL_THREAD_ID.get();
    }

    public static void clear() {
        THREAD_LOCAL_THREAD_ID.remove();
    }

    /** 가상 스레드 */
    public static void withScopedTrace(ThreadId threadId, Runnable runnable) {
        ScopedValue.where(SCOPED_THREAD_ID, threadId).run(() -> {
            setThreadId(threadId);
            runnable.run();
            clear();
        });
    }

    /** ReactorContext */
    public static Context withReactorContext(Context context) {
        return context.put("threadId", getThreadId());
    }

}
