package com.deadmoose.fairisle.data;

import java.util.List;

import com.google.common.base.Preconditions;

/**
 * Some common Patterns and methods for working with them.
 */
public class Patterns
{
    /** A single stitch. */
    public static final Pattern SOLID = new PatternImpl(1, 1, new Boolean[] { true });

    /**
     * Generates a new Pattern that is a copy of the provided one, repeated n times.
     */
    public static Pattern extend (Pattern pattern, int n)
    {
        Preconditions.checkArgument(n > 0);

        int width = pattern.width();

        Pattern newPattern = new PatternImpl(width * n, pattern.height());

        for (int row = 0, rows = pattern.height(); row < rows; row++) {
            List<Boolean> src = pattern.getRow(row);
            List<Boolean> dest = newPattern.getRow(row);

            for (int col = 0, cols = width; col < cols; col++) {
                for (int ii = 0; ii < n; ii++) {
                    dest.set(col + (width * ii), src.get(col));
                }
            }
        }

        return newPattern;
    }

    /**
     * Returns a duplicate of the provided Pattern.
     */
    public static Pattern clone (Pattern pattern)
    {
        return extend(pattern, 1);
    }

    /**
     * Returns a Pattern which is a view of the provided pattern, repeated vertically n times.
     */
    public static Pattern repeat (Pattern pattern, int n)
    {
        return new RepeatedPattern(pattern, n);
    }

    /**
     * Inspects the Pattern and determines if it's vertically symmetric.
     */
    public static boolean isVerticallySymmetric (Pattern pattern)
    {
        int top = 0;
        int bottom = pattern.height() - 1;

        while (top < bottom) {
            if (!pattern.getRow(top).equals(pattern.getRow(bottom))) {
                return false;
            }

            top++;
            bottom--;
        }

        return true;
    }

    protected static class RepeatedPattern extends Pattern
    {
        public final Pattern pattern;
        public final int n;

        protected RepeatedPattern (Pattern pattern, int n) {
            Preconditions.checkArgument(n > 0);
            this.pattern = pattern;
            this.n = n;
        }

        @Override public int width () {
            return pattern.width();
        }

        @Override public int height () {
            return pattern.height() * n;
        }

        @Override public List<Boolean> getRow (int row) {
            return pattern.getRow(row % pattern.height());
        }

        @Override public Pattern invert () {
            pattern.invert();
            return this;
        }
    }
}
