/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package botsample;

import com.shephertz.app42.server.AppWarpServer;
import com.shephertz.app42.server.idomain.BaseServerAdaptor;

/**
 *
 * @author Suyash
 */
public class BotSample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String appconfigPath = System.getProperty("user.dir")+System.getProperty("file.separator")+"AppConfig.json";
	boolean started = AppWarpServer.start(new BotServerAdaptor(), appconfigPath);
    }
    
}
