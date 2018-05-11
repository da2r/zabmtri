package zabmtri;

import java.lang.reflect.Field;

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
	
	public boolean hasEquals(String property) {
		try {
			if (alpha == null) {
				if (beta == null) {
					return true;
				} else {
					return false;
				}
			}  else {
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
		}  else {
			if (y == null) {
				return false;
			} else {
				return x.equals(y);
			}
		}
	}
	
}
