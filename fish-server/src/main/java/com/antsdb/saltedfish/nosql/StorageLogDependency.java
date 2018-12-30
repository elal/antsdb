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
package com.antsdb.saltedfish.nosql;

import java.util.List;

/**
 * 
 * @author *-xguo0<@
 */
class StorageLogDependency implements LogDependency {

    private Humpback humpback;

    public StorageLogDependency(Humpback humpback) {
        this.humpback = humpback;
    }

    @Override
    public String getName() {
        return "storage";
    }

    @Override
    public List<LogDependency> getChildren() {
        return null;
    }

    @Override
    public long getLogPointer() {
        return this.humpback.getStorageEngine().getLogSpan().y;
    }

}
