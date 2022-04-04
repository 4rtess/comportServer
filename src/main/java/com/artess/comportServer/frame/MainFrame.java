package com.artess.comportServer.frame;

import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;
import org.cef.handler.CefFocusHandlerAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    private final CefApp CEF_APP;
    private final CefClient CEF_CLIENT;
    private final CefBrowser CEF_BROWSER;

    private final Component CEF_BROWSER_UI;

    private boolean BROWSER_FOCUS = true;

    public MainFrame(String startURL, boolean useOSR, boolean isTransparent) throws Exception {
        setUndecorated(true);

        CefAppBuilder builder = new CefAppBuilder();

        builder.getCefSettings().windowless_rendering_enabled = useOSR;
        builder.setAppHandler(new MavenCefAppHandlerAdapter() {
            @Override
            public void stateHasChanged(CefApp.CefAppState state) {
                if (state == CefApp.CefAppState.TERMINATED) System.exit(0);
            }
        });
        CEF_APP = builder.build();
        CEF_CLIENT = CEF_APP.createClient();
        CefMessageRouter msgRouter = CefMessageRouter.create();
        CEF_CLIENT.addMessageRouter(msgRouter);

        CEF_BROWSER = CEF_CLIENT.createBrowser(startURL, useOSR, isTransparent);

        CEF_BROWSER_UI = CEF_BROWSER.getUIComponent();



        CEF_CLIENT.addFocusHandler(new CefFocusHandlerAdapter() {
            @Override
            public void onGotFocus(CefBrowser browser) {
                if(BROWSER_FOCUS) return;
                BROWSER_FOCUS=true;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                browser.setFocus(true);
            }
            @Override
            public void onTakeFocus(CefBrowser browser, boolean next) {
                BROWSER_FOCUS=false;
            }
        });


        getContentPane().add(CEF_BROWSER_UI, BorderLayout.CENTER);
        pack();
        setExtendedState(MAXIMIZED_BOTH);

        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CefApp.getInstance().dispose();
                dispose();
            }
        });

    }
}
