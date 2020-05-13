package util;

public class XLCircularException extends XLException{

    public XLCircularException() {
        super("Circular dependency detected!");
    }
}
