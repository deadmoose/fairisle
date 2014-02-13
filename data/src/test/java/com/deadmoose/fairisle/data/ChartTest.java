package com.deadmoose.fairisle.data;

import org.junit.Assert;
import org.junit.Test;

/**
 * TODO: Parameterize. Oh god, I'm not in the mood right now.
 */
public class ChartTest
{
    @Test
    public void minWidth ()
    {
        Chart chart = new Chart();

        chart.add(Patterns.SOLID);
        Assert.assertEquals(1, chart.minWidth());

        chart.add(Patterns.extend(Patterns.SOLID, 2));
        Assert.assertEquals(2, chart.minWidth());

        chart.add(Patterns.extend(Patterns.SOLID, 3));
        Assert.assertEquals(6, chart.minWidth());

        chart.add(Patterns.extend(Patterns.SOLID, 4));
        Assert.assertEquals(12, chart.minWidth());

        chart.add(Patterns.extend(Patterns.SOLID, 5));
        Assert.assertEquals(60, chart.minWidth());

        chart.add(Patterns.extend(Patterns.SOLID, 6));
        Assert.assertEquals(60, chart.minWidth());

        chart.add(Patterns.extend(Patterns.SOLID, 7));
        Assert.assertEquals(420, chart.minWidth());
    }

    @Test
    public void isLegalWidth ()
    {
        Chart chart = new Chart();

        chart.add(Patterns.SOLID);
        chart.add(Patterns.extend(Patterns.SOLID, 2));
        chart.add(Patterns.extend(Patterns.SOLID, 3));

        Assert.assertTrue(chart.isLegalWidth(6));
        Assert.assertTrue(chart.isLegalWidth(12));
        Assert.assertTrue(chart.isLegalWidth(18));
        Assert.assertFalse(chart.isLegalWidth(1));
    }

    @Test
    public void toStringA ()
    {
        Chart chart = new Chart();

        chart.add(Patterns.SOLID);
        Assert.assertEquals("X", chart.toString());

        chart.add(Patterns.extend(Patterns.SOLID, 2));
        Assert.assertEquals("XX\nXX", chart.toString());

        chart.add(Patterns.extend(Patterns.SOLID, 3));
        Assert.assertEquals("XXXXXX\nXXXXXX\nXXXXXX", chart.toString());
    }

    @Test
    public void toStringB ()
    {
        Chart chart = new Chart();

        chart.add(Patterns.SOLID);
        Assert.assertEquals("X", chart.toString());

        chart.add(new PatternImpl(2, 1, new Boolean[] { true, false }));
        Assert.assertEquals("XX\nXO", chart.toString());

        chart.add(Patterns.extend(Patterns.clone(Patterns.SOLID).invert(), 3));
        Assert.assertEquals("XXXXXX\nXOXOXO\nOOOOOO", chart.toString());
    }

    @Test
    public void toStringC ()
    {
        Chart chart = new Chart();

        chart.add(Patterns.SOLID);
        Assert.assertEquals("X", chart.toString());

        chart.add(new Chart.Placement(new PatternImpl(2, 1, new Boolean[] { true, false }), 1));
        Assert.assertEquals("XX\nOX", chart.toString());

        chart.add(Patterns.extend(Patterns.clone(Patterns.SOLID).invert(), 3));
        Assert.assertEquals("XXXXXX\nOXOXOX\nOOOOOO", chart.toString());
    }
}
