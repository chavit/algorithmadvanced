package splitfindmin;

public class Element implements ElementParent {
	Element next, prev;
	ElementParent parent;
	int value;

	Element(int value) {
		this.value = value;
	}

	void setNext(Element nextE, boolean b) {
		if (b) {
			next = nextE;
		} else {
			prev = nextE;
		}
	}

	public void removeNext() {
		if (next != null) {
			next.prev = null;
			next = null;
		}
	}

	public void removePrev() {
		if (prev != null) {
			prev.next = null;
			prev = null;
		}
	}

	public int getMin() {
		return parent.getMin();
	}

	public void update(int d) {
		value = Math.min(value, d);
		parent.update(d);
	}

}
	
//
//	SubList initS(int leftSize, int levelNum, boolean isForward) {
//		SubListElement head, tail;
//		if (leftSize <= 3) {
//			
//			parent = new SubListElement(value);
//			head = new SubListElement((SubListElement) parent);
//			if (leftSize == 1) {
//				tail = null;
//			} else {
//				tail = (isForward ? next : prev).initS(leftSize - 1, levelNum,
//						isForward);
//			}
//		} else {
//			Element pos = this;
//			int a = 2 * AkkermanTable.getInstance().get(levelNum,
//					AkkermanTable.getInstance().a(levelNum, leftSize));
//			int len = leftSize / a;
//			Element headE = null;
//			Element prevE = null;
//			for (int i = 0; i < len; i++) {
//				Element cur = new Element(Integer.MAX_VALUE);
//				for (int j = 0; j < a; pos = isForward ? pos.next : pos.prev, j++) {
//					cur.value = Math.min(pos.value, cur.value);
//					pos.parent = cur;
//				}
//				if (prevE != null) {
//					cur.setNext(prevE, !isForward);
//					prevE.setNext(cur, isForward);
//				} else {
//					headE = cur;
//				}
//				prevE = cur;
//			}
//			head = new SubListElement(headE.initS(len, levelNum - 1,
//					isForward));
//			tail = pos == null ? null : pos.initS(leftSize - a * len, levelNum,
//					isForward);
//		}
//		head.setNext(tail, isForward);
//		if (tail != null) {
//			tail.setNext(head, !isForward);
//		}
//		return head;
//	}
//
//
//	@Override
//	public SubListElement[] splitBefore(int levelNum) {
//		if (prev == null) {
//			return new SubListElement[] { null,
//					parent.splitBefore(levelNum - 1)[1].from };
//		} else {
//			return prev.splitAfter(levelNum);
//		}
//
//	}
//
//	@Override
//	public SubListElement[] splitAfter(int levelNum) {
//		if (next == null) {
//			return new SubListElement[] {
//					parent.splitAfter(levelNum - 1)[0].from, null };
//		}
//		SubListElement[] p1 = splitByElement(levelNum);
//		SubListElement left = getLeft(p1[0], p1[2]);
//		SubListElement right = getRight(p1[1], p1[2]);
//		left = addLeftGroups(left, p1[2], levelNum);
//		right = addRightGroups(right, p1[2], levelNum);
//		next.prev = null;
//		next = null;
//		return new SubListElement[] { left, right };
//	}
//
//	private SubListElement addRightGroups(SubListElement right,
//			SubListElement me, int levelNum) {
//		int rightSize = 0;
//		Element last = this;
//		while (last.next != null && last.next.parent == parent) {
//			last = last.next;
//			rightSize++;
//		}
//		if (rightSize > 0) {
//			SubListElement tailAdd = last.initS(rightSize,
//					levelNum, false);
//			tailAdd.head().setFrom(me.from);
//			if (right != null) {
//				right.prev = tailAdd;
//			}
//			tailAdd.next = right;
//			right = tailAdd;
//		}
//		return right == null ? null : right.head();
//	}
//
//	private SubListElement addLeftGroups(SubListElement left,
//			SubListElement me, int levelNum) {
//		int leftSize = 1;
//		Element first = this;
//		while (first.prev != null && first.prev.parent == parent) {
//			first = first.prev;
//			leftSize++;
//		}
//		SubListElement headAdd = first.initS(leftSize, levelNum,
//				true);
//		headAdd.setFrom(me.from);
//		if (left != null) {
//			left.next = headAdd;
//			headAdd.prev = left;
//		}
//		return headAdd.head();
//	}
//
//	private SubListElement getRight(SubListElement head,
//			SubListElement me) {
//		SubListElement res = me.next;
//		if (res != null) {
//			res.prev = null;
//		}
//		if (head != null) {
//			SubListElement addition = new SubListElement(head);
//			addition = me.from;
//			if (res != null) {
//				res.prev = addition;
//				addition.next = res;
//			}
//			res = addition;
//		}
//		return res;
//	}
//
//	private SubListElement getLeft(SubListElement head,
//			SubListElement me) {
//		SubListElement res = me.prev;
//		if (res != null) {
//			res.next = null;
//		}
//		if (head != null) {
//			SubListElement addition = new SubListElement(head);
//			addition = me.from;
//			if (res != null) {
//				res.next = addition;
//				addition.prev = res;
//			}
//			res = addition;
//		}
//		return res;
//	}
//
//	private SubListElement[] splitByElement(int levelNum) {
//		SubListElement[] p1 = parent.splitBefore(levelNum - 1);
//		SubListElement[] p2 = parent.splitAfter(levelNum - 1);
//		return new SubListElement[] { p1[0], p2[1], p1[1].from };
//	}
//
//	@Override
//	public int getMin() {
//		return parent.getMin();
//	}
//
//	@Override
//	public void update(int d) {
//		value = Math.min(value, d);
//	}

