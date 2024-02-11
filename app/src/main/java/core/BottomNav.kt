package core

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import timesheetapp.R

@Composable
fun CustomBottomNavigationItemView(
    modifier: Modifier = Modifier,
    homePageSelected: Boolean = false,
    summaryPageSelected: Boolean = false,
    onHomeButtonAction: () -> Unit = {},
    onSummaryButtonAction: () -> Unit = {},
) {
    BottomNavigation(modifier = modifier) {
        BottomNavigationItem(
            selected = homePageSelected,
            onClick = { onHomeButtonAction() },
            icon = {
                IconText(
                    text = stringResource(R.string.home),
                    imageVector = Icons.Filled.Home,
                )
            },
        )
        BottomNavigationItem(
            selected = summaryPageSelected,
            onClick = { onSummaryButtonAction() },
            icon = {
                IconText(
                    text = stringResource(R.string.summary),
                    imageVector = Icons.Filled.Summarize,
                )
            },
        )
    }
}