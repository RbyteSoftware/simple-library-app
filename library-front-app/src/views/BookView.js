import React from "react";
import {Datagrid, List, TextField} from 'react-admin';
import PostBulkActionButtons from '../component/PostBulkActionButtons';
import DefaultListActions from "../component/DefaultListActions";
import ReleaseStateButton from "../component/ReleaseStateButton";

export const BooksView = (props) => (
    <List {...props} perPage={5} title={'Книги'} sort={{field: 'isbNumber', order: 'ASC'}}
          actions={<DefaultListActions/>} bulkActionButtons={<PostBulkActionButtons/>}>
        <Datagrid>
            <TextField source="isbNumber" label={'ISBN'}/>
            <TextField source="title" label={'Название'}/>
            <TextField source="author" label={'Автор'}/>
            <ReleaseStateButton resource='books'/>
        </Datagrid>
    </List>
);
export default BooksView;