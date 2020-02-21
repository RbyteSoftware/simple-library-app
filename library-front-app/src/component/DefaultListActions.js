import {CreateButton, ExportButton, sanitizeListRestProps, TopToolbar} from "react-admin";
import React from "react";

const DefaultListActions = ({
                                currentSort,
                                className,
                                resource,
                                filters,
                                displayedFilters,
                                exporter, // you can hide ExportButton if exporter = (null || false)
                                filterValues,
                                permanentFilter,
                                hasCreate, // you can hide CreateButton if hasCreate = false
                                basePath,
                                selectedIds,
                                onUnselectItems,
                                showFilter,
                                maxResults,
                                total,
                                ...rest
                            }) => (
    <TopToolbar className={className} {...sanitizeListRestProps(rest)}>
        <CreateButton basePath={basePath}/>
        <ExportButton
            disabled={total === 0}
            resource={resource}
            sort={currentSort}
            filter={{...filterValues, ...permanentFilter}}
            exporter={exporter}
            maxResults={maxResults}
        />
    </TopToolbar>
);

export default DefaultListActions;