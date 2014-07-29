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
        otherwise({
            redirectTo: '/content/create'
        });
  }]);
