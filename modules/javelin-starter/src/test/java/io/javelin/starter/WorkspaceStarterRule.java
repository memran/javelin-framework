package io.javelin.starter;

import io.javelin.support.Input;
import io.javelin.support.ValidationRule;

final class WorkspaceStarterRule extends ValidationRule {
    @Override
    public String name() {
        return "workspace-starter";
    }

    @Override
    public String key() {
        return "name";
    }

    @Override
    protected boolean passes(Input input) {
        return input.text("name").isPresent();
    }

    @Override
    protected String message() {
        return "must be present";
    }
}
