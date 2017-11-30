/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hector.desktopcast;

import com.google.common.eventbus.EventBus;
import com.hector.desktopcast.capture.DesktopManager;
import com.hector.desktopcast.cast.ChromeCastManager;
import java.util.concurrent.ScheduledExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.litvak.chromecast.api.v2.Application;
import su.litvak.chromecast.api.v2.ChromeCast;
import su.litvak.chromecast.api.v2.Status;

/**
 *
 * @author robin
 */
public class DesktopCast {

    private static final Logger logger = LoggerFactory.getLogger(DesktopCast.class);

    private static final DesktopManager desktopManager = new DesktopManager();
    private static final ChromeCastManager chromeCastManager = new ChromeCastManager();
    
    

    public static void main(String[] args) throws Exception
    {
        logger.info("Welcome");
        
        ChromeCast chromecast = new ChromeCast("192.168.1.179");
        chromeCastManager.selectChromecast(chromecast);
        
        
        /*
        chromecast.load("http://192.168.1.99:8080");
        chromecast.play();
        
        status = chromecast.getStatus();
        logger.info(status.toString()); */
        
        int s = System.in.read();
        chromeCastManager.shutdown();
        
    }
}
