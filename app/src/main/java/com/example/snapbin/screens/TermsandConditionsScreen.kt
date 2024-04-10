package com.example.snapbin.screens

//import com.example.snapbin.Navigation.SystemBackButtonHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.snapbin.Components.NormalTextComponentTerms
import com.example.snapbin.Components.NormalTextComponents
import com.example.snapbin.Components.WelcomeComponent
import com.example.snapbin.R

@Composable
fun TermsandConditionsScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center)
    {
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                NormalTextComponents(value = stringResource(R.string.Title))
                Spacer(modifier = Modifier.height(16.dp))
                WelcomeComponent(value = stringResource(R.string.terms_and_condition))
                LazyColumn(modifier = Modifier.fillMaxSize()){
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.TopStart
                        ){
                            NormalTextComponentTerms(
                                value = "Welcome to SnapBin!\n" +
                                        "\n" +
                                        "These terms and conditions outline the rules and regulations for the use of SnapBin's Application, located at [your app's website or platform].\n" +
                                        "\n" +
                                        "By accessing this application, we assume you accept these terms and conditions. Do not continue to use SnapBin if you do not agree to all of the terms and conditions stated on this page.\n" +
                                        "\n" +
                                        "The following terminology applies to these Terms and Conditions, Privacy Statement and Disclaimer Notice and all Agreements: \"Client\", \"You\" and \"Your\" refers to you, the person log on this application and compliant to the Company’s terms and conditions. \"The Company\", \"Ourselves\", \"We\", \"Our\" and \"Us\", refers to our Company. \"Party\", \"Parties\", or \"Us\", refers to both the Client and ourselves. All terms refer to the offer, acceptance and consideration of payment necessary to undertake the process of our assistance to the Client in the most appropriate manner for the express purpose of meeting the Client’s needs in respect of provision of the Company’s stated services, in accordance with and subject to, prevailing law of [your country]. Any use of the above terminology or other words in the singular, plural, capitalization and/or he/she or they, are taken as interchangeable and therefore as referring to same.\n" +
                                        "\n" +
                                        "Cookies\n" +
                                        "\n" +
                                        "We employ the use of cookies. By accessing SnapBin, you agreed to use cookies in agreement with the SnapBin's Privacy Policy.\n" +
                                        "\n" +
                                        "Most interactive applications use cookies to let us retrieve the user’s details for each visit. Cookies are used by our application to enable the functionality of certain areas to make it easier for people visiting our website. Some of our affiliate/advertising partners may also use cookies.\n" +
                                        "\n" +
                                        "License\n" +
                                        "\n" +
                                        "Unless otherwise stated, SnapBin and/or its licensors own the intellectual property rights for all material on SnapBin. All intellectual property rights are reserved. You may access this from SnapBin for your own personal use subjected to restrictions set in these terms and conditions.\n" +
                                        "\n" +
                                        "You must not:\n" +
                                        "\n" +
                                        "Republish material from SnapBin\n" +
                                        "Sell, rent or sub-license material from SnapBin\n" +
                                        "Reproduce, duplicate or copy material from SnapBin\n" +
                                        "Redistribute content from SnapBin\n" +
                                        "This Agreement shall begin on the date hereof.\n" +
                                        "\n" +
                                        "Parts of this application offer an opportunity for users to post and exchange opinions and information in certain areas of the website. SnapBin does not filter, edit, publish or review Comments prior to their presence on the application. Comments do not reflect the views and opinions of SnapBin, its agents and/or affiliates. Comments reflect the views and opinions of the person who post their views and opinions. To the extent permitted by applicable laws, SnapBin shall not be liable for the Comments or for any liability, damages or expenses caused and/or suffered as a result of any use of and/or posting of and/or appearance of the Comments on this application.\n" +
                                        "\n" +
                                        "SnapBin reserves the right to monitor all Comments and to remove any Comments which can be considered inappropriate, offensive or causes breach of these Terms and Conditions."
                            )
                        }

                    }
                }

            }
        }
    }
}
