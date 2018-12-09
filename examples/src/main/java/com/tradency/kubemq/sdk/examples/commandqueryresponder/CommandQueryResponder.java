package com.tradency.kubemq.sdk.examples.commandqueryresponder;

import com.tradency.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import com.tradency.kubemq.sdk.commandquery.RequestReceive;
import com.tradency.kubemq.sdk.commandquery.Responder;
import com.tradency.kubemq.sdk.commandquery.Response;
import com.tradency.kubemq.sdk.examples.commonexample.BaseExample;
import com.tradency.kubemq.sdk.subscription.EventsStoreType;
import com.tradency.kubemq.sdk.subscription.SubscribeRequest;
import com.tradency.kubemq.sdk.subscription.SubscribeType;

import javax.net.ssl.SSLException;
import java.text.MessageFormat;

class CommandQueryResponder extends BaseExample {

    private Responder.RequestResponseObserver HandleIncomingRequests;
    private Responder responder;

    CommandQueryResponder() throws ServerAddressNotSuppliedException, SSLException {
        super("CommandQueryResponder");
        responder = new Responder();
        // todo fix example - reimplement as in the C#
        HandleIncomingRequests = requestReceive -> {
            System.out.println(MessageFormat.format(
                    "Subscriber Received Event: Metadata:''{0}'', Channel:''{1}''",
                    requestReceive.getMetadata(),
                    requestReceive.getChannel()
            ));

            Response response = new Response(requestReceive);
            response.setBody("OK".getBytes());
            response.setMetadata("Received Request");
            response.setClientID("Response Return");
            return response;
        };
        CreateSubscribeToQueries();
        CreateSubscribeToCommands();
    }

    private void CreateSubscribeToCommands() throws ServerAddressNotSuppliedException, SSLException {
        SubscribeRequest subscribeRequest = CreateSubscribeRequest(SubscribeType.Queries);
        responder.SubscribeToRequests(subscribeRequest, HandleIncomingRequests);
    }

    private void CreateSubscribeToQueries() throws ServerAddressNotSuppliedException, SSLException {
        SubscribeRequest subscribeRequest = CreateSubscribeRequest(SubscribeType.Commands);
        responder.SubscribeToRequests(subscribeRequest, HandleIncomingRequests);
    }

}
