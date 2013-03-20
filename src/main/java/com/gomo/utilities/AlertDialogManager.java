package com.gomo.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import org.json.JSONException;

import java.io.IOException;

public class AlertDialogManager {

    private static final boolean ALERT_DIALOG_CANCELABLE = false;
    private static final String ERROR_ALERT_DIALOG_TITLE = "Error";
    private static final String INVALID_JENKINS_JSON_FILE_ALERT_DIALOG_MESSAGE = "Invalid Jenkins JSON file";
    private static final String ERROR_ALERT_DIALOG_BUTTON_TEXT = "OK";
    private static final String INPUT_SOURCE_UNREACHABLE_ALERT_DIALOG_MESSAGE = "Source unreachable";
    private static final String NOT_A_JSON_FILE_ALERT_DIALOG_MESSAGE = "Not a JSON file";
    private static final String ERROR_ALERT_DIALOG_MESSAGE = "ERROR! Contact Cirrus team";

    public static void showAlertDialog(Activity activity, String title, String message, String buttonText, AlertDialog.OnClickListener onClickListener) {

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(buttonText, onClickListener);
        alert.setCancelable(ALERT_DIALOG_CANCELABLE);
        alert.show();
    }

    public static void showAlertDialogForException(Exception exception, Activity activity, AlertDialog.OnClickListener onClickListener) {

        if (exception instanceof IOException) {

            showAlertDialog(activity, ERROR_ALERT_DIALOG_TITLE, INPUT_SOURCE_UNREACHABLE_ALERT_DIALOG_MESSAGE, ERROR_ALERT_DIALOG_BUTTON_TEXT, onClickListener);

        } else if (exception instanceof JSONException) {

            showAlertDialog(activity, ERROR_ALERT_DIALOG_TITLE, NOT_A_JSON_FILE_ALERT_DIALOG_MESSAGE, ERROR_ALERT_DIALOG_BUTTON_TEXT, onClickListener);

        } else if (exception instanceof NullPointerException) {

            showAlertDialog(activity, ERROR_ALERT_DIALOG_TITLE, INVALID_JENKINS_JSON_FILE_ALERT_DIALOG_MESSAGE, ERROR_ALERT_DIALOG_BUTTON_TEXT, onClickListener);

        } else {

            showAlertDialog(activity, ERROR_ALERT_DIALOG_TITLE, ERROR_ALERT_DIALOG_MESSAGE, ERROR_ALERT_DIALOG_BUTTON_TEXT, onClickListener);
            exception.printStackTrace();
        }
    }
}