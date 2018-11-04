/**
 *   ownCloud Android client application
 *
 *   @author Bartek Przybylski
 *   @author Christian Schabesberger
 *   Copyright (C) 2012 Bartek Przybylski
 *   Copyright (C) 2018 ownCloud GmbH.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License version 2,
 *   as published by the Free Software Foundation.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.nigelcloud.android.ui.activity;

import com.nigelcloud.android.datamodel.FileDataStorageManager;
import com.nigelcloud.android.ui.helpers.FileOperationsHelper;
import com.nigelcloud.android.files.services.FileDownloader.FileDownloaderBinder;
import com.nigelcloud.android.files.services.FileUploader.FileUploaderBinder;
import com.nigelcloud.android.services.OperationsService.OperationsServiceBinder;

public interface ComponentsGetter {

    /**
     * To be invoked when the parent activity is fully created to get a reference
     * to the FileDownloader service API.
     */
     FileDownloaderBinder getFileDownloaderBinder();

    
    /**
     * To be invoked when the parent activity is fully created to get a reference
     * to the FileUploader service API.
     */
     FileUploaderBinder getFileUploaderBinder();

    
    /**
     * To be invoked when the parent activity is fully created to get a reference
     * to the OperationsSerivce service API.
     */
     OperationsServiceBinder getOperationsServiceBinder();

    
     FileDataStorageManager getStorageManager();
    
     FileOperationsHelper getFileOperationsHelper();


}
