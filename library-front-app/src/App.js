// in src/App.js
import React from 'react';
import {Admin, Resource} from 'react-admin';
import authProvider from "./authProvider";
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
//import PostIcon from '@material-ui/icons/Book';
import UserView from './views/UserView.js'
import polyglotI18nProvider from 'ra-i18n-polyglot';
import restDataProvider from './api/restDataProvider.js'
import russianMessages from 'ra-language-russian';

const i18nProvider = polyglotI18nProvider(() => russianMessages, 'ru');

const App = () => (
    <Admin dataProvider={restDataProvider} authProvider={authProvider} i18nProvider={i18nProvider}>
        <Resource name="users" list={UserView} icon={AccountCircleIcon} options={{label: 'Пользователи'}}/>
    </Admin>
);

export default App;