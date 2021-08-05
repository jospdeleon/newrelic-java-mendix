package com.mendix.systemwideinterfaces.core;

import java.util.HashMap;
import java.util.Map;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.BaseClass)
public abstract class UserAction<R> {

	@Trace
	public R executeAction() throws Exception {
		R result;
		try {
			result = Weaver.callOriginal();
		} catch (Exception e) {
			Map<String,String> customAttribs = new HashMap<String, String>();
			customAttribs.put("mendix.class", this.getClass().getSimpleName());
			
			NewRelic.noticeError(e, customAttribs);
			throw e;
		}
		return result;
	}
}
