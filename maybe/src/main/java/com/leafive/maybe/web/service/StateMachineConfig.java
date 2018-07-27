package com.leafive.maybe.web.service;

import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import com.leafive.maybe.web.domain.Events;
import com.leafive.maybe.web.domain.States;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
		states.withStates().initial(States.UNPAID).states(EnumSet.allOf(States.class));
	}

	@Override
	public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
		transitions.withExternal().source(States.UNPAID).target(States.WAITING_FOR_RECEIVE).event(Events.PAY).and()
				.withExternal().source(States.WAITING_FOR_RECEIVE).target(States.DONE).event(Events.RECEIVE);
	}
}
