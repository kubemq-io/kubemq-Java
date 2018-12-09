package com.tradency.kubemq.sdk.examples.commandqueryinitiator;

import com.tradency.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import com.tradency.kubemq.sdk.commandquery.RequestType;
import com.tradency.kubemq.sdk.commandquery.Response;
import com.tradency.kubemq.sdk.commandquery.lowlevel.Initiator;
import com.tradency.kubemq.sdk.examples.commonexample.BaseExample;

import javax.net.ssl.SSLException;

class CommandQueryInitiator extends BaseExample {

    CommandQueryInitiator() throws ServerAddressNotSuppliedException, SSLException {
        super("CommandQueryInitiator");
        SendLowLevelRequest();
    }

    private void SendLowLevelRequest() throws ServerAddressNotSuppliedException, SSLException {
        Initiator initiator = new Initiator();
        Response response = initiator.SendRequest(CreateLowLevelRequest(RequestType.Query));
        initiator.SendRequest(CreateLowLevelRequest(RequestType.Command));
    }
}
