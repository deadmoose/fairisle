package com.deadmoose.fairisle.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.Sets;

import com.deadmoose.fairisle.data.Chart.Placement;

/**
 * A chart for knitting fair isle.
 *
 * Consists of a series of Patterns stacked one after another, and is required to loop cleanly.
 * That is, the minimum width of a Chart is the lowest common multiple of the widths of all Patterns
 * in it.
 */
public class Chart extends ArrayList<Placement>
{
    /**
     * A placement of a Pattern is that pattern plus a horizontal offset.
     */
    public static class Placement
    {
        public final Pattern pattern;

        public Placement (Pattern pattern) {
            this(pattern, 0);
        }

        public Placement (Pattern pattern, int offset) {
            this.pattern = pattern;
            setOffset(offset);
        }

        public int offset () {
            return _offset;
        }

        public void setOffset (int offset) {
            int width = pattern.width();
            while (offset < 0) {
                offset += width;
            }

            while (offset >= width) {
                offset -= width;
            }

            _offset = offset;
        }

        @Override
        public Placement clone () {
            return new Placement(pattern, _offset);
        }

        @Override
        public String toString () {
            StringBuilder str = new StringBuilder();

            int width = pattern.width();

            for (List<Boolean> row : pattern.rows()) {
                for (int ii = 0; ii < width; ii++) {
                    str.append(row.get((ii + _offset) % width) ? "X" : "O");
                }
                str.append("\n");
            }
            str.deleteCharAt(str.length() - 1);

            return str.toString();
        }

        protected int _offset;
    }

    /**
     * Function to convert from a Pattern to a zero-offset Placement.
     */
    public static Function<Pattern, Placement> TO_PLACEMENT = new Function<Pattern, Placement>() {
        public Placement apply (Pattern pattern) {
            return new Placement(pattern);
        }
    };

    /**
     * Computes the minimum width of this Chart.
     */
    public int minWidth ()
    {
        if (isEmpty()) {
            return 0;
        }

        if (size() == 1) {
            return get(0).pattern.width();
        }

        // TODO: There's almost certainly a better approach to this...
        int minWidth = 0;
        Set<Integer> widths = Sets.newHashSet();
        for (Placement placement : this) {
            int width = placement.pattern.width();
            widths.add(width);
            if (minWidth < width) {
                minWidth = width;
            }
        }

        while (true) {
            boolean adjusted = false;
            for (int width : widths) {
                while (minWidth % width != 0) {
                    minWidth++;
                    adjusted = true;
                }
            }

            if (!adjusted) {
                break;
            }

        }

        return minWidth;
    }

    /**
     * Returns if the proposed width is legal.
     */
    public boolean isLegalWidth (int width)
    {
        for (Placement placement : this) {
            if (width % placement.pattern.width() != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Convenience method for adding a Pattern.
     */
    public boolean add (Pattern pattern)
    {
        return add(new Placement(pattern));
    }

    @Override
    public String toString ()
    {
        int width = minWidth();
        StringBuilder str = new StringBuilder();

        for (Placement placement : this) {
            String placementStr = placement.toString();
            for (int ii = 0, jj = width/placement.pattern.width(); ii < jj; ii++) {
                str.append(placementStr);
            }
            str.append("\n");
        }

        str.deleteCharAt(str.length() - 1);

        return str.toString();
    }
}
