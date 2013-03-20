package com.gomo.utilities;

import com.gomo.activities.MonitorActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class SharedPreferencesManagerTest {

    private static final String SAMPLE_STRING_VALUE = "GoMo";
    private static final String SAMPLE_STRING_KEY = "projectName";
    private static final String DEFAULT_STRING_VALUE = "Hi";
    private static final int SAMPLE_INT_VALUE = 123;
    private static final String SAMPLE_INT_KEY = "projectName";
    private static final int SAMPLE_DEFAULT_VALUE = 0;
    private static final String SAMPLE_BOOLEAN_KEY = "commitInfo";
    private static final boolean SAMPLE_BOOLEAN_VALUE = true;
    private static final boolean SAMPLE_DEFAULT_BOOLEAN_VALUE = false;
    private MonitorActivity monitorActivity;
    private SharedPreferencesManager sharedPreferencesManager;

    @Before
    public void setUp() throws Exception {

        monitorActivity = new MonitorActivity();
        sharedPreferencesManager = new SharedPreferencesManager(monitorActivity);
        sharedPreferencesManager.clearAll();
    }

    @Test
    public void givenAKeyAndString_ShouldSaveAndLoadTheString() throws Exception {

        sharedPreferencesManager.saveString(SAMPLE_STRING_KEY, SAMPLE_STRING_VALUE);

        assertEquals(SAMPLE_STRING_VALUE, sharedPreferencesManager.loadString(SAMPLE_STRING_KEY, DEFAULT_STRING_VALUE));
    }

    @Test
    public void givenAKeyWithNoString_ShouldReturnDefaultValue() throws Exception {

        assertEquals(DEFAULT_STRING_VALUE, sharedPreferencesManager.loadString(SAMPLE_STRING_KEY, DEFAULT_STRING_VALUE));
    }

    @Test
    public void givenAKeyAndString_SaveShouldReturnTrue() throws Exception {

        assertTrue(sharedPreferencesManager.saveString(SAMPLE_STRING_KEY, SAMPLE_STRING_VALUE));
    }

    @Test
    public void givenAKeyAndStringSet_ShouldSaveAndLoadTheString() throws Exception {

        Set<String> setOfStrings = new HashSet<String>();
        setOfStrings.add("abc");
        setOfStrings.add("abcd");
        setOfStrings.add(("abcdef"));

        sharedPreferencesManager.saveStringSet(SAMPLE_STRING_KEY, setOfStrings);

        assertEquals(setOfStrings, sharedPreferencesManager.loadStringSet(SAMPLE_STRING_KEY, setOfStrings));
    }

    @Test
    public void givenAKeyWithEmptyStringSet_ShouldReturnDefaultValue() throws Exception {

        Set<String> emptySetOfString = new HashSet<String>();

        assertEquals(emptySetOfString, sharedPreferencesManager.loadStringSet(SAMPLE_STRING_KEY, emptySetOfString));
    }

    @Test
    public void givenAKeyWithEmptyStringSet_ShouldReturnTrue() throws Exception {

        Set<String> setOfStrings = new HashSet<String>();
        setOfStrings.add("abc");
        setOfStrings.add("abcd");
        setOfStrings.add(("abcdef"));

        assertTrue(sharedPreferencesManager.saveStringSet(SAMPLE_STRING_KEY, setOfStrings));
    }

    @Test
    public void givenAKeyAndInt_ShouldSaveAndLoadTheInt() throws Exception {

        sharedPreferencesManager.saveInt(SAMPLE_INT_KEY, SAMPLE_INT_VALUE);

        assertEquals(SAMPLE_INT_VALUE, sharedPreferencesManager.loadInt(SAMPLE_INT_KEY, SAMPLE_DEFAULT_VALUE));
    }

    @Test
    public void givenAKeyWithNoInt_ShouldReturnDefaulValue() throws Exception {

        assertEquals(SAMPLE_DEFAULT_VALUE, sharedPreferencesManager.loadInt(SAMPLE_INT_KEY, SAMPLE_DEFAULT_VALUE));
    }

    @Test
    public void givenAKeyAndInt_SaveShouldReturnTrue() throws Exception {

        assertTrue(sharedPreferencesManager.saveInt(SAMPLE_INT_KEY, SAMPLE_INT_VALUE));
    }

    @Test
    public void givenAKeyAndBooleanState_ShouldSaveAndLoadTheBoolean() throws Exception {

        sharedPreferencesManager.saveBoolean(SAMPLE_BOOLEAN_KEY, SAMPLE_BOOLEAN_VALUE);

        assertEquals(SAMPLE_BOOLEAN_VALUE, sharedPreferencesManager.loadBoolean(SAMPLE_BOOLEAN_KEY, SAMPLE_DEFAULT_BOOLEAN_VALUE));
    }
}
