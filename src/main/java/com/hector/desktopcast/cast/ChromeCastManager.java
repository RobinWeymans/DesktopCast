/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hector.desktopcast.cast;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.litvak.chromecast.api.v2.Application;
import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.ChromeCasts;
import su.litvak.chromecast.api.v2.ChromeCastsListener;
import su.litvak.chromecast.api.v2.Status;

/**
 *
 * @author robin
 */
public class ChromeCastManager implements ChromeCastsListener {
    
    private static final Logger logger = LoggerFactory.getLogger(ChromeCastManager.class);
    
    private List<ChromeCast> chromecasts;
    private ChromeCast selectedChromecast;
    
    private static final String APP_ID = "CC1AD845"; //Default receiver
    private Application app;
    
    public ChromeCastManager()
    {
        try
        {
            ChromeCasts.startDiscovery();
            ChromeCasts.registerListener(this);
        }
        catch(IOException e)
        {
            logger.error(e.getMessage());
        }
    }
    
    public void shutdown()
    {
        try
        {
            logger.info("Unregistering the chromecast listener.");
            ChromeCasts.unregisterListener(this);
            logger.info("Unregistering the chromecast listener. [DONE]");
            
            logger.info("Stopping chromecast discovery.");
            ChromeCasts.stopDiscovery();
            logger.info("Stopping chromecast discovery. [DONE]");
            
            if(selectedChromecast != null && selectedChromecast.isConnected())
            {
                if(selectedChromecast.isAppRunning(APP_ID))
                {
                    logger.info("Stopping chromecast app.");
                    selectedChromecast.stopApp();                
                    logger.info("Stopping chromecast app. [DONE]");
                }
                if(selectedChromecast.isConnected())
                {
//                    try
//                    {
//                        logger.info("Disconnecting from chromecast.");
//                        selectedChromecast.disconnect();                    
//                        logger.info("Disconnecting from chromecast. [DONE]");
//                    }
//                    catch(SocketException e)
//                    {
//                        
//                    }
                }
            }
        }
        catch(IOException e)
        {
            logger.error(e.getMessage());
        }
    }
    
    public List<ChromeCast> getChromeCasts()
    {
        return chromecasts;
    }
    
    /**
     * If the app is connected to another chromecast at this time, it stops the app and disconnects from the chromecast.
     * Next, it connects to the new chromecast.
     * @param chromecast The new chromecast to connect to.
     * @throws IOException 
     * @throws java.security.GeneralSecurityException 
     */
    public void selectChromecast(ChromeCast chromecast) throws IOException, GeneralSecurityException{
        
        if( selectedChromecast != null && selectedChromecast.isConnected())
        {
            selectedChromecast.stopApp();
            selectedChromecast.disconnect();
        }
        
        selectedChromecast = chromecast;
        selectedChromecast.connect();
        
        Status status = chromecast.getStatus();
        if (chromecast.isAppAvailable(APP_ID) && !status.isAppRunning(APP_ID)) {
            app = chromecast.launchApp(APP_ID);
        }
        
        chromecast.load("http://192.168.1.99:8080");
        chromecast.play();
    }

    @Override
    public void newChromeCastDiscovered(ChromeCast chromecast) {
        logger.info("+{}", chromecast.getName());
    }

    @Override
    public void chromeCastRemoved(ChromeCast chromecast) {
        logger.info("-{}", chromecast.getName());
    }
}
