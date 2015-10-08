/**
 * Created by matan on 28/09/15.
 */



var app = angular.module('loginPageApp', []);

app.controller('loginPageController', ['$scope', '$sce', '$http', function ($scope, $sce, $http) {
    $scope.new = false;

    function login() {
        $http({url: '/app/login',
        method: "POST",
        headers:  {'Content-Type': 'application/x-www-form-urlencoded'},
                data: {'loginType': 1, email: $scope.email, password: $scope.password}}
        ).then(function success(response) {

            },
            function error(response) {
            });
    }
    function register() {
        $http({url: '/app/signup',
                method: "POST",
                headers:  {'Content-Type': 'application/x-www-form-urlencoded'},
                data: {'loginType': 1, name: $scope.name, email: $scope.email, password: $scope.password}}
        ).then(function success(response) {

            },
            function error(response) {
            });
    }
    $scope.suggest = ["new user?", "login"];
    $scope.funcs = [login, register];
    $scope.texts = ["Login", "Sign up"];
    $scope.error = "Password is incorrect";
}]);