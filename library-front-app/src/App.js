// in src/App.js
import React from 'react';
import {Admin, Resource} from 'react-admin';
import authProvider from "./authProvider";
import PostIcon from '@material-ui/icons/Book';
import {BooksView} from './views/BooksView.js'
import jsonServerProvider from 'ra-data-json-server';
import russianMessages from 'ra-language-russian';

const dataProvider = jsonServerProvider('http://jsonplaceholder.typicode.com');

const messages = {
    'ru': russianMessages,
};

const App = () => (
    <Admin dataProvider={dataProvider} authProvider={authProvider} messages={messages} title="Библиотека">
        <Resource name="users" title="Книги" list={BooksView} icon={PostIcon}/>
    </Admin>
);

export default App;