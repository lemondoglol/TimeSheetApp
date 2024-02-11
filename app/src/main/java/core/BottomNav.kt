package core

import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import timesheetapp.R

@Composable
fun CustomBottomNavigationItemView(
    modifier: Modifier = Modifier,
    onHomeButtonAction: () -> Unit = {},
    onSummaryButtonAction: () -> Unit = {},
) {
    BottomNavigation {
        ButtonWithIcon(
            text = stringResource(R.string.home),
            imageVector = Icons.Filled.Home,
            onClick = {
                onHomeButtonAction()
            },
        )
        ButtonWithIcon(
            text = stringResource(R.string.summary),
            onClick = {
                onSummaryButtonAction()
            },
            imageVector = Icons.Filled.Book,
        )
    }
}