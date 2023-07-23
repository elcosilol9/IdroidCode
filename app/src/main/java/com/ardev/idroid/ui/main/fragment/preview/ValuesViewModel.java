package com.ardev.idroid.ui.main.fragment.preview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import com.ardev.idroid.ui.main.fragment.preview.model.Item;
 

public class ValuesViewModel extends ViewModel {

 
    
	SavedStateHandle ssh;
public  ValuesViewModel(SavedStateHandle ssh) {
    
	 this.ssh = ssh;
	} 
	  public void setItem(Item item) {
	  //ssh.set("item", item);
	  ssh.set("path", item.getFilePath());
	  ssh.set("name", item.getName());
	  ssh.set("value", item.getValue());
	  ssh.set("type", item.getType());
	 
	  }
	
	public LiveData<Item> getItem() {
	 return ssh.getLiveData("item");
	  }
	
	public LiveData<String> getFilePath() {
	
	return ssh.getLiveData("path");
	}
	
    
       public LiveData<String> getName() {
         return  ssh.getLiveData("name");
    }

     
       
         public LiveData<String> getItemValue() {
         return  ssh.getLiveData("value");
    }

    
   public LiveData<String> getType() {
         return  ssh.getLiveData("type");
    }
    
    public void setState(boolean isEditing) {
    ssh.set("state", isEditing);
    }
 
   public LiveData<Boolean> getState() {
         return  ssh.getLiveData("state");
    }
    
     
         
}
