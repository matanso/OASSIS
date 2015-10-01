var app = angular.module('profileApp', []);
app.controller('profileController', ['$scope', '$sce', '$http', function ($scope, $sce, $http) {
    $scope.profile = {};
    $http.get('/app/getUserInfo').then(function success(response) {
        $scope.profile = response.data
    }, function error(response) {
    });
}]);