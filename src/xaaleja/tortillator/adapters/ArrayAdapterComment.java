package xaaleja.tortillator.adapters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import xaaleja.tortillator.R;
import xaaleja.tortillator.model.Comment;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ArrayAdapterComment extends ArrayAdapter<Comment>
{
    private static LayoutInflater inflater=null;
	private ArrayList<Comment> arrayComments;
	
	public ArrayAdapterComment(Activity activity, ArrayList<Comment> arrayComments)
	{
		super(activity.getApplicationContext(),R.layout.item_comment,arrayComments);
		this.arrayComments= arrayComments;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.item_comment, null);
		TextView username = (TextView)vi.findViewById(R.id.com_username);
		username.setText(arrayComments.get(position).getUsername());
		
		TextView dateView = (TextView)vi.findViewById(R.id.com_date);
		Date date = arrayComments.get(position).getDatetime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd - HH:mm");

		dateView.setText(dateFormat.format(date));
		
		TextView text = (TextView)vi.findViewById(R.id.com_text);
		text.setText(this.arrayComments.get(position).getText());
		return vi;
	}
}
