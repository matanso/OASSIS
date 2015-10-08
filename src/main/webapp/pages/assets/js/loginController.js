/**
 * Created by matan on 28/09/15.
 */



var app = angular.module('homepageApp', []);

app.controller('loginController', ['$scope', '$sce', '$http', function ($scope, $sce, $http) {
    $scope.loggedIn = 0;
    $http.get('/app/loggedIn').then(function success(response) {
        if(response.data.success == 0) {
            $scope.loggedIn = +response.data.loggedIn;
        }
    }, function error(response) {
    });

    function logoutFunc() {
        $http.get('/app/logout').then(function success(response) {
                var logoutData = response.data;
                if (logoutData.success == 0) {
                    $scope.loggedIn = 0;
                }
            },
            function error(response) {
            });
    }

    $scope.logout = logoutFunc;
}]);