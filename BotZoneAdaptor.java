/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package botsample;

import com.shephertz.app42.server.domain.TurnBasedRoom;
import com.shephertz.app42.server.idomain.BaseZoneAdaptor;
import com.shephertz.app42.server.idomain.HandlingResult;
import com.shephertz.app42.server.idomain.IRoom;
import com.shephertz.app42.server.idomain.IUser;
import com.shephertz.app42.server.idomain.IZone;

/**
 *
 * @author Suyash
 */
public class BotZoneAdaptor extends BaseZoneAdaptor {
    
    IZone zone;
    
    BotZoneAdaptor(IZone _zone)
    {
        zone = _zone;
    }
    
    @Override
    public void onAdminRoomAdded(IRoom room)
    {
        System.out.println("Admin Room Added : "+room.getId());
        if(room.isTurnBased())
        {
            TurnBasedRoom turnRoom = (TurnBasedRoom)room;
            turnRoom.setAdaptor(new BotRoomAdaptor(turnRoom, zone));
        }
    }
    
    @Override
    public void handleCreateRoomRequest(IUser user, IRoom room, HandlingResult result)
    {
        System.out.println("Room Created : "+room.getId());
        if(room.isTurnBased())
        {
            TurnBasedRoom turnRoom = (TurnBasedRoom)room;
            turnRoom.setAdaptor(new BotRoomAdaptor(turnRoom, zone));
        }
    } 
}
