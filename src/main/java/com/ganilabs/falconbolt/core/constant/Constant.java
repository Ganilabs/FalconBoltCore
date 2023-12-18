package com.ganilabs.falconbolt.core.constant;

public class Constant {
    public static final String APP_TITLE = "Falcon Bolt";
    public static class ModelChangeMessages{
        public static final String NEW_PLUGIN_FOUND = "newPluginFound";
        public static final String PROJECT_CRUD = "projectCRUD";
    }
    public static class ErrorMessages{
    	public static final String ERROR_ENCOUNTERED = "errorEncountered";
    	public static final String PLUGINS_FAILED_TO_LOAD = "pluginsFailedToLoad";
    	public static final String RESOURCE_FAILED_TO_LOAD = "resourceFailedToLoad";
    	public static final String CUSTOM_ERROR_MESSAGE = "customErrorMessage";
    }
    public static class ViewMessages{
    	public static final String NEW_PROJECT_NAME = "newProjectName";
    	public static final String OPERATION_SUCCESS = "operationSuccess";
    	public static final String OPEN_SELECTED_PROJECT = "openSelectedProject";
    	public static final String DELETE_SELECTED_PROJECT = "deleteSelectedProject";
    	public static final String CLOSE_OPENED_PROJECT = "closeOpenedProject";
    }

    public static class ToolMessages{
        public static final String OPEN_CLOSED_TOOL = "openClosedTool";
        public static final String CLOSE_SELECTED_TOOL = "closeSelectedTool";
        public static final String SCAN_RESULT_CHANGE = "SCAN_RESULT_CHANGED";
    }
    
}
