package com.ardev.idroid.ui.main;


import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import android.view.Menu;
import com.ardev.idroid.ui.main.model.Editable;
import com.ardev.idroid.ext.JsonUtils;
import com.ardev.idroid.ext.Base64Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.nio.charset.Charset;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
 
 

public class MainViewModel extends ViewModel {
	 private MutableLiveData<Boolean> mIsSaved = new MutableLiveData<>(false);
	 private MutableLiveData<Boolean> mIndexing = new MutableLiveData<>(false);
    private MutableLiveData<String> mCurrentState = new MutableLiveData<>(null);
    private MutableLiveData<File> breadcrumItem = new MutableLiveData<File>();
    private MutableLiveData<Menu> menu = new MutableLiveData<Menu>();
    private MutableLiveData<String> currentPath = new MutableLiveData<String>();
   private final MutableLiveData<Integer> currentPosition = new MutableLiveData<>(0);
   private final MutableLiveData<Boolean> first= new MutableLiveData<>(true);
	 private MutableLiveData<List<File>> currentList = new MutableLiveData<List<File>>();
    
	SavedStateHandle ssh;
public  MainViewModel(SavedStateHandle ssh) { 
	 this.ssh = ssh;
	 first.setValue(true);
	 
	} 
    public void init() {
    //obtener getListEditor()
    File openFile = new File(getFatherPath().getValue() + "/app/build/.cache", ".open");
if (openFile.exists()) {
	try {
	
   List<Editable> listEditable =  JsonUtils.jsonToList(Base64Util.decode(FileUtils.readFileToString(openFile, Charset.defaultCharset())), Editable.class);
  setListEditor(listEditable);
   } catch (Exception e) {} 
    }
    }
    
    public LiveData<Boolean> isSaved() {
        if (mIsSaved == null) {
            mIsSaved = new MutableLiveData<>(false);
        }
       
        return mIsSaved;
    }

    public void savedText(Boolean state) {
        mIsSaved.setValue(state);
    }
    
    
     public LiveData<Menu> getMenu() {
       
        return menu;
    }

    public void setMenu(Menu _menu) {
        menu.setValue(_menu);
    }
    
    
     public MutableLiveData<String> getCurrentState() {
        return mCurrentState;
    }
 
    public void setCurrentState(@Nullable String message) {
        mCurrentState.setValue(message);
    }

	 public MutableLiveData<Boolean> isIndexing() {
        return mIndexing;
    }

    public void setIndexing(boolean indexing) {
        mIndexing.setValue(indexing);
    }
    
    
    
   public LiveData<List<String>> getListDir(){
	 List<String> result = new ArrayList<String>();
	 List<File> mFiles = getListCurrent().getValue();
	if(mFiles != null){
		for(File f : mFiles){
			if(f.isDirectory()){
				
				result.add(f.getName());
			}
			
			
		}
		
	}
	
	ssh.set("dir", result);

		return ssh.getLiveData("dir");
		
	
		
	}
    
    
    
    
    public LiveData<List<File>> getListCurrent() {
    
    return ssh.getLiveData("list");
    }
    
    public void setListCurrent(List<File> mList) {
    
   ssh.set("list", mList);
    }
	
	  public void addBreadcrumbItem(String path) {
    	ssh.set("breadcrumb_item",new File(path));

}
	
	public LiveData<File> getBreadcrumbItem() {
         return  ssh.getLiveData("breadcrumb_item");
    }

	

    public LiveData<String> getCurrentPath() {
         return  ssh.getLiveData("path");
    }

    public void setCurrentPath(String path) {
    	//ssh.set("breadcrumb_item",new File(path));
		ssh.set("path", path);
       }
       
       public LiveData<String> getFatherPath() {
         return  ssh.getLiveData("father");
    }

    public void setFatherPath(String father) {
		ssh.set("father", father);
		setCurrentPath(father);
       }
         
         //editor start
         
      public void addEditor(Editable editable)  {
     List<Editable> mList = getListEditor().getValue();
     if (mList == null) {
            mList = new ArrayList<>();
        }
        
        if (!mList.contains(editable)) {
        mList.add(editable);
      ssh.set("list_editor", mList);
      bkpListEditor(mList);
        } 
      
	setCurrentTabPosition(mList.indexOf(editable));
     
      }
      
      
      public void closeEditor(Editable editable) {
      
      List<Editable> mList = getListEditor().getValue();
     if (mList == null) return;
        Editable find = null;
        for (Editable child : mList) {
            if (editable.equals(child)) find = child;
        }
        if (find != null) {
            mList.remove(find);
            ssh.set("list_editor", mList);
            bkpListEditor(mList);
        }
    }
    
     public void closeOthersEditor(Editable editable) {
      List<Editable> mList = getListEditor().getValue();
     if (mList == null) return;
        Editable find = null;
        for (Editable child : mList) {
            if (editable.equals(child)) find = child;
        }
        if (find != null) {
        	mList.clear();
            mList.add(find);
            ssh.set("list_editor", mList);
            bkpListEditor(mList);
        }
    }
      
      
      
     public void closeAllEditor(){
     ssh.set("list_editor", new ArrayList<>());
     unBkp(".open");
	 unBkp(".position");
     }
      
       
   public LiveData<List<Editable>> getListEditor() {
         return  ssh.getLiveData("list_editor");
    }
    
    
   public void setListEditor( List<Editable> list ) {
	 
		ssh.set("list_editor", list);
		
		 
		
	 }


     

    public LiveData<Integer> getCurrentTabPosition() {
        return currentPosition;
    }


    public void setCurrentTabPosition(int pos) {
        Integer value = currentPosition.getValue();
        if (value != null && value.equals(pos)) {
            return;
        }
        
        
   
    
    currentPosition.setValue(pos);
    bkpCurrentTabPosition(pos);
    
        
        
        
    }
    
      //editor end
   private void bkpListEditor(List<Editable> eList) {
   try {
         File openFile = new File(getFatherPath().getValue() + "/app/build/.cache", ".open");
         String content  = new GsonBuilder()
                .setPrettyPrinting()
                .create()
                .toJson(eList,new TypeToken<List<Editable>>(){}.getType());
                
       String encodedContent = Base64Util.encode(content);  
        
        if(!openFile.exists()) openFile.createNewFile();
		if(!eList.isEmpty()){
        FileUtils.writeStringToFile(openFile,
                 encodedContent,
                 Charset.defaultCharset());
                 }
                 } catch (Exception e) {}
   
   }
         
         
    private void bkpCurrentTabPosition(int position) {
   try {
         File positionFile = new File(getFatherPath().getValue() + "/app/build/.cache", ".position");
        
        if(!positionFile.exists()) positionFile.createNewFile();
		
        FileUtils.writeStringToFile(positionFile,
                 new Gson().toJson(position) ,
                 Charset.defaultCharset());
                 
                 } catch (Exception e) {}
   
   
         }
		 
		 private  void  unBkp(String path )  {
			 try {
			 File file = new File(getFatherPath().getValue() + "/app/build/.cache", path);
			 if(file.exists()) FileUtils.delete(file);
			 } catch (Exception e) {}
			 
			 }
         
         
}
