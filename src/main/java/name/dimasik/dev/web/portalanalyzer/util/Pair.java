package name.dimasik.dev.web.portalanalyzer.util;

/**
 * Holds two entities with types described by T and K.
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public class Pair<T, K> {

	private final T first;
	private final K second;

	/**
	 * Construct new {@link Pair} 
	 * @param first The first
	 * @param second The second
	 */
	public Pair(T first, K second) {
		this.first = first;
		this.second = second;
	}
	
	/**
	 * Get the first
	 * @return The first
	 */
	public T getFirst() {
		return first;
	}

	/**
	 * Get the second
	 * @return The second
	 */
	public K getSecond() {
		return second;
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("Pair ").append(System.identityHashCode(this))
				.append(" ['first':").append(first)
				.append(", 'second':").append(second).append("]").toString();
	}
}
