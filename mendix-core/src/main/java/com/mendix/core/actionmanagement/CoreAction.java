package com.mendix.core.actionmanagement;

import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import java.util.logging.Level;

import com.mendix.thirdparty.org.json.JSONException;
import com.mendix.thirdparty.org.json.JSONObject;
import com.newrelic.api.agent.Logger;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;

@Weave(type = MatchType.BaseClass)
public abstract class CoreAction<R> {

	public abstract String getActionName();
	public abstract JSONObject getMetaInfo();
	
	@NewField
	private static final Logger LOGGER = NewRelic.getAgent().getLogger();

    @Trace
	public R execute() throws Exception {
    	
    	LOGGER.log(Level.FINER, "INSIDE MENDIX CORE ACTION");
    	
    	if (getMetaInfo() != null) {
        	LOGGER.log(Level.FINER, "MENDIX CORE ACTION : Meta-info {0}", getMetaInfo().toString());
        	String type = "";
        	String subtype = "";
    		try {
            	type = getMetaInfo().getString("type");
            	LOGGER.log(Level.FINER, "MENDIX CORE ACTION : TYPE {0}", type);   		
            	JSONObject action = (JSONObject)getMetaInfo().get("action");
            	
        		if (action != null) {
        			subtype = action.getString("type");
                	LOGGER.log(Level.FINER, "MENDIX CORE ACTION : Action Sub-type {0}", subtype);    		    			
        		}
    		} catch (JSONException e) {
    			LOGGER.log(Level.FINER, "MENDIX CORE ACTION : Meta info type/subtype is null");
    			if (type == null) {
    				type = "";
    			}
    			if (subtype == null) {
    				subtype = "";
    			}
    		}

        	Transaction transaction = NewRelic.getAgent().getTransaction();
        	transaction.setTransactionName(TransactionNamePriority.CUSTOM_HIGH, false, "MendixCore", type, subtype);
    	}
    	
    	
		R result;
		try {
			result = Weaver.callOriginal();
		} catch (Exception e) {
			NewRelic.noticeError(e);
			throw e;
		}
		return result;
	}

}
