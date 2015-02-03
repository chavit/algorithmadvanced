public class SplitFindMinSlow {
	boolean[] after;
	int[] a;
	int n;

	public SplitFindMinSlow(int[] a) {
		int n = a.length;
		this.a = a.clone();
		after = new boolean[n];
	}

	public void update(int i, int d) {
		a[i] = Math.min(a[i], d);
	}

	public void split(int i) {
		after[i] = true;
	}

	public int[] allMinimums() {
		int[] res = a.clone();
		for (int i = 0; i < a.length - 1; i++) {
			if (!after[i]) {
				res[i + 1] = Math.min(res[i], res[i + 1]);
			}
		}
		for (int i = a.length - 1; i > 0; i--) {
			if (!after[i-1]) {
				res[i - 1] = Math.min(res[i - 1], res[i]);
			}
		}
		return res;
	}

}
