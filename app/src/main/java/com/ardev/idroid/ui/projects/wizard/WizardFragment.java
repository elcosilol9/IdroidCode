package com.ardev.idroid.ui.home.wizard;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.os.Build;
import android.widget.AdapterView;
import android.text.Editable;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.WorkerThread;
import android.widget.AutoCompleteTextView;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.transition.TransitionManager;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ardev.builder.project.Project;
import com.ardev.idroid.ext.Utils;
import com.ardev.f.Decompress;
import com.ardev.idroid.commons.SingleTextWatcher;
import com.google.android.material.transition.MaterialFadeThrough;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;
import java.io.InputStream;
import java.io.IOException;
import android.text.InputType;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import com.ardev.idroid.R;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.Executors;
import com.ardev.f.storage.Storage;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;
import androidx.core.view.ViewCompat;
import java.util.Calendar;

public class WizardFragment extends DialogFragment {

    public CallbackResult callbackResult;

    public void setOnProjectCreated(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }
	
	
    MaterialToolbar toolbar;
    Storage st;
     private RecyclerView mRecyclerView;
    private WizardTemplateAdapter mAdapter;

    private View mTemplates;
    private View mDetails;
    private Template mCurrentTemplate;
    private View view;
    private boolean mLast;
      private TextInputLayout mNameLayout;
    private TextInputLayout mSaveLocationLayout;
    private TextInputEditText mSaveLocationText;
    private TextInputLayout mPackageNameLayout;
    private TextInputLayout mMinSdkLayout;
    private TextInputLayout mLanguageLayout;
	private TextView mApiInfo;
    private AutoCompleteTextView mLanguageText;
    private AutoCompleteTextView mMinSdkText;
    private String pathFather;
     

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
view = inflater.inflate(R.layout.fragment_wizard, container, false);

       toolbar = view.findViewById(R.id.toolbar);
       mTemplates = view.findViewById(R.id.templates_layout);
        mDetails = view.findViewById(R.id.details_layout);
       mRecyclerView = mTemplates.findViewById(R.id.template_recyclerview);
       mNameLayout = mDetails.findViewById(R.id.til_app_name);
        mPackageNameLayout = mDetails.findViewById(R.id.til_package_name);
	mSaveLocationLayout = mDetails.findViewById(R.id.til_save_location);
	mSaveLocationText = mDetails.findViewById(R.id.et_save_location);
	mLanguageLayout = mDetails.findViewById(R.id.til_language);
      mLanguageText = mDetails.findViewById(R.id.et_language);
	mMinSdkLayout = mDetails.findViewById(R.id.til_min_sdk);
        mMinSdkText = mDetails.findViewById(R.id.et_min_sdk);
	 mApiInfo = mDetails.findViewById(R.id.api_info);
       
        

        
      

        return view;
    }
    
     @Override
    public void onViewCreated(@NonNull View view,  Bundle savedInstanceState) {
     ViewCompat.setTransitionName(view, "toWizard");
    st = new Storage(requireActivity());
    
    mRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), Utils.getRowCount(Utils.dp(132))));
                
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
           pathFather = requireContext().getExternalFilesDir("projects").getAbsolutePath();
            } else {
        
        pathFather = Environment.getExternalStorageDirectory() + File.separator + "IdroidCode"+ File.separator + "projects";
        }
    
        mAdapter = new WizardTemplateAdapter();
        mRecyclerView.setAdapter(mAdapter);
         init();
        loadTemplates();
    }
    
       @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    
    
    
    
private void init(){
	
toolbar.setTitle("Nuevo proyecto");
toolbar.setSubtitle("");
toolbar.setNavigationOnClickListener(this::onNavigateBack);

requireActivity().getOnBackPressedDispatcher().addCallback( new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
onNavigateBack(null);
}
});

}

  

    private void initDetailsView() {
        List<String> languages = mCurrentTemplate.getLanguageList();
		List<String> sdks = mCurrentTemplate.getSdkList();
		mApiInfo.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_info, 0, 0, 0);
		mApiInfo.setCompoundDrawablePadding(8);
		mApiInfo.setVisibility(View.GONE);
        mNameLayout.getEditText().addTextChangedListener(new SingleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
             			
                String generateSaveLocation = pathFather + "/" + editable.toString().replace(" ", "");
                mSaveLocationLayout.getEditText().setText(generateSaveLocation);
    
   int lastIndex =  mSaveLocationLayout.getEditText().getText().toString().length();
   mSaveLocationLayout.getEditText().setSelection(lastIndex, lastIndex);
            }
        });
        
        mPackageNameLayout.getEditText().addTextChangedListener(new SingleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                //verifyPackageName(editable);
            }
        });

        
         
		mSaveLocationText.setInputType(InputType.TYPE_NULL);
        mSaveLocationLayout.getEditText().addTextChangedListener(new SingleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                //verifySaveLocation(editable);
            }
        });

        
        
        mLanguageText.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, languages));

        
        
        mMinSdkText.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, sdks));
				
         
            mMinSdkText.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
      
            
             for(int i = mCurrentTemplate.getMinSdk(); i<= 31; i++ ){
            
            if (i == mCurrentTemplate.getMinSdk() + position) {
            if (i == 31) mApiInfo.setVisibility(View.GONE);
           else {
            mApiInfo.setText( getApiInfo().get(i ));
            mApiInfo.setVisibility(View.VISIBLE);
            }
            }
            
        }
        
        
                
            }

           
        });
    }



