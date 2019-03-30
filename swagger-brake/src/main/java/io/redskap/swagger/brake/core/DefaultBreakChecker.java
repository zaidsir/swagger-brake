package io.redskap.swagger.brake.core;

import static java.util.stream.Collectors.toList;

import java.util.Collection;

import io.redskap.swagger.brake.core.model.Specification;
import io.redskap.swagger.brake.core.rule.BreakingChangeRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultBreakChecker implements BreakChecker {
    private final Collection<BreakingChangeRule<? extends BreakingChange>> rules;
    private final CheckerOptionsProvider checkerOptionsProvider;

    @Override
    public Collection<BreakingChange> check(Specification oldApi, Specification newApi, CheckerOptions checkerOptions) {
        if (log.isDebugEnabled()) {
            rules.stream().map(BreakingChangeRule::getClass).map(Class::getName).forEach(name -> log.debug("Rule configured: {}", name));
        }
        if (oldApi == null) {
            throw new IllegalArgumentException("oldApi must be provided");
        }
        if (newApi == null) {
            throw new IllegalArgumentException("newApi must be provided");
        }
        if (checkerOptions == null) {
            throw new IllegalArgumentException("checkerOptions must be provided");
        }
        checkerOptionsProvider.set(checkerOptions);
        return rules.stream().map(rule -> rule.checkRule(oldApi, newApi)).flatMap(Collection::stream).collect(toList());
    }
}
