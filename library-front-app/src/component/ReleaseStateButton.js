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
import {connect} from 'react-redux';
import {Button, showNotification} from "react-admin";

let port = 8081;
let API_URL = window.location.protocol + '//' + window.location.hostname + ':' + port;

class ReleaseStateButton extends Component {
    // hardcode events
    eventArray = ['TAKE_BOOK', 'RELEASE_BOOK'];
    buttonLabels = ['Взять книжку', 'Отдать книжку'];
    state = {
        isOwner: this.props.record.bookRelease.isOwner,
        isRelease: this.props.record.bookRelease.isRelease
    };

    changeButtonState = () => {
        const changeState = !this.state.isRelease;
        this.setState({isRelease: changeState, isOwner: true})
    };

    onClickAction = () => {
        const {resource, record} = this.props;
        const event = this.eventArray[this.state.isRelease ? 0 : 1];
        const request = new Request(API_URL + "/" + resource + "/events", {
            method: 'POST',
            body: JSON.stringify({'bookEvent': event, 'bookId': record.id}),
            headers: new Headers({
                'Content-Type': 'application/json',
                'Authorization': 'Basic ' + localStorage.getItem('token')
            })
        });
        fetch(request).then(response => {
            if (response.status < 200 || response.status >= 300) {
                return JSON.stringify({'success': false, 'error': 'Ошибка вызова API'})
            }
            return response.json();
        }).then(({success, error}) => {
            if (success) {
                this.changeButtonState();
                this.props.showNotification(!this.state.isRelease ? 'Книжка взята!' : 'Книга возвращена!');
            } else
                this.props.showNotification(error);
        });
    };

    render() {
        const {isRelease, isOwner} = this.state;
        console.log(this.state);
        const disable = !isRelease && !isOwner;
        const currentLabel = this.buttonLabels[isRelease ? 0 : 1];
        return (
            <Button label={currentLabel} onClick={this.onClickAction} disabled={disable}/>
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
export default connect(null, {showNotification})(ReleaseStateButton);