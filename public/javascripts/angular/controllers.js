'use strict';

/* Controllers */

var contentAppControllers = angular.module('contentAppControllers', ['ngTable', 'angularFileUpload' ]);


contentAppControllers.controller('ContentCreateCtrl', ['$scope', '$http', '$upload',
    function ($scope, $http, $upload) {
        $scope.isAllFieldsEntered = true;

        $scope.onInputChange = function (cnt) {
            
            if (cnt.title != null && cnt.fileId != null && cnt.title != '' && cnt.fileId != '' && cnt.subject != null && cnt.subject != '')
                $scope.isAllFieldsEntered = false;
            else
                $scope.isAllFieldsEntered = true;

        }
        
        $scope.onFileSelect = function($files) {
            var data = {title: $scope.cnt.title, subject: $scope.cnt.subject, fileId: $scope.cnt.fileId};
            //$files: an array of files selected, each file has name, size, and type.
            for (var i = 0; i < $files.length; i++) {
              var file = $files[i];
              $scope.upload = $upload.upload({
                url: 'content/upload', //upload.php script, node.js route, or servlet url
                method: 'POST', // or 'PUT',
                //headers: {'header-key': 'header-value'},
                //withCredentials: true,
                data: data,
                file: file, // or list of files ($files) for html5 only
                fileName: data.fileId, // or ['1.jpg', '2.jpg', ...] // to modify the name of the file(s)
                // customize file formData name ('Content-Desposition'), server side file variable name. 
                //fileFormDataName: myFile, //or a list of names for multiple files (html5). Default is 'file' 
                // customize how data is added to formData. See #40#issuecomment-28612000 for sample code
                //formDataAppender: function(formData, key, val){}
              }).progress(function(evt) {
                console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
              }).success(function(data, status, headers, config) {
                // file is uploaded successfully
                console.log(data);
              });
              //.error(...)
              //.then(success, error, progress); 
              // access or attach event listeners to the underlying XMLHttpRequest.
              //.xhr(function(xhr){xhr.upload.addEventListener(...)})
            }
            /* alternative way of uploading, send the file binary with the file's content-type.
               Could be used to upload files to CouchDB, imgur, etc... html5 FileReader is needed. 
               It could also be used to monitor the progress of a normal http post/put request with large data*/
            // $scope.upload = $upload.http({...})  see 88#issuecomment-31366487 for sample code.
          };

        $scope.cntFormSubmit = function (cnt) {
            $http({
                method: 'POST',
                url: 'content/create',
                data: { content: cnt }
            }).
                success(function (data, status, headers, config) {

                    if (data.status == 'OK') {
                        $scope.statusMessage = "Content Added successfully";
                    } else if (data.status == 'NG') {
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

    $http.get('content/all').success(function (data_) {
        var data = data_.content;

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
                url: 'content/find',
                data: { searchTerm : searchTerm }
            }).
                success(function (data, status, headers, config) {

                    if (data.status == 'OK') {
                        $scope.statusMessage.type = 'success';
                        $scope.tableData = data.content;

                    } else if (data.status == 'NG') {
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
                url: 'content/generateAccessCode',
                data: { contentId: $scope.selectedContentId }
            }).
                success(function (data, status, headers, config) {

                    if (data.status == 'OK') {
                        $scope.accessKey = data.accessKey;

                    } else if (data.status == 'NG') {
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