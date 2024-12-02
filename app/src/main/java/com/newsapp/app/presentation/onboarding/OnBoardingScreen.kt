package com.newsapp.app.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.newsapp.app.presentation.Dimens.MediumPadding2
import com.newsapp.app.presentation.Dimens.PageIndicatorWidth
import com.newsapp.app.presentation.common.NewsButton
import com.newsapp.app.presentation.common.NewsTextButton
import com.newsapp.app.presentation.onboarding.components.OnBoardingPage
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier) {

    Column(modifier = Modifier.fillMaxWidth()) {
        val pagerState = rememberPagerState(initialPage = 0) { pages.size }
        val buttonsState = remember {
            derivedStateOf {
                when (pagerState.currentPage) {
                    0 -> listOf("", "Next")
                    1 -> listOf("Back", "Next")
                    2 -> listOf("Back", "Get Started")
                    else -> listOf("", "")

                }
            }
        }

        HorizontalPager(state = pagerState) { index ->
            OnBoardingPage(page = pages[index])
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding2)
                .navigationBarsPadding(),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PageIndicator(
                modifier = Modifier.width(PageIndicatorWidth),
                pagesSize = pages.size,
                selectedPage = pagerState.currentPage
            )

            Row(verticalAlignment = Alignment.CenterVertically) {

                val scope = rememberCoroutineScope()

                if (buttonsState.value[0].isNotEmpty()) {
                    NewsTextButton(text = buttonsState.value[0], onClick = {

                        scope.launch {
                            pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                        }

                    })
                }

                NewsButton(text = buttonsState.value[1], onClick = {
                    scope.launch {
                        if (pagerState.currentPage == pages.size) {
                            //TODO: Navigate to the main screen
                        } else {
                            pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                        }
                    }
                })
            }
        }

        Spacer(modifier = Modifier.weight(0.3f))

    }

}