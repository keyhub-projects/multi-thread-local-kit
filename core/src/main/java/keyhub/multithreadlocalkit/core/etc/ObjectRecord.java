package keyhub.multithreadlocalkit.core.etc;

public record ObjectRecord<T>(
        Class<T> objectType,
        Object object
) {
    @SuppressWarnings("unchecked")
    public static <T> ObjectRecord<T> from(T object){
        return (ObjectRecord<T>) new ObjectRecord<>(object.getClass(), object);
    }

    public T get() {
        return objectType.cast(object);
    }

    public Class<T> getObjectType() {
        return objectType;
    }
}
