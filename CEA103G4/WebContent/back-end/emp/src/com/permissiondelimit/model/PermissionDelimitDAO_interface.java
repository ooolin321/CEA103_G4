package com.permissiondelimit.model;

import java.util.List;

public interface PermissionDelimitDAO_interface {
	public void insert(PermissionDelimitVO permissiondelimitVO);
	public void update(PermissionDelimitVO permissiondelimitVO);
	public void delete(String per_id);
	public PermissionDelimitVO findByPrimaryKey(String per_id);
	public List<PermissionDelimitVO> getAll();
	//uri來比對
	public PermissionDelimitVO findByUri(String per_uri);
}
