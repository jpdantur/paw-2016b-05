package edu.tp.paw.model.filter;

import java.math.BigDecimal;

import edu.tp.paw.model.IBuilder;


public class PriceFilterBuilder implements IBuilder<PriceFilter> {

	private final Range<BigDecimal> priceRange = Range.greaterThanOrEqual(new BigDecimal(0));
	 
	
	private FilterBuilder filterBuilder;
	
	public PriceFilterBuilder(FilterBuilder filterBuilder) {
		this.filterBuilder = filterBuilder;
	}
	
	public PriceFilterBuilder between(BigDecimal price1, BigDecimal price2) {
		priceRange.between(price1, price2);
		//.gte(price1).lte(price2);
		return this;
	}
	
	public PriceFilterBuilder lt(BigDecimal price1) {
		priceRange.lt(price1);
		return this;
	}
	
	public PriceFilterBuilder lte(BigDecimal price1) {
		priceRange.lte(price1);
		return this;
	}
	
	public PriceFilterBuilder gt(BigDecimal price1) {
		priceRange.gt(price1);
		return this;
	}
	
	public PriceFilterBuilder gte(BigDecimal price1) {
		priceRange.gte(price1);
		return this;
	}
	
	public PriceFilterBuilder any() {
		priceRange.gte(new BigDecimal(0)).toInfinity();
		return this;
	}
	
	public FilterBuilder and() {
		return filterBuilder;
	}
	
	public FilterBuilder end() {
		return filterBuilder;
	}

	public Range<BigDecimal> getPriceRange() {
		return priceRange;
	}
	
	@Override
	public PriceFilter build() {
		return new PriceFilter(this);
	}
	
}
