package zabmtri;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Pair<T> {
	public T alpha = null;
	public T beta = null;

	public boolean onAlpha() {
		return alpha != null;
	}

	public boolean onBeta() {
		return beta != null;
	}

	public boolean onBoth() {
		return onAlpha() && onBeta();
	}

	public T any() {
		if (alpha != null) {
			return alpha;
		} else if (beta != null) {
			return beta;
		} else {
			return null;
		}
	}
	
	private T anyBeta() {
		if (beta != null) {
			return beta;
		} else if (alpha != null) {
			return alpha;
		} else {
			return null;
		}
	}


	public boolean hasEquals(String property) {
		try {
			if (alpha == null) {
				if (beta == null) {
					return true;
				} else {
					return false;
				}
			} else {
				if (beta == null) {
					return false;
				} else {
					Field f = any().getClass().getField(property);
					return isEquals(f.get(alpha), f.get(beta));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isEquals(Object x, Object y) {
		if (x == null) {
			if (y == null) {
				return true;
			} else {
				return false;
			}
		} else {
			if (y == null) {
				return false;
			} else {
				return x.equals(y);
			}
		}
	}

	public static <T> List<T> join(List<Pair<T>> list) {
		List<T> join = new ArrayList<T>();
		for (Pair<T> p : list) {
			join.add(p.any());
		}
		return join;
	}
	
	public static <T> List<T> joinBeta(List<Pair<T>> list) {
		List<T> join = new ArrayList<T>();
		for (Pair<T> p : list) {
			join.add(p.anyBeta());
		}
		return join;
	}

}
