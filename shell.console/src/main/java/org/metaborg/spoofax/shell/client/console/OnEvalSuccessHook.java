package org.metaborg.spoofax.shell.client.console;

import java.util.function.Consumer;

import org.metaborg.spoofax.shell.client.IDisplay;
import org.metaborg.spoofax.shell.commands.StyledText;

import com.google.inject.Inject;

/**
 * Called upon success of an evaluation command.
 */
class OnEvalSuccessHook implements Consumer<StyledText> {
    private IDisplay display;

    /**
     * @param display The {@link IDisplay} to show the result on.
     */
    @Inject
    OnEvalSuccessHook(IDisplay display) {
        this.display = display;
    }

    @Override
    public void accept(StyledText s) {
        display.displayResult(s);
    }
}