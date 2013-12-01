package com.github.syqnew.dao.impl;

import com.github.syqnew.dao.MetadataDao;
import com.github.syqnew.domain.Metadata;

/**
 * Implementation of MetadataDao interface
 * 
 * @author snew
 *
 */
public class MetadataDaoImpl extends BaseDaoImpl<Metadata> implements MetadataDao {
	
	/**
	 * Creates a MetadataDaoImpl object
	 */
	public MetadataDaoImpl() {
		super(Metadata.class);
	}

}
