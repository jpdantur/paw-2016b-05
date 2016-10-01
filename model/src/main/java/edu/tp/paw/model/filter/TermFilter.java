package edu.tp.paw.model.filter;

import java.util.Optional;

public class TermFilter {
	
	private final Optional<String> term;
	
	/* package */ TermFilter(TermFilterBuilder builder) {
		this.term = builder.getTerm();
	}
	
	public Optional<String> getTerm() {
		return term;
	}
	
}
