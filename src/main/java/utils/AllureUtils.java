package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.StepResult;
import pages.Page;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

public class AllureUtils {

    static String defaultStepTitle = "takeScreenshot";

    public static void setDefaultStepTitle(String defaultStepTitle) {
        AllureUtils.defaultStepTitle = defaultStepTitle;
    }

    public static void stepWithParametersMapList(String stepName, List<Map<String, String>> mapList) {

        StepResult stepResult = new StepResult()
                .setName(stepName)
//                .setStatus(Status.PASSED)
                .setDescription("This is another custom step description.");

        Allure.getLifecycle().startStep(stepName, stepResult);
        Allure.getLifecycle().updateStep(step -> {
            List<Parameter> Parameter = step.getParameters();
            mapList.forEach(map -> {
                Parameter.add(new Parameter()
                        .setName(map.get("key"))
                        .setValue(map.get("value")));
            });

        });
        Allure.getLifecycle().stopStep();
    }

    public static void stepWithParametersList(String stepName, List<List<String>> list) {
        // The step's body with parameters

        StepResult stepResult = new StepResult()
                .setName(stepName)
//                .setStatus(Status.PASSED)
                .setDescription("This is another custom step description.");

        Allure.getLifecycle().startStep(stepName, stepResult);
        Allure.getLifecycle().updateStep(step -> {
            List<Parameter> parameters = step.getParameters();
            list.forEach(it -> {
                parameters.add(new Parameter()
                        .setName(it.get(0))
                        .setValue(it.get(1)));
            });

        });

        Allure.getLifecycle().stopStep();
    }

    public static void stepTakeScreenshot(byte[] screenshot) {
        Allure.step(defaultStepTitle, stepContext -> {
            takeScreenshot(screenshot);
        });
    }

    public static void stepTakeScreenshot(byte[] screenshot, String screenShotTitle) {
        Allure.step(defaultStepTitle, stepContext -> {
            takeScreenshot(screenshot, screenShotTitle);
        });
    }

    public static <P extends Page<P>> void stepTakeScreenshot(Page<P> klass) {
        Allure.step(defaultStepTitle, stepContext -> {
            takeScreenshot(klass);
        });
    }

    public static void takeScreenshot(byte[] screenshot) {
        Allure.addAttachment("Page screenshot", new ByteArrayInputStream(screenshot));
    }

    public static void takeScreenshot(byte[] screenshot, String screenShotTitle) {
        Allure.addAttachment(screenShotTitle, new ByteArrayInputStream(screenshot));
    }

    public static <P extends Page<P>> void takeScreenshot(Page<P> klass) {
        Allure.addAttachment(klass.getClassName(), new ByteArrayInputStream(klass.takeScreenshot()));
    }
}
