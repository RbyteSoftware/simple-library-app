// src/authProvider.js
import React from "react";

let port = 8081;
let API_URL = window.location.protocol + '//' + window.location.hostname + ':' + port;

const authProvider = {
    login: ({username, password}) => {
        console.log(API_URL);
        const request = new Request(API_URL + '/authenticate', {
            method: 'POST',
            body: JSON.stringify({username, password}),
            headers: new Headers({'Content-Type': 'application/json'})
        });
        return fetch(request)
            .then(response => {
                if (response.status < 200 || response.status >= 300)
                    throw new Error(response.statusText);
                return response.json();
            }).then(({token}) => localStorage.setItem('token', token))

    },
    logout: params => {
        localStorage.removeItem('token');
        return Promise.resolve()
    },
    checkAuth: params => localStorage.getItem('token') ? Promise.resolve() : Promise.reject(),
    checkError: error => {
        if (error === 401 || error === 403) {
            localStorage.removeItem('token');
        }
        return Promise.reject();
    },
    getPermissions: params => Promise.resolve(),
};

export default authProvider;