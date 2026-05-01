package app.validation;

import io.javelin.support.Input;
import io.javelin.support.ValidationRule;

public abstract class AbstractWorkspaceRule extends ValidationRule {
    @Override
    public String name() {
        return "abstract-workspace";
    }

    @Override
    public String key() {
        return "ignored";
    }

    @Override
    protected boolean passes(Input input) {
        return true;
    }

    @Override
    protected String message() {
        return "ignored";
    }
}
