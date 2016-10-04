package edu.tp.paw.model.filter;

import java.util.Optional;

import edu.tp.paw.model.IBuilder;

public class TermFilterBuilder implements IBuilder<TermFilter> {

	private Optional<String> term = Optional.empty();
	
	private FilterBuilder filterBuilder;
	
	public TermFilterBuilder(FilterBuilder filterBuilder) {
		this.filterBuilder = filterBuilder;
	}
	
	public TermFilterBuilder text(String term) {
		this.term = Optional.ofNullable(term);
		return this;
	}
	
	public FilterBuilder and() {
		return filterBuilder;
	}
	
	public FilterBuilder end() {
		return filterBuilder;
	}
	
	public Optional<String> getTerm() {
		return term;
	}
	
	public TermFilter build() {
		return new TermFilter(this);
	}
	
}
