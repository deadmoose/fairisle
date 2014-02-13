package com.deadmoose.fairisle.data;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Preconditions;

/**
 * Represents a fair isle pattern.
 */
public class PatternImpl extends Pattern
{
    /**
     * Makes a new Pattern with the given dimensions, all empty.
     */
    public PatternImpl (int width, int height)
    {
        this(width, height, null);
    }

    /**
     * Makes a new pattern with the given dimensions, backing it with the provided array.
     */
    public PatternImpl (int width, int height, Boolean[] data)
    {
        Preconditions.checkArgument(width > 0 && height > 0);
        if (data != null) {
            Preconditions.checkArgument(data.length == width * height);
        } else {
            data = new Boolean[width * height];
            Arrays.fill(data, false);
        }

        _width = width;
        _height = height;
        _data = data;
        _dataAsList = Arrays.asList(_data);
    }

    @Override
    public int width ()
    {
        return _width;
    }

    @Override
    public int height ()
    {
        return _height;
    }

    @Override
    public List<Boolean> getRow (int row)
    {
        return _dataAsList.subList(row * _width, (row + 1) * _width);
    }

    @Override
    public Pattern clone ()
    {
        return new PatternImpl(_width, _height, _data.clone());
    }

    @Override
    public Pattern invert ()
    {
        for (int ii = 0; ii < _data.length; ii++) {
            _data[ii] = !_data[ii];
        }
        return this;
    }

    /** How many stitches wide this pattern is. */
    protected int _width;

    /** How many rows tall this pattern is. */
    protected int _height;

    /** The bitmap of stitches. */
    protected Boolean[] _data;

    /** A List exposing _data. */
    protected List<Boolean> _dataAsList;
}