private void onNavigateBack(View view) {
        if (!mLast) {
        dismiss();
           
        } else {
        toolbar.setTitle("Nuevo proyecto");
        toolbar.setSubtitle("");
         toolbar.getMenu().clear(); 
            showTemplatesView();
            mLast = false;
        }
    }


    private void onNavigateNext() {
        if (!mLast) {
        
        
            showDetailsView();
            mLast = true;
        } else {
           createProjectAsync();
        }
    }

    private void showTemplatesView() {
        mTemplates.setVisibility(View.GONE);

        MaterialSharedAxis sharedAxis = new MaterialSharedAxis(MaterialSharedAxis.Z, false);

        TransitionManager.beginDelayedTransition((ViewGroup) requireView(), sharedAxis);

        mDetails.setVisibility(View.GONE);
        mTemplates.setVisibility(View.VISIBLE);
         
    }

private void showDetailsView() {
		
        mLanguageText.setText("");
        mMinSdkText.setText("");
        mApiInfo.setText("");
       
        mDetails.setVisibility(View.GONE);

        MaterialSharedAxis sharedAxis = new MaterialSharedAxis(MaterialSharedAxis.Z, true);

        TransitionManager.beginDelayedTransition((ViewGroup) requireView(), sharedAxis);

        mDetails.setVisibility(View.VISIBLE);
        mTemplates.setVisibility(View.GONE);
          initDetailsView();
    }

    private void loadTemplates() {
        TransitionManager.beginDelayedTransition((ViewGroup) requireView(), new MaterialFadeThrough());
        
        mRecyclerView.setVisibility(View.GONE);

       Executors.newSingleThreadExecutor().execute(() -> {
            List<Template> templates = getTemplates();

            if (getActivity() != null) {
                requireActivity().runOnUiThread(() -> {
                    TransitionManager.beginDelayedTransition((ViewGroup) requireView(),
                            new MaterialFadeThrough());
                    
                    mRecyclerView.setVisibility(View.VISIBLE);

                    mAdapter.submitList(templates);

                    mAdapter.setOnItemClick((item, pos) -> {
                        mCurrentTemplate = item;
                        
                        onNavigateNext();
                        toolbar.setTitle(mCurrentTemplate.getName());
                        toolbar.setSubtitle("Crear un " + mCurrentTemplate.getName());
                        toolbar.inflateMenu(R.menu.wizard_menu);
                        toolbar.setOnMenuItemClickListener( menu -> {
            int id = menu.getItemId();
if(id == R.id.create) onNavigateNext();

return true;
});

                    });
                });
            }
       });
    }


