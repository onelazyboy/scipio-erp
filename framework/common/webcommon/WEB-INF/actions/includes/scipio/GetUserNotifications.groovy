import org.ofbiz.base.util.*;


final module = "GetUserNotifications.groovy"

if(context.userLogin!=null){
    inMap = [:];
    inMap.toPartyId = context.userLogin.partyId;
    result = runService("getSystemMessages", inMap);
    context.systemNotifications = result.messages !=null ? result.messages : [];
    context.systemNotificationsCount = result.count;
}