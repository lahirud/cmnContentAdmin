'use strict';

/* Controllers */
var phonecatApp = angular.module('phonecatApp', []);

phonecatApp.controller('PhoneListCtrl', function ($scope, $http) {
//  $http.get('../assets/data/phones.json').success(function(data) {
//    $scope.phones = data;
//  });
    
    $http.get('/api/phones').success(function(data){
        $scope.phones = data;
    });

  $scope.orderProp = 'age';
  
  $scope.savePhone = function(phone){
      //console.log(JSON.stringify(phone));
      $http({ url: '/api/phones/save',
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          data : phone}).success(function(data1){
              $scope.phones = data1;
          //$scope.$apply(function() { $location.path("/create/"); });
      });
  };
  
});