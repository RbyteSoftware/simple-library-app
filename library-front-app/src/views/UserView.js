import React from 'react';
import {Datagrid, List, TextField} from 'react-admin';
import PostBulkActionButtons from '../component/PostBulkActionButtons';
import DefaultListActions from "../component/DefaultListActions";


export const UserList = (props) => (
    <List {...props} perPage={5} title={'Пользователи'} sort={{field: 'login', order: 'ASC'}}
          actions={<DefaultListActions/>} bulkActionButtons={<PostBulkActionButtons/>}>
        <Datagrid>
            <TextField source="login" label={'Логин'}/>
            <TextField source="firstName" label={'Имя'}/>
            <TextField source="lastName" label={'Фамилия'}/>
            <TextField source="email" label={'Почта'}/>
        </Datagrid>
    </List>
);

export default UserList;