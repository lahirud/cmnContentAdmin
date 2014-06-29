'use strict';

/* Controllers */

var contentAppControllers = angular.module('contentAppControllers', ['ngTable']);


contentAppControllers.controller('ContentCreateCtrl', ['$scope','$http',
    function ($scope,$http) {
        $scope.isAllFieldsEntered = true;

        $scope.onInputChange = function (cnt) {
            
            if (cnt.title != null && cnt.fileId != null && cnt.title != '' && cnt.fileId != '' && cnt.subject != null && cnt.subject != '')
                $scope.isAllFieldsEntered = false;
            else
                $scope.isAllFieldsEntered = true;

        }

        $scope.cntFormSubmit = function (cnt) {
            $http({
                method: 'POST',
                url: 'MockHandlers/ContentHandler.ashx',
                data: { content: cnt }
            }).
                success(function (data, status, headers, config) {

                    if (data.cntResponse.trStatus == 'success') {
                        $scope.statusMessage = "Content Added successfully";
                    } else if (data.cntResponse.trStatus == 'error') {
                        $scope.statusMessage = "Failed to add content";
                    }
                }).
                error(function (data, status, headers, config) {
                    $scope.statusMessage = "Internal Error";
                });
        };

        $scope.reset = function () {
            $scope.cnt.title = '';
        }
    }
]);

contentAppControllers.controller('ContentEditCtrl', function ($scope, $http, $filter, ngTableParams) {

    $http.get('mockObjects/content.json').success(function (data_) {
        var data = data_;

        $scope.tableParams = new ngTableParams({
            page: 1,            // show first page
            count: 10,           // count per page
            sorting: {
                title: 'asc'     // initial sorting
            }
        }, {
            total: data.length, // length of data
            getData: function ($defer, params) {

                var filteredData = params.filter() ?
                    $filter('filter')(data, params.filter()) :
                    data;

                var orderedData = params.sorting() ?
                        $filter('orderBy')(filteredData, params.orderBy()) :
                        data;

                params.total(orderedData.length); // set total for recalc pagination
                $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            }
        });
    });

    $scope.orderProp = 'title';



});

contentAppControllers.controller('AccessCodeAdd', ['$scope', '$http',
    function ($scope, $http) {
        $scope.statusMessage = '';
        $scope.statusMessage.type = 'idle';
        $scope.tableData;
        $scope.isContentSelected = false;
        $scope.selectedContentId;
        $scope.selectedContentTitle;
        $scope.accessKey;
        $scope.isTableDisplay = false;

        $scope.cntSearchSubmit = function (searchTerm) {

            $scope.isTableDisplay = true;

            $http({
                method: 'POST',
                url: 'MockHandlers/MockHandlerGeneral.ashx',
                data: { searchTerm : searchTerm }
            }).
                success(function (data, status, headers, config) {

                    if (data.cntResponse.trStatus == 'success') {
                        $scope.statusMessage.type = 'success';
                        $scope.tableData = data.cntResponse.content;

                    } else if (data.cntResponse.trStatus == 'error') {
                        $scope.statusMessage = "Content not found.";
                        $scope.statusMessage.type = 'warn';
                    }
                }).
                error(function (data, status, headers, config) {
                    $scope.statusMessage = "Internal Error";
                });
        }

        $scope.isSearchDisabled = true;
        $scope.onSearchTermChange = function (searchTerm) {
            if (searchTerm != null && searchTerm != '') {
                $scope.isSearchDisabled = false;
            } else {
                $scope.isSearchDisabled = true;
            }
        }

        $scope.checkChange = function (contentId, contentTitle) {
            $scope.isContentSelected = true;
            $scope.selectedContentId = contentId;
            $scope.selectedContentTitle = contentTitle;
        }

        $scope.generateAccessCode = function () {
            $http({
                method: 'POST',
                url: 'MockHandlers/MockAccessKeyHandler.ashx',
                data: { contentId: $scope.contentId }
            }).
                success(function (data, status, headers, config) {

                    if (data.cntResponse.trStatus == 'success') {
                        $scope.accessKey = data.cntResponse.accessKey;
                        console.log(data.cntResponse.accessKey);

                    } else if (data.cntResponse.trStatus == 'error') {
                        $scope.statusMessage = "Key Generation Failed.";
                        $scope.statusMessage.type = 'warn';
                    }
                }).
                error(function (data, status, headers, config) {
                    $scope.statusMessage = "Internal Error";
                });
        }
    }
]);


contentAppControllers.run(function ($rootScope, $route) {
    $rootScope.$on("$routeChangeSuccess", function (currentRoute, previousRoute) {
        $rootScope.title = $route.current.title;
    });
});