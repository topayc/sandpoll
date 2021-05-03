package com.sandwhale.testepoll.conponent;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sandwhale.testepoll.R;

public class ListDialog extends Dialog {


    Context mContext;  
    TextView mTitle = null;  
    ListView mList;

    View v = null;  
    
    public ListDialog(Context context) {  
        super(context);  
        mContext = context;  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.list_dialog);  
       
        v = getWindow().getDecorView();  
        v.setBackgroundResource(android.R.color.transparent);  
        mTitle = (TextView) findViewById(R.id.dialogTitle); 
        mList = (ListView)findViewById(R.id.content_list);

    }  

    public void setAdpater(BaseAdapter adapter){
    	mList.setAdapter(adapter);
    }
    
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener ){
    	if (mList == null){
    		Log.d("리스트가 널입니다", "----------------------------------------");
    	}
    	mList.setOnItemClickListener(listener);
    }
    
    @Override  
    public void setTitle(CharSequence title) {  
        super.setTitle(title);  
        mTitle.setText(title);  
    }  
    @Override  
    public void setTitle(int titleId) {  
        super.setTitle(titleId);  
        mTitle.setText(mContext.getResources().getString(titleId));  
    }  
    /**  
     * Set the message text for this dialog's window.  
     *   
     * @param message  
     *      - The new message to display in the title.  
     */  
  
    /**  
     * Set the message text for this dialog's window. The text is retrieved from the resources with the supplied  
     * identifier.  
     *   
     * @param messageId  
     *      - the message's text resource identifier <br>  
     * @see <b>Note : if resourceID wrong application may get crash.</b><br>  
     *   Exception has not handle.  
     */  
 
}
