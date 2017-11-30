/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hector.desktopcast.capture;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author robin
 */
public class DesktopManager
{
    private List<Desktop> desktops;
    
    public DesktopManager()
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screenDevices = ge.getScreenDevices();
        
        desktops = new ArrayList<>();
        for(GraphicsDevice screenDevice : screenDevices)
        {
            desktops.add(new Desktop(screenDevice.getIDstring()));
        }
    }
    
    public List<Desktop> getDesktops()
    {
        return desktops;
    }
}
