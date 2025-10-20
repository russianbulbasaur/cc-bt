package bdecode;

public interface BdecodedObject {
    int stringLength();
    BdecodedObjectType type();
    Object toJavaObject();
}
