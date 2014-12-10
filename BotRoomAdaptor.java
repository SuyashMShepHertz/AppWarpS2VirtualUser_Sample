/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package botsample;

import com.shephertz.app42.server.domain.VirtualUser;
import com.shephertz.app42.server.domain.Room;
import com.shephertz.app42.server.domain.Zone;
import com.shephertz.app42.server.idomain.BaseTurnRoomAdaptor;
import com.shephertz.app42.server.idomain.HandlingResult;
import com.shephertz.app42.server.idomain.ITurnBasedRoom;
import com.shephertz.app42.server.idomain.IUser;
import com.shephertz.app42.server.idomain.IZone;
import java.util.List;

/**
 *
 * @author Suyash
 */
public class BotRoomAdaptor extends BaseTurnRoomAdaptor{
    
    ITurnBasedRoom room;
    IZone zone;
    int userCount = 0;
    int ticksAfterLastUser;
    int waitTime = 30 * 1000;//30 Seconds or 30*1000 milliseconds
    int maxUser = 4;
    boolean gameStarted = false;
    
    BotRoomAdaptor(ITurnBasedRoom _room, IZone _zone)
    {
        room = _room;
        zone = _zone;
    }
    
    @Override
    public void handleUserJoinRequest(IUser user, HandlingResult result){
        System.out.println("handleUserJoinRequest : "+user.getName());
        System.out.println("Usre Count : " + userCount);
        userCount += 1;
        if(gameStarted == false)
            ticksAfterLastUser = 0;
    }
    
    @Override
    public void onUserLeaveRequest(IUser user){
        System.out.println("onUserLeaveRequest : "+user.getName());
        System.out.println("Usre Count : " + userCount);
        userCount -= 1;
    }
    
    @Override
    public void handleMoveRequest(IUser sender, String moveData, HandlingResult result)
    {
        System.out.println("Move Completed. Sender : "+sender.getName()+" moveData : "+moveData);
    }
    
    @Override
    public void handleStartGameRequest(IUser sender, HandlingResult result)
    {
        //Fail the request to start the game because we will start it from server
        result.code = 4;
    }
    
    @Override
    public void onTimerTick(long time) {
        if(gameStarted == false)
        {
            ticksAfterLastUser += time; 
            if(ticksAfterLastUser > waitTime)
            {
                if(userCount > 0)
                {
                    for(int i=0; i<maxUser-userCount; ++i)
                    {
                        IUser user = (IUser) new VirtualUser("Bot"+i, (Room)room, (Zone)zone); 
                        room.addUser(user, true);
                        System.out.println("Adding Bot User : "+user.getName());
                    }
                    
                    gameStarted = true;
                    room.startGame(room.getJoinedUsers().get(0).getName());
                }
                else
                    ticksAfterLastUser = 0;
            }
        }
        else
        {
            if(room.getTurnUser().getName().contains("Bot"))
            {
                room.sendMoveUpdate(getNextTurn(room.getTurnUser().getName()), "Hello", room.getTurnUser().getName());
            }
        }
    }
    
    IUser getNextTurn(String lastSender){
        List<IUser> users=room.getJoinedUsers();
        if(users.size()>0){
            if(lastSender.length()==0){
                return users.get(0);
            }else{
                for (IUser user: users){
                    if(user.getName().equals(lastSender)){
                        int nextIndex = users.indexOf(user)+1;
                        if(nextIndex<users.size()){
                            return users.get(nextIndex);
                        }else{
                            return users.get(0);
                        }
                    }
                }
            }
        }
        return null;
    }
}
