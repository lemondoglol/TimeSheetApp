package timesheetapp.summary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import core.ButtonWithIcon
import core.CustomBottomNavigationItemView
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class SummaryFragment : Fragment() {

    private val viewModel by viewModels<SummaryFragmentViewModel>()

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
                            onHomeButtonAction = {
                                val action = SummaryFragmentDirections.actionSummaryFragmentToHomeFragment()
                                findNavController().navigate(action)
                            }
                        )
                    },
                    content = {
                        val shareLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            ButtonWithIcon(
                                text = "Share",
                                imageVector = Icons.Filled.Share,
                                onClick = {
                                    val contentToShare = viewModel.currentReport.value
                                    val sharedFileUri = createTemporaryFile(context, "shared_content.txt", contentToShare)
                                    shareContent(shareLauncher, sharedFileUri)
                                }
                            )

                            Text(
                                modifier = Modifier.padding(top = it.calculateTopPadding()),
                                text = viewModel.currentReport.value
                            )
                        }

                    }
                )
            }
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