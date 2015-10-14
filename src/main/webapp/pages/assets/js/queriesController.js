/**
 * Created by matan on 28/09/15.
 */



var app = angular.module('queriesApp', []);
app.controller('queriesController', ['$scope', '$sce', '$http', function ($scope, $sce, $http) {
    $scope.queries = [];
    $http.get('/app/getQueries').then(function success(response) {
        $scope.queries = response.data.queries
    }, function error(response) {
    });
    $scope.view_query = function (query_id) {
        sessionStorage.setItem("queryId", query_id);
        window.location.href = 'viewQuery.html';
    };
}]);