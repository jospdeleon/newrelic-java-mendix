package com.mendix.webui.requesthandling;

import java.util.logging.Level;

import com.mendix.m2ee.api.IMxRuntimeRequest;
import com.mendix.m2ee.api.IMxRuntimeResponse;
import com.mendix.thirdparty.org.json.JSONObject;
import com.mendix.webui.jsonserialization.ResultSerializer;
import com.newrelic.api.agent.Logger;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public class ClientRequestHandler {

	@NewField
	private static final Logger LOGGER = NewRelic.getAgent().getLogger();

	@Trace
	private void handleAction(final IMxRuntimeRequest request, final IMxRuntimeResponse response, final ResultSerializer resultSerializer, final JSONObject jsonRequest, final String actionName) {

		LOGGER.log(Level.FINER, "MENDIX CLIENT HANDLER JSON - {0}", jsonRequest.toString());
		LOGGER.log(Level.FINER, "MENDIX CLIENT HANDLER ACTION - {0}", actionName);
		
		if (actionName != "") {
			String path = "";
			JSONObject params = (JSONObject)jsonRequest.get("params");
			Transaction transaction = NewRelic.getAgent().getTransaction();

			//Currently, further info is extracted only for retrieve and microflow actions
			if (actionName.startsWith("retrieve")) {
	    		if (actionName.endsWith("xpath")) {
	    			path = params.getString("xpath").substring(2); //remove the '//' in the value
	    			
	    		} else if (actionName.endsWith("ids")) {
	    			String value = params.getJSONArray("ids").toString();
	    			NewRelic.addCustomParameter("mendix.ids", value);
	    		}
	    	} else if (actionName.equalsIgnoreCase("executemicroflow")) {
	    		path = params.getString("name");
	    	}

	    	transaction.setTransactionName(TransactionNamePriority.CUSTOM_HIGH, true, "MendixAction", actionName, path);
		}

		Weaver.callOriginal();
	}
}
