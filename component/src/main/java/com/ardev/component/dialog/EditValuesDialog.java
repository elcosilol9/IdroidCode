package com.ardev.component.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.os.Handler;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import com.ardev.idroid.commons.SingleTextWatcher;
import com.ardev.idroid.ext.ColorUtils;
import com.ardev.idroid.ext.ValuesUtils;
import com.ardev.idroid.ui.main.fragment.preview.ValuesViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ardev.component.R;
import android.text.method.DigitsKeyListener;
import android.text.InputType;
import android.text.InputFilter;
import com.ardev.component.view.colorpicker.ColorPicker;
import com.ardev.component.view.colorpicker.listeners.SimpleColorSelectionListener;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class  EditValuesDialog extends DialogFragment {

    private AlertDialog dialog;
     
    private DialogListener mListener; 
    public void setOnResultListener( DialogListener mListener) { this.mListener = mListener; }
    private ValuesViewModel mViewModel;
    private int mPosition;
    public void setData(ValuesViewModel viewModel, int position){ 
    this.mViewModel = viewModel;
    this.mPosition = position;
    }
    
    TextInputEditText name, value;
    
    

    public static  EditValuesDialog newInstance() {
         EditValuesDialog fragment = new  EditValuesDialog();
        return fragment;
    }

    public  EditValuesDialog() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
final View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit_string_dialog, (ViewGroup) getView(), false);
initView(view);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setTitle("Editar " + mViewModel.getType().getValue());
        builder.setView(view);
        builder.setNegativeButton("Cancelar", (dlg, i) -> mViewModel.setState(false));
        builder.setPositiveButton(
                "Guardar", (dlg, i) ->workSpace());
                    
              

        dialog = builder.create();
       dialog.setCanceledOnTouchOutside(false);
        
       view.post(() -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false));

        
        return dialog;
    }
    
    private void initView(View view) {
    final TextInputLayout til_name = view.findViewById(R.id.til_name);
    final TextInputLayout til_value =  view.findViewById(R.id.til_value);
    name = view.findViewById(R.id.et_name);
    value =  view.findViewById(R.id.et_value);
    
    if(mViewModel.getType().getValue().equals("color")){
		 til_value.setEndIconMode(TextInputLayout.END_ICON_CUSTOM);
		 til_value.setEndIconDrawable(R.drawable.ic_palette);
		 
		 til_value.setEndIconContentDescription("Paleta de colores");
		 til_value.setEndIconOnClickListener(this::showColorPickerDialog);
		value.setFilters(new InputFilter[] { new InputFilter.LengthFilter(9) });
	
    } else {
     value.setSingleLine(false);
	 value.setImeOptions(EditorInfo.IME_NULL);
    value.setMaxLines(4);
    }
    
    
    
    name.setText(mViewModel.getName().getValue());
    til_name.getEditText().addTextChangedListener(new SingleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
           
            
				if (mViewModel.getType().getValue().equals("color")) {
			
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(ValuesUtils.isAttrNameFormat(editable.toString()) && ColorUtils.isColorFormat(value.getText().toString()) && (!mViewModel.getName().getValue().equals(editable.toString()) || !mViewModel.getItemValue().getValue().equals(value.getText().toString())));
            } else {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(ValuesUtils.isAttrNameFormat(editable.toString()) && !value.getText().toString().trim().isEmpty() && (!mViewModel.getName().getValue().equals(editable.toString()) || !mViewModel.getItemValue().getValue().equals(value.getText().toString())));
            
                }
            }
        });
    value.setText(mViewModel.getItemValue().getValue());    
    til_value.getEditText().addTextChangedListener(new SingleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
				if (mViewModel.getType().getValue().equals("color")) {
				
					 til_value.setEndIconVisible(ColorUtils.isColorFormat(editable.toString()) );
					dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(ColorUtils.isColorFormat(editable.toString()) && ValuesUtils.isAttrNameFormat(name.getText().toString())  && (!mViewModel.getItemValue().getValue().equals(editable.toString()) || !mViewModel.getName().getValue().equals(name.getText().toString())));
            
            } else {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled( !editable.toString().trim().isEmpty() && ValuesUtils.isAttrNameFormat(name.getText().toString()) && (!mViewModel.getItemValue().getValue().equals(editable.toString()) || !mViewModel.getName().getValue().equals(name.getText().toString())));
            }
			
			  
            }
        });
    
    
    
    }
    
    private void showColorPickerDialog(View view) {
    
		 ColorPickerDialog cpd = new ColorPickerDialog();
		 cpd.setCurrentColor(value.getText().toString());
		 cpd.setOnColorSelected( color -> {
		
		  value.setText(String.format("#%06X", (0xFFFFFF & color)));
		  });
		 cpd.show(getParentFragmentManager(), "color_picker_dialog");
		 }
    
    
    
    private void workSpace() {
    
   final String _name = name.getText().toString().trim();
    final String _value = value.getText().toString().trim();
    
    Executors.newSingleThreadExecutor().execute(() -> {

	ValuesUtils.writeInFile(mPosition, mViewModel.getFilePath().getValue(), _name, _value, mViewModel.getType().getValue());
	  new Handler(Looper.getMainLooper()).post(() ->mListener.onResult() );
	
   });
 
			
    }
    
    
    
    

    public interface DialogListener {
        void onResult();
    }

     
}
