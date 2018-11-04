/**
 *   ownCloud Android client application
 *
 *   @author masensio
 *   @author David González Verdugo
 *   @author Christian Schabesberger
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

package com.nigelcloud.android.operations;

import com.nigelcloud.android.lib.common.OwnCloudClient;
import com.nigelcloud.android.lib.common.operations.RemoteOperationResult;
import com.nigelcloud.android.lib.common.utils.Log_OC;
import com.nigelcloud.android.lib.resources.shares.GetRemoteSharesForFileOperation;
import com.nigelcloud.android.lib.resources.shares.ShareParserResult;
import com.nigelcloud.android.operations.common.SyncOperation;

/**
 * Provide a list shares for a specific file.
 */
public class GetSharesForFileOperation extends SyncOperation<ShareParserResult> {
    
    private static final String TAG = GetSharesForFileOperation.class.getSimpleName();
    
    private String mPath;
    private boolean mReshares;
    private boolean mSubfiles;

    /**
     * Constructor
     * 
     * @param path      Path to file or folder
     * @param reshares  If set to false (default), only shares from the current user are returned
     *                  If set to true, all shares from the given file are returned
     * @param subfiles  If set to false (default), lists only the folder being shared
     *                  If set to true, all shared files within the folder are returned.
     */
    public GetSharesForFileOperation(String path, boolean reshares, boolean subfiles) {
        mPath = path;
        mReshares = reshares;
        mSubfiles = subfiles;
    }

    protected RemoteOperationResult<ShareParserResult> run(OwnCloudClient client) {
        GetRemoteSharesForFileOperation operation = new GetRemoteSharesForFileOperation(mPath,
                mReshares, mSubfiles);

        RemoteOperationResult<ShareParserResult> result = operation.execute(client);

        if (result.isSuccess()) {

            // Update DB with the response
            Log_OC.d(TAG, "File = " + mPath + " Share list size  " + result.getData().getShares().size());
            getStorageManager().saveShares(result.getData().getShares());

        } else if (result.getCode() == RemoteOperationResult.ResultCode.SHARE_NOT_FOUND) {
            // no share on the file - remove local shares
            getStorageManager().removeSharesForFile(mPath);

        }

        return result;
    }
}