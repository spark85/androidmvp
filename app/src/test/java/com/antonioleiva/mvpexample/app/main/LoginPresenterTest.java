package com.antonioleiva.mvpexample.app.main;

import com.antonioleiva.mvpexample.app.Login.LoginInteractor;
import com.antonioleiva.mvpexample.app.Login.LoginInteractorImpl;
import com.antonioleiva.mvpexample.app.Login.LoginPresenterImpl;
import com.antonioleiva.mvpexample.app.Login.LoginView;

import net.bytebuddy.dynamic.scaffold.TypeWriter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
/**
 * Created by davidozersky on 2016-11-19.
 */

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    LoginView loginView;

    @Mock
    private LoginInteractor loginInteractor;

    @Mock
    private LoginInteractor.OnLoginFinishedListener loginFinishedListener;

    private LoginPresenterImpl loginPresenter;

    @Before
    public void setup() {
        loginPresenter = new LoginPresenterImpl(loginView, loginInteractor);
    }

    @Test
    public void testOnDestroyDestroysLoginView() {
        loginPresenter.onDestroy();
        assertNull(loginPresenter.getLoginView());
        //test loginView = null
    }

    @Test
    public void testValidateCredentialsShowProgress() {
        loginPresenter.validateCredentials("username", "password");
        verify(loginView, times(1)).showProgress();
    }

    @Test
    public void testValidateCredentialsCallsLogin() {
        ArgumentCaptor<String> usernameCaptor = forClass(String.class);
        ArgumentCaptor<String> passwordCaptor = forClass(String.class);
        ArgumentCaptor<LoginInteractor.OnLoginFinishedListener> loginInteractorArgumentCaptor = forClass(LoginInteractor.OnLoginFinishedListener.class);
        loginPresenter.validateCredentials("username", "password");
        verify(loginInteractor, times(1)).login(usernameCaptor.capture(),
                                                passwordCaptor.capture(),
                                                loginInteractorArgumentCaptor.capture());
        assertThat(usernameCaptor.getValue(), is("username"));
        assertThat(passwordCaptor.getValue(), is("password"));
        assertThat(loginInteractorArgumentCaptor.getValue(), is((LoginInteractor.OnLoginFinishedListener) loginPresenter));

    }
}
