/**
 * Created by matan on 12/10/15.
 */

var app = angular.module('homepageApp');
app.controller('NavBarController', ['$scope', '$sce', '$http', function ($scope, $sce, $http) {
    $scope.loggedIn = false;

    $http.get('/app/loggedIn').then(function success(response) {
        if(response.data.success == 0) {
            $scope.loggedIn = response.data.loggedIn;
        }
    }, function error(response) {
    });
}]);