/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @author Konstantin Pozdeev
 * @version 0.1
 */

package org.opendaylight.defense4all.core.impl;

import org.opendaylight.defense4all.core.CounterStat;
import org.opendaylight.defense4all.core.TrafficTuple;

public class PNStatSum extends CounterStat {

	// PNStatSum is actually same counter without 'last reading' field
	// Location is hard-coded to 
	public static final String PN_SUM_LOCATION = "pn_summary";
	
	public PNStatSum(String pnKey) {
		super ( PN_SUM_LOCATION, pnKey);
		status = Status.ACTIVE;
		if (  latestRate == null) 
			latestRate = new TrafficTuple();
		if (  average == null) 
			average = new TrafficTuple();
		if (  latestRate == null) 
			latestRate = new TrafficTuple();
	}
	
	public void reset() {
		if ( average != null ) average.zero();
		if ( latestRate != null ) latestRate.zero();
		status = Status.ACTIVE;
	}

	public void add(CounterStat counterStat) {
		// counters from diverted path will be invalidated after end attack
		if (counterStat.status == Status.INVALID )
			return;
		
		if ( counterStat.status == Status.ACTIVE && status == Status.ACTIVE ) {
			status = Status.ACTIVE;
		} else {
			status = counterStat.status;
		}
	
		if(status == Status.WARMUP_PERIOD) {
			average.zero();
			return;
		}
		
		// summarize latest rate
		latestRate.add(counterStat.latestRate);
		
		// copy attacks status from incoming counter
		copyAttacked (counterStat );
	
		// update moving average of summary counter
		if ( status == Status.LEARNING_PERIOD || status == Status.ACTIVE )
			average.add(counterStat.average);
	}
}
