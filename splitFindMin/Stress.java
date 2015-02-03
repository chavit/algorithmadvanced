import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import splitfindmin.AkkermanTable;
import splitfindmin.SplitFindMin;

public class Stress {
	static Random rnd = new Random(239);

	public static void main(String[] args) {
		System.out.println("Akkerman Test");
		for (int i = 1; i <= 5; i++) {
			for (int j = 0; j <= 5; j++) {
				System.out.print(AkkermanTable.getInstance().get(i, j) + " ");
			}
			System.out.println();
		}

		System.out.println("Inverse Akkerman Test");
		for (int i = 1; i < 10; i++) {
			System.out.print(AkkermanTable.getInstance().alpha(i, i) + " ");
		}

		System.out.println();
		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < 100; j++) {
				System.out.print(AkkermanTable.getInstance().a(i, j) + " ");
			}
			System.out.println();
		}
		// for (int i = 1; i < 150; i++) {
		// checkShort(i,100);
		// }

		// for (int i = 2000; i < 2200; i++) {
		//	checkShort(i, 100);
		//}
		//	for (int i = 0; i < 3; i++ ){
		//		perfomanseTest(1000000);
		//	}
			for (int i = 0; i < 1; i++ ){
				perfomanseTest(10000000);
			}

	}

	private static void perfomanseTest(int n) {
		int[] a = new int[n];
		List<Integer> splits = new ArrayList<>();
		for (int i =0; i< n; i++) {
			a[i] = rnd.nextInt(1000000000);
			splits.add(i);
		}
		Collections.shuffle(splits,rnd);
		long tm = System.currentTimeMillis();
		SplitFindMin findMin = new SplitFindMin(a);
		for (int i = 0; i < n; i++) {
			if (i % 10000 == 0) {
				System.err.println(i);
			}
			findMin.split(splits.get(i));
//			if (rnd.nextBoolean()) {
//				findMin.getMin(rnd.nextInt(n));
//			} else {
//				findMin.update(rnd.nextInt(n), rnd.nextInt(1000000000));
//			}
		}
		System.err.println(System.currentTimeMillis() - tm);
	}

	private static void checkShort(int n, int iters) {
		for (int it = 1; it < iters; it++) {
			int[] a = new int[n];
			for (int i = 0; i < n; i++) {
				a[i] = rnd.nextInt((int) (1e+9));
			}
			SplitFindMin structure = new SplitFindMin(a);
			SplitFindMinSlow check = new SplitFindMinSlow(a);
			if (!eq(n, check, structure)) {
				throw new AssertionError();
			}
			List<Integer> splits = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				splits.add(i);
			}
			Collections.shuffle(splits, rnd);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (rnd.nextInt(n) == 0) {
						structure.update(j, rnd.nextInt((int) 1e+9));
						check.update(j, rnd.nextInt((int) 1e+9));
					}
				}
				structure.split(splits.get(i));
				check.split(splits.get(i));
				if (!eq(n, check, structure)) {
					throw new AssertionError();
				}
				if (rnd.nextBoolean()) {
					int pos = rnd.nextInt(n);
					structure.split(pos);
					check.split(pos);
				}
				if (!eq(n, check, structure)) {
					throw new AssertionError();
				}
			}
		}
		System.out.println("Checked len " + n);
	}

	private static boolean eq(int n, SplitFindMinSlow check,
			SplitFindMin structure) {
		int[] res = check.allMinimums();
		for (int i = 0; i < n; i++) {
			res[i] = structure.getMin(i);
		}
		return true;
	}

}
