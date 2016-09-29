package edu.tp.paw.model.filter;

import java.util.Optional;

public class Range<T extends Comparable<T>> {
	
	private Optional<T> min;
	private Optional<T> max;
	
	private BoundType minBoundType;
	private BoundType maxBoundType;
	
	public enum BoundType {
		OPEN, CLOSED, INF;
	};
	
	private Range() {
	}
	
	public static <T extends Comparable<T>> Range<T> closed(final T minInclusive, final T maxInclusive) {
		Range<T> range = new Range<T>();
		range.min = Optional.ofNullable(minInclusive);
		range.max = Optional.ofNullable(maxInclusive);
		
		if (range.min.isPresent() && range.max.isPresent()) {
			// min > max
			if (range.min.get().compareTo(range.max.get()) > 0) {
				Optional<T> temp = range.min;
				range.min = range.max;
				range.max = temp;
			}
			range.minBoundType = BoundType.CLOSED;
			range.maxBoundType = BoundType.CLOSED;
		} else if (!range.min.isPresent() && !range.max.isPresent()) {
			range.minBoundType = BoundType.INF;
			range.maxBoundType = BoundType.INF;
		} else if (range.min.isPresent()) {
			// max range is not present
			range.minBoundType = BoundType.CLOSED;
			range.maxBoundType = BoundType.INF;
		} else {
			range.minBoundType = BoundType.INF;
			range.maxBoundType = BoundType.CLOSED;
		}
		
		return range;
	}
	
	public static <T extends Comparable<T>> Range<T> closedOpen(final T minInclusive, final T max) {
		Range<T> range = new Range<T>();
		range.min = Optional.ofNullable(minInclusive);
		range.max = Optional.ofNullable(max);
		
		if (range.min.isPresent() && range.max.isPresent()) {
			// min = max
			if (range.min.get().compareTo(range.max.get()) == 0) {
				range.minBoundType = BoundType.CLOSED;
				range.maxBoundType = BoundType.CLOSED;
			}
			// min > max
			else if (range.min.get().compareTo(range.max.get()) > 0) {
				Optional<T> temp = range.min;
				range.min = range.max;
				range.max = temp;
			}
			range.minBoundType = BoundType.CLOSED;
			range.maxBoundType = BoundType.OPEN;
		} else if (!range.min.isPresent() && !range.max.isPresent()) {
			range.minBoundType = BoundType.INF;
			range.maxBoundType = BoundType.INF;
		} else if (range.min.isPresent()) {
			range.minBoundType = BoundType.CLOSED;
			range.maxBoundType = BoundType.INF;
		} else {
			range.minBoundType = BoundType.INF;
			range.maxBoundType = BoundType.OPEN;
		}
		
		return range;
	}
	
	public static <T extends Comparable<T>> Range<T> openClosed(final T min, final T maxInclusive) {
		Range<T> range = new Range<T>();
		range.min = Optional.ofNullable(min);
		range.max = Optional.ofNullable(maxInclusive);
		
		if (range.min.isPresent() && range.max.isPresent()) {
			// min = max
			if (range.min.get().compareTo(range.max.get()) == 0) {
				range.minBoundType = BoundType.CLOSED;
				range.maxBoundType = BoundType.CLOSED;
			}
			// min > max
			else if (range.min.get().compareTo(range.max.get()) > 0) {
				Optional<T> temp = range.min;
				range.min = range.max;
				range.max = temp;
			}
			range.minBoundType = BoundType.OPEN;
			range.maxBoundType = BoundType.CLOSED;
		} else if (!range.min.isPresent() && !range.max.isPresent()) {
			range.minBoundType = BoundType.INF;
			range.maxBoundType = BoundType.INF;
		} else if (range.min.isPresent()) {
			range.minBoundType = BoundType.OPEN;
			range.maxBoundType = BoundType.INF;
		} else {
			range.minBoundType = BoundType.INF;
			range.maxBoundType = BoundType.CLOSED;
		}
		
		return range;
	}
	
	public static <T extends Comparable<T>> Range<T> all() {
		Range<T> range = new Range<T>();
		range.min = Optional.empty();
		range.max = Optional.empty();
		
		range.minBoundType = BoundType.INF;
		range.maxBoundType = BoundType.INF;
		
		return range;
	}
	
	public static <T extends Comparable<T>> Range<T> greaterThan(final T min) {
		Range<T> range = new Range<T>();
		range.min = Optional.ofNullable(min);
		range.max = Optional.empty();
		
		range.minBoundType = BoundType.OPEN;
		range.maxBoundType = BoundType.INF;
		
		if (!range.min.isPresent()) {
			range.minBoundType = BoundType.INF;
		}
		
		return range;
	}
	
