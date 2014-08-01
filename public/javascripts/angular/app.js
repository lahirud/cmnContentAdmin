'use strict';

/* App Module */

var contentApp = angular.module('contentApp', [
  'ngRoute',
  'contentAppAnimations',
  'contentAppControllers',
  'contentAppFilters',
  'contentAppServices'
]);

contentApp.config(['$routeProvider',
  function ($routeProvider) {
      $routeProvider.
        when('/content/create', {
            templateUrl: 'partials/create-content.html',
            controller: 'ContentCreateCtrl',
            title: 'Create Content'
        }).
        when('/content/edit', {
              templateUrl: 'partials/edit-content.html',
              controller: 'ContentEditCtrl',
              title: 'Edit Content'
        }).
        when('/content/add-code', {
            templateUrl: 'partials/add-access-code.html',
            controller: 'AccessCodeAdd',
            title: 'Add Accesss Code'
        }).
        when('/login', {
            templateUrl: 'partials/login.html',
            controller: 'loginController',
            title: 'Login'
        }).
        when('/account/manageusers', {
            templateUrl: 'partials/manage-users.html',
            controller: 'manageUsersController',
            title: 'Manage Users'
        }).
        otherwise({
            redirectTo: '/login'
        });
  }]);
