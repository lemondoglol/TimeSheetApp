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
import androidx.compose.foundation.layout.*
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
import java.io.File
import java.io.FileOutputStream

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
                        SummaryScreen(modifier = Modifier.padding(top = it.calculateTopPadding()))
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
            verticalArrangement = Arrangement.Center,
        ) {
            // Search Field
            SearchInputField(
                modifier = Modifier.fillMaxWidth()
            )

            val shareLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

            ButtonWithIcon(
                text = "Share",
                imageVector = Icons.Filled.Share,
                onClick = {
                    val contentToShare = viewModel.currentReport.value
                    val sharedFileUri = createTemporaryFile(requireContext(), "shared_content.txt", contentToShare)
                    shareContent(shareLauncher, sharedFileUri)
                }
            )

            // Testing, remove it
            Text(
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
                text = stringResource(R.string.select_start_date)
            )

            DatePickerButton(
                text = stringResource(R.string.select_end_date)
            )
        }
    }

    private fun createTemporaryFile(context: android.content.Context, fileName: String, content: String): Uri {
        val file = File(context.cacheDir, fileName)
        val fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(content.toByteArray())
        fileOutputStream.close()

        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    private fun shareContent(shareLauncher: ActivityResultLauncher<Intent>, contentUri: Uri) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_STREAM, contentUri)
        }
        shareLauncher.launch(shareIntent)
    }

}