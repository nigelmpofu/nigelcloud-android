package com.nigelcloud.android.test.ui.testSuites;

import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import com.nigelcloud.android.test.ui.groups.FlexibleCategories;
import com.nigelcloud.android.test.ui.groups.InProgressCategory;
import com.nigelcloud.android.test.ui.groups.FlexibleCategories.TestClassPrefix;
import com.nigelcloud.android.test.ui.groups.FlexibleCategories.TestClassSuffix;
import com.nigelcloud.android.test.ui.groups.FlexibleCategories.TestScanPackage;


@RunWith(FlexibleCategories.class)
@IncludeCategory(InProgressCategory.class)
@TestScanPackage("com.nigelcloud.android.test.ui.testSuites")
@TestClassPrefix("")
@TestClassSuffix("TestSuite")
public class RunInProgressTest {

}
