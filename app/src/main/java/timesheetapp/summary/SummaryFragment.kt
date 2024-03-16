package timesheetapp.summary

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import core.ButtonWithIcon
import core.CustomBottomNavigationItemView
import core.DatePickerButton
import dagger.hilt.android.AndroidEntryPoint
import timesheetapp.R
import ui.Padding
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

@AndroidEntryPoint
class SummaryFragment : Fragment() {

    private val viewModel by viewModels<SummaryFragmentViewModel>()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Scaffold(
                    bottomBar = {
                        CustomBottomNavigationItemView(
                            summaryPageSelected = true,
                            onHomeButtonAction = {
                                val action = SummaryFragmentDirections.actionSummaryFragmentToHomeFragment()
                                findNavController().navigate(action)
                            }
                        )
                    },
                    content = {
                        SummaryScreen(modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                vertical = Padding.paddingMedium,
                                horizontal = Padding.paddingMedium,
                            )
                        )
                    }
                )
            }
        }
    }

    @Composable
    private fun SummaryScreen(
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Padding.paddingMedium),
        ) {
            // Search Field
            SearchInputField(
                modifier = Modifier.fillMaxWidth()
            )

            val shareLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

            ButtonWithIcon(
                text = stringResource(R.string.share),
                imageVector = Icons.Filled.Share,
                onClick = {
                    val contentToShare = viewModel.currentReport.value
                    contentToShare.let {
                        val sharedFileUri = createTemporaryFile(requireContext(), "shared_content.txt", it)
                        shareContent(shareLauncher, sharedFileUri)
                    }
                }
            )

            // Testing, remove it
            Text(
                text = "Search from: ${viewModel.searchDateRange.value}"
            )

            HorizontalScrollableTextView(
                text = viewModel.currentReport.value
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun SearchInputField(
        modifier: Modifier = Modifier,
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            DatePickerButton(
                text = stringResource(R.string.select_start_date),
                onDateSelected = viewModel::onSelectedStartDate,
            )

            DatePickerButton(
                text = stringResource(R.string.select_end_date),
                onDateSelected = viewModel::onSelectedEndDate,
            )
        }
    }

    private fun createTemporaryFile(context: android.content.Context, fileName: String, content: String): Uri {
        val file = File(context.cacheDir, fileName)

        BufferedWriter(FileWriter(file)).use { writer ->
            writer.write(content)
        }

        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    private fun shareContent(shareLauncher: ActivityResultLauncher<Intent>, contentUri: Uri) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
//            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, contentUri)
        }
        shareLauncher.launch(shareIntent)
    }

    @Composable
    private fun HorizontalScrollableTextView(text: String) {
        val scrollState = rememberScrollState()

        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(Padding.paddingSmall)
        ) {
            Text(
                text = text,
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
            )
        }
    }

}