/**
 * Created by matan on 28/09/15.
 */



var app = angular.module('loginPageApp', []);

app.controller('loginPageController', ['$scope', '$sce', '$http', function ($scope, $sce, $http) {
    $scope.new = false;

    function login() {
        $http({
                url: '/app/login',
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                data: $.param({'loginType': 1, email: $scope.email, password: $scope.password})
            }
        ).then(function success(response) {
            console.log("success");
            },
            function error(response) {
                console.log("error");
            });
    }

    function register() {
        if ($scope.password != $scope.password2) {
            $scope.error = "Passwords do not match.";
            return;
        }
        $http({
                url: '/app/signup',
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                data: $.param({'loginType': 1, name: $scope.name, email: $scope.email, password: $scope.password})
            }
        ).then(function success(response) {
                if (response.success == 0) {
                    // TODO
                }
                else {
                    $scope.error = response.error || "An error occurred.";
                }
            },
            function error(response) {

            });
    }

    $scope.suggest = ["new user?", "login"];
    $scope.funcs = [login, register];
    $scope.texts = ["Login", "Sign up"];
    $scope.error = "";
}]);