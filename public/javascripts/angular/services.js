'use strict';

/* Services */

var contentAppServices = angular.module('contentAppServices', ['ngResource', 'ngCookies']);

//contentAppServices.factory('Phone', ['$resource',
//  function($resource){
//    return $resource('phones/:phoneId.json', {}, {
//      query: {method:'GET', params:{phoneId:'phones'}, isArray:true}
//    });
//  }]);

contentAppServices.factory('AuthService', function ($http, Session, $cookieStore) {
    return {
        login: function (credentials) {
            console.log(JSON.stringify(credentials));
            return $http
                .post('/user/login', credentials)
//                .success(function (data, status, headers, config) {
//                    console.log("got response");
//                });
                .then(function (res) {
                    console.log(JSON.stringify(res));
//                    Session.create(res.data.auth_token, res.data.username, res.data.username);
//                    $cookieStore.put('Session_Cookie', Session);
                });
        },
        logout: function () {
            return $http
                .post('/user/logout')
                .then(function (res) {
                    $cookieStore.remove('Session_Cookie');
                });
        }

    }
});

contentAppServices.service('Session', function () {
    this.create = function (auth_token, userid, username) {
        this.auth_token = auth_token;
        this.userid = userid;
        this.username = username;
    };
    this.destroy = function () {
        this.auth_token = null;
        this.userid = null;
        this.username = null;
    };
    return this;
})
