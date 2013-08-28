package com.github.syqnew.dao.impl;

import com.github.syqnew.dao.MetadataDao;
import com.github.syqnew.domain.Metadata;

public class MetadataDaoImpl extends BaseDaoImpl<Metadata> implements MetadataDao {
	
	public MetadataDaoImpl() {
		super(Metadata.class);
	}

}
