/**
 * Created by matan on 28/09/15.
 */



var app = angular.module('homepageApp', []);
app.controller('loginController', ['$scope', '$sce', '$http', function ($scope, $sce, $http) {
    var loginButton = $sce.trustAsHtml('<img src="images/google_login.png" alt="Sign in with Google" style="PADDING-TOP: 12px; PADDING-BOTTOM: 12px">');
    var logoutButton = $sce.trustAsHtml('Logout');
    $scope.loggedIn = 0;

    $http.get('app/loggedIn').then(function success(response) {
        if(response.success == 0)
        {
            $scope.loggedIn = response.loggedIn;
        }
    }, function error(response) {
    });

    function loginFunc(credentials) {
        $http.post('app/login', credentials).then(function success(response) {
                var loginData = response.data;
                if (loginData.success == 0) {
                    $scope.loggedIn = 1;
                }
            },
            function error(response) {
            });
    }

    function logoutFunc() {
        $http.get('app/logout').then(function success(response) {
                var logoutData = response.data;
                if (logoutData.success == 0) {
                    $scope.loggedIn = 0;
                }
            },
            function error(response) {
            });
    }

    $scope.loginButtons = [loginButton, logoutButton];
    $scope.loginFuncs = [loginFunc, logoutFunc];
}]);