package splitfindmin;

public class Struct implements ElementParent {
	SubListElement subListVertex;
	int minValue;

	public Struct(int value) {
		minValue = value;
	}

	public Struct(SubListElement rightV) {
		minValue = Integer.MAX_VALUE;
		while (rightV != null) {
			rightV.inWhich = this;
			minValue = Math.min(minValue, rightV.minValue);
			rightV = rightV.next;
		}
	}

	@Override
	public int getMin() {
		if (subListVertex == null) {
			return minValue;
		} else {
			if (subListVertex.inWhich == null) {
				System.err.println("hi");
				throw new AssertionError();
			}
			return subListVertex.inWhich.getMin();
		}
	}

	@Override
	public void update(int d) {
		minValue = Math.min(d,minValue);
		if (subListVertex != null) {
			subListVertex.minValue = minValue;
			subListVertex.inWhich.update(d);
		}
	}
}
