package io.quarkus.ts.openshift.common.after;

/**
 * Interface to perform an action after an OpenShift failure.
 */
public interface OnOpenShiftFailureAction {

    /**
     * Action to perform.
     */
    void execute();
}
