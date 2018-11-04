/**
 *   ownCloud Android client application
 *
 *   @author David González Verdugo
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

import com.nigelcloud.android.datamodel.OCFile;
import com.nigelcloud.android.lib.common.OwnCloudClient;
import com.nigelcloud.android.lib.common.operations.RemoteOperationResult;
import com.nigelcloud.android.lib.resources.files.chunks.MoveRemoteChunksFileOperation;

/**
 * Operation moving a {@link OCFile} to its final destination after being upload in chunks
 */
public class MoveChunksFileOperation extends MoveFileOperation {

    private String mFileLastModifTimestamp;
    private long mFileLength;

    /**
     * Constructor
     * @param srcPath          Remote path of the {@link OCFile} to move.
     * @param targetParentPath  Path to the folder where the file will be moved into.
     * @param fileLastModifTimestamp Timestamp of last modification of file to move.
     * @param fileLength        Total length of the file to move.
     */
    public MoveChunksFileOperation(String srcPath, String targetParentPath, String fileLastModifTimestamp,
                                   long fileLength) {
        super(srcPath, targetParentPath);
        mFileLastModifTimestamp = fileLastModifTimestamp;
        mFileLength = fileLength;
    }

    @Override
    protected RemoteOperationResult run(OwnCloudClient client) {

        MoveRemoteChunksFileOperation operation = new MoveRemoteChunksFileOperation(
                mSrcPath,
                mTargetParentPath,
                false,
                mFileLastModifTimestamp,
                mFileLength
        );

        return operation.execute(client);
    }
}