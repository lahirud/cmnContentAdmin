'use strict';

/* Controllers */
var phonecatApp = angular.module('phonecatApp', []);

phonecatApp.controller('PhoneListCtrl', function ($scope, $http) {
  $http.get('../assets/data/phones.json').success(function(data) {
    $scope.phones = data;
  });

  $scope.orderProp = 'age';
  
  $scope.savePhone = function(){
      var formData = $scope.form;
      console.log(JSON.stringify(formData));
  };
  
});