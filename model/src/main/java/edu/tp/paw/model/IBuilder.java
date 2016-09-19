package edu.tp.paw.model;

public interface IBuilder<T> {

	/**
	 * Creates a new builder
	 * @return The created builder
	 */
	public T build();
}
