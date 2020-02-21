import React from "react";
import {Datagrid, List, TextField} from 'react-admin';
import PostBulkActionButtons from '../component/PostBulkActionButtons';
import DefaultListActions from "../component/DefaultListActions";
import ReleaseStateButton from "../component/ReleaseStateButton";


// const ReleaseStateButton = ({record}) => {
//     const isRelease = record.bookRelease.isRelease;
//     return (
//         <div>
//         { !isRelease === false ? (
//             <Button label='Взять книжку'/>
//         ):(
//             <Button label='Отдать книжку'/>
//         ) }
//         </div>
//     )
// }

export const BooksView = (props) => (
    <List {...props} perPage={5} title={'Книги'} sort={{field: 'isbNumber', order: 'ASC'}}
          actions={<DefaultListActions/>} bulkActionButtons={<PostBulkActionButtons/>}>
        <Datagrid>
            <TextField source="isbNumber" label={'ISBN'}/>
            <TextField source="title" label={'Название'}/>
            <TextField source="author" label={'Автор'}/>
            <ReleaseStateButton/>
        </Datagrid>
    </List>
);
//          { bookRelease.isRelease === true ? <Button /> : <TextField source="author" label={'Автор'}/> }
export default BooksView;