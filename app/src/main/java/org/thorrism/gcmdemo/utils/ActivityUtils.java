package org.thorrism.gcmdemo.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by lcrawford on 1/26/16.
 */
public class ActivityUtils {

    public static void createToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void createSnackbar(View v, String text){
        Snackbar.make(v, text, Snackbar.LENGTH_LONG).show();
    }
}
