package com.ibnu.distory

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ibnu.distory.presentation.home.HomeFragment
import com.ibnu.distory.presentation.login.LoginFragment
import com.ibnu.distory.presentation.settings.SettingsFragment
import com.ibnu.distory.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class LoginLogoutTest {

    lateinit var instrumentationContext: Context
    private val email = "ibnubatutah001@gmail.com"
    private val password = "12345678"

    @Before
    fun setUp() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun loadLoginScreen() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val titleScenario = launchFragmentInContainer<LoginFragment>(themeResId = R.style.Theme_Distory)

        titleScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.main_navigation)
            navController.setCurrentDestination(R.id.loginFragment)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.edtEmail)).perform(typeText(email))
        onView(withId(R.id.edtPassword)).perform(typeText(password))
        closeSoftKeyboard()
    }

    @Test
    fun loadHomeScreen() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val titleScenario = launchFragmentInContainer<HomeFragment>(themeResId = R.style.Theme_Distory)

        titleScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.main_navigation)
            navController.setCurrentDestination(R.id.homeFragment)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        onView(withId(R.id.btn_setting)).check(matches(isDisplayed()))
    }

    @Test
    fun loadSettingScreen() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val titleScenario = launchFragmentInContainer<SettingsFragment>(themeResId = R.style.Theme_Distory)

        titleScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.main_navigation)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.btnLogout)).check(matches(isDisplayed()))
        onView(withId(R.id.btnLogout)).perform(click())
        onView(withId(android.R.id.button2)).perform(click())
    }
}