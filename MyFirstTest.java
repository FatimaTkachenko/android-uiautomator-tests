package ru.netology.testing.uiautomator;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class MyFirstTest {

    private UiDevice mDevice;

    @Before
    public void setUp() {
        mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();

        String appPackage = "ru.netology.testing.uiautomator";
        mDevice.executeShellCommand("am start " + appPackage + "/.MainActivity");
        mDevice.wait(Until.hasObject(By.pkg(appPackage).depth(0)), 5000);
    }

    @Test
    public void testSetEmptyText() throws UiObjectNotFoundException {
        UiObject inputField = mDevice.findObject(new UiSelector()
                .resourceId("ru.netology.testing.uiautomator:id/userInput"));
        
        UiObject changeTextBtn = mDevice.findObject(new UiSelector()
                .resourceId("ru.netology.testing.uiautomator:id/buttonChange"));
        
        UiObject textView = mDevice.findObject(new UiSelector()
                .resourceId("ru.netology.testing.uiautomator:id/textToBeChanged"));
        
        String oldText = textView.getText();
        
        inputField.click();
        inputField.clearTextField();
        inputField.setText(" ");
        
        changeTextBtn.click();
        
        String newText = textView.getText();
        assertEquals("Текст не должен измениться", oldText, newText);
    }

    @Test
    public void testOpenNewActivity() throws UiObjectNotFoundException {
        String testText = "Привет, Мир!";
        
        UiObject inputField = mDevice.findObject(new UiSelector()
                .resourceId("ru.netology.testing.uiautomator:id/userInput"));
        
        UiObject openActivityBtn = mDevice.findObject(new UiSelector()
                .resourceId("ru.netology.testing.uiautomator:id/buttonActivity"));
        
        inputField.click();
        inputField.clearTextField();
        inputField.setText(testText);
        
        openActivityBtn.click();
        
        mDevice.waitForWindowUpdate(null, 5000);
        
        UiObject newActivityTextView = mDevice.findObject(new UiSelector()
                .resourceId("ru.netology.testing.uiautomator:id/text"));
        
        String actualText = newActivityTextView.getText();
        assertEquals("Текст в новой Activity должен совпадать", testText, actualText);
    }
}
