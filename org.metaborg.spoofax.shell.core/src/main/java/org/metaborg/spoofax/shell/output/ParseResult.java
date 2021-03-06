package org.metaborg.spoofax.shell.output;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.metaborg.core.context.IContext;
import org.metaborg.core.messages.IMessage;
import org.metaborg.spoofax.core.stratego.IStrategoCommon;
import org.metaborg.spoofax.core.unit.ISpoofaxParseUnit;
import org.metaborg.spoofax.shell.commands.IReplCommand;
import org.spoofax.interpreter.terms.IStrategoTerm;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Represents a {@link ParseResult} as returned by the {@link IReplCommand}.
 * Wraps a {@link ISpoofaxParseUnit}.
 */
public class ParseResult extends AbstractSpoofaxTermResult<ISpoofaxParseUnit> {

    /**
     * Create a {@link ParseResult}.
     * @param common  the {@link IStrategoCommon} service
     * @param unit    the wrapped {@link ISpoofaxParseUnit}
     */
    @AssistedInject
    public ParseResult(IStrategoCommon common,
                       @Assisted ISpoofaxParseUnit unit) {
        super(common, unit);
    }

    @Override
    public Optional<IStrategoTerm> ast() {
        return Optional.ofNullable(unit().ast());
    }

    @Override
    public Optional<IContext> context() {
        return Optional.empty();
    }

    @Override
    public List<IMessage> messages() {
        return StreamSupport.stream(unit().messages().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public String sourceText() {
        return unit().input().text();
    }

    @Override
    public boolean valid() {
        return unit().valid() && unit().success();
    }

}
