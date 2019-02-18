/*
 * Combination Generator
 * Copyright (C) 2005 David Cesarino de Sousa
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package net.davidcesarino.math;

public class CombinationGenerator {

	private int[] a;
	private int n;
	private int r;
	private long numLeft;
	private long total;

	public CombinationGenerator(int n, int r) {
		if (r > n) {
			throw new IllegalArgumentException();
		}
		if (n < 1) {
			throw new IllegalArgumentException();
		}
		this.n = n;
		this.r = r;
		a = new int[r];
		long nFact = getFactorial(n);
		long rFact = getFactorial(r);
		long nminusrFact = getFactorial(n - r);
		total = nFact / (rFact * nminusrFact);
		reset();
	}

	private void reset() {
		for (int i = 0; i < a.length; i++) {
			a[i] = i;
		}
		numLeft = total;
	}

	public int getNumLeft() {
		return Math.toIntExact(numLeft);
	}

	public boolean hasMore() {
		return numLeft > 0;
	}

	private static long getFactorial(int n) {
		long fact = 1;
		for (int i = n; i > 1; i--) fact *= i;
		return fact;
	}

	public int[] getNext() {
		if (numLeft == total) {
			numLeft -= 1;
			return a;
		}

		int i = r - 1;
		while (a[i] == n - r + i) {
			i--;
		}
		a[i] = a[i] + 1;
		for (int j = i + 1; j < r; j++) {
			a[j] = a[i] + j - i;
		}

		numLeft -= 1;
		return a;
	}

}
