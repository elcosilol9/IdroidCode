package com.ardev.idroid.ui.main.fragment.preview;

import android.content.Intent;
import android.os.Looper;
import android.os.Handler;
import android.widget.TextView;
import com.google.android.material.transition.MaterialFade;
import androidx.transition.TransitionManager;
import com.ardev.idroid.ui.main.fragment.preview.model.Item;
import com.ardev.tools.parser.ValuesXmlParser;
import com.ardev.idroid.ext.ValuesUtils;
import com.ardev.component.dialog.ColorPickerDialog;
import com.ardev.component.dialog.EditValuesDialog;
import java.util.List;
import java.io.File;
import android.content.SharedPreferences;
import android.content.Context;
import android.os.Bundle;
import java.util.ArrayList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelStore;
import com.google.android.material.textfield.TextInputEditText;
import java.util.concurrent.Executors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.divider.MaterialDividerItemDecoration;
import androidx.core.widget.NestedScrollView;
import com.ardev.f.storage.Storage;
import java.util.concurrent.Executors;
import com.google.android.material.textfield.TextInputLayout;
import android.app.Activity;
import com.ardev.idroid.ext.UtilKt;
import com.ardev.idroid.R;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.FileReader;
import java.io.Reader;
import java.io.IOException;

public class ValuesFragment extends Fragment  {

	ValuesViewModel vm; 
	RecyclerView rv;
	FloatingActionButton fab;
	LinearLayoutManager layoutManager;
	ValuesAdapter la;
	SharedPreferences sp;
	 TextView empty;
	Storage st;
	NestedScrollView scrollingView;


	public ValuesFragment(String path) {
      
        Bundle args = new Bundle();
        args.putString("path", path);
        setArguments(args);
        
    }


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		vm = new ViewModelProvider(requireActivity(),
				new SavedStateViewModelFactory(requireActivity().getApplication(), requireActivity()))
						.get(ValuesViewModel.class);
						vm.setState(false);
						
						
		st = new Storage(requireActivity());
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.fragment_values, container, false);
		scrollingView  = v.findViewById(R.id.scrolling_view);
		rv = v.findViewById(R.id.list);
		fab = v.findViewById(R.id.fab);
	     empty  = v.findViewById(R.id.empty_item);
		
		return v;
	}
	
	@Override
	public void onViewCreated(View arg0, Bundle arg1) {
		super.onViewCreated(arg0, arg1);
		rvInit();
		fabInit();
		initParser();
	}

	@Override
	public void onResume() {
		super.onResume();
       initParser();

	}

	private void fabInit() {
	UtilKt.addSystemWindowInsetToMargin(fab, false, false, false, true);
		fab.setOnClickListener(view -> {});

	}

	private void rvInit() {

		
		
		la = new ValuesAdapter();
		rv.setAdapter(la);
		rv.setHasFixedSize(true);
		rv.setItemAnimator(new DefaultItemAnimator());
		la.setOnClickListener((view, position, item) -> {
		vm.setItem(item);
	
	EditValuesDialog dialog =	new EditValuesDialog();
		dialog.setData(vm, position);
	dialog	.setOnResultListener( ()-> {
	vm.setState(false);
	 initParser(); } );
	 vm.setState(true);
	dialog	.show( getParentFragmentManager(), "edit_item");
		
		});
	

la.setOnColorClickListener((view, position, item) -> {


 
		 ColorPickerDialog cpd = new ColorPickerDialog();
		 cpd.setCurrentColor(item.getValue());
		 cpd.setOnColorSelected( color -> {
		
		  String mColor = String.format("#%06X", (0xFFFFFF & color));
		
		
 Executors.newSingleThreadExecutor().execute(() -> {
 
	ValuesUtils.writeInFile(position, item.getFilePath(), item.getName(), mColor, item.getType());
	  new Handler(Looper.getMainLooper()).post(() -> { 
	  vm.setState(false);
	  initParser();
	  } );
   });

  });
  		vm.setState(true);
		 cpd.show(getParentFragmentManager(), "color_picker_dialog");

});
	}

	public void initParser() {		
		if (vm.getState().getValue() == false) {
		Executors.newSingleThreadExecutor().execute(() -> {

		new ValuesXmlParser(requireActivity())
		.setParser(requireArguments().getString("path", ""))
		.setOnParseListener( list -> {
		requireActivity().runOnUiThread(() -> showList(list));
		});

		});
			}
			}

			 

	 
	private void showList(List<Item> list) {
   
      
        
            if (list.isEmpty()) {
                scrollingView.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            } else {
                scrollingView.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);
                la.setList(list);
            }
      
      
      
	}



	 

	 

}