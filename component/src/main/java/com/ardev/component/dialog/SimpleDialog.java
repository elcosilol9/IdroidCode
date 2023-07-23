package com.ardev.component.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;
import android.view.View;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SimpleDialog {
   
public  SimpleDialog(){}

 public SimpleDialog(
            Context context,
            String title,
            String textPositive,
            String message,
            DialogInterface.OnClickListener cancel,
            DialogInterface.OnClickListener positive
             
             ) {
       
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(context);
        dialog.setTitle(title);
        dialog.setMessage(message);

        dialog.setNegativeButton(android.R.string.cancel, cancel);

        dialog.setPositiveButton(android.R.string.ok, positive);
        dialog.show();
    }
    
     public SimpleDialog(
            Context context,
            String title,
            String textPositive,
             String message,
            DialogInterface.OnClickListener cancel,
            DialogInterface.OnClickListener positive,
               View view
               ) {
       
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(context);
        dialog.setTitle(title);
        dialog.setView(view);

        dialog.setNegativeButton(android.R.string.cancel, cancel);

        dialog.setPositiveButton(android.R.string.ok, positive);
        dialog.show();
    }
    
    
    
    
   
		

	public void AboutDialog(Context context){
		new MaterialAlertDialogBuilder (context)
				.setTitle("Acerca de")
				.setMessage("Esta aplicación fue creada por Alexander Rotela")
				.setPositiveButton(android.R.string.ok, (d, which)->{
					
					
					})
							.setNegativeButton(android.R.string.cancel, (d, which)->{
								d.dismiss();
								
							}). show();
						
	
		
	}
}
