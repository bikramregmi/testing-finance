package com.mobilebanking.network;

/**
 * Created by manohar-td-003 on 6/2/17.
 */
public class NodeProtocol {

    public String processServerMessage(String message) {
        String reply = null;
        if(message.equals("Success")){
            reply="End";
        }else{
            reply="End";
        }

        return reply;
    }

    public String processClientMessage(String message) {
        String reply = null;
        if(message.contains("pacs")){

            reply="Success";
        }else{
            reply="End";
        }

        return reply;
    }

}

