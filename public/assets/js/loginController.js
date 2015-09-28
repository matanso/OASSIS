/**
 * Created by matan on 28/09/15.
 */

function loginFunc(){

    return 1;  // Logged in
}

function logoutFunc(){
    console.log("Logout!");
    return 0; // Not logged in
}

var app = angular.module('homepageApp', []);
app.controller('loginController', [ '$scope', '$sce', '$http', function($scope, $sce, $http){
    var loginButton  = $sce.trustAsHtml('<img src="images/google_login.png" alt="Sign in with Google" style="PADDING-TOP: 12px; PADDING-BOTTOM: 12px">');
    var logoutButton = $sce.trustAsHtml('Logout');
    $scope.loginButtons = [loginButton, logoutButton];
    $scope.loginFuncs = [loginFunc, logoutFunc];
    $scope.loggedIn = 0;
}]);