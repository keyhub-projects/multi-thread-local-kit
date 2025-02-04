package keyhub.multithreadlocalkit.core.thread.local;

import keyhub.multithreadlocalkit.core.etc.UuidV7Generator;

public record ThreadId(
        String id
) {
    public static ThreadId getInstance(){
        return new ThreadId(UuidV7Generator.generate().toString());
    }

    public String get(){
        return this.id;
    }
}
