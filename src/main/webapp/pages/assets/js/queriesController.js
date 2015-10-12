/**
 * Created by matan on 28/09/15.
 */



var app = angular.module('queriesApp', []);
app.controller('queriesController', ['$scope', '$sce', '$http', function ($scope, $sce, $http) {
    $scope.queries = [];
    $http.get('/app/getQueries').then(function success(response){$scope.queries=response.data}, function error(response){});
}]);