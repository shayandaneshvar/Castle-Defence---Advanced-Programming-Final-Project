package ir.ac.kntu.model;

import ir.ac.kntu.model.Soldiers.SoldierKind;

/**
 * @author TOP
 */
public class UnknownTypeException extends Throwable {

    /**
     * @param kind
     */
    public UnknownTypeException(SoldierKind kind) {
        super("Unknown kind Exception!" + kind);
    }

    /**
     * @param object
     */
    public UnknownTypeException(Object object) {
        super(object.toString());
    }
}