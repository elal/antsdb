/*-------------------------------------------------------------------------------------------------
 _______ __   _ _______ _______ ______  ______
 |_____| | \  |    |    |______ |     \ |_____]
 |     | |  \_|    |    ______| |_____/ |_____]

 Copyright (c) 2016, antsdb.com and/or its affiliates. All rights reserved. *-xguo0<@

 This program is free software: you can redistribute it and/or modify it under the terms of the
 GNU GNU Lesser General Public License, version 3, as published by the Free Software Foundation.

 You should have received a copy of the GNU Affero General Public License along with this program.
 If not, see <https://www.gnu.org/licenses/lgpl-3.0.en.html>
-------------------------------------------------------------------------------------------------*/
package com.antsdb.saltedfish.sql.vdm;

import com.antsdb.saltedfish.cpp.Heap;
import com.antsdb.saltedfish.cpp.Int4;
import com.antsdb.saltedfish.sql.DataType;

/**
 * 
 * @author *-xguo0<@
 */
public class FuncReleaseLock extends Function {

    @Override
    public int getMinParameters() {
        return 1;
    }

    @Override
    public DataType getReturnType() {
        return DataType.integer();
    }

    @Override
    public long eval(VdmContext ctx, Heap heap, Parameters params, long pRecord) {
        long pLockName = this.parameters.get(0).eval(ctx, heap, params, pRecord);
        if (pLockName == 0) {
            return 0;
        }
        String lockName = AutoCaster.getString(heap, pLockName);
        Integer result = FuncGetLock.unlock(lockName, ctx.getSession().getId());
        return result==null ? 0 : Int4.allocSet(heap, result);
    }

}
