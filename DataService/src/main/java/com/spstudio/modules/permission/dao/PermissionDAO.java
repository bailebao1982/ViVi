/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.modules.permission.dao;

import java.util.List;
import java.util.Map;

/**
 *
 * @author wewezhu
 */
public interface PermissionDAO {
    public boolean login(Map<String, Object> params);
}
