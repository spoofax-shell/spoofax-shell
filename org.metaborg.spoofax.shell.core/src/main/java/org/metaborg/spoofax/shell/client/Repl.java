package org.metaborg.spoofax.shell.client;

import java.awt.Color;
import java.io.IOException;

import org.metaborg.spoofax.shell.commands.CommandNotFoundException;
import org.metaborg.spoofax.shell.commands.ICommandInvoker;
import org.metaborg.spoofax.shell.commands.IReplCommand;
import org.metaborg.spoofax.shell.core.StyledText;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Interactive REPL (Read-Eval-Print Loop) which reads an expression or command typed in by the user
 * via an {@link IEditor}, executes them with a {@link ICommandInvoker} and prints it to an
 * {@link IDisplay}.
 */
public class Repl {
    private ICommandInvoker invoker;
    private IEditor editor;
    private IDisplay display;

    private boolean running;

    /**
     * @param editor
     *            The {@link IEditor} for receiving input.
     * @param display
     *            The {@link IDisplay} for displaying results.
     * @param invoker
     *            The {@link ICommandInvoker} for executing user input.
     */
    @Inject
    Repl(IEditor editor, IDisplay display, ICommandInvoker invoker) {
        this.editor = editor;
        this.display = display;
        this.invoker = invoker;

        // TODO: this does not really belong here.
        this.editor.setPrompt(new StyledText(Color.GREEN, "[In ]: "));
        this.editor.setContinuationPrompt(new StyledText("[...]: "));
    }

    /**
     * Run the Repl, asking for input and sending it for execution.
     *
     * @throws IOException
     *             when an IO error occurs.
     */
    public void run() throws IOException {
        String input;
        running = true;
        while (running && (input = editor.getInput()) != null) {
            input = input.trim();
            if (input.length() == 0) {
                continue;
            }
            try {
                invoker.execute(input);
            } catch (CommandNotFoundException e) {
                display.displayError(e.getMessage());
            }
        }
    }

    /**
     * Exit the Repl.
     */
    static class ExitCommand implements IReplCommand {
        private Provider<Repl> replProvider;

        /**
         * @param replProvider
         *            Provides the Repl instance.
         */
        @Inject
        ExitCommand(Provider<Repl> replProvider) {
            this.replProvider = replProvider;
        }

        @Override
        public String description() {
            return "Exit the REPL session.";
        }

        @Override
        public void execute(String... args) {
            replProvider.get().running = false;
        }
    }
}