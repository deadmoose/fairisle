package com.deadmoose.fairisle.data;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    PatternTest.ValidDimensions.class,
    PatternTest.InvalidDimensions.class,
    PatternTest.Symmetry.class,
    PatternTest.Repeat.class,
    PatternTest.Extend.class,
    PatternTest.Invert.class,
})
public class PatternTest
{
    @RunWith(Parameterized.class)
    public static class ValidDimensions {
        @Parameters
        public static Collection<Object[]> data () {
            return Arrays.asList(new Object[][] {
                { 1, 1 },
                { 2, 1 },
            });
        }

        @Parameter
        public int width;

        @Parameter(1)
        public int height;

        @Test
        @SuppressWarnings("unused")
        public void test () {
            new PatternImpl(width, height);
        }
    }

    @RunWith(Parameterized.class)
    public static class InvalidDimensions {
        @Parameters
        public static Collection<Object[]> data () {
            return Arrays.asList(new Object[][] {
                { 0, 0 },
                { 0, 1 },
                { 1, 0 },
                { -1,  1 },
                {  1, -1 },
            });
        }

        @Parameter
        public int width;

        @Parameter(1)
        public int height;

        @Test(expected = IllegalArgumentException.class)
        @SuppressWarnings("unused")
        public void test () {
            new PatternImpl(width, height);
        }
    }

    @RunWith(Parameterized.class)
    public static class Symmetry
    {
        @Parameters
        public static Collection<Object[]> data () {
            return Arrays.asList(new Object[][] {
                { // Only one row means always symmetric
                    true, 1, 1,
                    new Boolean[] {
                        true,
                    }
                },
                { // Only one row means always symmetric
                    true, 1, 1,
                    new Boolean[] {
                        false,
                    }
                },
                {
                    true, 1, 2,
                    new Boolean[] {
                        true,
                        true,
                    }
                },
                {
                    true, 1, 2,
                    new Boolean[] {
                        false,
                        false,
                    }
                },
                {
                    false, 1, 2,
                    new Boolean[] {
                        true,
                        false,
                    }
                },
                {
                    true, 1, 3,
                    new Boolean[] {
                        true,
                        false,
                        true,
                    }
                },
                {
                    true, 1, 3,
                    new Boolean[] {
                        true,
                        true,
                        true,
                    }
                },
                {
                    false, 1, 3,
                    new Boolean[] {
                        false,
                        true,
                        true,
                    }
                },
                {
                    true, 2, 3,
                    new Boolean[] {
                        true, true,
                        true, true,
                        true, true,
                    }
                },
                { // Middle row can be whatever
                    true, 2, 3,
                    new Boolean[] {
                        true, true,
                        true, false,
                        true, true,
                    }
                },
                {
                    false, 2, 3,
                    new Boolean[] {
                        true, true,
                        true, true,
                        true, false,
                    }
                },
            });
        }

        @Parameter(0)
        public boolean expected;

        @Parameter(1)
        public int width;

        @Parameter(2)
        public int height;

        @Parameter(3)
        public Boolean[] pattern;

        @Test
        public void test () {
            Assert.assertEquals(expected,
                Patterns.isVerticallySymmetric(new PatternImpl(width, height, pattern)));
        }
    }

    @RunWith(Parameterized.class)
    public static class Extend
    {
        @Parameters
        public static Collection<Object[]> data () {
            return Arrays.asList(new Object[][] {
                {
                    new PatternImpl(1, 1, new Boolean[] {
                        true,
                    }),
                    2,
                    new PatternImpl(2, 1, new Boolean[] {
                        true, true,
                    }),
                },
                {
                    new PatternImpl(1, 1, new Boolean[] {
                        false,
                    }),
                    2,
                    new PatternImpl(2, 1, new Boolean[] {
                        false, false,
                    }),
                },
                {
                    new PatternImpl(2, 1, new Boolean[] {
                        true, false,
                    }),
                    2,
                    new PatternImpl(4, 1, new Boolean[] {
                        true, false, true, false,
                    }),
                },
                {
                    new PatternImpl(2, 2, new Boolean[] {
                        true, false,
                        false, true,
                    }),
                    2,
                    new PatternImpl(4, 2, new Boolean[] {
                        true, false, true, false,
                        false, true, false, true,
                    }),
                },
            });
        }

        @Parameter
        public Pattern initial;

        @Parameter(1)
        public int n;

        @Parameter(2)
        public Pattern expected;

        @Test
        public void extend () {
            Assert.assertEquals(expected, Patterns.extend(initial, n));
        }
    }

    @RunWith(Parameterized.class)
    public static class Repeat
    {
        @Parameters
        public static Collection<Object[]> data () {
            return Arrays.asList(new Object[][] {
                {
                    new PatternImpl(1, 1, new Boolean[] {
                        true,
                    }),
                    2,
                    new PatternImpl(1, 2, new Boolean[] {
                        true,
                        true,
                    }),
                },
                {
                    new PatternImpl(1, 1, new Boolean[] {
                        false,
                    }),
                    2,
                    new PatternImpl(1, 2, new Boolean[] {
                        false,
                        false,
                    }),
                },
                {
                    new PatternImpl(2, 1, new Boolean[] {
                        true, false,
                    }),
                    2,
                    new PatternImpl(2, 2, new Boolean[] {
                        true, false,
                        true, false,
                    }),
                },
                {
                    new PatternImpl(2, 2, new Boolean[] {
                        true, false,
                        false, true,
                    }),
                    2,
                    new PatternImpl(2, 4, new Boolean[] {
                        true, false,
                        false, true,
                        true, false,
                        false, true,
                    }),
                },
            });
        }

        @Parameter
        public Pattern initial;

        @Parameter(1)
        public int n;

        @Parameter(2)
        public Pattern expected;

        @Test
        public void repeat () {
            Assert.assertEquals(expected, Patterns.repeat(initial, n));
        }
    }

    @RunWith(Parameterized.class)
    public static class Invert
    {
        @Parameters
        public static Collection<Object[]> data () {
            return Arrays.asList(new Object[][] {
                {
                    new PatternImpl(1, 1, new Boolean[] {
                        true,
                    }),
                    new PatternImpl(1, 1, new Boolean[] {
                        false,
                    }),
                },
                {
                    new PatternImpl(1, 1, new Boolean[] {
                        false,
                    }),
                    new PatternImpl(1, 1, new Boolean[] {
                        true,
                    }),
                },
                {
                    new PatternImpl(1, 2, new Boolean[] {
                        true, false,
                    }),
                    new PatternImpl(1, 2, new Boolean[] {
                        false, true,
                    }),
                },
                {
                    Patterns.repeat(new PatternImpl(1, 1, new Boolean[] {
                        true,
                    }), 2),
                    new PatternImpl(1, 2, new Boolean[] {
                        false,
                        false,
                    }),
                },
            });
        }

        @Parameter
        public Pattern initial;

        @Parameter(1)
        public Pattern expected;

        @Test
        public void invert () {
            Assert.assertEquals(expected, initial.invert());
        }
    }
}
