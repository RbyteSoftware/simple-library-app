import React, {Fragment} from 'react';
import {CreateButton, Datagrid, ExportButton, List, sanitizeListRestProps, TextField, TopToolbar} from 'react-admin';
import DeleteButtonWithConfirmation from "../component/DeleteButtonWithConfirmation";

const PostBulkActionButtons = props => (
    <Fragment>
        <DeleteButtonWithConfirmation {...props} />
    </Fragment>
);
const ListActions = ({
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


export const UserList = (props) => (
    <List {...props} perPage={5} title={'Пользователи'} sort={{field: 'login', order: 'ASC'}}
          actions={<ListActions/>} bulkActionButtons={<PostBulkActionButtons/>}>
        <Datagrid>
            <TextField source="login" label={'Логин'}/>
            <TextField source="firstName" label={'Имя'}/>
            <TextField source="lastName" label={'Фамилия'}/>
            <TextField source="email" label={'Почта'}/>
        </Datagrid>
    </List>
);

export default UserList;