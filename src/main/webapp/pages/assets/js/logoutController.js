/**
 * Created by matan on 28/09/15.
 */



var app = angular.module('logoutApp', []);
app.controller('logoutController', ['$scope', '$sce', '$http', '$location', function ($scope, $sce, $http, $location) {
    $http.get('/app/logout').then(function success(response){
        $location.path('/');
        $location.replace();
    })
}]);