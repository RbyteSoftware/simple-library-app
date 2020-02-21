/*
Copyright (c) 2020 Mikhail Yuzbashev
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
import PropTypes from "prop-types";
import React, {Component} from "react";
import {Button, showNotification} from "react-admin";

let port = 8081;
let API_URL = window.location.protocol + '//' + window.location.hostname + ':' + port;

class ReleaseStateButton extends Component {
    state = {
        isRelease: this.props.record.bookRelease.isRelease
    };

    changeButtonState = () => {
        this.setState(!this.state.isRelease)
    };

    onClickAction = () => {
        const {resource} = this.props;
        // todo create body
        const request = new Request(API_URL + "/" + resource + "/events", {
            method: 'POST',
            body: {},
            headers: new Headers({
                'Content-Type': 'application/json',
                'Authenticate': 'Basic ' + localStorage.getItem('token')
            })
        });
        fetch(request).then(response => {
            if (response.status < 200 || response.status >= 300) {
                showNotification("Ошибка вызова API");
                throw new Error("Ошибка вызова API");
            }
            return response.json();
        }).then(({success, error}) => {
            if (success)
                this.changeButtonState();
            else
                showNotification(error);
        });
    };

    render() {
        const {isRelease} = this.state;
        return (
            <div>
                {!isRelease === false ? (
                    <Button label='Взять книжку' onClick={this.onClickAction}/>
                ) : (
                    <Button label='Отдать книжку' onClick={this.onClickAction}/>
                )}
            </div>
        )
    }
}

ReleaseStateButton.propTypes = {
    basePath: PropTypes.string,
    classes: PropTypes.object,
    className: PropTypes.string,
    record: PropTypes.object,
    resource: PropTypes.string.isRequired
};
export default (ReleaseStateButton);