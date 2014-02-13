package com.deadmoose.fairisle.data;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class Pattern
{
    /**
     * The width of this pattern, in stitches.
     */
    public abstract int width ();

    /**
     * The height of this pattern, in rows.
     */
    public abstract int height ();

    /**
     * Returns a List backed by the data for the requested row.
     */
    public abstract List<Boolean> getRow (int row);

    /**
     * Returns an Iterable to give an Iterator over all rows in the Pattern.
     */
    public Iterable<List<Boolean>> rows ()
    {
        return new Iterable<List<Boolean>>() {
            public Iterator<List<Boolean>> iterator () {
                return new Iterator<List<Boolean>>() {
                    @Override public boolean hasNext () {
                        return _row < height();
                    }

                    @Override public List<Boolean> next () {
                        if (!hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return getRow(_row++);
                    }

                    @Override public void remove () {
                        throw new UnsupportedOperationException();
                    }

                    protected int _row = 0;
                };
            }
        };
    }

    /**
     * Flips the color of each stitch in this Pattern.
     *
     * @returns this, for chaining
     */
    public Pattern invert ()
    {
        int width = width();
        for (List<Boolean> row : rows()) {
            for (int ii = 0; ii < width; ii++) {
                row.set(ii, !row.get(ii));
            }
        }

        return this;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (!(obj instanceof Pattern)) {
            return false;
        }

        Pattern that = (Pattern)obj;
        if (this.width() != that.width() || this.height() != that.height()) {
            return false;
        }

        for (int ii = 0, jj = this.height(); ii < jj; ii++) {
            if (!this.getRow(ii).equals(that.getRow(ii))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode ()
    {
        // TODO: Better hash code
        return height() ^ width();
    }

    @Override
    public String toString ()
    {
        StringBuilder str = new StringBuilder();
        for (int ii = 0, jj = height(); ii < jj; ii++) {
            for (Boolean stitch : getRow(ii)) {
                str.append(stitch ? 'X' : 'O');
            }
            if (ii < jj - 1) {
                str.append("\n");
            }
        }

        return str.toString();
    }
}