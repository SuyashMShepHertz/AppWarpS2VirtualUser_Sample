/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package botsample;

import com.shephertz.app42.server.idomain.BaseServerAdaptor;
import com.shephertz.app42.server.idomain.IZone;

/**
 *
 * @author Suyash
 */
public class BotServerAdaptor extends BaseServerAdaptor{
    
    BotServerAdaptor()
    {
        
    }
    
    @Override
    public void onZoneCreated(IZone zone)
    {             
        System.out.println("Zone Created : "+zone.getAppKey());
        zone.setAdaptor(new BotZoneAdaptor(zone));
    }
}
