package com.ibnu.distory.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ibnu.distory.data.network.ApiResponse
import com.ibnu.distory.data.repository.AuthRepository
import com.ibnu.distory.utils.CoroutinesTestRule
import com.ibnu.distory.utils.DummyData
import com.ibnu.distory.utils.helper.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var authRepository: AuthRepository


    private lateinit var loginViewModel: LoginViewModel

    private val dummyEmail = DummyData.emailDummy()
    private val dummyPassword = DummyData.passwordDummy()
    private val dummyToken = DummyData.tokenDummy()

    @Before
    fun setUp() {
        authRepository = Mockito.mock(authRepository::class.java)
        loginViewModel = LoginViewModel(authRepository)
    }

    @get:Rule
    var mainCoroutineRule = CoroutinesTestRule()

    @Test
    fun `Login success and get result success`(): Unit = runTest {
        val response = ApiResponse.Success("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLWI2d3NBNDFDTmtUSTVITE8iLCJpYXQiOjE2ODAxMDY5ODd9.7PvzP8TiwBX61JZCRsypL8LF38NxaqdtRhNu_lVUey0")
        val flow = flowOf(response)

        `when`(authRepository.login(dummyEmail, dummyPassword)).thenReturn(flow)

        loginViewModel.loginUser(dummyEmail, dummyPassword)

        verify(authRepository).login(dummyEmail, dummyPassword)

        val observer = Observer<Event<ApiResponse<String>>>{}
        loginViewModel.loginResult.observeForever(observer)

        val result = loginViewModel.loginResult.value
        result?.getContentIfNotHandled().let { responseResult ->
            if (responseResult is ApiResponse.Success) {
                assertNotNull(responseResult)
                assertSame(dummyToken, responseResult.data)
            }
        }
    }

    @Test
    fun `Login failed and get error result with Exception`(): Unit = runTest {
        val response = ApiResponse.Error("Error Login")
        val flow = flowOf(response)

        `when`(authRepository.login(dummyEmail, dummyPassword)).thenReturn(flow)

        loginViewModel.loginUser(dummyEmail, dummyPassword)

        verify(authRepository).login(dummyEmail, dummyPassword)

        val observer = Observer<Event<ApiResponse<String>>>{}
        loginViewModel.loginResult.observeForever(observer)

        val result = loginViewModel.loginResult.value
        result?.getContentIfNotHandled().let { responseResult ->
            if (responseResult is ApiResponse.Error) {
                assertNotNull(responseResult)
            }
        }
    }

}