	public static <T extends Comparable<T>> Range<T> greaterThanOrEqual(final T min) {
		Range<T> range = new Range<T>();
		range.min = Optional.ofNullable(min);
		range.max = Optional.empty();
		
		range.minBoundType = BoundType.CLOSED;
		range.maxBoundType = BoundType.INF;
		
		if (!range.min.isPresent()) {
			range.minBoundType = BoundType.INF;
		}
		
		return range;
	}
	
	public static <T extends Comparable<T>> Range<T> lessThan(final T max) {
		Range<T> range = new Range<T>();
		range.min = Optional.empty();
		range.max = Optional.ofNullable(max);
		
		range.minBoundType = BoundType.INF;
		range.maxBoundType = BoundType.OPEN;
		
		if (!range.max.isPresent()) {
			range.minBoundType = BoundType.INF;
		}
		
		return range;
	}
	
	public static <T extends Comparable<T>> Range<T> lessThanOrEqual(final T max) {
		Range<T> range = new Range<T>();
		range.min = Optional.empty();
		range.max = Optional.ofNullable(max);
		
		range.minBoundType = BoundType.INF;
		range.maxBoundType = BoundType.CLOSED;
		
		if (!range.max.isPresent()) {
			range.minBoundType = BoundType.INF;
		}
		
		return range;
	}
	
	public Range<T> gt(final T min) {
		this.min = Optional.ofNullable(min);
		this.minBoundType = BoundType.OPEN;
		if (!this.min.isPresent()) {
			this.minBoundType = BoundType.INF;
		}
		return this;
	}
	
	public Range<T> lt(final T max) {
		this.max = Optional.ofNullable(max);
		this.maxBoundType = BoundType.OPEN;
		if (!this.max.isPresent()) {
			this.maxBoundType = BoundType.INF;
		}
		return this;
	}
	
	public Range<T> gte(final T min) {
		this.min = Optional.ofNullable(min);
		this.minBoundType = BoundType.CLOSED;
		if (!this.min.isPresent()) {
			this.minBoundType = BoundType.INF;
		}
		return this;
	}
	
	public Range<T> lte(final T max) {
		this.max = Optional.ofNullable(max);
		this.maxBoundType = BoundType.CLOSED;
		if (!this.max.isPresent()) {
			this.maxBoundType = BoundType.INF;
		}
		return this;
	}
	
	public Range<T> toInfinity() {
		this.max = Optional.empty();
		this.maxBoundType = BoundType.INF;
		return this;
	}
	
	public Range<T> fromInfinity() {
		this.min = Optional.empty();
		this.minBoundType = BoundType.INF;
		return this;
	}
	
	public boolean hasUpperBound() {
		return this.maxBoundType != BoundType.INF;
	}
	
	public boolean hasLowerBound() {
		return this.minBoundType != BoundType.INF;
	}
	
	public BoundType lowerBoundType() {
		return minBoundType;
	}
	
	public BoundType upperBoundType() {
		return maxBoundType;
	}
	
	public Optional<T> lowerBound() {
		return min;
	}
	
	public Optional<T> upperBound() {
		return max;
	}
	
	public boolean contains(T val) {
		
		// null is not contained is any range
		if (val == null) return false;
		
		if (this.minBoundType == BoundType.INF && this.maxBoundType == BoundType.INF) {
			return true;
		} else if (this.minBoundType == BoundType.INF) {
			if (this.maxBoundType == BoundType.CLOSED) {
				return this.max.get().compareTo(val) >= 0;
			} else {
				return this.max.get().compareTo(val) > 0;
			}
		} else if (this.minBoundType == BoundType.INF) {
			if (this.minBoundType == BoundType.CLOSED) {
				return this.min.get().compareTo(val) <= 0;
			} else {
				return this.min.get().compareTo(val) < 0;
			}
		} else {
			if (this.minBoundType == BoundType.CLOSED && this.maxBoundType == BoundType.CLOSED) {
				return this.max.get().compareTo(val) >= 0 && this.min.get().compareTo(val) <= 0;
			} else if (this.minBoundType == BoundType.CLOSED) {
				return this.max.get().compareTo(val) > 0 && this.min.get().compareTo(val) <= 0;
			} else if (this.maxBoundType == BoundType.CLOSED) {
				return this.max.get().compareTo(val) >= 0 && this.min.get().compareTo(val) < 0;
			} else {
				return this.max.get().compareTo(val) > 0 && this.min.get().compareTo(val) < 0;
			}
		}
		
	}
	
	@Override
	public String toString() {
		
		return (this.minBoundType == BoundType.INF || this.minBoundType == BoundType.OPEN ? "(" : "[")
			+ (this.minBoundType == BoundType.INF ? "-Inf" : this.min.get().toString())
			+ ".."
			+ (this.maxBoundType == BoundType.INF ? "Inf" : this.max.get().toString())
			+ (this.maxBoundType == BoundType.INF || this.minBoundType == BoundType.OPEN ? ")" : "]");
	}

}
