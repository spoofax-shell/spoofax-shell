package org.metaborg.spoofax.shell.commands;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.metaborg.spoofax.shell.client.Repl;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.inject.Provider;

/**
 * Tests the functionality of the {@link ExitCommand}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExitCommandTest {

    @Mock
    private Provider<Repl> provider;
    @Mock
    private Repl repl;

    private ExitCommand exitCommand;

    /**
     * Set up the {@link Repl} mock.
     */
    @Before
    public void setup() {
        when(provider.get()).thenReturn(repl);
        exitCommand = new ExitCommand(provider);
    }

    /**
     * Check the description of the command.
     */
    @Test
    public void testDescription() {
        assertThat(exitCommand.description(), isA(String.class));
    }

    /**
     * Check the execution of the command.
     */
    @Test
    public void testExecute() {
        exitCommand.execute(new String[] { });
        verify(repl).setRunning(false);
    }
}
