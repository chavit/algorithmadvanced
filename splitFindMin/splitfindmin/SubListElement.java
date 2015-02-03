package splitfindmin;

public class SubListElement {
	SubListElement prev, next;
	Struct inWhich;
	int minValue;

	public SubListElement(Struct head) {
		head.subListVertex = this;
		minValue = head.minValue;		
	}

	public void setNext(SubListElement cur, boolean forward) {
		if (forward) {
			next = cur;
		} else {
			prev = cur;
		}
	}

	public SubListElement head() {
		SubListElement res = this;
		while (res.prev != null) {
			res = res.prev;
		}
		return res;
	}
}