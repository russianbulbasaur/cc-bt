package bdecode;

public interface BdecodedObject {
    int stringLength();
    Object data();
    BdecodedObjectType type();
}
