package com.mendix.externalinterface.connector;

import java.util.logging.Level;

import com.mendix.m2ee.api.IMxRuntimeRequest;
import com.mendix.m2ee.api.IMxRuntimeResponse;
import com.newrelic.api.agent.Logger;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.BaseClass)
public class RequestHandler {

	@NewField
	private static final Logger LOGGER = NewRelic.getAgent().getLogger();

	@Trace
	protected void processRequest(IMxRuntimeRequest var1, IMxRuntimeResponse var2, String var3) throws Exception {

		String handler = this.getClass().getSimpleName();
		LOGGER.log(Level.FINER, "MENDIX REQUEST HANDLER - {0}", handler);
		if (handler != "") {
			Transaction transaction = NewRelic.getAgent().getTransaction();
	    	transaction.setTransactionName(TransactionNamePriority.CUSTOM_HIGH, true, "MendixHandler", handler);
		}

		if (var1.getResourcePath() != "") {
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("mendix.resourcePath", var1.getResourcePath());
		}

		try {
			Weaver.callOriginal();
		} catch (Exception e) {
			NewRelic.noticeError(e);
			throw e;
		}

	}

}