private List<Template> getTemplates() {
        try {
            File file = requireContext().getExternalFilesDir("templates");
            	
            extractTemplatesMaybe();

            File[] templateFiles = file.listFiles();
            
            if (templateFiles == null) {
                return Collections.emptyList();
            }
            if (templateFiles.length == 0) {
                extractTemplatesMaybe();
            }
            templateFiles = file.listFiles();
            if (templateFiles == null) {
                return Collections.emptyList();
            }

            List<Template> templates = new ArrayList<>();
            for (File child : templateFiles) {
                Template template = Template.fromFile(child);
                if (template != null) {
                    templates.add(template);
                }
            }
            return templates;
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private void extractTemplatesMaybe() throws IOException {
        File hashFile = new File(requireContext().getExternalFilesDir("templates"), "hash");
        
        if (!hashFile.exists()) {
            extractTemplates();
        } else {
            InputStream newIs = requireContext().getAssets()
                    .open("templates.zip");
            String newIsMd5 = Utils.calculateMD5(newIs);
            String oldMd5 = FileUtils.readFileToString(hashFile);

            if (!newIsMd5.equals(oldMd5)) {
                extractTemplates();
            }  
        }
    }

    private void extractTemplates() throws IOException {
        File templatesDir = new File(requireContext().getExternalFilesDir(null),
                "templates");
        if (templatesDir.exists()) {
           FileUtils.deleteDirectory(templatesDir);
        }

        Decompress.unzipFromAssets(requireContext(), "templates.zip",
                templatesDir.getParent());
                
        File hashFile = new File(templatesDir, "hash");
        
        if (!hashFile.createNewFile()) {
            throw new IOException("Unable to create hash file");
        }
		
        FileUtils.writeStringToFile(hashFile,
                 Utils.calculateMD5(requireContext().getAssets()
                        .open("templates.zip")),
                 Charset.defaultCharset());
				 
    }
    
    
    
    
    
    
    private void createProjectAsync() {
  Executors.newSingleThreadExecutor().execute(() -> {
            String savePath = mSaveLocationLayout.getEditText().getText().toString();

            try {
                // if (validateDetails()) {
                    createProject();
                // } else {
                    // requireActivity().runOnUiThread(this::showDetailsView);
                    // return;
                // }

                Project project = new Project(new File(savePath));
                replacePlaceholders(new File(savePath));

                 requireActivity().runOnUiThread(() -> {
                 if(getActivity() != null && callbackResult != null) {
                     dismiss();
                     callbackResult.sendResult(project);
                     }
                });
            } catch (IOException e) {
            requireActivity().runOnUiThread(this::showDetailsView);
                
            }
            
            });
       
    }



@WorkerThread
    private void createProject() throws IOException {

        File projectRoot = new File(mSaveLocationLayout.getEditText().getText().toString());
        if (!projectRoot.exists()) {
            if (!projectRoot.mkdirs()) {
                throw new IOException("Unable to create directory");
            }
        }
        boolean isJava = mLanguageText.getText().toString().equals("Java");
        File sourcesDir = new File(mCurrentTemplate.getPath() +
                "/" + (isJava ? "java" : "kotlin"));
        if (!sourcesDir.exists()) {
            throw new IOException("Unable to find source file for language " +
                    mLanguageText.getText());
        }

        String packageNameDir = mPackageNameLayout.getEditText()
                .getText().toString()
                .replace(".", "/");
        File targetSourceDir = new File(projectRoot, "/app/src/main/java/" + packageNameDir);
        if (!targetSourceDir.exists()) {
            if (!targetSourceDir.mkdirs()) {
                throw new IOException("Unable to create target directory");
            }
        }
        FileUtils.copyDirectory(sourcesDir, projectRoot);
        FileUtils.deleteDirectory(new File(projectRoot, "app/src/main/java/$packagename"));
        FileUtils.copyDirectory(new File(sourcesDir,
                "app/src/main/java/$packagename"), targetSourceDir);
    }



@WorkerThread
    private void replacePlaceholders(File file) throws IOException {
        File[] files = file.listFiles();
        if (files != null) {
            for (File child : files) {
                if (child.isDirectory()) {
                    replacePlaceholders(child);
                    continue;
                }
                if (child.getName().endsWith(".gradle")) {
                    replacePlaceholder(child);
                } else if (child.getName().endsWith(".java") || child.getName().endsWith(".kt")) {
                    replacePlaceholder(child);
                } else if (child.getName().endsWith(".xml")) {
                    replacePlaceholder(child);
                }
            }
        }
    }

    /**
     * Replaces the placeholders in a file such as $packagename, $appname
     *
     * @param file Input file
     */
    @WorkerThread
    private void replacePlaceholder(File file) throws IOException {
        String string;
        try {
            string = FileUtils.readFileToString(file, Charset.defaultCharset());
        } catch (IOException e) {
            return;
        }
        String targetSdk = "31";
        String minSdk = mMinSdkText.getText().toString()
                .substring("API".length() + 1, "API".length() + 3); // at least 2 digits
        int minSdkInt = Integer.parseInt(minSdk);

        FileUtils.writeStringToFile(
                file,
                string.replace("$packagename", mPackageNameLayout.getEditText().getText())
                        .replace("$appname", mNameLayout.getEditText().getText())
                        .replace("${targetSdkVersion}", targetSdk)
                        .replace("${minSdkVersion}", String.valueOf(minSdkInt)),
                StandardCharsets.UTF_8
        );
    }


   private HashMap<Integer, String> getApiInfo() {
    HashMap<Integer, String> apiInfoList = new HashMap<>();
      
         apiInfoList.put(21, getScope("98,6%"));
         apiInfoList.put(22, getScope("98,1%"));
		 apiInfoList.put(23, getScope("95,6%"));
         apiInfoList.put(24, getScope("91,7%"));
         apiInfoList.put(25, getScope("89,1%"));
		 apiInfoList.put(26, getScope("86,7%"));
		 apiInfoList.put(27, getScope("83,5%"));
         apiInfoList.put(28, getScope("75,1%"));
		 apiInfoList.put(29, getScope("58,9%"));
         apiInfoList.put(30, getScope("35%"));
         apiInfoList.put(31, "");
		
        return apiInfoList;
    }
	
	private String getScope(String percent) {
		
	return String.format(Locale.getDefault(),requireContext().getResources().getString(R.string.device_scope_format), percent);
	}
   

    public interface CallbackResult {
        void sendResult(Project project);
    }

}