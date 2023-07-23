package com.ardev.idroid.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.ardev.idroid.ui.home.ProjectManager;
import com.ardev.builder.project.Project;
import com.ardev.idroid.ui.main.MainViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles the communication between the Index service and the main fragment
 */
public class IndexServiceConnection implements ServiceConnection {

    private final MainViewModel mMainViewModel;
    
    private Project mProject;

    public IndexServiceConnection(MainViewModel mainViewModel) {
        mMainViewModel = mainViewModel;
      
      
    }

    public void setProject(Project project) {
        mProject = project;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        IndexService.IndexBinder binder = (IndexService.IndexBinder) iBinder;
        try {
            mProject.setCompiling(true);
            binder.index(mProject, new TaskListener());
        } finally {
            mProject.setCompiling(false);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mMainViewModel.setIndexing(false);
        mMainViewModel.setCurrentState(null);
    }

    private class TaskListener implements ProjectManager.TaskListener {

        @Override
        public void onTaskStarted(String message) {
            mMainViewModel.setCurrentState(message);
        }

        
        @Override
        public void onComplete(Project project, boolean success, String message) {
            mMainViewModel.setIndexing(false);
            mMainViewModel.setCurrentState(message);
            
        }
    }

   

   
}
