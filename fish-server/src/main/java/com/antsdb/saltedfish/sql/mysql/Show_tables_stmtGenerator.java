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
package com.antsdb.saltedfish.sql.mysql;

import com.antsdb.saltedfish.lexer.MysqlParser.Show_tables_stmtContext;
import com.antsdb.saltedfish.sql.Generator;
import com.antsdb.saltedfish.sql.GeneratorContext;
import com.antsdb.saltedfish.sql.OrcaException;
import com.antsdb.saltedfish.sql.planner.Planner;
import com.antsdb.saltedfish.sql.vdm.CursorMaker;
import com.antsdb.saltedfish.sql.vdm.Filter;
import com.antsdb.saltedfish.sql.vdm.Instruction;
import com.antsdb.saltedfish.sql.vdm.Operator;

public class Show_tables_stmtGenerator extends Generator<Show_tables_stmtContext>{

    @Override
    public Instruction gen(GeneratorContext ctx, Show_tables_stmtContext rule) throws OrcaException {
        boolean isFull = (rule.K_FULL() != null);
        String ns = (rule.identifier() != null) ? Utils.getIdentifier(rule.identifier()) : null;
        if (ns == null) {
            ns = ctx.getSession().getCurrentNamespace();
        }
        if (ns == null) {
            throw new OrcaException("no database selected");
        }
        String like = Utils.getLiteralValue(rule.string_value());
        CursorMaker result = new ShowTables(ctx.getSession(), ns, isFull, like);
        if (rule.where_clause() != null) {
            Planner planner = new Planner(ctx);
            planner.addTableOrView("", result, true, false);
            Operator where = ExprGenerator.gen(ctx, planner, rule.where_clause().expr());
            result = new Filter(result, where, ctx.getNextMakerId());
        }
        return result;
    }

}
