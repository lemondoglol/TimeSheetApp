package timesheetapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import core.ButtonWithIcon
import core.CustomBottomNavigationItemView
import core.OutLinedNumberInputTextField
import dagger.hilt.android.AndroidEntryPoint
import ui.Padding
import ui.TickerMainInformationSecion

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeFragmentViewModel>()

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
                            onSummaryButtonAction = {
                                val action = HomeFragmentDirections.actionHomeFragmentToSummaryFragment()
                                findNavController().navigate(action)
                            }
                        )
                    },
                    content = {
                        MainOperationScreen(
                            modifier = Modifier
                                .padding(
                                    top = it.calculateTopPadding(),
                                    bottom = it.calculateBottomPadding(),
                                )
                                .fillMaxSize()
                                .padding(Padding.rowPadding),
                        )
                    }
                )
            }
        }
    }

    @Composable
    private fun MainOperationScreen(
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Padding.paddingSmall),
        ) {
            // Ticket Row
            OutLinedNumberInputTextField(
                label = stringResource(R.string.repair_order),
                hint = stringResource(R.string.enter_repair_order),
                onValueChange = {
                    viewModel.fetchTicket(it.toString())
                    viewModel.onRepairOrderValueChanged(it.toString())
                }
            )

            // Old and New Record Row

            // Information Section
            TickerMainInformationSecion(
                modifier = Modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 1f, fill = false)
                    .padding(vertical = Padding.paddingMedium),
                ticketEntity = viewModel.oldRepairOrder.value,
                viewModel = viewModel,
            )

            // Update, Close Ticket Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                ButtonWithIcon(
                    onClick = viewModel::insertTicket,
                    imageVector = Icons.Filled.Update,
                    text = stringResource(R.string.update),
                )

                ButtonWithIcon(
                    onClick = viewModel::closeTicket,
                    imageVector = Icons.Filled.Close,
                    text = stringResource(R.string.close_ticket),
                )
            }
        }
    }

}
