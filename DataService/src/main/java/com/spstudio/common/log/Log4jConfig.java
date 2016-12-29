/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.common.log;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

/**
 *
 * @author wewezhu
 */
public class Log4jConfig {

    public void enableInfo(String target) {
        LogManager.getLogger(target).setLevel(Level.INFO);
      
    }

    public void enableWarn(String target) {
        LogManager.getLogger(target).setLevel(Level.WARN);
    }

    public void enableError(String target) {
        LogManager.getLogger(target).setLevel(Level.ERROR);
    }

    public void enableDebug(String target) {
        LogManager.getLogger(target).setLevel(Level.DEBUG);
    }
}
