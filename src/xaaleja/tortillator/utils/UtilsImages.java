package xaaleja.tortillator.utils;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

public class UtilsImages
{
	public static Drawable loadImageAssets(Context context, String path)
	{
		
		Drawable image = null;
		InputStream isImage = null;
		
		AssetManager am = context.getAssets();
		try 
		{
			isImage = am.open(path);
			image = Drawable.createFromStream(isImage, "image");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			if(isImage !=null)
			{
				try {
					isImage.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
		return image;
	}
	
	
}