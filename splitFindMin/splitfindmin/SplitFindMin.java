package splitfindmin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import splitfindmin.ElementCompressor.ElementCompressorResult;

public class SplitFindMin {
	private Element[] elements;
	private int level = 0;

	public SplitFindMin(int[] a) {
		int n = a.length;
		level = AkkermanTable.getInstance().alpha(n, n);
		long tm = System.currentTimeMillis();
		elements = new Element[n];
		for (int i = 0; i < n; i++) {
			elements[i] = new Element(a[i]);
		}
		for (int i = 0; i + 1 < n; i++) {
			elements[i].setNext(elements[i + 1], true);
			elements[i + 1].setNext(elements[i], false);
		}
		System.err.println(System.currentTimeMillis() - tm);
		Struct res = initS(elements[0], n, level, true);
	}

	void split(Element element) {
		List<Element> elementPath = new ArrayList<>();
		List<SubListElement> positions = new ArrayList<>();
		List<Struct> structs = new ArrayList<>();
		ElementParent parent = element;
		while (parent instanceof Element) {
			Element e = (Element) parent;
			elementPath.add(e);
			parent = e.parent;
		}
		Struct cur = (Struct) parent;
		while (cur.subListVertex != null) {
			structs.add(cur);
			positions.add(cur.subListVertex);
			cur = cur.subListVertex.inWhich;
		}
		Collections.reverse(positions);
		Collections.reverse(structs);

		if (elementPath.size() != positions.size()
				|| structs.size() != positions.size() || structs.size() > 5) {
			System.err.println(elementPath.size() + " " + positions.size());

			throw new AssertionError();
		}
		removeAfter(elementPath, positions, structs, 0, level);
	}

	Struct[] removeAfter(List<Element> elems, List<SubListElement> positions,
			List<Struct> structs, int i, int level) {
		boolean isNotLast = elems.get(i).next != null
				&& elems.get(i).next.parent == elems.get(i).parent;
		Struct[] parts;
		if (i == elems.size() - 1) {
			parts = new Struct[] { structs.get(i), null };
		} else {
			parts = isNotLast ? removeElement(elems, positions, structs, i + 1,
					level - 1) : removeAfter(elems, positions, structs, i + 1,
					level - 1);
		}
		SubListElement rightV = getBorder(parts[1], positions.get(i), false);
		if (isNotLast) {
			List<Struct> newGroups = getAdd(elems.get(i), level, 0, false);
			rightV = addGroups(rightV, newGroups, false);
		}
		SubListElement leftV = getBorder(parts[0], positions.get(i), true);
		if (isNotLast) {
			List<Struct> newGroups = getAdd(elems.get(i), level, 1, true);
			leftV = addGroups(leftV, newGroups, true);
		}
		elems.get(i).removeNext();
		return new Struct[] { leftV == null ? null : new Struct(leftV.head()),
				rightV == null ? null : new Struct(rightV) };
	}

	Struct[] removeElement(List<Element> elems, List<SubListElement> positions,
			List<Struct> structs, int i, int level) {
		Struct[] parts;
		if (i == elems.size() - 1) {
			parts = new Struct[] { null, null };
		} else {
			parts = removeElement(elems, positions, structs, i + 1, level - 1);
		}
		SubListElement leftV = getBorder(parts[0], positions.get(i), true);
		List<Struct> newGroups = getAdd(elems.get(i), level, 0, true);
		leftV = addGroups(leftV, newGroups, true);
		SubListElement rightV = getBorder(parts[1], positions.get(i), false);
		newGroups = getAdd(elems.get(i), level, 0, false);
		rightV = addGroups(rightV, newGroups, false);
		elems.get(i).removeNext();
		elems.get(i).removePrev();
		return new Struct[] { leftV == null ? null : new Struct(leftV.head()),
				rightV == null ? null : new Struct(rightV) };
	}

	SubListElement addGroups(SubListElement v, List<Struct> newGroups,
			boolean isLeft) {
		for (int j = 0; j < newGroups.size(); j++) {
			SubListElement nw = new SubListElement(newGroups.get(j));
			if (v != null) {
				v.setNext(nw, isLeft);
				nw.setNext(v, !isLeft);
			}
			v = nw;
		}
		return v;
	}

	private List<Struct> getAdd(Element e, int level, int st, boolean isLeft) {
		int size = st;
		Element cur = e;
		Element prev = isLeft ? e.prev : e.next;
		while (prev != null && prev.parent == e.parent) {
			size++;
			cur = prev;
			prev = isLeft ? cur.prev : cur.next;
		}
		return initSubList(cur, size, level, isLeft);
	}

	private SubListElement getBorder(Struct struct, SubListElement old,
			boolean isLeft) {
		SubListElement prev = isLeft ? old.prev : old.next;
		if (struct == null) {
			if (prev != null) {
				prev.setNext(null, isLeft);
			}
			return prev;
		} else {
			SubListElement left = new SubListElement(struct);
			if (prev != null) {
				prev.setNext(left, isLeft);
			}
			left.setNext(prev, !isLeft);
			return left;
		}
	}

	Struct initS(Element start, int left, int level, boolean forward) {
		List<Struct> res = initSubList(start, left, level, forward);
		Struct struct = new Struct(Integer.MAX_VALUE);
		createListAndSetStruct(res, struct, forward);
		return struct;
	}

	private void createListAndSetStruct(List<Struct> res, Struct struct,
			boolean forward) {
		SubListElement prev = new SubListElement(res.get(0));
		struct.minValue = Math.min(res.get(0).minValue, struct.minValue);
		prev.inWhich = struct;
		for (int i = 1; i < res.size(); i++) {
			SubListElement cur = new SubListElement(res.get(i));
			cur.setNext(prev, !forward);
			prev.setNext(cur, forward);
			cur.inWhich = struct;
			struct.minValue = Math.min(res.get(i).minValue, struct.minValue);
			prev = cur;
		}
	}

	List<Struct> initSubList(Element start, int left, int level, boolean forward) {
		List<Struct> res = new ArrayList<>();
		while (left > 3) {
			int a = 2 * AkkermanTable.getInstance().get(level,
					AkkermanTable.getInstance().a(level, left));
			int len = left / a;
			Element prev = null, first = null;
			for (int i = 0; i < len; i++) {
				ElementCompressorResult cRes = ElementCompressor.compressor(
						start, a, forward);
				if (prev != null) {
					prev.setNext(cRes.compressed, forward);
					cRes.compressed.setNext(prev, !forward);
				}
				prev = cRes.compressed;
				start = cRes.pos;
				if (first == null) {
					first = prev;
				}
			}
			res.add(initS(first, len, level - 1, forward));
			left -= len * a;
		}
		while (left > 0) {
			Struct singleton = new Struct(start.value);
			start.parent = singleton;
			res.add(singleton);
			left--;
			start = forward ? start.next : start.prev;
		}
		return res;
	}

	public int getMin(int i) {
		return elements[i].getMin();
	}

	public void update(int i, int d) {
		elements[i].update(d);
	}

	public void split(int i) {
		split(elements[i]);
	}
}
