package splitfindmin;

public class AkkermanTable {
	private static AkkermanTable instance = null;

	public static AkkermanTable getInstance() {
		return (instance == null) ? instance = new AkkermanTable() : instance;
	}

	private int max = 31;
	private int[][] akkerman;

	public int get(int n, int m) {
		if (n >= max || m >= max) {
			return Integer.MAX_VALUE;
		} else {
			return akkerman[n][m];
		}
	}

	public int a(int row, int n) {
		int j = 0;
		while (2 * akkerman[row][j] <= n) {
			j++;
		}
		return j - 1;
	}

	public int alpha(int n, int m) {
		if (m < n) {
			throw new AssertionError();
		}
		int col = m / n;
		int i = 1;
		while (akkerman[i][col] < n) {
			i++;
		}
		return i;
	}

	private AkkermanTable() {
		akkerman = new int[max][max];
		for (int i = 1; i < max; i++) {
			akkerman[i][0] = 2;
		}
		for (int i = 1; i < max; i++) {
			akkerman[1][i] = (1 << i);
		}
		for (int i = 2; i < max; i++) {
			for (int j = 1; j < max; j++) {
				long prev = akkerman[i][j - 1];
				akkerman[i][j] = (prev >= max) ? Integer.MAX_VALUE / 2
						: akkerman[i - 1][(int) prev];
			}
		}
	}

}
