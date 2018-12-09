package io.kubemq.sdk.examples.commandqueryinitiator;

import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.commandquery.RequestType;
import io.kubemq.sdk.commandquery.Response;
import io.kubemq.sdk.commandquery.lowlevel.Initiator;
import io.kubemq.sdk.examples.commonexample.BaseExample;

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
