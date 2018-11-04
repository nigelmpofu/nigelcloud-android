/**
 *   ownCloud Android client application
 *
 *   @author purigarcia
 *   Copyright (C) 2016 ownCloud GmbH.
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

package com.nigelcloud.android.test.ui.testSuites;

import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;

import com.nigelcloud.android.test.ui.groups.FlexibleCategories;
import com.nigelcloud.android.test.ui.groups.IgnoreTestCategory;
import com.nigelcloud.android.test.ui.groups.SmokeTestCategory;
import com.nigelcloud.android.test.ui.groups.FlexibleCategories.TestClassPrefix;
import com.nigelcloud.android.test.ui.groups.FlexibleCategories.TestClassSuffix;
import com.nigelcloud.android.test.ui.groups.FlexibleCategories.TestScanPackage;


@RunWith(FlexibleCategories.class)
@ExcludeCategory(IgnoreTestCategory.class)
@IncludeCategory(SmokeTestCategory.class)
@TestScanPackage("com.nigelcloud.android.test.ui.testSuites")
@TestClassPrefix("")
@TestClassSuffix("TestSuite")
public class RunSmokeTests {
}