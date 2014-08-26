package xaaleja.tortillator.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastWriter 
{
	public static void writeToast(String message, Context context)
	{
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
